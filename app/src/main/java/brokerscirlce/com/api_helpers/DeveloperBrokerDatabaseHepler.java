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
import brokerscirlce.com.model.Developers.DeveloperBrokers.DeveloperBrokersData;
import brokerscirlce.com.model.Developers.DeveloperBrokers.DeveloperBrokersUtil;
import brokerscirlce.com.services.VolleyService;

public class DeveloperBrokerDatabaseHepler {

    private String TAG = "DeveloperBrokerDatabaseHepler";
    private static final String DEVELOPER_BROKER = Constant.BASE_URL+"api/developer-brokers/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&developer_id=";

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<DeveloperBrokersData> mBrokerList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<DeveloperBrokersData> developerBrokersData);
    }

    public DeveloperBrokerDatabaseHepler() {
    }

    public void readBrokerList(final DataStatus dataStatus, Context mContext, String developerid){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mBrokerList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",DEVELOPER_BROKER+developerid);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                DeveloperBrokersUtil[] developerBrokersUtils = gson.fromJson(plain, DeveloperBrokersUtil[].class);
                //SETTING DATA TO DATASTATUS
                mBrokerList.addAll(developerBrokersUtils[0].getData());
                dataStatus.DataIsLoaded(mBrokerList);


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
