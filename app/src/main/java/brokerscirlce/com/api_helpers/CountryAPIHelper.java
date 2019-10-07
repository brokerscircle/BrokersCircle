package brokerscirlce.com.api_helpers;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.Country.CountryData;
import brokerscirlce.com.model.Country.CountryUtil;
import brokerscirlce.com.services.VolleyService;
import brokerscirlce.com.util.Constant;

public class CountryAPIHelper {

    private String TAG = "CountryAPIHelper";
    private static final String COUNTRY_URL = Constant.BASE_URL+"api/countries/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<CountryData> mCountryList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<CountryData> countryData);
    }

    public CountryAPIHelper() {
    }

    public void readCountryList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",COUNTRY_URL);
    }

    public void readSingleCountry(final DataStatus dataStatus, Context mContext, String countryID){
        this.dataStatus = dataStatus;
this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",COUNTRY_URL+"&id="+countryID);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mCountryList.clear();
                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                CountryUtil[] countryUtils = gson.fromJson(plain, CountryUtil[].class);
                //SETTING DATA TO DATASTATUS
                mCountryList.addAll(countryUtils[0].getData());
                dataStatus.DataIsLoaded(mCountryList);



            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
