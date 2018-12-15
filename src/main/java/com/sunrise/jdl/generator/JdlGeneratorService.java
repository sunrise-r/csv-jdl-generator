package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.config.JdlConfig;
import com.sunrise.jdl.generator.service.jdl.JdlGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Generate JDL and resources
 */
public class JdlGeneratorService {

    private final JdlConfig jdlConfig;
    private final JdlGenerator jdlGenerator;

    public JdlGeneratorService(JdlConfig jdlConfig) throws IOException {

        this.jdlConfig = jdlConfig;
        jdlGenerator = new JdlGenerator();
    }

    public void generate() throws IOException, TemplateException {

        jdlGenerator.generateJdl(jdlConfig);


       /* final Settings settings = new Settings();
        settings.getEntitiesToIngore().addAll(jdlConfig.getIgnoreEntities());
        settings.getFieldsToIngore().addAll(jdlConfig.getIgnoreFields());
        settings.setPaginationType(PaginationType.fromString(jdlConfig.getPaginateType()));
        settings.setUseMapStruct(jdlConfig.isMapStruct());
        settings.setGenerateServiciesFor(jdlConfig.getGenerateServiceFor().stream().collect(Collectors.joining(",")));
        settings.setExceptServiceGenerationFor(jdlConfig.getExceptServiceGeneration().stream().collect(Collectors.joining(",")));
        settings.setMicroserviceName(jdlConfig.getMicroservice());
        EntitiesService entitiesService;
        if (jdlConfig.getSourceFolder() != null) {
            entitiesService = new EntitiesService(settings);
            File directory = new File(jdlConfig.getSourceFolder());
            File[] files = directory.listFiles();
            List<InputStream> resources = new ArrayList<InputStream>(files.length);
            for (File f : files) {
                if (f.getName().endsWith(".csv")) {
                    try {
                        resources.add(new FileInputStream(f));
                    } catch (FileNotFoundException e) {
                        System.err.println("Failed to read file .  Reason: " + e.getMessage());
                    }
                }
            }

            File targetFile = new File(jdlConfig.getTargetFile());

            Collection<Entity> entities = entitiesService.readAll(resources);
            System.out.println(entities.size() + " entities description was read from csv files");
            int numberOfCreatedStructures = entitiesService.createStructures(entities);
            System.out.print(numberOfCreatedStructures + " structures was created for readed enities");

            Set<String> names = entities.stream().map(e -> e.getClassName()).collect(Collectors.toSet());
            List<Relation> relations = entities.stream().map(e -> e.getRelations()).flatMap(Collection::stream).collect(Collectors.toList());
            relations.stream()
                    .filter(r -> !names.contains(r.getEntityTo())).forEach(r -> System.out.println("Unable to find entity for the relation=" + r.getEntityTo()));
            System.out.println("Total count of entities created: " + entities.size());

            entitiesService.checkRelations(entities).entrySet().stream().forEach(e -> {
                System.out.println(String.format("Where are not exists entities for entity %s", e.getKey().getClassName()));
                e.getValue().stream().forEach(r -> {
                    System.out.println(String.format("Entity %s does not exists", r.getEntityTo()));
                });
            });

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false))) {
                entitiesService.writeEntities(entities, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final String path = jdlConfig.getTargetResourceFolder();
            DescriptionServiceSettings descriptionSettings = new DescriptionServiceSettings(jdlConfig.getGateway(), jdlConfig.getMicroservice());
            DescriptionService descriptionService = new DescriptionService(descriptionSettings);
            descriptionService.getEntitiesDescription(entities).entrySet().stream().forEach(key -> saveFile(path, key.getKey(), key.getValue()));
        }*/
    }


    private static void saveFile(String path, String fileName, String content) {
        /*try {
            FileUtils.writeStringToFile(new File(path, fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}
