package brokerscirlce.com.services;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import brokerscirlce.com.interfaces.IResult;

public class VolleyService {

    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult mResultCallback, Context mContext) {
        this.mResultCallback = mResultCallback;
        this.mContext = mContext;
    }

    public void getDataVolley(final String requestType, String url){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                if(mResultCallback != null)
                    mResultCallback.notifySuccess(requestType, response);
            }, error -> {
                if(mResultCallback != null)
                    mResultCallback.notifyError(requestType, error);
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(stringRequest);

        }catch(Exception ignored){

        }
    }

    public void postDataVolley(final String requestType, String url, JSONObject sendObj){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST,url,sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType,response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            });

            queue.add(jsonObj);

        }catch(Exception e){

        }
    }
}
