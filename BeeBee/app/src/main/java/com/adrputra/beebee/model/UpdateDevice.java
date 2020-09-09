package com.adrputra.beebee.model;

import android.accessibilityservice.GestureDescription;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.provider.MediaStore;
import android.support.v4.app.INotificationSideChannel;

import java.io.Serializable;
import java.net.Inet4Address;

public class UpdateDevice {
    private Integer id;
    private Integer deviceDefinitionId;
    private String brand;
    private String type;
    private String model;
    private Integer userId;
    private String macAddress;
    private String name;

    public UpdateDevice(Integer id, Integer deviceDefinitionId, String brand, String type, Integer userId, String macAddress, String name){
        this.id = id;
        this.deviceDefinitionId = deviceDefinitionId;
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.userId = userId;
        this.macAddress = macAddress;
        this.name = name;
    }
    public Integer getId(){return id;}

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getDeviceDefinitionId(){return deviceDefinitionId;}

    public void setDeviceDefinitionId(Integer deviceDefinitionId){this.deviceDefinitionId = deviceDefinitionId;}

    public String getBrand(){return brand;}

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getType(){return type;}

    public void setType(String type){
        this.type= type;
    }

    public String getModel(){return model;}

    public void setModel(String model){
        this.model = model;
    }

    public Integer getUserId(){return userId;}

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public String getMacAddress(){return macAddress;}

    public void setMacAddress(String macAddress){
        this.macAddress = macAddress;
    }

    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }


}
