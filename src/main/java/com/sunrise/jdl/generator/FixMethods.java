package com.sunrise.jdl.generator;

import com.sunrise.jdl.generator.ui.ProjectionInfo;

public class FixMethods {

    public static void fixListProjections(ProjectionInfo projectionInfo) {
        if(projectionInfo.getCode() == null) return;
        projectionInfo.setCode(projectionInfo.getCode().replaceAll("ListProjection",""));
    }


}
