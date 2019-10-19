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
import brokerscircle.com.model.Addresses.AddressUtil;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.services.VolleyService;

public class AddressAPIHelper {

    private String TAG = "AddressAPIHelper";
    private static final String ALL_ADDRESS_LIST = Constant.BASE_URL+"addresses/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    private static final String SINGLE_ADDRESS_DATA = Constant.BASE_URL+"addresses/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&id=";
    private static final String ADDRESS_REF_ID = Constant.BASE_URL+"addresses/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&reference_id=";
    private static final String ADDRESS_REF_TYPE = Constant.BASE_URL+"addresses/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&reference_type=";
    private static final String ADDRESS_Verified = Constant.BASE_URL+"addresses/list?app_id="+ Constant.APP_ID+"&app_key="+Constant.APP_KEY+"&verification_status=Verified";

    //Volley services
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    DataStatus dataStatus;
    Context mContext;
    private List<AddressesData> mAddressList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<AddressesData> addressesData);
    }

    public AddressAPIHelper() {
    }

    public void readAdressesList(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ALL_ADDRESS_LIST);
    }

    //Single Address
    public void readSingleAdressesData(final DataStatus dataStatus, Context mContext, String id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",SINGLE_ADDRESS_DATA+id);
    }

    //Refrence Address
    public void readRefrenceAdressesData(final DataStatus dataStatus, Context mContext, String reference_id){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ADDRESS_REF_ID+reference_id);
    }

    //Refrence Type Address
    public void readRefrenceTypeAdressesData(final DataStatus dataStatus, Context mContext, String reference_Type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ADDRESS_REF_TYPE+reference_Type);
    }

    //Refrence with type Address
    public void readRefrenceWithTypeAdressesData(final DataStatus dataStatus, Context mContext, String reference_id, String reference_Type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ADDRESS_REF_ID+reference_id+"&reference_type="+reference_Type);
    }

    //Refrence type verified Address
    public void readAdressesVerifiedType(final DataStatus dataStatus, Context mContext, String reference_Type){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ADDRESS_REF_TYPE+reference_Type+"&verification_status=Verified"+reference_Type);
    }

    //Verified Address
    public void readAdressesVerified(final DataStatus dataStatus, Context mContext){
        this.dataStatus = dataStatus;
        this.mContext = mContext;
        mAddressList.clear();

        //Getting Json from url
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,mContext);
        mVolleyService.getDataVolley("GETCALL",ADDRESS_Verified);
    }

    private void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                AddressUtil[] addressUtils = gson.fromJson(plain, AddressUtil[].class);
                //SETTING DATA TO DATASTATUS
                mAddressList.addAll(addressUtils[0].getData());
                dataStatus.DataIsLoaded(mAddressList);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
