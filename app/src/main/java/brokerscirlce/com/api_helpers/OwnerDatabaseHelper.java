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
import brokerscirlce.com.model.Owner.OnwerUtil;
import brokerscirlce.com.model.Owner.OwnerData;
import brokerscirlce.com.services.VolleyService;

public class OwnerDatabaseHelper {

    private String TAG = "OwnerDatabaseHelper";
    private static final String ALL_OWNER = Constant.BASE_URL+"api/owners/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<OwnerData> mOwnerList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<OwnerData> ownerData);
    }

    public OwnerDatabaseHelper() {
    }

    public void readOwnerList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mOwnerList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_OWNER);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                OnwerUtil[] onwerUtils = gson.fromJson(plain, OnwerUtil[].class);
                for (int i = 0; i < onwerUtils[0].getData().size(); i++){
                    onwerUtils[0].getData().get(i).setPicture(onwerUtils[0].getUrl()+"/"+onwerUtils[0].getData().get(i).getPicture());
                }
                //SETTING DATA TO DATASTATUS
                mOwnerList.addAll(onwerUtils[0].getData());
                dataStatus.DataIsLoaded(mOwnerList);


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
