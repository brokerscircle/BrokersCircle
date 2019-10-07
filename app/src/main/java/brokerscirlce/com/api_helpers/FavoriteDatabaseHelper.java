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
import brokerscirlce.com.model.Favorite.FavoriteData;
import brokerscirlce.com.model.Favorite.FavoriteUtil;
import brokerscirlce.com.services.VolleyService;

public class FavoriteDatabaseHelper {

    private String TAG = "FavoriteDatabaseHelper";
    private static final String FAVORITE_URL = Constant.BASE_URL+"api/favorites/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<FavoriteData> mFavoriteList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<FavoriteData> favoriteData);
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

}
