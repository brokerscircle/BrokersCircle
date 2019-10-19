package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import brokerscircle.com.Activities.AppIntro.IntroActivity;
import brokerscircle.com.R;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.Helper;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private Helper helper;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    ShimmerTextView shimmer_tv,hub_shimmer_tv, shimmer_sub_tv, shimmer_copyright_tv, shimmer_web_tv, shimmer_loading_tv;
    Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Helper Instance
        helper = new Helper(this);
        setContentView(R.layout.splash_activity);

        shimmer_tv = findViewById(R.id.shimmer_tv);
        hub_shimmer_tv = findViewById(R.id.hub_shimmer_tv);
        shimmer_sub_tv = findViewById(R.id.shimmer_sub_tv);
        shimmer_copyright_tv = findViewById(R.id.shimmer_copyright_tv);
        shimmer_web_tv = findViewById(R.id.shimmer_web_tv);
        shimmer_loading_tv = findViewById(R.id.shimmer_loading_tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                LoginUser user = helper.getLoggedInUser();
                if (user != null) {    //Check if user if logged in
                    Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
                    done(user);
                } else {
                    Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStart() {

        shimmer = new Shimmer();
        shimmer.start(shimmer_tv);
        shimmer.start(hub_shimmer_tv);
        shimmer.start(shimmer_sub_tv);
//        shimmer.start(shimmer_copyright_tv);
//        shimmer.start(shimmer_web_tv);
//        shimmer.start(shimmer_loading_tv);

        super.onStart();
    }

    private void done(LoginUser user) {
        startActivity(new Intent(this, DashboardActivityTwo.class));
        finish();
    }
}
