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
import brokerscirlce.com.model.PhoneCountryCode.PhoneCountryCodeData;
import brokerscirlce.com.model.PhoneCountryCode.PhoneCountryCodeUtil;
import brokerscirlce.com.services.VolleyService;
import brokerscirlce.com.util.Constant;

public class PhoneCountryCodeAPIHelper {

    private String TAG = "PhoneCountryCodeAPIHelper";
    private static final String PHONE_CODE_URL = Constant.BASE_URL+"api/country-code/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<PhoneCountryCodeData> mPhoneCodeList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<PhoneCountryCodeData> phoneCountryCodeData);
    }

    public PhoneCountryCodeAPIHelper() {
    }

    public void readPhoneCodeList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PHONE_CODE_URL);
    }

    public void readPhoneCodeSingleList(final DataStatus dataStatus, Context mContext, String countryId){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PHONE_CODE_URL+"&country_id="+countryId);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mPhoneCodeList.clear();

                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PhoneCountryCodeUtil[] phoneCodeUtils = gson.fromJson(plain, PhoneCountryCodeUtil[].class);

                //SETTING DATA TO DATASTATUS
                mPhoneCodeList.addAll(phoneCodeUtils[0].getData());
                dataStatus.DataIsLoaded(mPhoneCodeList);


            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
