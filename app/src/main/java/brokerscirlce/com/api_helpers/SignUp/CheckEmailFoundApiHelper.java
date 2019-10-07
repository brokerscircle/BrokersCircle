package brokerscirlce.com.api_helpers.SignUp;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.Signup.CheckEmail.CheckEmailFoundUtil;
import brokerscirlce.com.services.VolleyService;
import brokerscirlce.com.util.Constant;

public class CheckEmailFoundApiHelper {

    private String TAG = "CheckEmailFoundApiHelper";
    //https://brokershub.com.pk/admin/public/api/sign-up/email?app_id=512234&app_key=A2AS3Z2XGXAVDA&email=info@muhammaduzair.com
    private static final String URL = Constant.BASE_URL+"api/sign-up/email?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&email=";

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<CheckEmailFoundUtil> mEmailList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<CheckEmailFoundUtil> emailFoundData);
    }

    public CheckEmailFoundApiHelper() {
    }

    public void checkEmailFound(final DataStatus dataStatus, Context mContext, String email){
        this.dataStatus = dataStatus;
this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",URL+email);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mEmailList.clear();

                String plain = Html.fromHtml(response).toString();
                Log.d(TAG, "notifySuccess: "+plain);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                CheckEmailFoundUtil[] checkEmailFoundUtils = gson.fromJson(plain, CheckEmailFoundUtil[].class);

                //SETTING DATA TO DATASTATUS
                mEmailList.add(checkEmailFoundUtils[0]);
                dataStatus.DataIsLoaded(mEmailList);

            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
