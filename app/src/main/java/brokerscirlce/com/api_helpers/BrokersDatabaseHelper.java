package brokerscirlce.com.api_helpers;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.util.Constant;
import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Brokers.BrokersUtils;
import brokerscirlce.com.services.VolleyService;

public class BrokersDatabaseHelper {
    private String TAG = "BrokersDatabaseHelper";
    private static final String BROKERS_URL = Constant.BASE_URL+"api/brokers/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<BrokersData> mBrokersList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<BrokersData> brokersUtils);
    }

    public BrokersDatabaseHelper() {
    }

    //All brokers LoginData
    public void readBrokersList( final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",BROKERS_URL);
    }

    //Single Brokers LoginData
    public void readBrokersDetail( final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",BROKERS_URL+"&id="+id);
    }

    // Brokers LoginData by Estate
    public void readBrokersByEstate( final DataStatus dataStatus, Context mContext, String comp_ID){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",BROKERS_URL+"&created_by_comp_id="+comp_ID);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mBrokersList.clear();
                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                BrokersUtils[] brokersData = gson.fromJson(plain, BrokersUtils[].class);
                for (int i = 0; i < brokersData[0].getData().size(); i++){
                    brokersData[0].getData().get(i).setPicture(brokersData[0].getUrl()+"/"+brokersData[0].getData().get(i).getPicture());
                }
                //SETTING DATA TO DATASTATUS
                mBrokersList.addAll(brokersData[0].getData());
                dataStatus.DataIsLoaded(mBrokersList);
            }
            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley requester " + error.getMessage());
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
