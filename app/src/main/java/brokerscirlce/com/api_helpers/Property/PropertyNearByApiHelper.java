package brokerscirlce.com.api_helpers.Property;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.Property.PropertyNearBy.PropertyNearByUtils;
import brokerscirlce.com.services.VolleyService;
import brokerscirlce.com.util.Constant;

public class PropertyNearByApiHelper {

    private String TAG = "PropertyNearByApiHelper";
    private static final String URL = Constant.BASE_URL+"api/property-near-by/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<PropertyNearByUtils> mList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<PropertyNearByUtils> propertyNearByUtils);
    }

    public PropertyNearByApiHelper() {
    }

    public void readPropertyNearByList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",URL);
    }

    public void readSinglePropertyNearBy(final DataStatus dataStatus, Context mContext, String id){
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
                PropertyNearByUtils[] propertyNearByUtils = gson.fromJson(plain, PropertyNearByUtils[].class);

                //SETTING DATA TO DATASTATUS
                mList.add(propertyNearByUtils[0]);
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
