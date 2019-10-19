package brokerscircle.com.api_helpers.Property;

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
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.model.Property.PropertyUtil;
import brokerscircle.com.services.VolleyService;

public class PropertyDatabaseHelper {

    private String TAG = "PropertyDatabaseHelper";
    private static final String PROPERTIES_URL = Constant.BASE_URL+"properties/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<PropertyData> mPropertyList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<PropertyData> propertyData);
    }

    public PropertyDatabaseHelper() {
    }

    //Read All Properties
    public void readPropertyList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL);
    }
    //Read property single
    public void readSinglePropertyList(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&id="+id);
    }

    //Read All by Company ID
    public void readPropertyByCompany(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&created_by_comp_id="+id);
    }

    //Read All For Sale Properties
    public void readPropertyByUser(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&created_by_user_id="+id);
    }

    //Read All For Sale Properties
    public void readPropertyForBuy(final DataStatus dataStatus, Context mContext, String buyid){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&parent_category_id="+buyid);
    }

    //Read All For Rent Properties
    public void readPropertyForRent(final DataStatus dataStatus, Context mContext, String rentid){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&parent_category_id="+rentid);
    }

    //Read All For Wanted Properties
    public void readPropertyForWanted(final DataStatus dataStatus, Context mContext, String wantedid){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&parent_category_id="+wantedid);
    }

    //Read All For OFFPla Properties
    public void readPropertyForOFFPlan(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mPropertyList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROPERTIES_URL+"&parent_category_id=6");
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PropertyUtil[] propertyUtils = gson.fromJson(plain, PropertyUtil[].class);

                for (int i = 0; i < propertyUtils[0].getData().size(); i++){
                    propertyUtils[0].getData().get(i).setThumbnail(propertyUtils[0].getUrl()+"/"+propertyUtils[0].getData().get(i).getThumbnail());
                }
                //SETTING DATA TO DATASTATUS
                mPropertyList.addAll(propertyUtils[0].getData());
                dataStatus.DataIsLoaded(mPropertyList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
