package com.sunrise.jdl.generator.service.jdl;

import com.sunrise.jdl.generator.config.JdlConfig;

import java.io.IOException;

public class JdlGenerator {

    private final JdlBuilder jdlBuilder;
    private  final JdlWriter jdlWriter;

    public JdlGenerator() throws IOException {
        jdlWriter = new JdlWriter();
        jdlBuilder = new JdlBuilder(analiseService)
    }


    public void generateJdl(JdlConfig jdlConfig){

    }
}
