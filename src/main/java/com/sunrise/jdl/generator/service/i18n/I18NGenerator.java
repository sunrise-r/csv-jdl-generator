package com.sunrise.jdl.generator.service.i18n;

import com.sunrise.jdl.generator.config.JdlConfig;
import com.sunrise.jdl.generator.entities.RawData;
import com.sunrise.jdl.generator.service.common.GeneratorWriter;
import com.sunrise.jdl.generator.service.jdl.JdlEntity;
import com.sunrise.jdl.generator.service.jdl.JdlRelation;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class I18NGenerator {

    private final I18NService i18NService;

    public I18NGenerator(GeneratorWriter writer) {
        this.i18NService = new I18NService(writer);
    }

    public void generate(String appName, File resourceFolder, Map<String, List<RawData>> rawData, List<JdlEntity> entities, List<JdlRelation> relations) {
        List<I18nModel> i18nModel = i18NService.generateModel(appName, rawData, entities, relations);
        i18nModel.stream().forEach(model -> i18NService.saveModel(resourceFolder, model));
    }

    public void generate(JdlConfig jdlConfig, Map<String, List<RawData>> rawData, List<JdlEntity> entities, List<JdlRelation> relations) {
        String appName = jdlConfig.getGateway();
        File resourceFolder = Paths.get(jdlConfig.getTargetResourceFolder()).toFile();
        this.generate(appName, resourceFolder, rawData, entities, relations);
    }
}
