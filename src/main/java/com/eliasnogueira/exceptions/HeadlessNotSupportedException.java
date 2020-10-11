package com.eliasnogueira.exceptions;

public class HeadlessNotSupportedException extends IllegalStateException {

    public HeadlessNotSupportedException(String browser) {
        super(String.format("Healdess not supported for %s browser", browser));
    }
}
