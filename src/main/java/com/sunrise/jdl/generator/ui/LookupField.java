package com.sunrise.jdl.generator.ui;

public class LookupField extends BaseField {

    private String lookupSource;

    private String lookupView;

    public LookupField lookup(String entityName) {
        this.lookupSource = String.format("lookup%sSourceListProjection", entityName);
        this.lookupView = String.format("lookup%sViewListProjection", entityName);
        return this;
    }

    public LookupField lookupSource(String lookupSource) {
        this.lookupSource = lookupSource;
        return this;
    }

    public LookupField lookupView() {
        this.lookupSource = lookupSource;
        return this;
    }

    public String getLookupSource() {
        return lookupSource;
    }

    public void setLookupSource(String lookupSource) {
        this.lookupSource = lookupSource;
    }

    public String getLookupView() {
        return lookupView;
    }

    public void setLookupView(String lookupView) {
        this.lookupView = lookupView;
    }
}
