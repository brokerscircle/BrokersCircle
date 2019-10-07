package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import brokerscirlce.com.Activities.AppIntro.IntroActivity;
import brokerscirlce.com.model.login_user.LoginUser;
import brokerscirlce.com.receivers.ConnectivityReceiver;
import brokerscirlce.com.R;
import brokerscirlce.com.util.Helper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Helper helper;

    Button mSignInBtn, mSignUpBtn, mWithoutLogin;
    RelativeLayout  mConnection_layout;
    Button mRetryButton;
    private ConnectivityReceiver receiver;

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
            setContentView(R.layout.main_activity);

            mConnection_layout = findViewById(R.id.connection_layout);
            mRetryButton = findViewById(R.id.retry_btn);
            mSignInBtn = findViewById(R.id.btn_sign_in);
            mSignUpBtn = findViewById(R.id.btn_sign_up);
            mWithoutLogin = findViewById(R.id.btn_without_ligin);

            //Check Internet Connection
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            receiver = new ConnectivityReceiver();
            registerReceiver(receiver, filter);
            //check network state

            mSignInBtn.setOnClickListener(this);
            mSignUpBtn.setOnClickListener(this);
            mWithoutLogin.setOnClickListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
            case R.id.btn_sign_up:
                startActivity(new Intent(MainActivity.this, SignUpStepOneActivity.class));
                break;
            case R.id.btn_without_ligin:
                startActivity(new Intent(MainActivity.this,IntroActivity.class));
                finish();
                break;
        }
    }

    private void done(LoginUser user) {
        startActivity(new Intent(this, DashboardActivityTwo.class));
        finish();
    }
}
