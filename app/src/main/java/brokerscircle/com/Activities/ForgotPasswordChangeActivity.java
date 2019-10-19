package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import brokerscircle.com.R;
import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;
import brokerscircle.com.util.Helper;

public class ForgotPasswordChangeActivity extends AppCompatActivity implements View.OnClickListener {

    Helper helper;


    private static final String TAG = "ForgotPasswordChange";
    //Volley services
    String forgotPasswordUrl = Constant.BASE_URL+"forgot-password/reset-password?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    private RelativeLayout mSubmitBtn;
    private EditText mNewPassword, mRetypePassword;
    private ImageView mNewPasswordDrawable, mRetypePasswordDrawable;
    private TextView mBackToSign;

    //Loading
    View progress_overlay;
    TextView mLoadingHeading;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        setContentView(R.layout.forgot_password_change_activity);

        Intent intent = getIntent();
        id = intent.getStringExtra("senduserid");

        mNewPassword = findViewById(R.id.et_newpassword);
        mNewPasswordDrawable = findViewById(R.id.drawable_newpassword);
        mRetypePassword = findViewById(R.id.et_retypepassword);
        mRetypePasswordDrawable = findViewById(R.id.drawable_retypepassword);
        mSubmitBtn = findViewById(R.id.btn_submit);
        mBackToSign = findViewById(R.id.tv_backtosignin);

        progress_overlay = findViewById(R.id.progress_overlay);
        mLoadingHeading = findViewById(R.id.tvheading);

        mNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.passwordValid(mNewPassword, mNewPasswordDrawable);
            }
        });

        mRetypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.passwordRetypeValid(mNewPassword, mRetypePassword, mRetypePasswordDrawable);
            }
        });

        mSubmitBtn.setOnClickListener(this);
        mBackToSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_submit:
                if (helper.passwordValid(mNewPassword, mNewPasswordDrawable) && helper.passwordRetypeValid(mNewPassword, mRetypePassword, mRetypePasswordDrawable)){
                    AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                    mLoadingHeading.setText("Almost done! we are setting your password...");

                    SubmitPassword();
//                    intent = new Intent(ForgotPasswordChangeActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
                }else {
                    AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                }
                break;
            case R.id.tv_backtosignin:
                intent = new Intent(ForgotPasswordChangeActivity.this, LoginActivity.class);
                AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void SubmitPassword() {

        AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
        String password = mNewPassword.getText().toString();

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);

        Log.d(TAG, "forgotPasswordUser: "+map+" Url "+forgotPasswordUrl);
        mVolleyService.postDataVolley("POSTCALL", forgotPasswordUrl, map);

    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject responseData = jsonObject.getJSONObject("response");

                    if (responseData.getString("error").equals("true")){
                        AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                        Toast.makeText(ForgotPasswordChangeActivity.this, responseData.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "notifySuccess: "+responseData.getString("message"));
                    }else {
                        AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                        mLoadingHeading.setText("Please wait while we are set up your new password...");
                        Intent intent = new Intent(ForgotPasswordChangeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley requester " + error);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                Toast.makeText(ForgotPasswordChangeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
