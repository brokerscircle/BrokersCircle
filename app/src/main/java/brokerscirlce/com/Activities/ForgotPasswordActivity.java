package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscirlce.com.R;
import brokerscirlce.com.adapters.DropDownSuggestionsArrayAdapters.PhoneCountryCodeArrayAdapter;
import brokerscirlce.com.api_helpers.PhoneCountryCodeAPIHelper;
import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.ForgotPassword.ForgotPasswordResponseApi;
import brokerscirlce.com.model.PhoneCountryCode.PhoneCountryCodeData;
import brokerscirlce.com.model.login_user.LoginUser;
import brokerscirlce.com.services.VolleyService;
import brokerscirlce.com.util.Constant;
import brokerscirlce.com.util.Helper;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ForgotPasswordActivity";
    private Helper helper;
    //Volley services
    String forgotPasswordUrl = Constant.BASE_URL+"api/forgot-password?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    EditText et_phone_code, et_phone;
    ImageView mPhoneCodeDrawable, mPhoneDrawable;
    SpinKitView loading_phone;
    TextView mBacktoMainTV;
    RelativeLayout mSendCodeBTN;

    //Bottom sheet content
    private LinearLayout mBottomSheet, mBottomSheetLoading;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText mBottomSheetSearch;
    private ListView mBottomSheetListView;
    private TextView mBottomSheetTitle;

    //List<PhoneCountryCodeData> phoneCountryCodeDataList = null;
    PhoneCountryCodeData phoneCountryCodeData;

    //Loading
    View progress_overlay;
    TextView mLoadingHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Helper Instance
        helper = new Helper(this);
        LoginUser user = helper.getLoggedInUser();
        if (user != null) {    //Check if user if logged in
            Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            done(user);
        } else {
            setContentView(R.layout.forgot_password_activity);

            //phoneCountryCodeDataList = new ArrayList<>();

            mBacktoMainTV = findViewById(R.id.tv_backtosignin);
            et_phone_code = findViewById(R.id.et_phone_code);
            et_phone = findViewById(R.id.et_phone);
            mPhoneCodeDrawable = findViewById(R.id.drawable_phone_code);
            mPhoneDrawable = findViewById(R.id.drawable_phone);
            loading_phone = findViewById(R.id.loading_phone);
            mSendCodeBTN = findViewById(R.id.btn_sendnow);

            //Bottom sheet contents get id
            mBottomSheet  = findViewById(R.id.bottom_sheet_layout_list);
            bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            mBottomSheetSearch = findViewById(R.id.search_view);
            mBottomSheetListView = findViewById(R.id.dialoglist);
            mBottomSheetTitle = findViewById(R.id.bottomsheettitle);
            mBottomSheetLoading = findViewById(R.id.loadingdata);

            progress_overlay = findViewById(R.id.progress_overlay);
            mLoadingHeading = findViewById(R.id.tvheading);

            et_phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    helper.phoneValid(et_phone, mPhoneDrawable);
                }
            });

            et_phone_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    helper.nullFieldValidation(et_phone_code, mPhoneCodeDrawable,10,"Please select your country code!");
                }
            });

            //OnClick
            mBacktoMainTV.setOnClickListener(this);
            mSendCodeBTN.setOnClickListener(this);


            getPhoneCountryCode();
        }
    }

    private void getPhoneCountryCode() {
        final PhoneCountryCodeArrayAdapter[] phoneCountryCodeArrayAdapter = new PhoneCountryCodeArrayAdapter[1];
        new PhoneCountryCodeAPIHelper().readPhoneCodeList(new PhoneCountryCodeAPIHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PhoneCountryCodeData> phoneCountryCodeData1) {
//                phoneCountryCodeDataList.clear();
//                phoneCountryCodeDataList = phoneCountryCodeData1;
                if(phoneCountryCodeData1.isEmpty()){
                    mBottomSheetLoading.setVisibility(View.VISIBLE);
                }else {
                    mBottomSheetLoading.setVisibility(View.GONE);
                    phoneCountryCodeArrayAdapter[0] = new PhoneCountryCodeArrayAdapter(ForgotPasswordActivity.this, phoneCountryCodeData1);
                    mBottomSheetListView.setAdapter(phoneCountryCodeArrayAdapter[0]);
                }

            }
        }, ForgotPasswordActivity.this);

        //CustomDialogBox customDialogBox = new CustomDialogBox();
        et_phone_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }else {
                    mBottomSheetTitle.setText("Select country phone code");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mBottomSheetSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            phoneCountryCodeArrayAdapter[0].getFilter().filter(charSequence.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    mBottomSheetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            phoneCountryCodeData = (PhoneCountryCodeData) adapterView.getItemAtPosition(i);
                            et_phone_code.setText(phoneCountryCodeData.getCode());
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view){
            Intent intent;

            switch (view.getId()) {
                case R.id.tv_backtosignin:
                    intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                    AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_sendnow:
                    if (helper.nullFieldValidation(et_phone_code, mPhoneCodeDrawable,10,"Please select your country code!") &&
                        helper.nullFieldValidation(et_phone, mPhoneDrawable,50,"Please select phone number!")) {
                        AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                        mLoadingHeading.setText("Sending code to " + et_phone_code.getText() + et_phone.getText() + " ...");
                        checkingAndSendCode();
                    } else {
                        AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                    }

                    break;
            }

        }

    private void checkingAndSendCode() {
        String codeid = et_phone_code.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);

        Map<String, String> map = new HashMap<>();
        map.put("country_code_id", codeid);
        map.put("number", phone);

        Log.d(TAG, "forgotPasswordUser: "+map+" Url "+forgotPasswordUrl);
        mVolleyService.postDataVolley("POSTCALL", forgotPasswordUrl, map);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ForgotPasswordResponseApi forgotPasswordResponseApi = gson.fromJson(plain, ForgotPasswordResponseApi.class);
                Log.d(TAG, "notifySuccess: code "+forgotPasswordResponseApi.getResponse().getCode());
                if (forgotPasswordResponseApi.getResponse().getData() != null && forgotPasswordResponseApi.getResponse().getCode().equals("200")){
                    AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                    Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordCodeActivity.class);
                    intent.putExtra("userid",forgotPasswordResponseApi.getResponse().getData().getId());
                    startActivity(intent);
                }else {
                    AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                    Toast.makeText(ForgotPasswordActivity.this, forgotPasswordResponseApi.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "response error: "+forgotPasswordResponseApi.getResponse().getMessage());
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley requester " + error);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void done(LoginUser user) {
        startActivity(new Intent(this, DashboardActivityTwo.class));
        finish();
    }
}


