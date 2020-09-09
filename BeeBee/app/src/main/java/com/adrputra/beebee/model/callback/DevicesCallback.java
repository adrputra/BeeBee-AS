package com.adrputra.beebee.model.callback;

import com.google.gson.annotations.SerializedName;

public class DevicesCallback {
    @SerializedName("id")
    private Integer id;

    @SerializedName("deviceDefinitionId")
    private Integer deviceDefinitionId;

    @SerializedName("brand")
    private String brand;

    @SerializedName("type")
    private String type;

    @SerializedName("model")
    private String model;

    @SerializedName("deviceCategory")
    private String deviceCategory;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("deviceParentId")
    private Integer deviceParentId;

    @SerializedName("macAddress")
    private String macAddress;

    @SerializedName("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceDefinitionId() {
        return deviceDefinitionId;
    }

    public void setDeviceDefinitionId(Integer deviceDefinitionId) {
        this.deviceDefinitionId = deviceDefinitionId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeviceParentId() {
        return deviceParentId;
    }

    public void setDeviceParentId(Integer deviceParentId) {
        this.deviceParentId = deviceParentId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
