package com.sunrise.jdl.generator.service;

/**
 * Created by igorch on 15.06.18.
 */
public class DescriptionServiceSettings {

    /**
     * Название приложения шлюцза в проекте.
     */
    private final String gatewayAppName;

    /**
     * Название микросервиса в котором будет распологаться сущность.
     */
    private final String microserviceAppName;

    public DescriptionServiceSettings(String gatewayAppName, String microserviceAppName) {
        this.gatewayAppName = gatewayAppName;
        this.microserviceAppName = microserviceAppName;
    }

    public String getGatewayAppName() {
        return gatewayAppName;
    }

    public String getMicroserviceAppName() {
        return microserviceAppName;
    }
}
