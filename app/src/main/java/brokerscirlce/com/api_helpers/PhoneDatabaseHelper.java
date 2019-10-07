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
import brokerscirlce.com.model.Phone.PhoneData;
import brokerscirlce.com.model.Phone.PhoneUtils;
import brokerscirlce.com.services.VolleyService;

public class PhoneDatabaseHelper {

    private String TAG = "PhoneDatabaseHelper";
    private static final String PHONE_DETAIL = Constant.BASE_URL+"api/phones/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&reference_id=";

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<PhoneData> mPhoneList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<PhoneData> phoneData);
    }

    public PhoneDatabaseHelper() {
    }

    public void readPhoneList(final DataStatus dataStatus, Context mContext, String id, String type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PHONE_DETAIL+id+"&reference_type="+type);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mPhoneList.clear();

                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PhoneUtils[] phoneUtils = gson.fromJson(plain, PhoneUtils[].class);

                //SETTING DATA TO DATASTATUS
                mPhoneList.addAll(phoneUtils[0].getData());
                dataStatus.DataIsLoaded(mPhoneList);


            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
