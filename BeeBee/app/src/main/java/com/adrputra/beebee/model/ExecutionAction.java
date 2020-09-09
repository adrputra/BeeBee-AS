package com.adrputra.beebee.model;

public class ExecutionAction {
    private String operation;
    private Integer deviceId;
    private String actionName;
    private Integer userId;

    public ExecutionAction(String operation, Integer deviceId, String actionName, Integer userId) {
        this.operation = operation;
        this.deviceId = deviceId;
        this.actionName = actionName;
        this.userId = userId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
