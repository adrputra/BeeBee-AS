package com.adrputra.beebee.model.callback;

import com.google.gson.annotations.SerializedName;

public class ExecutionActionCallback {
    @SerializedName("parameter")
    private String parameter;

    @SerializedName("state")
    private String state;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
