package brokerscircle.com.api_helpers.Jobs;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.jobs.JobData;
import brokerscircle.com.model.jobs.JobUtils;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;

public class JobsDatabaseHelper {

    private String TAG = "JobsDatabaseHelper";
    private static final String JOBS_URL = Constant.BASE_URL+"jobs/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<JobData> mJobList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<JobData> jobData);
    }

    public JobsDatabaseHelper() {
    }

    //Read All Jobs
    public void readJobList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL);
    }
    //Read property Job
    public void readSingleJobList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&id="+id);
    }

    //Read Job by Listing Type
    public void readJobListByListingType(final DataStatus dataStatus, Context mContext, String listing_type_id){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&listing_type_id="+listing_type_id);
    }

    //Read Job by parent category
    public void readJobListByParentCategory(final DataStatus dataStatus, Context mContext, String parentCategoryID){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&parent_category_id="+parentCategoryID);
    }

    //Read Job by child category
    public void readJobListByChildCategory(final DataStatus dataStatus, Context mContext, String childCategoryID){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&child_category_id="+childCategoryID);
    }

    //Read Job by Sub Child Category
    public void readJobListBySubChildCategory(final DataStatus dataStatus, Context mContext, String subChildCategory_id){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&sub_child_category_id="+subChildCategory_id);
    }

    //Read Job by Currency
    public void readJobListByCurrency(final DataStatus dataStatus, Context mContext, String currency_id){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&currency_id="+currency_id);
    }

    //Read Job by Company
    public void readJobListByCompany(final DataStatus dataStatus, Context mContext, String created_by_comp_id){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&created_by_comp_id="+created_by_comp_id);
    }

    //Read Job by User
    public void readJobListByUser(final DataStatus dataStatus, Context mContext, String created_by_user_id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mJobList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",JOBS_URL+"&created_by_user_id="+created_by_user_id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                JobUtils[] jobsUtils = gson.fromJson(plain, JobUtils[].class);
                for (int i = 0; i < jobsUtils[0].getData().size(); i++){
                    jobsUtils[0].getData().get(i).setThumbnail(jobsUtils[0].getUrl()+"/"+jobsUtils[0].getData().get(i).getThumbnail());
                }
                //SETTING DATA TO DATASTATUS
                mJobList.addAll(jobsUtils[0].getData());
                dataStatus.DataIsLoaded(mJobList);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
