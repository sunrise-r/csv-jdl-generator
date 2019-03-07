package com.sunrise.jdl.generator.service.iad;

import com.sunrise.jdl.generator.ui.UIGenerateParameters;

import java.io.IOException;

public class UIGeneratorService {

    private final UIGenerateParameters uiGenerateParameters;
    private final UIGenerator uiGenerator;

    public UIGeneratorService(UIGenerateParameters uiGenerateParameters) {
        this.uiGenerateParameters = uiGenerateParameters;
        uiGenerator = new UIGenerator();
    }

    public void generate() throws IOException {
        UIData uiData = uiGenerator.generateEntitiesPresentations(uiGenerateParameters);
        uiGenerator.writeUIData(uiData, uiGenerateParameters.getTargetPath());
    }
}
