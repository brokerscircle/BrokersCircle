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
import brokerscirlce.com.model.MasterAPI.MasterData;
import brokerscirlce.com.model.MasterAPI.MasterUtil;
import brokerscirlce.com.services.VolleyService;

public class MasterDatabaseHelper {

    private String TAG = "MasterDatabaseHelper";
    private static final String MASTER_URL = Constant.BASE_URL+"api/user-and-user-type-master/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<MasterData> mMasterList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<MasterData> masterData);
    }

    public MasterDatabaseHelper() {
    }
    //Read All Record
    public void readMasterList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL);
    }
    //Read Single Record
    public void readMasterSingleList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&id="+id);
    }
    //Read Record By UserData ID
    public void readMasterByUser_ID(final DataStatus dataStatus, Context mContext, String userID){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&user_id="+userID);
    }
    //Read Record By UserData Type
    public void readMasterByUser_Type(final DataStatus dataStatus, Context mContext, String userTYPE){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&user_type="+userTYPE);
    }
    //Read Record By Reference ID
    public void readMasterByReference_ID(final DataStatus dataStatus, Context mContext, String refID){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&reference_id="+refID);
    }
    //Read Record By Reference Type
    public void readMasterByReference_Type(final DataStatus dataStatus, Context mContext, String refTYPE){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&reference_type="+refTYPE);
    }
    //Read Record By Reference id + Type
    public void readMasterByReference_ID_AND_RefType(final DataStatus dataStatus, Context mContext,String refID, String refTYPE){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&reference_id="+refID+"&reference_type="+refTYPE);
    }
    //Read Record By Reference id + UserData Type
    public void readMasterByReference_ID_AND_User_Type(final DataStatus dataStatus, Context mContext,String refID, String userTYPE){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mMasterList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",MASTER_URL+"&reference_id="+refID+"&user_type="+userTYPE);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MasterUtil[] masterUtils = gson.fromJson(plain, MasterUtil[].class);

                //SETTING DATA TO DATASTATUS
                mMasterList.addAll(masterUtils[0].getData());
                dataStatus.DataIsLoaded(mMasterList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
