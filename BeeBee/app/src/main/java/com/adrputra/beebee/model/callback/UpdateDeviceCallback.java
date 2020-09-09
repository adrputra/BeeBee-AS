package com.adrputra.beebee.model.callback;

import com.google.gson.annotations.SerializedName;

public class UpdateDeviceCallback {
    @SerializedName("desc")
    private String desc;

    public String getDesc(){return desc;}

    public void setDesc(){
        this.desc = desc;
    }
}
