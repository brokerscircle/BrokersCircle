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
import brokerscircle.com.model.Galleries.GalleriesData;
import brokerscircle.com.model.Galleries.GalleriesUtil;
import brokerscircle.com.services.VolleyService;

public class GalleriesDatabaseHelper {

    private String TAG = "GalleriesDatabaseHelper";
    private static final String GALLERIES_LIST = Constant.BASE_URL+"galleries/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<GalleriesData> mGalleriesList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;


    public interface DataStatus{
        void DataIsLoaded(List<GalleriesData> galleriesData);
    }

    public GalleriesDatabaseHelper() {
    }

    public void readGalleriesList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",GALLERIES_LIST);
    }
    //External Link Single data
    public void readSingleGalleriesList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",GALLERIES_LIST+"&id="+id);
    }
    //External Link with reference id
    public void readGalleriesWithReferenceID(final DataStatus dataStatus, Context mContext, String ref_id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",GALLERIES_LIST+"&reference_id="+ref_id);
    }
    //External Link with reference Type
    public void readGalleriesWithReferenceType(final DataStatus dataStatus, Context mContext, String ref_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",GALLERIES_LIST+"&reference_type="+ref_type);
    }
    //External Link with reference id and Type
    public void readGalleriesWithReference_ID_AND_Type(final DataStatus dataStatus, Context mContext, String ref_id, String ref_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",GALLERIES_LIST+"&reference_id="+ref_id+"&reference_type="+ref_type);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mGalleriesList.clear();

                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                GalleriesUtil[] galleriesUtils = gson.fromJson(plain, GalleriesUtil[].class);

                //SETTING DATA TO DATASTATUS
                mGalleriesList.addAll(galleriesUtils[0].getData());
                dataStatus.DataIsLoaded(mGalleriesList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
