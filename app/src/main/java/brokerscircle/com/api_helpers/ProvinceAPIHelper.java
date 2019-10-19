package brokerscircle.com.api_helpers;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.Province.ProvinceData;
import brokerscircle.com.model.Province.ProvinceUtil;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;

public class ProvinceAPIHelper {
    private String TAG = "ProvinceAPIHelper";
    private static final String PROVINCE_URL = Constant.BASE_URL+"provinces/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<ProvinceData> mProvinceList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<ProvinceData> provinceData);
    }

    public ProvinceAPIHelper() {
    }

    public void readProvinceList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROVINCE_URL);
    }

    public void readSingleProvince(final DataStatus dataStatus, Context mContext, String provinceID){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROVINCE_URL+"&id="+provinceID);
    }

    public void readProvincebyCountry(final DataStatus dataStatus, Context mContext, String counteryID){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",PROVINCE_URL+"&country_id="+counteryID);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mProvinceList.clear();

                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ProvinceUtil[] provinceUtils = gson.fromJson(plain, ProvinceUtil[].class);

                //SETTING DATA TO DATASTATUS
                mProvinceList.addAll(provinceUtils[0].getData());
                dataStatus.DataIsLoaded(mProvinceList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}
