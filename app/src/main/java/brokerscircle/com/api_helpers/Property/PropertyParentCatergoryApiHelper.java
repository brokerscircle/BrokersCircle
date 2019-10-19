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
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryUtils;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;

public class PropertyParentCatergoryApiHelper {

    private String TAG = "PropertyParentCatergoryApiHelper";
    private static final String URL = Constant.BASE_URL+"property-parent-category/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;


    private List<PropertyParentCategoryUtils> mParentList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<PropertyParentCategoryUtils> propertyParentCategoryUtils);
    }

    public PropertyParentCatergoryApiHelper() {
    }

    public void readPropertyParentCatergoryList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",URL);
    }

    public void readSinglePropertyParentCatergory(final DataStatus dataStatus, Context mContext, String id){
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
                mParentList.clear();

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                PropertyParentCategoryUtils[] propertyParentCategoryUtils = gson.fromJson(plain, PropertyParentCategoryUtils[].class);
                //SETTING DATA TO DATASTATUS
                mParentList.add(propertyParentCategoryUtils[0]);
                dataStatus.DataIsLoaded(mParentList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
