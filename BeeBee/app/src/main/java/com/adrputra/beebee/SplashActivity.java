package com.adrputra.beebee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.adrputra.beebee.config.SharedPref;
import com.adrputra.beebee.model.callback.AuthenticateCallback;
import com.adrputra.beebee.rest.RestAdapter;
import com.adrputra.beebee.rest.RestInterface;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.adrputra.beebee.config.Constant.X_SECRET;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG  = "SPLASH_ACTIVITY";
    private final Context ctx        = SplashActivity.this;

    private RestInterface api;
    SharedPref sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPref = new SharedPref(ctx);
        api = RestAdapter.getClient().create(RestInterface.class);
        doAuthenticate();
    }

    public void doAuthenticate() {
        Log.i(TAG, "DO AUTHENTICATE: ");
        Call<AuthenticateCallback> call = api.getAuthenticate(X_SECRET);
        call.enqueue(new Callback<AuthenticateCallback>() {
            @Override
            public void onResponse(Call<AuthenticateCallback> call, Response<AuthenticateCallback> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            sharedPref.setAccessToken(response.body().getAccessToken());
                            startLoginActivity();
                            Log.i(TAG, "AUTHENTICATE SUCCESS, => "+response.body().getAccessToken());
                            break;
                        default:
                            Log.i(TAG, "AUTHENTICATE FAILED, => "+response.raw());
                            break;
                    }
                } else {
                    Log.i(TAG, "AUTHENTICATE FAILED, => "+response.raw());
                }
            }

            @Override
            public void onFailure(Call<AuthenticateCallback> call, Throwable t) {
                Log.i(TAG, "AUTHENTICATE FAILUR, => "+t.toString());
            }
        });
    }

    public void startLoginActivity(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        };
        new Timer().schedule(task, 3000);
    }
}
