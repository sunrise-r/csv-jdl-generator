package com.sunrise.jdl.generator.service.common;

import com.google.common.collect.Maps;
import com.sunrise.jdl.generator.config.JdlConfig;
import com.sunrise.jdl.generator.service.i18n.I18nModel;
import com.sunrise.jdl.generator.service.jdl.JdlData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class GeneratorWriter {

    private final Configuration fmkConfig;

    public GeneratorWriter() throws IOException {
        fmkConfig = new Configuration(Configuration.VERSION_2_3_28);
        fmkConfig.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "templates");
        fmkConfig.setDefaultEncoding("UTF-8");
        fmkConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        fmkConfig.setLogTemplateExceptions(false);
        fmkConfig.setWrapUncheckedExceptions(true);
    }

    public void renderJdl(JdlConfig jdlConfig, JdlData jdlData, OutputStream stream) throws IOException, TemplateException {
        Map<String, Object> model = Maps.newHashMap();
        model.put("model", jdlData);
        model.put("jdlConfig", jdlConfig);
        Template template = fmkConfig.getTemplate("jdl.ftl");
        Writer out = new OutputStreamWriter(stream);
        template.process(model, out);
    }

    public void renderI18N(I18nModel i18nModel, FileOutputStream fileOutputStream) throws IOException, TemplateException {
        Map<String, Object> model = Maps.newHashMap();
        model.put("model", i18nModel);
        Template template = fmkConfig.getTemplate("i18n.ftl");
        Writer out = new OutputStreamWriter(fileOutputStream);
        template.process(model, out);
    }
}
