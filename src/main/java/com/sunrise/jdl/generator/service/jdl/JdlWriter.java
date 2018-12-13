package com.sunrise.jdl.generator.service.jdl;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class JdlWriter {

    private final Configuration fmkConfig;

    public JdlWriter() throws IOException {
        fmkConfig = new Configuration(Configuration.VERSION_2_3_28);
        fmkConfig.setDirectoryForTemplateLoading(new File("/templates"));
        fmkConfig.setDefaultEncoding("UTF-8");
        fmkConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        fmkConfig.setLogTemplateExceptions(false);
        fmkConfig.setWrapUncheckedExceptions(true);
    }

    public void renderJdl(JdlData jdlData, OutputStream stream) throws IOException, TemplateException {
        Map<String, Object> model = Maps.newHashMap();
        model.put("model", jdlData);
        Template template = fmkConfig.getTemplate("jdl.ftl");
        Writer out = new OutputStreamWriter(stream);
        template.process(model, out);
    }
}
