package com.sunrise.jdl.generator.entities;

public enum TemplateKeyTypes {
    LIST("List"),
    LINK("Link"),
    STRING("String");

    private final String text;

    TemplateKeyTypes(String text) {
        this.text = text;
    }
}
