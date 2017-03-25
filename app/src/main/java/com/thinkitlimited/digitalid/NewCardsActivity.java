package com.thinkitlimited.digitalid;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thinkitlimited.digitalid.Util.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JOSHUA BUJO on 2/13/2017.
 */

public class NewCardsActivity extends AppCompatActivity {
    final String url = "http://api.digitalid.co.ug/cards/addcard.php";
    EditText inputIdNumber, inputIdName, inputExpiryDate, inputNationality;
    ProgressDialog pDialog;
    Preferences mPreferences;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        mPreferences= new Preferences(this);

        inputIdNumber = (EditText)findViewById(R.id.inputIDNumber);
        inputIdName = (EditText)findViewById(R.id.inputIDName);
        inputExpiryDate= (EditText)findViewById(R.id.inputExpiryDate);
        inputNationality = (EditText)findViewById(R.id.inputNationality);



        Explode explode = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

    }

    //Runs on click of btnAddCard
    public void onClickAddCard(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Post Successful: ", response);
                        Toast.makeText(NewCardsActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(NewCardsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_number", inputIdNumber.getText().toString());
                params.put("id_name", inputIdName.getText().toString());
                params.put("expiry_date", inputExpiryDate.getText().toString());
                params.put("nationality", inputIdName.getText().toString());
                params.put("user_id", mPreferences.getUserId());

                return params;
            }
        };
        queue.add(postRequest);

    }
}
