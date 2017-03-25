package com.thinkitlimited.digitalid;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.thinkitlimited.digitalid.Util.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JOSHUA BUJO on 2/6/2017.
 */

public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.et_Phonenumber)
    EditText etPhoneNumber;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.bt_go)
    Button btGo;
    @InjectView(R.id.cv)
    CardView cv;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    ProgressDialog pDialog;
    Preferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        etPhoneNumber = (EditText) findViewById(R.id.et_Phonenumber);
        etPassword = (EditText) findViewById(R.id.et_password);

        mPreferences= new Preferences(this);

        if(mPreferences.getUserId().length()>0){
            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
            Intent i2 = new Intent(LoginActivity.this,ProfileActivity.class);
            i2.putExtra("phone_number",mPreferences.getPhone());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(i2, oc2.toBundle());
            }else {
                startActivity(i2);
            }
        }
        //final Button btGO = (Button) findViewById(R.id.bt_go);


    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                if(etPhoneNumber.getText().toString().length()< 7 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Incorrect phonenumber")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                    return;
                }
                if(etPassword.getText().toString().length()< 6 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Incorrect password")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                    return;
                }
                Log.d("go", "login btn clicked");
                Explode explode = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                }


                pDialog= ProgressDialog.show(LoginActivity.this,"", "Loading...", true);
                pDialog.setCancelable(false);



                final String phone_number = etPhoneNumber.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pDialog.dismiss();

                            JSONObject jsonResponse = new JSONObject(response);
                            int success = jsonResponse.getInt("success");
                            Log.d("success","successful");

                            if (success==1){
                                String phonenumber = jsonResponse.getString("phone_number");
                                String user_id = jsonResponse.getString("user_id");

                                Preferences prefs= new Preferences(LoginActivity.this);
                                prefs.setUserId(user_id);
                                prefs.setUserPhone(phonenumber);

                                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                Intent i2 = new Intent(LoginActivity.this,ProfileActivity.class);
                                i2.putExtra("phone_number",phonenumber);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    startActivity(i2, oc2.toBundle());
                                }else{
                                    startActivity(i2);
                                }


                                /*Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                intent.putExtra("phone_number",phonenumber);

                                LoginActivity.this.startActivity(intent);*/

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();

                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(phone_number,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);




                /*ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i2 = new Intent(this,ProfileActivity.class);
                startActivity(i2, oc2.toBundle());
                break;*/
                break;
        }
    }
}


