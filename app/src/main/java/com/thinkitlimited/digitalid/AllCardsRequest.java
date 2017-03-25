package com.thinkitlimited.digitalid;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JOSHUA BUJO on 2/12/2017.
 */

public class AllCardsRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://api.digitalid.co.ug/cards/viewcard.php";
    private Map<String,String> params;

    public AllCardsRequest(String user_id, Response.Listener<String> listener){
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);

        params = new HashMap<>();
        params.put("user_id", user_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
