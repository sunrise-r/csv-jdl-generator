package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.config.GeneratorConfig;
import com.sunrise.jdl.generator.service.iad.UIGeneratorService;
import com.sunrise.jdl.generator.ui.UIGenerateParameters;
import freemarker.template.TemplateException;
import org.apache.commons.cli.*;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    private static final String HELP = "help";
    private static final String JDL_CONFIG_FILE = "jdlConfigFile";
    private static final String GID_CONFIG_FILE = "gidConfigFile";

    public static void main(String[] args) throws IOException, TemplateException {
        Options options = new Options();
        options.addOption(JDL_CONFIG_FILE, true, "JDL generation config file.");
        options.addOption(GID_CONFIG_FILE, true, "UI generation config file.");
        options.addOption(HELP, false, "show this help");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            return;
        }

        if (cmd == null) {
            System.err.println("Parsing failed.  Reason: cmd is null");
            return;
        }

        if (cmd.hasOption(HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jdlGenerator", options);
            return;
        }

        File configFile = null;
        // Load config file
        if (cmd.hasOption(JDL_CONFIG_FILE)) {
            configFile = new File(cmd.getOptionValue(JDL_CONFIG_FILE));
        }
        if (cmd.hasOption(GID_CONFIG_FILE)) {
            configFile = new File(cmd.getOptionValue(GID_CONFIG_FILE));
        }
        if (configFile == null) {
            System.err.println("No config file specified for generation");
            return;
        }

        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(configFile);

        if (cmd.hasOption(JDL_CONFIG_FILE)) {
            GeneratorConfig generatorConfig = yaml.load(inputStream);
            JdlGeneratorService jdlGeneratorService = new JdlGeneratorService(generatorConfig.getJdlConfig());
            jdlGeneratorService.generate();
        }
        if (cmd.hasOption(GID_CONFIG_FILE)) {
            UIGenerateParameters generateParameters = yaml.load(inputStream);
            UIGeneratorService uiGeneratorService = new UIGeneratorService(generateParameters);
            uiGeneratorService.generate();
        }
    }
}
