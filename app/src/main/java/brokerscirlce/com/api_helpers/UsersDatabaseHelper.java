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
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.model.Users.UsersUtil;
import brokerscirlce.com.services.VolleyService;

public class UsersDatabaseHelper {

    private String TAG = "UsersDatabaseHelper";
    private static final String ALL_USERS_LIST = Constant.BASE_URL+"api/users/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<UsersData> mUserList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<UsersData> usersData);
    }

    public UsersDatabaseHelper() {
    }

    public void readUserList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mUserList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_USERS_LIST);
    }

    public void readSingleUserList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mUserList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_USERS_LIST+"&id="+id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                UsersUtil[] usersUtils = gson.fromJson(plain, UsersUtil[].class);
                for (int i = 0; i < usersUtils[0].getData().size(); i++){
                    usersUtils[0].getData().get(i).setImg(usersUtils[0].getUrl()+"/"+usersUtils[0].getData().get(i).getImg());
                }
                //SETTING DATA TO DATASTATUS
                mUserList.addAll(usersUtils[0].getData());
                dataStatus.DataIsLoaded(mUserList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
