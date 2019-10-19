package brokerscircle.com.api_helpers.Property;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.Property.PropertyFeatures.PropertyFeaturesUtils;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;

public class PropertyFeaturesApiHelper {

    private String TAG = "PropertyFeaturesApiHelper";
    private static final String URL = Constant.BASE_URL+"property-features/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<PropertyFeaturesUtils> mList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<PropertyFeaturesUtils> propertyFeaturesUtils);
    }

    public PropertyFeaturesApiHelper() {
    }

    public void readPropertyFeaturesList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",URL);
    }

    public void readSinglePropertyFeature(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",URL+"&id="+id);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {

            @Override
            public void notifySuccess(String requestType,String response) {
                mList.clear();

                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PropertyFeaturesUtils[] propertyFeaturesUtils = gson.fromJson(plain, PropertyFeaturesUtils[].class);

                //SETTING DATA TO DATASTATUS
                mList.add(propertyFeaturesUtils[0]);
                dataStatus.DataIsLoaded(mList);


            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
