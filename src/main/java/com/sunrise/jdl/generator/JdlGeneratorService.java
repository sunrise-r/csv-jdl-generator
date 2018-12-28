package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.config.JdlConfig;
import com.sunrise.jdl.generator.service.common.GeneratorWriter;
import com.sunrise.jdl.generator.service.i18n.I18NGenerator;
import com.sunrise.jdl.generator.service.jdl.JdlData;
import com.sunrise.jdl.generator.service.jdl.JdlGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Generate JDL and resources
 */
public class JdlGeneratorService {

    private final JdlConfig jdlConfig;
    private final JdlGenerator jdlGenerator;
    private final I18NGenerator i18NGenerator;

    public JdlGeneratorService(JdlConfig jdlConfig) throws IOException {
        this.jdlConfig = jdlConfig;
        GeneratorWriter writer = new GeneratorWriter();
        jdlGenerator = new JdlGenerator(writer);
        i18NGenerator = new I18NGenerator(writer);
    }

    public void generate() throws IOException, TemplateException {
        JdlData jdlData = jdlGenerator.generateJdl(jdlConfig);
        i18NGenerator.generate(jdlConfig, jdlData.getGroupedByEntityName(), jdlData.getJdlEntities(), jdlData.getJdlRelations());
    }

}
