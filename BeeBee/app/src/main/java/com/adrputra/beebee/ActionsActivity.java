package com.adrputra.beebee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrputra.beebee.adapter.ActionsAdapter;
import com.adrputra.beebee.adapter.EventsAdapter;
import com.adrputra.beebee.config.RecyclerTouchListener;
import com.adrputra.beebee.config.SharedPref;
import com.adrputra.beebee.model.ExecutionAction;
import com.adrputra.beebee.model.callback.ActionsCallback;
import com.adrputra.beebee.model.callback.ExecutionActionCallback;
import com.adrputra.beebee.model.callback.LastStatusCallback;
import com.adrputra.beebee.rest.RestAdapter;
import com.adrputra.beebee.rest.RestInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionsActivity extends AppCompatActivity {

    private static final String TAG  = "ACTIONS_ACTIVITY";
    private final Context ctx        = ActionsActivity.this;

    private RestInterface api;
    SharedPref sharedPref;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    ProgressDialog pDialog;
    private Integer device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recycler_devices);
        recyclerView.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(ctx);
        pDialog.setCancelable(false);

        initToolbar();

        sharedPref = new SharedPref(ctx);
        api = RestAdapter.getClient().create(RestInterface.class);
        device_id = i.getIntExtra("DEVICE_ID", 26633);
        getActions(device_id);
    }

    protected void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void getActions(Integer deviceId) {
        Log.i(TAG, "GET ACTIONS");

        pDialog.setMessage("Please wait ..");
        showDialog();

        Call<List<ActionsCallback>> call = api.getActions(deviceId,"Bearer "+sharedPref.getAccessToken(), sharedPref.getJwt());
        call.enqueue(new Callback<List<ActionsCallback>>() {
            @Override
            public void onResponse(Call<List<ActionsCallback>> call, Response<List<ActionsCallback>> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            hideDialog();
                            final List<ActionsCallback> actions = response.body();
                            recyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(new ActionsAdapter(actions, R.layout.list_item_action, getApplicationContext()));

                            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    ActionsCallback action = actions.get(position);
                                    executionAction(action.getName());
                                }
                            }));

                            Log.i(TAG, "ACTIONS SUCCESS, ID : => "+response.body().get(0).getName());
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
            public void onFailure(Call<List<ActionsCallback>> call, Throwable t) {
                hideDialog();
                Log.i(TAG, "DEVICES FAILUR, => "+t.toString());
            }

        });
    }

    public void executionAction(final String actionName) {
        Log.i(TAG, "EXECUTE ACTIONS");

        pDialog.setMessage("Please wait ..");
        showDialog();

        Call<ExecutionActionCallback> call = api.postExecuteAction("Bearer "+sharedPref.getAccessToken(), sharedPref.getJwt(), new ExecutionAction("deviceControl", device_id, actionName, sharedPref.getUserId()));
        call.enqueue(new Callback<ExecutionActionCallback>() {
            @Override
            public void onResponse(Call<ExecutionActionCallback> call, Response<ExecutionActionCallback> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            hideDialog();
                            Toast.makeText(getApplicationContext(), actionName+" : "+response.body().getState(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ExecutionActionCallback> call, Throwable t) {
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
