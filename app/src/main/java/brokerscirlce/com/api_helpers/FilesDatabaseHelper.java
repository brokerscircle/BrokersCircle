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
import brokerscirlce.com.model.Files.FilesData;
import brokerscirlce.com.model.Files.FilesUtil;
import brokerscirlce.com.services.VolleyService;

public class FilesDatabaseHelper {

    private String TAG = "FilesDatabaseHelper";
    private static final String Files_LIST = Constant.BASE_URL+"api/files/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<FilesData> mFilesList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<FilesData> filesData);
    }

    public FilesDatabaseHelper() {
    }

    public void readFilesList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",Files_LIST);
    }
    //External Link Single data
    public void readSingleFilesList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",Files_LIST+"&id="+id);
    }
    //External Link with reference id
    public void readFilesWithReferenceID(final DataStatus dataStatus, Context mContext, String ref_id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",Files_LIST+"&reference_id="+ref_id);
    }
    //External Link with reference Type
    public void readFilesWithReferenceType(final DataStatus dataStatus, Context mContext, String ref_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",Files_LIST+"&reference_type="+ref_type);
    }
    //External Link with reference id and Type
    public void readFilesWithReference_ID_AND_Type(final DataStatus dataStatus, Context mContext, String ref_id, String ref_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",Files_LIST+"&reference_id="+ref_id+"&reference_type="+ref_type);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mFilesList.clear();

                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                FilesUtil[] filesUtils = gson.fromJson(plain, FilesUtil[].class);

                //SETTING DATA TO DATASTATUS
                mFilesList.addAll(filesUtils[0].getData());
                dataStatus.DataIsLoaded(mFilesList);


            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
