package com.adrputra.beebee.model.callback;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;

public class LastStatusCallback {
    @SerializedName("parameter")
    private String parameter;

    @SerializedName("value")
    private String value;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
