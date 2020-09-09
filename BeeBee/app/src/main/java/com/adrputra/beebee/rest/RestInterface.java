package com.adrputra.beebee.rest;

import com.adrputra.beebee.model.ExecutionAction;
import com.adrputra.beebee.model.Login;
import com.adrputra.beebee.model.UpdateDevice;
import com.adrputra.beebee.model.callback.ActionsCallback;
import com.adrputra.beebee.model.callback.AuthenticateCallback;
import com.adrputra.beebee.model.callback.DevicesCallback;
import com.adrputra.beebee.model.callback.ExecutionActionCallback;
import com.adrputra.beebee.model.callback.LastStatusCallback;
import com.adrputra.beebee.model.callback.LoginCallback;
import com.adrputra.beebee.model.callback.UpdateDeviceCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestInterface {

    @GET("applicationmgt/authenticate")
    Call<AuthenticateCallback> getAuthenticate(
            @Header("X-Secret") String xSecret
    );

    @POST("usermgt/v1/authenticate")
    Call<LoginCallback> postLogin(
            @Header("Authorization") String accessToken,
            @Body Login body
    );

    @GET("userdevicemgt/v1/devices")
    Call<List<DevicesCallback>> getDevices(
            @Header("Authorization") String accessToken,
            @Header("X-IoT-JWT") String jwt
    );

    @GET("userdevicemgt/v1/devices/{deviceId}/actions")
    Call<List<ActionsCallback>> getActions(
            @Path("deviceId") Integer deviceId,
            @Header("Authorization") String accessToken,
            @Header("X-IoT-JWT") String jwt
    );

    @POST("userdevicecontrol/v1/devices/executeaction")
    Call<ExecutionActionCallback> postExecuteAction(
            @Header("Authorization") String accessToken,
            @Header("X-IoT-JWT") String jwt,
            @Body ExecutionAction body
    );

    @GET("userdevicemgt/v1/devices/{deviceId}/status")
    Call<List<LastStatusCallback>> getDataStatus(
            @Path("deviceId") Integer deviceId,
            @Header("Authorization") String accessToken,
            @Header("X-IoT-JWT") String jwt
    );

    @PUT("userdevicemgt/v1/devices/{deviceId}")
    Call<UpdateDeviceCallback> updateDevice(
            @Header("Authorization") String accessToken,
            @Header("X-IoT-JWT") String jwt,
            @Body UpdateDevice body
    );
}
