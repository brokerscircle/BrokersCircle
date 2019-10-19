package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import brokerscircle.com.R;
import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;
import brokerscircle.com.util.Helper;

public class ForgotPasswordCodeActivity extends AppCompatActivity implements View.OnClickListener {

    Helper helper;

    private static final String TAG = "ForgotCodeActivity";
    //Volley services
    String forgotPasswordUrl = Constant.BASE_URL+"forgot-password/code-validation?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    private RelativeLayout mVerifyBTN;
    private PinEntryEditText pinEntry;
    private TextView mBacktoSignin;

    //Loading
    View progress_overlay;
    TextView mLoadingHeading;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Helper Instance
        helper = new Helper(this);
        LoginUser user = helper.getLoggedInUser();
        if (user != null) {    //Check if user if logged in
            Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            done(user);
        }else {
            setContentView(R.layout.forgot_password_code_activity);

            Intent intent = getIntent();
            id = intent.getStringExtra("userid");
            if (id.equals("")){
                Intent intent1 = new Intent(ForgotPasswordCodeActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show();
            }

            pinEntry = findViewById(R.id.txt_pin_entry);
            mVerifyBTN = findViewById(R.id.btn_verify);
            mBacktoSignin = findViewById(R.id.tv_backtosignin);

            progress_overlay = findViewById(R.id.progress_overlay);
            mLoadingHeading = findViewById(R.id.tvheading);

            mVerifyBTN.setOnClickListener(this);
            mBacktoSignin.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_verify:
                if ((pinEntry != null) && (!id.equals(""))) {
                    checkCode();
                }
                break;
            case R.id.tv_backtosignin:
                intent = new Intent(ForgotPasswordCodeActivity.this, LoginActivity.class);
                AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("LongLogTag")
    private void checkCode() {
        AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
        String code = pinEntry.getText().toString();
        Toast.makeText(ForgotPasswordCodeActivity.this, code, Toast.LENGTH_SHORT).show();

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("code", code);

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
                        Toast.makeText(ForgotPasswordCodeActivity.this, responseData.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "notifySuccess: "+responseData.getString("message"));
                    }else {
                        AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                        mLoadingHeading.setText("Verifing  your code please wait ...");
                        Intent intent = new Intent(ForgotPasswordCodeActivity.this, ForgotPasswordChangeActivity.class);
                        intent.putExtra("senduserid", id);
                        startActivity(intent);
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
                Toast.makeText(ForgotPasswordCodeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void done(LoginUser user) {
        startActivity(new Intent(this, DashboardActivityTwo.class));
        finish();
    }
}
