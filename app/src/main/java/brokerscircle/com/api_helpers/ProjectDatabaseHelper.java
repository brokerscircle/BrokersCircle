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
import brokerscircle.com.model.Project.ProjectData;
import brokerscircle.com.model.Project.ProjectUtil;
import brokerscircle.com.services.VolleyService;

public class ProjectDatabaseHelper {

    private String TAG = "ProjectDatabaseHelper";
    private static final String PROJECT_URL = Constant.BASE_URL+"projects/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<ProjectData> mProjectList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<ProjectData> projectData);
    }

    public ProjectDatabaseHelper() {
    }

    //Read All Project
    public void readProjectList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mProjectList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROJECT_URL);
    }
    //Read Project single
    public void readSingleProjectList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mProjectList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROJECT_URL+"&id="+id);
    }

    //Read All by Developer ID
    public void readProjectByDeveloper(final DataStatus dataStatus, Context mContext, String developer_id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mProjectList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROJECT_URL+"&developer_id="+developer_id);
    }

    //Read All by user Project
    public void readProjectByUser(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mProjectList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROJECT_URL+"&created_by_user_id="+id);
    }

    //Read Project by parent cat id
    public void readProjectByParentCategory(final DataStatus dataStatus, Context mContext, String parentid){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mProjectList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROJECT_URL+"&parent_category_id="+parentid);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ProjectUtil[] projectUtils = gson.fromJson(plain, ProjectUtil[].class);
                for (int i = 0; i < projectUtils[0].getData().size(); i++){
                    projectUtils[0].getData().get(i).setThumbnail(projectUtils[0].getUrl()+"/"+projectUtils[0].getData().get(i).getThumbnail());
                }
                //SETTING DATA TO DATASTATUS
                mProjectList.addAll(projectUtils[0].getData());
                dataStatus.DataIsLoaded(mProjectList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
