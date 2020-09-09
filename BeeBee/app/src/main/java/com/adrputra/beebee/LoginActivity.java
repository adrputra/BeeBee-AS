package com.adrputra.beebee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adrputra.beebee.config.SharedPref;
import com.adrputra.beebee.model.Login;
import com.adrputra.beebee.model.callback.LoginCallback;
import com.adrputra.beebee.rest.RestAdapter;
import com.adrputra.beebee.rest.RestInterface;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG  = "LOGIN_ACTIVITY";
    private final Context ctx        = LoginActivity.this;

    private RestInterface api;
    SharedPref sharedPref;
    ProgressDialog pDialog;
    Button btnLogin;
    TextInputEditText edtUsername;
    TextInputEditText edtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = new SharedPref(ctx);
        pDialog = new ProgressDialog(ctx);
        pDialog.setCancelable(false);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtUsername.setText("paradurant@gmail.com");
        edtPassword.setText("Flexiot8@adr");

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (validateInput(username, password)) doLogin(username, password);
            }
        });

    }

    protected boolean validateInput(String username, String password) {
        boolean result = true;

        if (username.length() == 0) {
            edtUsername.setError("Username is empty");
            result = false;
        }

        if (password.length() == 0) {
            edtPassword.setError("Password is empty");
            result = false;
        }

        return result;
    }

    protected void doLogin(String username, String password) {
        pDialog.setMessage("Please wait ..");
        showDialog();

        api = RestAdapter.getClient().create(RestInterface.class);
        Call<LoginCallback> call = api.postLogin("Bearer "+sharedPref.getAccessToken(), new Login(username, password));
        call.enqueue(new Callback<LoginCallback>() {
            @Override
            public void onResponse(Call<LoginCallback> call, Response<LoginCallback> response) {
                if (response.isSuccessful()) {
                    switch (response.code()) {
                        case 200:
                            startActivityMain(response);
                            Log.i(TAG, "Login success, => ID USER : "+response.body().getData().getUserId());
                            break;
                        default:
                            hideDialog();
                            Log.i(TAG, "Login failed, => "+response.raw());
                            Toast.makeText(ctx, "Username dan password salah !", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginCallback> call, Throwable t) {
                hideDialog();
                Toast.makeText(ctx, "Terjadi kesalahan", Toast.LENGTH_LONG).show();
                Log.e(TAG, "ERROR => ", t);
            }
        });
    }

    protected void startActivityMain(Response<LoginCallback> data) {
        hideDialog();

        sharedPref.setJwt(data.body().getJwt());
        sharedPref.setUserId(data.body().getData().getUserId());

        Intent i = new Intent(ctx, MainActivity.class);
        i.putExtra("nama", edtUsername.getText().toString());
        startActivity(i);

        finish();
    }

    protected void showDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hideDialog() {
        if (pDialog.isShowing()) pDialog.hide();
    }
}
