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
import brokerscircle.com.model.Reviews.ReviewData;
import brokerscircle.com.model.Reviews.ReviewUtils;
import brokerscircle.com.services.VolleyService;

public class ReviewsDatabaseHelper {
    private String TAG = "PastDealingDatabaseHelper";
    private static final String REVIEWS_LIST = Constant.BASE_URL+"ratings/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<ReviewData> mReviewsList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<ReviewData> reviewData);
    }

    public ReviewsDatabaseHelper() {
    }

    public void readReviewList(final DataStatus dataStatus, Context mContext, String refID, String refType){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",REVIEWS_LIST+"&reference_id="+refID+"&reference_type="+refType);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mReviewsList.clear();

                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ReviewUtils[] reviewUtils = gson.fromJson(plain, ReviewUtils[].class);

                //SETTING DATA TO DATASTATUS
                mReviewsList.addAll(reviewUtils[0].getData());
                dataStatus.DataIsLoaded(mReviewsList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
