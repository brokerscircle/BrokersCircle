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
import brokerscircle.com.model.Email.EmailData;
import brokerscircle.com.model.Email.EmailUtil;
import brokerscircle.com.services.VolleyService;

public class EmailDatabaseHelper {

    private String TAG = "EmailDatabaseHelper";
    private static final String EMAIL_LIST = Constant.BASE_URL+"emails/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;

    //Volley Services
    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private List<EmailData> mEmailList = new ArrayList<>();
    DataStatus dataStatus;
    Context mContext;

    public interface DataStatus{
        void DataIsLoaded(List<EmailData> emailData);
    }

    public EmailDatabaseHelper() {
    }

    public void readEmailList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST);
    }

    //Single Email
    public void readSingleEmail(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST+"&id="+id);
    }

    //Reference ID Email
    public void readEmailWithReferenceID(final DataStatus dataStatus, Context mContext, String ref_ID){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST+"&reference_id="+ref_ID);
    }

    //Reference Type Email
    public void readEmailWithReferenceType(final DataStatus dataStatus, Context mContext, String ref_Type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST+"&reference_type="+ref_Type);
    }

    //Reference ID + Reference Type Email
    public void readEmailWithReferenceIDAndReferenceType(final DataStatus dataStatus, Context mContext, String ref_ID, String ref_Type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST+"&reference_id="+ref_ID+"&reference_type="+ref_Type);
    }

    //Reference ID + Reference Type Email
    public void readEmailWithReferenceTypeAndVerified(final DataStatus dataStatus, Context mContext, String ref_Type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST+"&reference_type="+ref_Type+"&verification_status=Verified");
    }

    //Reference ID + Reference Type Email
    public void readEmailVerified(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",EMAIL_LIST+"&verification_status=Verified");
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,String response) {
                mEmailList.clear();

                String plain = Html.fromHtml(response).toString();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                EmailUtil[] emailUtils = gson.fromJson(plain, EmailUtil[].class);

                //SETTING DATA TO DATASTATUS
                mEmailList.addAll(emailUtils[0].getData());
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
