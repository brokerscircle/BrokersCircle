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
import brokerscircle.com.model.Developers.DevelopersData;
import brokerscircle.com.model.Developers.DevelopersUtils;
import brokerscircle.com.services.VolleyService;

public class DevelopersDatabaseHelper {
    private String TAG = "DevelopersDatabaseHelper";
    private static final String DEVELOPERS_URL = Constant.BASE_URL+"developers/list?app_id="+ Constant.APP_ID +"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<DevelopersData> mDevelopersList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<DevelopersData> developersData);
    }

    public DevelopersDatabaseHelper() {
    }

    public void readDeveloperssList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mDevelopersList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",DEVELOPERS_URL);
    }

    public void readDevelopersDetail(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mDevelopersList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",DEVELOPERS_URL+"&id="+id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                DevelopersUtils[] developersUtils = gson.fromJson(plain, DevelopersUtils[].class);
                for (int i = 0; i < developersUtils[0].getData().size(); i++){
                    developersUtils[0].getData().get(i).setLogo(developersUtils[0].getUrl()+"/"+developersUtils[0].getData().get(i).getLogo());
                }
                //SETTING DATA TO DATASTATUS
                mDevelopersList = developersUtils[0].getData();
                dataStatus.DataIsLoaded(mDevelopersList);


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
