package brokerscircle.com.api_helpers;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.util.Constant;
import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.UserQuota.UserQuotaData;
import brokerscircle.com.model.UserQuota.UserQuotaUtil;
import brokerscircle.com.services.VolleyService;

public class UserQuotaDatabaseHelper {

    private String TAG = "UserQuotaDatabaseHelper";
    private static final String QUOTA_URL = Constant.BASE_URL+"user-quotas/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<UserQuotaData> mQuotaList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<UserQuotaData> userQuotaData);
    }

    public UserQuotaDatabaseHelper() {
    }

    public void readQuotaList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mQuotaList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",QUOTA_URL);
    }

    //get Single Record
    public void readSingleQuotaList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mQuotaList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",QUOTA_URL+"&user_id="+id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                UserQuotaUtil[] userQuotaUtils = gson.fromJson(plain, UserQuotaUtil[].class);

                //SETTING DATA TO DATASTATUS
                mQuotaList.addAll(userQuotaUtils[0].getData());
                dataStatus.DataIsLoaded(mQuotaList);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
