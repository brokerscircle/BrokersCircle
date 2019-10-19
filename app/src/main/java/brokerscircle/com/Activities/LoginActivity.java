package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import brokerscircle.com.R;
import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Helper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private Helper helper;
    //Volley services
    String loginURL = "https://brokerscircles.com/admin/public/api/oauth/token?";
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    EditText mEmailEditText, mPasswordEditText;
    ImageView mEmailDrawable, mPasswordDrawable;
    TextView mForgotPasswordTV, mCreateAccountTV;
    RelativeLayout mSignIn;
    //SpinKitView mLoading;
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
            setContentView(R.layout.login_activity);

            mEmailEditText = findViewById(R.id.et_email);
            mEmailDrawable = findViewById(R.id.drawable_email);
            mPasswordEditText = findViewById(R.id.et_password);
            mPasswordEditText = findViewById(R.id.et_password);
            mPasswordDrawable = findViewById(R.id.drawable_password);
            mForgotPasswordTV = findViewById(R.id.tv_forgot_password);
            mCreateAccountTV = findViewById(R.id.tv_create_account);
            mSignIn = findViewById(R.id.btn_sign_in);
            //mLoading = findViewById(R.id.spin_kit);
            progress_overlay = findViewById(R.id.progress_overlay);
            mLoadingHeading = findViewById(R.id.tvheading);

            //EditTextValidation
            mEmailEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    helper.emailOrPhoneValid(mEmailEditText, mEmailDrawable);
                }
            });
            //Password
            mPasswordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    helper.nullFieldValidation(mPasswordEditText, mPasswordDrawable,250, "password");
                }
            });

            // OnClick
            mForgotPasswordTV.setOnClickListener(this);
            mCreateAccountTV.setOnClickListener(this);
            //progress_overlay.setOnClickListener(this);
            mSignIn.setOnClickListener(this);

        }
    }

    private void done(LoginUser user) {
        Intent intent = new Intent(LoginActivity.this, DashboardActivityTwo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.tv_forgot_password:
                intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_create_account:
                intent = new Intent(LoginActivity.this, SignUpStepOneActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_sign_in:

                final String userEmail = mEmailEditText.getText().toString().trim();
                final String userPassword = mPasswordEditText.getText().toString().trim();

                if (helper.emailOrPhoneValid(mEmailEditText, mEmailDrawable) &&
                        helper.nullFieldValidation(mPasswordEditText, mPasswordDrawable,250, "Please enter your password!")){
                    AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                    mLoadingHeading.setText("Signing in...");
                    Log.d(TAG, "onClick: Email: "+userEmail+" password "+ userPassword);
                    signInUser(userEmail, userPassword, loginURL);
                }else {
                    AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                    //Toast.makeText(this, "Please fill empty field", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void signInUser(String userEmail, String userPassword, String loginURL) {

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);

        Map<String, String> map = new HashMap<>();
        map.put("email", userEmail);
        map.put("password", userPassword);

        Log.d(TAG, "signInUser: "+map+" Url "+loginURL);
        mVolleyService.postDataVolley("POSTCALL", loginURL, map);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                String plain = Html.fromHtml(response).toString();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                LoginUser loginUsers = gson.fromJson(plain, LoginUser.class);
                Log.d(TAG, "notifySuccess: code "+loginUsers.getResponse().getCode());
                if (loginUsers != null && loginUsers.getResponse().getCode() == 200){
                    helper.setLoggedInUser(loginUsers);
                    done(loginUsers);
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley requester " + error);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
