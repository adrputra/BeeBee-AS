package com.adrputra.beebee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrputra.beebee.adapter.DevicesAdapter;
import com.adrputra.beebee.adapter.EventsAdapter;
import com.adrputra.beebee.config.RecyclerTouchListener;
import com.adrputra.beebee.config.SharedPref;
import com.adrputra.beebee.model.callback.DevicesCallback;
import com.adrputra.beebee.model.callback.LastStatusCallback;
import com.adrputra.beebee.rest.RestAdapter;
import com.adrputra.beebee.rest.RestInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG  = "MAIN_ACTIVITY";
    private final Context ctx        = MainActivity.this;

    TextView nama;
    String usernameLogin;

    private RestInterface api;
    SharedPref sharedPref;
    RecyclerView recyclerView, recyclerView2;
    private Toolbar toolbar;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.usernameShow);
        Intent startingIntent = getIntent();
        usernameLogin = startingIntent.getStringExtra("nama");
        nama.setText(usernameLogin);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_devices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2= (RecyclerView) findViewById(R.id.recycler_status);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        pDialog = new ProgressDialog(ctx);
        pDialog.setCancelable(false);

        initToolbar();

        sharedPref = new SharedPref(ctx);
        api = RestAdapter.getClient().create(RestInterface.class);
        getDevices();
    }

    protected void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void getDataStatus(final Integer deviceId){
        Log.i(TAG, "GET DATA");

        pDialog.setMessage("Please wait ..");
        showDialog();

        Call<List<LastStatusCallback>> call = api.getDataStatus(deviceId, "Bearer "+sharedPref.getAccessToken(), sharedPref.getJwt());
        call.enqueue(new Callback<List<LastStatusCallback>>() {
            @Override
            public void onResponse(Call<List<LastStatusCallback>> call, Response<List<LastStatusCallback>> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            hideDialog();
                            final List<LastStatusCallback> dataStatus = response.body();
                            recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(new EventsAdapter(dataStatus, R.layout.list_item_event, getApplicationContext()));
                            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    LastStatusCallback eventData = dataStatus.get(position);
                                    Intent i = new Intent(ctx, ActionsActivity.class);
                                    i.putExtra("DEVICE_ID", deviceId);
                                    startActivity(i);
                                }
                            }));

                            Log.i(TAG, "ACTIONS SUCCESS, ID : => "+response.body().get(0).getParameter());
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
            public void onFailure(Call<List<LastStatusCallback>> call, Throwable t) {
                hideDialog();
                Log.i(TAG, "DEVICES FAILUR, => "+t.toString());
            }

        });
    }

    public void getDevices() {
        Log.i(TAG, "GET DEVICES");

        pDialog.setMessage("Please wait ..");
        showDialog();

        Call<List<DevicesCallback>> call = api.getDevices("Bearer "+sharedPref.getAccessToken(), sharedPref.getJwt());
        call.enqueue(new Callback<List<DevicesCallback>>() {
            @Override
            public void onResponse(Call<List<DevicesCallback>> call, Response<List<DevicesCallback>> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            hideDialog();
                            final List<DevicesCallback> devices = response.body();
                            recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(new DevicesAdapter(devices, R.layout.list_item_device, getApplicationContext()));

                            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    DevicesCallback device = devices.get(position);
                                    getDataStatus(device.getId());
                                    Intent i = new Intent(ctx, EditActivity.class);
                                    i.putExtra("DEVICE_ID", device.getId());
                                    i.putExtra("DEFINITION_ID", device.getDeviceDefinitionId());
                                    i.putExtra("BRAND", device.getBrand());
                                    i.putExtra("TYPE", device.getType());
                                    i.putExtra("MODEL", device.getModel());
                                    i.putExtra("USER_ID", device.getUserId());
                                    i.putExtra("MAC_ADDRESS", device.getMacAddress());
                                    i.putExtra("DEVICE_NAME", device.getName());
                                    startActivity(i);
                                }
                            }));

                            Log.i(TAG, "DEVICES SUCCESS, ID : => "+response.body().get(0).getId());
                            break;
                        default:
                            hideDialog();
                            Log.i(TAG, "DEVICES FAILED, => "+response.raw());
                            break;
                    }
                } else {
                    hideDialog();
                    Log.i(TAG, "DEVICES FAILED, => "+response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<DevicesCallback>> call, Throwable t) {
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
