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
import brokerscirlce.com.model.Notification.NotificationData;
import brokerscirlce.com.model.Notification.NotificationUtil;
import brokerscirlce.com.services.VolleyService;

public class NotificationDatabaseHelper {

    private String TAG = "NotificationDatabaseHelper";
    private static final String NOTIFICATION_URL = Constant.BASE_URL+"api/notifications/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<NotificationData> mNotificationList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<NotificationData> notificationData);
    }

    public NotificationDatabaseHelper() {
    }
    //GEt All record
    public void readNotificationList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mNotificationList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",NOTIFICATION_URL);
    }

    //Get Single record
    public void readSingleNotificationList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mNotificationList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",NOTIFICATION_URL+"&id="+id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                NotificationUtil[] notificationUtils = gson.fromJson(plain, NotificationUtil[].class);

                //SETTING DATA TO DATASTATUS
                mNotificationList.addAll(notificationUtils[0].getData());
                dataStatus.DataIsLoaded(mNotificationList);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
