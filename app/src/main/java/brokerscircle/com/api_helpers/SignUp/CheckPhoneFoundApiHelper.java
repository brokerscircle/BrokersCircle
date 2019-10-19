package brokerscircle.com.api_helpers.SignUp;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.Signup.CheckPhone.CheckPhoneFoundUtil;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;

public class CheckPhoneFoundApiHelper {

    private String TAG = "CheckPhoneFoundApiHelper";
    private static final String URL = Constant.BASE_URL+"sign-up/number?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&number=";

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<CheckPhoneFoundUtil> mPhoneList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<CheckPhoneFoundUtil> phoneFoundData);
    }

    public CheckPhoneFoundApiHelper() {
    }

    public void checkPhoneFound(final DataStatus dataStatus, Context mContext, String phone){
        this.dataStatus = dataStatus;
this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",URL+phone);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mPhoneList.clear();

                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                CheckPhoneFoundUtil[] checkPhoneFoundUtils = gson.fromJson(plain, CheckPhoneFoundUtil[].class);

                //SETTING DATA TO DATASTATUS
                mPhoneList.add(checkPhoneFoundUtils[0]);
                dataStatus.DataIsLoaded(mPhoneList);
            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
