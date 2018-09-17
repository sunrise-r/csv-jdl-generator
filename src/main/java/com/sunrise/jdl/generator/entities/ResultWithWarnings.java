package com.sunrise.jdl.generator.entities;

import java.util.List;

public class ResultWithWarnings<T>{
    public final List<String> warnings;
    public final T result;
    public ResultWithWarnings(List<String> exceptions, T result){
        this.warnings = exceptions;
        this.result= result;
    }
}