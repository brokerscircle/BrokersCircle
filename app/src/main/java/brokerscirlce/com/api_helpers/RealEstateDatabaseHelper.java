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
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.model.RealEstates.RealEstateUtils;
import brokerscirlce.com.services.VolleyService;

public class RealEstateDatabaseHelper {
    private String TAG = "RealEstateDatabaseHelper";
    private static final String ESTATES_URL = Constant.BASE_URL+"api/companies/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<RealEstateData> mEstateList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<RealEstateData> realEstateData);
    }

    public RealEstateDatabaseHelper() {
    }

    public void readEstateList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mEstateList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ESTATES_URL);
    }

    public void readEstateDetail(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mEstateList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ESTATES_URL+"&id="+id);
    }

    //Getting LoginData
    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                RealEstateUtils[] realEstateUtils = gson.fromJson(plain, RealEstateUtils[].class);

                //SETTING DATA TO DATASTATUS
                mEstateList = realEstateUtils[0].getData();
                dataStatus.DataIsLoaded(mEstateList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
