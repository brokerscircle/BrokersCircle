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
import brokerscirlce.com.model.System_Setting.SystemSettingData;
import brokerscirlce.com.model.System_Setting.SystemSettingUtils;
import brokerscirlce.com.services.VolleyService;

public class SystemSettingDatabaseHelper {

    private String TAG = "SystemSettingDatabaseHelper";
    private static final String ALL_USERS_LIST = Constant.BASE_URL+"api/system-settings/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<SystemSettingData> mSettingList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<SystemSettingData> systemSettingData);
    }

    public SystemSettingDatabaseHelper() {
    }

    public void readSettingList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mSettingList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_USERS_LIST);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                SystemSettingUtils[] systemSettingUtils = gson.fromJson(plain, SystemSettingUtils[].class);

                //SETTING DATA TO DATASTATUS
                mSettingList.addAll(systemSettingUtils[0].getData());
                dataStatus.DataIsLoaded(mSettingList);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
