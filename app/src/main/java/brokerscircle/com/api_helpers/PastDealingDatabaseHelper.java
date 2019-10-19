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
import brokerscircle.com.model.PastDealing.PastDealingData;
import brokerscircle.com.model.PastDealing.PastDealingUtils;
import brokerscircle.com.services.VolleyService;

public class PastDealingDatabaseHelper {

    private String TAG = "PastDealingDatabaseHelper";
    private static final String PASTDEALING_URL = Constant.BASE_URL+"broker-to-broker-dealings/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<PastDealingData> mPastDealingList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<PastDealingData> pastDealingData);
    }

    public PastDealingDatabaseHelper() {
    }

    public void readBrokerPastDealingList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PASTDEALING_URL+"&broker_property_lister_id="+id+"&broker_property_linker_id="+id);
    }

    public void readEstatePastDealingList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PASTDEALING_URL+"&created_by_comp_id="+id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mPastDealingList.clear();

                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PastDealingUtils[] pastDealingUtils = gson.fromJson(plain, PastDealingUtils[].class);

                //SETTING DATA TO DATASTATUS
                mPastDealingList.addAll(pastDealingUtils[0].getData());
                dataStatus.DataIsLoaded(mPastDealingList);


            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
