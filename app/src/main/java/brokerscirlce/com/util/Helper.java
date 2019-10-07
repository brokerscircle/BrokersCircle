package brokerscirlce.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brokerscirlce.com.R;
import brokerscirlce.com.model.System_Setting.SystemSettingData;
import brokerscirlce.com.model.login_user.LoginUser;

public class Helper {

    private static final String CURRENT_USER = "CURRENT_USER";
    private static final String SIGNUP_COLLECTION = "SIGNUP_COLLECTION";
    private static final String SYSTEM_SETTING = "SYSTEM_SETTING";

    //Validation Purpose
    private Pattern pattern;
    private Matcher matcher;
    final String EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    //final String PHONE_PATTERN = "^[0][1-9]\\d{9}$|^[1-9]\\d{9}$";
    final String PHONE_PATTERN = "^[1-9]\\d{9}$";
    final String NAME_PATTERN = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    //final String CNIC_PATTERN = "^[0-9+]{5}-[0-9+]{7}-[0-9]{1}$";
    final String CNIC_PATTERN = "^[0-9]{13}$";

    private SharedPreferenceHelper sharedPreferenceHelper;
    private Gson gson;

    public Helper(Context context){
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        gson = new Gson();
    }

    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return sharedPreferenceHelper;
    }

    public static void openPlayStore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    //Close Keyboard
    public static void closeKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String covertTimeToText(String dataDate) {
        String time = "";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            PrettyTime prettyTime = new PrettyTime();
            time = prettyTime.format(pasTime);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return time;
    }

    public SystemSettingData getSystemSetting() {
        String savedUserPref = sharedPreferenceHelper.getStringPreference(SYSTEM_SETTING);
        if (savedUserPref != null)
            return gson.fromJson(savedUserPref, new TypeToken<SystemSettingData>() {
            }.getType());
        return null;
    }

    public void setSystemSetting(SystemSettingData SystemSettingData) {
        sharedPreferenceHelper.setStringPreference(SYSTEM_SETTING, gson.toJson(SystemSettingData, new TypeToken<SystemSettingData>() {
        }.getType()));
    }

    public LoginUser getLoggedInUser() {
        String savedUserPref = sharedPreferenceHelper.getStringPreference(CURRENT_USER);
        if (savedUserPref != null)
            return gson.fromJson(savedUserPref, new TypeToken<LoginUser>() {
            }.getType());
        return null;
    }

    public void setLoggedInUser(LoginUser user) {
        sharedPreferenceHelper.setStringPreference(CURRENT_USER, gson.toJson(user, new TypeToken<LoginUser>() {
        }.getType()));
    }

    public void logout() {
        //sharedPreferenceHelper.clearPreference(CURRENT_USER);
        sharedPreferenceHelper.clear();
    }

    public boolean isLoggedIn() {
        return sharedPreferenceHelper.getStringPreference(CURRENT_USER) != null;
    }

    public void setSignupCollection(Map<String, String > signupCollection) {
        sharedPreferenceHelper.setStringPreference(SIGNUP_COLLECTION, gson.toJson(signupCollection, new TypeToken<Map<String, String>>() {
        }.getType()));
    }

    public Map<String, String> getSignupCollection() {
        String savedPref = sharedPreferenceHelper.getStringPreference(SIGNUP_COLLECTION);
        if (savedPref != null)
            return gson.fromJson(savedPref, new TypeToken<Map<String, String>>() {
            }.getType());
        return null;
    }

    public void clearSignUpCollection() {
        sharedPreferenceHelper.clearPreference(SIGNUP_COLLECTION);
    }

    /////////////////////////////////////////Validations\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public boolean emailOrPhoneValid(EditText mEmailEditText, ImageView mEmailDrawable) {
        if (mEmailEditText.getText().toString().trim().equalsIgnoreCase("")) {

            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEmailEditText.setError("Please enter email address or phone!", null);
                }
            });
            return false;

        }else if (!isEmailValid(mEmailEditText) && !isPhoneValid(mEmailEditText)){

            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEmailEditText.setError("Please enter a valid phone or email address! \nlike yourname@mail.com", null);
                }
            });
            return false;

        }else if (mEmailEditText.getText().toString().length() > 50){
            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEmailEditText.setError("Please enter maximum 50 characters!",null);
                }
            });
            return false;
        }else {

            mEmailDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circlegreen);
            mEmailDrawable.setOnClickListener(null);
            mEmailEditText.setError(null);
            return true;
        }
    }

    public boolean emailValid(EditText mEmailEditText, ImageView mEmailDrawable) {
        if (mEmailEditText.getText().toString().trim().equalsIgnoreCase("")) {

            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEmailEditText.setError("Please enter email address!", null);
                }
            });
            return false;

        }else if (!isEmailValid(mEmailEditText)){

            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEmailEditText.setError("Please enter a valid phone or email address! \nlike yourname@mail.com", null);
                }
            });
            return false;

        }else {
            mEmailDrawable.setVisibility(View.VISIBLE);
            mEmailDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circlegreen);
            mEmailDrawable.setOnClickListener(null);
            mEmailEditText.setError(null);
            return true;
        }
    }

    public boolean phoneValid(EditText mEditText, ImageView mEmailDrawable) {
        if (mEditText.getText().toString().trim().equalsIgnoreCase("")) {

            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter your email address!", null);
                }
            });
            return false;

        }else if (!isPhoneValid(mEditText)){

            mEmailDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circle_red);
            mEmailDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter a valid phone number", null);
                }
            });
            return false;

        }else {
            mEmailDrawable.setVisibility(View.VISIBLE);
            mEmailDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mEmailDrawable.setBackgroundResource(R.drawable.circlegreen);
            mEmailDrawable.setOnClickListener(null);
            mEditText.setError(null);
            return true;
        }
    }

    public boolean passwordValid(EditText mPasswordEditText, ImageView mDrawable) {
        if (mPasswordEditText.getText().toString().trim().equalsIgnoreCase("")){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPasswordEditText.setError("Please enter password!",null);
                }
            });
            return false;
        }else if (mPasswordEditText.getText().toString().trim().length() < 6){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPasswordEditText.setError("Password must be contain atleast 6 characters!",null);
                }
            });
            return false;
        }else if (mPasswordEditText.getText().toString().length() > 30){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPasswordEditText.setError("Please enter maximum 30 characters!",null);
                }
            });
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            mPasswordEditText.setError(null);
            return true;
        }
    }

    public boolean passwordRetypeValid(EditText mNewPasswordEditText, EditText mRetypePasswordEditText, ImageView mDrawable) {
        if (!mRetypePasswordEditText.getText().toString().trim().equals(mNewPasswordEditText.getText().toString().trim())){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRetypePasswordEditText.setError("Password did't match!",null);
                }
            });
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            mRetypePasswordEditText.setError(null);
            return true;
        }
    }

    public boolean nullFieldValidation(EditText mEditText, ImageView mDrawable, int length, String text) {
        if (mEditText.getText().toString().trim().equalsIgnoreCase("")){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter "+text+"!",null);
                }
            });
            return false;
        }else if (mEditText.getText().toString().trim().length() > length) {
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter maximum " + length + " lengths characters!", null);
                }
            });
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            mEditText.setError(null);
            return true;
        }
    }

    public boolean isLengthValidation(EditText mEditText, ImageView mDrawable, int length) {
        if (mEditText.getText().toString().trim().length() > length) {
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter maximum " + length + " digits!", null);
                }
            });
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            mEditText.setError(null);
            return true;
        }
    }

    public boolean personNameValidation(EditText mEditText, ImageView mDrawable) {
        if (mEditText.getText().toString().trim().equalsIgnoreCase("")){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter name!",null);
                }
            });
            return false;
        }else if (!isValidName(mEditText)){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter valid name like (Your Name)!\nnote: don't use any (number, symbol)",null);
                }
            });
            return false;
        }else if (mEditText.getText().toString().length()> 50){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter maximum 50 characters!",null);
                }
            });
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            mEditText.setError(null);
            return true;
        }
    }

    public boolean cnicValidation(EditText mEditText, ImageView mDrawable) {
        if (!isValidCNIC(mEditText)){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter valid CNIC like (12345-1234567-9)!",null);
                }
            });
            return false;
        }else if (mEditText.getText().toString().length()> 30){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            mDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditText.setError("Please enter maximum 30 digits!",null);
                }
            });
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            mEditText.setError(null);
            return true;
        }
    }

    private boolean isValidCNIC(EditText mEditText) {
        pattern = Pattern.compile(CNIC_PATTERN);
        matcher = pattern.matcher(mEditText.getText().toString().trim());
        return matcher.matches();
    }

    private boolean isPhoneValid(EditText mEditText) {
        return Pattern.compile(PHONE_PATTERN).matcher(mEditText.getText().toString().trim()).matches();
    }
//    private boolean isPhoneValid(EditText mEditText) {
//        return android.util.Patterns.PHONE.matcher(mEditText.getText().toString().trim()).matches();
//    }

    private boolean isEmailValid(EditText mEmailEditText) {
        return Pattern.compile(EMAIL_PATTERN).matcher(mEmailEditText.getText().toString().trim()).matches();
    }

//    private boolean isEmailValid(EditText mEmailEditText) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailEditText.getText().toString().trim()).matches();
//    }

    private boolean isValidName(EditText mNameEditText) {
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(mNameEditText.getText().toString().trim());
        return matcher.matches();
    }

    public boolean isSpinnerSelected(Spinner mSpinner, ImageView mDrawable) {
        if (mSpinner.getSelectedItem() == null || mSpinner.getSelectedItemPosition() == 0){
            mDrawable.setImageResource(R.drawable.ic_close_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circle_red);
            return false;
        }else{
            mDrawable.setImageResource(R.drawable.ic_done_all_black_24dp);
            mDrawable.setBackgroundResource(R.drawable.circlegreen);
            mDrawable.setOnClickListener(null);
            return true;
        }
    }
}
