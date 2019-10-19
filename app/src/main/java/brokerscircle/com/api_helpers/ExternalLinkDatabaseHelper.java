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
import brokerscircle.com.model.ExternelLink.ExternalLinkData;
import brokerscircle.com.model.ExternelLink.ExternelLinkUtil;
import brokerscircle.com.services.VolleyService;

public class ExternalLinkDatabaseHelper {

    private String TAG = "ExternalLinkDatabaseHelper";
    private static final String EXTERNAL_LINK_LIST = Constant.BASE_URL+"external-link/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<ExternalLinkData> mExternalLinkList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<ExternalLinkData> externalLinkData);
    }

    public ExternalLinkDatabaseHelper() {
    }

    public void readExternalLinkList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EXTERNAL_LINK_LIST);
    }
    //External Link Single data
    public void readSingleExternalLinkList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EXTERNAL_LINK_LIST+"&id="+id);
    }
    //External Link with reference id
    public void readExternalLinkWithReferenceID(final DataStatus dataStatus, Context mContext, String ref_id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EXTERNAL_LINK_LIST+"&reference_id="+ref_id);
    }
    //External Link with reference Type
    public void readExternalLinkWithReferenceType(final DataStatus dataStatus, Context mContext, String ref_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EXTERNAL_LINK_LIST+"&reference_type="+ref_type);
    }
    //External Link with reference id and Type
    public void readExternalLinkWithReferenceID_AND_ReferenceType(final DataStatus dataStatus, Context mContext, String ref_id, String ref_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EXTERNAL_LINK_LIST+"&reference_id="+ref_id+"&reference_type="+ref_type);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mExternalLinkList.clear();
                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ExternelLinkUtil[] externalLinkUtils = gson.fromJson(plain, ExternelLinkUtil[].class);

                //SETTING DATA TO DATASTATUS
                mExternalLinkList.addAll(externalLinkUtils[0].getData());
                dataStatus.DataIsLoaded(mExternalLinkList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
