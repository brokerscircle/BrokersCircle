package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.Activities.ChangePassword.ChangePasswordActivity;
import brokerscirlce.com.adapters.StylesRecyclerviewAdapter;
import brokerscirlce.com.model.DashboardStyle;
import brokerscirlce.com.R;
import brokerscirlce.com.model.login_user.LoginUser;
import brokerscirlce.com.util.Helper;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Helper helper;
    private LoginUser user = null;

    //Topbar
    private LinearLayout mBackButton;
    private RoundedImageView userProfileIMG;
    private TextView mNameTextView, mRealEstateTextView;

    private RecyclerView recyclerViewDashboardStyles;
    private List<DashboardStyle> styles;
    private ToggleButton mDashboardStyle, mLogoutBtn, mChangePassword;

    private LinearLayout mLayoutDashboardCheckboxes;
    private CheckBox mDashboardOneCheck, mDashboardTwoCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        user = helper.getLoggedInUser();
        if (user == null){
            startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            finish();
        }else {
            setContentView(R.layout.setting_activity);

            initAppBar();

            initContent();
        }
    }

    private void initAppBar() {
        mBackButton = findViewById(R.id.btn_back);
        userProfileIMG = findViewById(R.id.profile_image);
        mNameTextView = findViewById(R.id.tv_user_name);
        mRealEstateTextView = findViewById(R.id.tv_real_state);

        mBackButton.setOnClickListener(this);

        //User Profile Image
        if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
            userProfileIMG.setImageResource(R.drawable.ic_user_icon_white);
        }else {
            Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_white).into(userProfileIMG);
        }
        //User Name
        mNameTextView.setText(user.getResponse().getData().getUser().getUserFullName());
        //User Real Estate
        mRealEstateTextView.setText(user.getResponse().getData().getUser().getCompName());
    }

    private void initContent() {
        mDashboardStyle = findViewById(R.id.dashboard_style);
        mChangePassword = findViewById(R.id.toggle_change_password);
        mLogoutBtn = findViewById(R.id.toggle_logout);

        initDashboaradStyles();

        mDashboardStyle.setOnCheckedChangeListener(this);
        mChangePassword.setOnCheckedChangeListener(this);
        mLogoutBtn.setOnCheckedChangeListener(this);
    }

    private void initDashboaradStyles() {

        mLayoutDashboardCheckboxes = findViewById(R.id.dashboardchecks);
        mDashboardOneCheck = findViewById(R.id.dashboard_one_check);
        mDashboardTwoCheck = findViewById(R.id.dashboard_two_check);
        recyclerViewDashboardStyles = findViewById(R.id.recyclerview);

        setUpStylesData();
    }

    private void setUpStylesData() {
        styles = new ArrayList<>();
        styles.add(new DashboardStyle(R.mipmap.style_one, "Style One", true));
        styles.add(new DashboardStyle(R.mipmap.style_two, "Style Two", false));
        styles.add(new DashboardStyle(R.mipmap.style_three, "Style Three",false));

        new StylesRecyclerviewAdapter().setConfig(recyclerViewDashboardStyles, SettingActivity.this,styles);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Intent intent;
        switch (compoundButton.getId()){
            case R.id.dashboard_style:
                if (b){
                    mLayoutDashboardCheckboxes.setVisibility(View.VISIBLE);
                }else {
                    mLayoutDashboardCheckboxes.setVisibility(View.GONE);
                }
                break;
            case R.id.dashboard_one_check:
                if (b){
                    mDashboardTwoCheck.setChecked(false);
                    recyclerViewDashboardStyles.setVisibility(View.VISIBLE);
                }else {
                    mDashboardTwoCheck.setChecked(true);
                    recyclerViewDashboardStyles.setVisibility(View.GONE);
                }
                break;
            case R.id.dashboard_two_check:
                if (b){
                    mDashboardOneCheck.setChecked(false);
                    recyclerViewDashboardStyles.setVisibility(View.GONE);
                }else {
                    mDashboardOneCheck.setChecked(true);
                    recyclerViewDashboardStyles.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.toggle_change_password:

                intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);

                break;
            case R.id.toggle_logout:
                helper.logout();
                intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
