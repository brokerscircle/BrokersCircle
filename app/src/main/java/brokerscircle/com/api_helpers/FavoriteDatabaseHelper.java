package brokerscircle.com.api_helpers;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.util.Constant;
import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoriteUtil;
import brokerscircle.com.services.VolleyService;

public class FavoriteDatabaseHelper {

    private String TAG = "FavoriteDatabaseHelper";
    private static final String FAVORITE_URL = Constant.BASE_URL+"favorites/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    private static final String FAVORITE_POST_URL = Constant.BASE_URL+"favorite/create?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    private static final String FAVORITE_DELETE_URL = Constant.BASE_URL+"favorite/delete?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<FavoriteData> mFavoriteList = new ArrayList<>();
    private List<FavoritePostResponseUtils> mPostResponse = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<FavoriteData> favoriteData);
        void DataIsPosted(List<FavoritePostResponseUtils> responseUtils);
        void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils);
    }

    public FavoriteDatabaseHelper() {
    }

    //Read All List
    public void readFavoriteList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mFavoriteList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",FAVORITE_URL);
    }
    //Read Favorite with reference id and reference type
    public void readFavoriteWithReferenceID_AND_Type(final DataStatus dataStatus, Context mContext, String id, String type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mFavoriteList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",FAVORITE_URL+"&reference_id="+id+"&reference_type="+type);
    }

    //Read Favorite with reference id and reference type
    public void readFavoriteWithCreatedUserIdAndReferenceID_AND_Type(final DataStatus dataStatus, Context mContext, String created_by_user_id, String reference_id, String reference_type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mFavoriteList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",FAVORITE_URL+"&created_by_user_id="+created_by_user_id+"&reference_id="+reference_id+"&reference_type="+reference_type);
    }

    public void postFavorite(final DataStatus dataStatus, Context mContext, Map<String,String> params){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mFavoriteList.clear();

        //Getting Json from url
        initVolleyPostCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.postDataVolley("Post Call",FAVORITE_POST_URL, params);
    }

    public void deleteFavorite(final DataStatus dataStatus, Context mContext, Map<String,String> params){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mFavoriteList.clear();

        //Getting Json from url
        initVolleyPostCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.deleteDataVolley("Delete Call",FAVORITE_DELETE_URL, params);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                FavoriteUtil[] favoriteUtils = gson.fromJson(plain, FavoriteUtil[].class);

                //SETTING DATA TO DATASTATUS
                mFavoriteList.addAll(favoriteUtils[0].getData());
                dataStatus.DataIsLoaded(mFavoriteList);


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

    private void initVolleyPostCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                FavoritePostResponseUtils[] responseUtils = gson.fromJson(plain, FavoritePostResponseUtils[].class);

                //SETTING DATA TO DATASTATUS
                mPostResponse.add(responseUtils[0]);
                dataStatus.DataIsPosted(mPostResponse);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

    private void initVolleyDeleteCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                FavoritePostResponseUtils[] responseUtils = gson.fromJson(plain, FavoritePostResponseUtils[].class);

                //SETTING DATA TO DATASTATUS
                mPostResponse.add(responseUtils[0]);
                dataStatus.DataIsDeleted(mPostResponse);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
