package brokerscirlce.com.api_helpers.Map;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.util.Constant;
import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.Map.MapTypes.MapTypesData;
import brokerscirlce.com.model.Map.MapTypes.MapTypesUtil;
import brokerscirlce.com.services.VolleyService;

public class MapTypesDatabaseHelper {

    private String TAG = "MapTypesDatabaseHelper";
    private static final String ALL_MAPS = Constant.BASE_URL+"api/map-types/list?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<MapTypesData> mMapList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<MapTypesData> mapsData);
    }

    public MapTypesDatabaseHelper() {
    }

    public void readMapsList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
this.mContext = mContext;
        mMapList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_MAPS);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MapTypesUtil[] mapsUtils = gson.fromJson(plain, MapTypesUtil[].class);
                for (int i = 0; i < mapsUtils[0].getData().size(); i++){
                    mapsUtils[0].getData().get(i).setThumbnail(mapsUtils[0].getUrl()+"/"+mapsUtils[0].getData().get(i).getTitle());
                }
                //SETTING DATA TO DATASTATUS
                mMapList.addAll(mapsUtils[0].getData());
                dataStatus.DataIsLoaded(mMapList);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
