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
import brokerscirlce.com.util.Helper;
import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.News.NewsData;
import brokerscirlce.com.model.News.NewsUtils;
import brokerscirlce.com.services.VolleyService;

public class NewsDatabaseHelper {

    private String TAG = "BrokersDatabaseHelper";
    private static final String ALL_NEWS_LIST = Constant.BASE_URL+"api/blog/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<NewsData> mNewsList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<NewsData> newsUtils);
    }

    public NewsDatabaseHelper() {
    }

    public void readNewsList( final DataStatus dataStatus, Context mContext) {
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        //Getting Json from url
        initVolleyCallback(mContext);
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_NEWS_LIST);
    }

    private void initVolleyCallback(Context mContext) {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mNewsList.clear();

                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                NewsUtils[] newsUtils = gson.fromJson(plain, NewsUtils[].class);
                for (int i = 0; i < newsUtils[0].getData().size(); i++){
                    String myFinalValue = Helper.covertTimeToText(newsUtils[0].getData().get(i).getCreatedAt());
                    //Log.d(TAG, "notifySuccess: Time"+myFinalValue);
                    newsUtils[0].getData().get(i).setCreatedAt(myFinalValue);
                    newsUtils[0].getData().get(i).setThumbnail(newsUtils[0].getUrl()+"/"+newsUtils[0].getData().get(i).getThumbnail());
                }
                //SETTING DATA TO DATASTATUS
                mNewsList.addAll(newsUtils[0].getData());
                dataStatus.DataIsLoaded(mNewsList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
