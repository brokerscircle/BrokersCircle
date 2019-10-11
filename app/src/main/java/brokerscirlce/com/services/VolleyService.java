package brokerscirlce.com.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public void postDataVolley(final String requestType, String url, Map<String,String> params){
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(mResultCallback != null)
                                mResultCallback.notifySuccess(requestType, response);
                            Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                            Log.d("VolleyService", "onResponse BrokerProfileActivity: "+response);
                            //parseData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(mResultCallback != null)
                                mResultCallback.notifyError(requestType, error);
                            Toast.makeText(mContext,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);

        }catch(Exception ignored){

        }
    }

    public void deleteDataVolley(final String requestType, String url, Map<String,String> params){
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(mResultCallback != null)
                                mResultCallback.notifySuccess(requestType, response);
                            Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                            Log.d("VolleyService", "onResponse BrokerProfileActivity: "+response);
                            //parseData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(mResultCallback != null)
                                mResultCallback.notifyError(requestType, error);
                            Toast.makeText(mContext,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);

        }catch(Exception ignored){

        }
    }
}
