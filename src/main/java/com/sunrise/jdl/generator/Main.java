package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.entities.Entity;
import com.sunrise.jdl.generator.entities.Field;
import com.sunrise.jdl.generator.entities.FieldBuilder;
import com.sunrise.jdl.generator.entities.ResultWithWarnings;
import com.sunrise.jdl.generator.service.EntitiesService;
import com.sunrise.jdl.generator.service.EntityTypeService;
import com.sunrise.jdl.generator.service.Settings;
import com.sunrise.jdl.generator.service.iad.UIGeneratorService;
import com.sunrise.jdl.generator.ui.UIGenerateParameters;
import org.apache.commons.cli.*;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;


public class Main {

    private static final String GID_ENTITIES = "entities";
    private static final String GID_RELATIONS = "relations";
    private static final String GID_ACTIONS = "actions";
    private static final String GID_CONFIG_FILE = "gidConfigFile";
    private static final String TARGET_RESOURCE_FOLDER = "targetResourceFolder";
    private static EntityTypeService entityTypeService = new EntityTypeService();

    public static void main(String[] args) throws IOException {

        Options options = new Options();
        options.addOption(GID_ENTITIES, true, "path to the 'entities' csv file");
        options.addOption(GID_RELATIONS, true, "path to the 'relations' csv file");
        options.addOption(GID_ACTIONS, true, "path to the 'actions' csv file");
        options.addOption(GID_CONFIG_FILE, true, "gid config file path");
        options.addOption(TARGET_RESOURCE_FOLDER, true, "set path where resource files will be generated");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            return;
        }
        UIGeneratorService generatorService = new UIGeneratorService();

        File entitiesFile = new File(cmd.getOptionValue(GID_ENTITIES));
        if (!entitiesFile.isFile()) throw new FileNotFoundException(entitiesFile.getAbsolutePath());

        File relationsFile = new File(cmd.getOptionValue(GID_RELATIONS));
        if (!relationsFile.isFile()) throw new FileNotFoundException(relationsFile.getAbsolutePath());

        File actionsFile = new File(cmd.getOptionValue(GID_ACTIONS));
        if (!actionsFile.isFile()) throw new FileNotFoundException(actionsFile.getAbsolutePath());


        UIGenerateParameters parameters = generatorService.loadConfig(cmd.getOptionValue(GID_CONFIG_FILE));
        EntitiesService entitiesService = new EntitiesService(new Settings());
        Collection<Entity> entities = entitiesService.readDataFromCSV(new FileInputStream(entitiesFile));
        for (FieldBuilder fb : parameters.getAdditionalFields())
            entities.forEach((x) -> x.getFields().add(fb.build()));
        Map<String, List<String>> relations = entityTypeService.readCsv(new FileInputStream(relationsFile));
        ResultWithWarnings<Map<String, List<Entity>>> entitiesHierarchy = entityTypeService.mergeTypesWithThemSubtypes(entities, relations);
        entitiesHierarchy.warnings.forEach(x -> System.out.println("WARNING: " + x));
        Map<String, Set<Field>> baseDataWithBaseFields = entityTypeService.prepareDataForParentEntity(entitiesHierarchy.result);
        File file = new File(cmd.getOptionValue(GID_ACTIONS));
        InputStream actionsStream = new FileInputStream(file);

        entities.forEach(x -> {
            Map<String, Object> data = new HashMap<>();
            List<Object> references = new ArrayList<>();
            data.put("References", references);
            x.getFields()
                    .stream()
                    .filter(f -> f.getFieldType().equalsIgnoreCase("Entity")
                            || f.getFieldType().equalsIgnoreCase("List")
                            && !f.getFieldName().equalsIgnoreCase("additionalDocuments"))
                    .forEach(f -> references.add(new Referense(f.getFieldName(), null)));
            Representer representer = new Representer();
            representer.addClassTag(Referense.class, Tag.MAP);
            Yaml yaml = new Yaml(representer);
            FileWriter writer = null;
            try {
                writer = new FileWriter(cmd.getOptionValue(TARGET_RESOURCE_FOLDER) + x.getClassName() + ".yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            yaml.dump(data, writer);
        });

    }

}

class Referense {
    private String className;
    private String indexName;

    public Referense(String className, String indexName) {
        this.className = className;
        this.indexName = indexName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
