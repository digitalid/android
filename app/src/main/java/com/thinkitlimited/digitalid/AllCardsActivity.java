package com.thinkitlimited.digitalid;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thinkitlimited.digitalid.Util.Preferences;
import com.thinkitlimited.digitalid.adapter.AllCardsAdapter;
import com.thinkitlimited.digitalid.classes.Cards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by JOSHUA BUJO on 2/17/2017.
 */
public class AllCardsActivity extends AppCompatActivity {
    //private String urlJsonObj = "http://api.digitalid.co.ug/cards/viewcard.php?user_id=23";

    private ProgressDialog pDialog;

    // temporary string to show the parsed response
    private String jsonResponse;
    public ArrayList<Cards> arraylist;
    public AllCardsAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layout_manager;
    Preferences preferences;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_cards);

        preferences= new Preferences(AllCardsActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        recyclerView.addItemDecoration(decoration);


        layout_manager =
                new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layout_manager);

        //adapter = new AllCardsAdapter(arraylist, AllCardsActivity.this);
        //recyclerView.setAdapter(adapter);


        Explode explode = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);

        }

        performLoadCars(preferences.getUserId());

    }

    public void performLoadCars(final String userId){
        pDialog= ProgressDialog.show(AllCardsActivity.this,"", "Loading...", true);
        pDialog.setCancelable(false);



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();

                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.d("success","successful");

                    if (success==1){
                        arraylist= new ArrayList<>();
                        JSONArray posts = jsonResponse.optJSONArray("posts");
                        for(int i=0; i< posts.length(); i++){
                            JSONObject obj= (JSONObject) posts.get(i);
                            arraylist.add(new Cards("1", obj.optString("id_name"), obj.optString("id_number"), obj.optString("nationality"),
                                    obj.optString("expiry_date"), "", ""));


                        }//end for loop


                        //now add to listview
                        if(arraylist!=null && !arraylist.isEmpty()){

                            adapter = new AllCardsAdapter(arraylist, AllCardsActivity.this);
                            recyclerView.setAdapter(adapter);
                        }

                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AllCardsActivity.this);
                        builder.setMessage("Failed")
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
        AllCardsRequest loginRequest = new AllCardsRequest(userId,responseListener);
        RequestQueue queue = Volley.newRequestQueue(AllCardsActivity.this);
        queue.add(loginRequest);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        public SpacesItemDecoration(int space) {
            this.mSpace = space;

        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            /*outRect.left = 0;
            outRect.right =0;
            outRect.bottom = 0;//(mSpace/2)+1;
            outRect.top =0;*/

            if(position==0){
                outRect.left = 0;
                outRect.right = 0;
                outRect.bottom = 1;
                outRect.top =0;
            }else{
                if((position%2)==0){
                    outRect.left =  (mSpace/4);
                    outRect.right = 0;
                    outRect.bottom =  (mSpace/3);
                    outRect.top =0;
                }else{
                    outRect.left = 0;
                    outRect.right = (mSpace/4);
                    outRect.bottom = (mSpace/3);
                    outRect.top =0;
                }


            }


        }
    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    /*private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Success","successful");

                try {
                    // Parsing json object response
                    // response will be a json object
                    //String name = response.getString("name");


                    //String email = response.getString("email");

                    JSONArray posts = response.getJSONArray("posts");
                    for (int k = 0; k < posts.length(); k++) {
                        JSONObject cards = (JSONObject) posts.get(k);


                        String IdNumber = cards.getString("id_number");
                        String IdName = cards.getString("id_name");
                        String expiryDate = cards.getString("expiry_date");
                        String nationality = cards.getString("nationality");


                        jsonResponse = "";
                        jsonResponse += "Number: " + IdNumber + "\n\n";
                        jsonResponse += "Names: " + IdName + "\n\n";
                        jsonResponse += "Expiry date: " + expiryDate + "\n\n";
                        jsonResponse += "Nationality: " + nationality + "\n\n";
                    }

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(AllCardsActivity.this);
        queue.add(jsonObjReq);
    }
*/
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
