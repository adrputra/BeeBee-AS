package com.adrputra.beebee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.adrputra.beebee.config.SharedPref;
import com.adrputra.beebee.model.ExecutionAction;
import com.adrputra.beebee.model.UpdateDevice;
import com.adrputra.beebee.model.callback.ExecutionActionCallback;
import com.adrputra.beebee.model.callback.UpdateDeviceCallback;
import com.adrputra.beebee.rest.RestAdapter;
import com.adrputra.beebee.rest.RestInterface;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "EDIT_ACTIVITY";
    private final Context ctx = EditActivity.this;
    private RestInterface api;
    SharedPref sharedPref;
    private Toolbar toolbar;
    ProgressDialog pDialog;
    TextInputEditText edt_deviceName, edt_deviceModel, edt_deviceType, edt_deviceBrand;
    String deviceName, macAddress, deviceBrand, deviceType, deviceModel;
    int deviceId,deviceDefinitionId, userId;
    Button editButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        pDialog = new ProgressDialog(ctx);
        pDialog.setCancelable(false);

        initToolbar();

        sharedPref = new SharedPref(ctx);
        api = RestAdapter.getClient().create(RestInterface.class);
        deviceName = i.getStringExtra("DEVICE_NAME");
        macAddress = i.getStringExtra("MAC_ADDRESS");
        deviceBrand = i.getStringExtra("BRAND");
        deviceType = i.getStringExtra("TYPE");
        deviceModel = i.getStringExtra("MODEL");
        deviceId = i.getIntExtra("DEVICE_ID",0);
        deviceDefinitionId = i.getIntExtra("DEFINITION_ID",0);
        userId = i.getIntExtra("USER_ID",0);

        edt_deviceName = findViewById(R.id.deviceName);
        edt_deviceBrand = findViewById(R.id.deviceBrand);
        edt_deviceType = findViewById(R.id.deviceType);
        edt_deviceModel = findViewById(R.id.deviceModel);
        editButton = findViewById(R.id.submitEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = edt_deviceName.getText().toString().trim();
                String deviceBrand = edt_deviceBrand.getText().toString().trim();
                String deviceType = edt_deviceType.getText().toString().trim();
                String deviceModel = edt_deviceModel.getText().toString().trim();
                updateDevice(deviceBrand,deviceModel,deviceName,deviceType);
            }
        });

        edt_deviceName.setText(deviceName);
        edt_deviceType.setText(deviceType);
        edt_deviceBrand.setText(deviceBrand);
        edt_deviceModel.setText(deviceModel);



    }
    protected void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Edit Device");
    }
    public void updateDevice(String deviceBrand, String deviceModel, String deviceName, String deviceType) {
        Log.i(TAG, "EXECUTE ACTIONS");

        pDialog.setMessage("Please wait ..");
        showDialog();

        Call<UpdateDeviceCallback> call = api.updateDevice("Bearer "+sharedPref.getAccessToken(), sharedPref.getJwt(), new UpdateDevice(deviceId,deviceDefinitionId,this.deviceType, this.deviceModel,sharedPref.getUserId(),macAddress, this.deviceName));
        call.enqueue(new Callback<UpdateDeviceCallback>() {
            @Override
            public void onResponse(Call<UpdateDeviceCallback> call, Response<UpdateDeviceCallback> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            hideDialog();
                            Toast.makeText(getApplicationContext(), EditActivity.this.deviceName +" : "+response.body().getDesc(), Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "ACTIONS SUCCESS, ID : => "+response.raw());
                            break;
                        default:
                            hideDialog();
                            Log.i(TAG, "ACTIONS FAILED, => "+response.raw());
                            break;
                    }
                } else {
                    hideDialog();
                    Log.i(TAG, "ACTIONS FAILED, => "+response.raw());
                }
            }

            @Override
            public void onFailure(Call<UpdateDeviceCallback> call, Throwable t) {
                hideDialog();
                Log.i(TAG, "DEVICES FAILUR, => "+t.toString());
            }

        });
    }
    protected void showDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hideDialog() {
        if (pDialog.isShowing()) pDialog.hide();
    }
}
