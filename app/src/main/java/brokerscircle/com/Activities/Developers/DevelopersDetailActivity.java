package brokerscircle.com.Activities.Developers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.Activities.LoginActivity;
import brokerscircle.com.api_helpers.FollowersDatabaseHelper;
import brokerscircle.com.model.Followers.FollowPostResponseUtils;
import brokerscircle.com.model.Followers.FollowersData;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.CustomDialogBox;
import brokerscircle.com.fragments.TabsFragments.DeveloperTabs.DeveloperBrokersTabFragment;
import brokerscircle.com.fragments.TabsFragments.DeveloperTabs.DeveloperCompletedTabFragment;
import brokerscircle.com.fragments.TabsFragments.DeveloperTabs.DeveloperInProgressTabFragment;
import brokerscircle.com.fragments.TabsFragments.DeveloperTabs.DeveloperOffPlanTabFragment;
import brokerscircle.com.fragments.TabsFragments.DeveloperTabs.DeveloperReviewTabFragment;
import brokerscircle.com.api_helpers.DevelopersDatabaseHelper;
import brokerscircle.com.api_helpers.ExternalLinkDatabaseHelper;
import brokerscircle.com.api_helpers.PhoneDatabaseHelper;
import brokerscircle.com.model.Developers.DevelopersData;
import brokerscircle.com.model.ExternelLink.ExternalLinkData;
import brokerscircle.com.model.Phone.PhoneData;
import brokerscircle.com.R;
import brokerscircle.com.util.Helper;

public class DevelopersDetailActivity extends AppCompatActivity {

    private static final String TAG = "DevelopersDetailActivity";
    private Helper helper;
    LoginUser user;

    //loading view
    private ShimmerFrameLayout mShimmerViewContainer;
    private EditText mSearchLayout;
    private TextView mFollowerTV, mBrokersCountTV, mDeveloperNameTV, mProjectCompletedTV, mPinProgressTV, mProjectOFFPlanTV, mAverageRatingTV;
    private TabLayout mTabLayout;
    private ImageView mBackButton, mChatBtn;
    private RoundedImageView mDeveloperImage;
    private ToggleButton mWebsiteBTN, mSMSBtn, mCallBtn, mFollowBtn;
    private RatingBar mRating;

    //Click animation instance
    ScaleAnimation scaleAnimation = null;

    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String developerID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new Helper(this);
        user = helper.getLoggedInUser();

        if (user == null) {    //Check if user if logged in
            //Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            Intent intent = new Intent(DevelopersDetailActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {

            setContentView(R.layout.developer_detail_activity);
            //Getting Id of from intent
            developerID = getIntent().getStringExtra("developerid");

            //Getting layout ids
            mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
            mSearchLayout = findViewById(R.id.et_search);
            mTabLayout = findViewById(R.id.tablayout);
            frameLayout=findViewById(R.id.framelayout);
            mBackButton = findViewById(R.id.btn_back);
            mChatBtn = findViewById(R.id.img_chat);
            mFollowerTV = findViewById(R.id.tv_followers);
            mBrokersCountTV = findViewById(R.id.tv_brokers_count);
            mDeveloperImage = findViewById(R.id.img_profile);
            mDeveloperNameTV = findViewById(R.id.tv_name);
            mWebsiteBTN = findViewById(R.id.btn_website);
            mSMSBtn = findViewById(R.id.btn_sms);
            mCallBtn = findViewById(R.id.btn_call);
            mFollowBtn = findViewById(R.id.btn_follow);
            mProjectCompletedTV = findViewById(R.id.tv_project_completed_count);
            mPinProgressTV = findViewById(R.id.tv_pro_progress_count);
            mProjectOFFPlanTV = findViewById(R.id.tv_off_plan_count);
            mAverageRatingTV = findViewById(R.id.tv_rattingcount);
            mRating = findViewById(R.id.rating);
            //End Getting layout ids

            // Click Animation ---------------------------------------------------------------------------
            //Animation Button Click
            scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            // Click Animation ---------------------------------------------------------------------------

            //Button Actions
            mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            //Button Actions
            mChatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DevelopersDetailActivity.this, ChatActivity.class);
                    startActivity(intent);
                }
            });

            //Follow click
            mFollowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(scaleAnimation);
                    if (mFollowBtn.isChecked()){
                        followingMethod();
                    }else {
                        unfollowMethod();
                    }
                }
            });

            implementTabs(developerID);

            //Get data from urls
            getData(developerID);
            //Get Phone details
            getPhoneNumber(developerID);
            //get website Detail
            getWebsite(developerID);

        }
    }


    private void unfollowMethod() {

        new FollowersDatabaseHelper().readFollowerWithToAndFromAndType(new FollowersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FollowersData> usersData) {
                if (!usersData.isEmpty()){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id",usersData.get(0).getId());
                    params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

                    new FollowersDatabaseHelper().DeleteFollower(new FollowersDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<FollowersData> usersData) {

                        }

                        @Override
                        public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                        }

                        @Override
                        public void DataIsDeleted(List<FollowPostResponseUtils> responseUtils) {
                            if (responseUtils.get(0).getError().equals("false")){
                                Toast.makeText(DevelopersDetailActivity.this, "Successfully UnFollow", Toast.LENGTH_SHORT).show();
                                mFollowBtn.setChecked(false);
                            }else {
                                Toast.makeText(DevelopersDetailActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                                mFollowBtn.setChecked(true);
                            }
                        }
                    }, DevelopersDetailActivity.this,params);
                }else {
                    mFollowBtn.setChecked(false);
                }
            }

            @Override
            public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

            }

            @Override
            public void DataIsDeleted(List<FollowPostResponseUtils> responseUtils) {

            }

        }, DevelopersDetailActivity.this,developerID, user.getResponse().getData().getUser().getUserId(),"UserToDeveloper");
    }

    private void followingMethod() {

        Map<String,String> params = new HashMap<String, String>();

        params.put("followed_from_id",user.getResponse().getData().getUser().getUserId());
        params.put("followed_to_id",developerID);
        params.put("followed_type","UserToDeveloper");
        params.put("created_by_comp_id",user.getResponse().getData().getUser().getCompId());
        params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

        new FollowersDatabaseHelper().PostFollower(new FollowersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FollowersData> usersData) {

            }

            @Override
            public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                if (responseUtils.get(0).getError().equals("false")){
                    Toast.makeText(DevelopersDetailActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                    mFollowBtn.setChecked(true);
                }else {
                    Toast.makeText(DevelopersDetailActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    mFollowBtn.setChecked(false);
                }
            }

            @Override
            public void DataIsDeleted(List<FollowPostResponseUtils> usersData) {

            }
        },DevelopersDetailActivity.this,params);

    }

    private void getWebsite(String developerID) {

        new ExternalLinkDatabaseHelper().readExternalLinkWithReferenceID_AND_ReferenceType(new ExternalLinkDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ExternalLinkData> externalLinkData) {
                if (externalLinkData.isEmpty()){
                    mWebsiteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            //animation
                            compoundButton.startAnimation(scaleAnimation);
                            if (externalLinkData.isEmpty()){
                                mWebsiteBTN.setChecked(false);
                                Toast.makeText(DevelopersDetailActivity.this, "No website Available", Toast.LENGTH_SHORT).show();
                            }else {
                                mWebsiteBTN.setChecked(true);
                                new CustomDialogBox().showWebsiteDialog(DevelopersDetailActivity.this, externalLinkData);
                            }
                        }
                    });
                }
            }
        }, DevelopersDetailActivity.this, developerID, "Developer");

    }

    private void getPhoneNumber(String developerid) {
        new PhoneDatabaseHelper().readPhoneList(new PhoneDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PhoneData> phoneData) {

                //SMS click
                mSMSBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        //animation
                        compoundButton.startAnimation(scaleAnimation);

                        //Check if list is not null
                        if (phoneData.isEmpty()){
                            mSMSBtn.setChecked(false);
                            Toast.makeText(DevelopersDetailActivity.this, "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mSMSBtn.setChecked(true);
                            new CustomDialogBox().showSMSDialog(DevelopersDetailActivity.this, phoneData);
                        }
                    }
                });

                //CALL click
                mCallBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        //animation
                        compoundButton.startAnimation(scaleAnimation);
                        if (phoneData.isEmpty()){
                            mCallBtn.setChecked(false);
                            Toast.makeText(DevelopersDetailActivity.this, "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mCallBtn.setChecked(true);
                            new CustomDialogBox().showCallDialog(DevelopersDetailActivity.this, phoneData);
                        }
                    }
                });

            }
        }, this, developerid,"PropertyDeveloper");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    private void getData(String developerID) {
        new DevelopersDatabaseHelper().readDevelopersDetail(new DevelopersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<DevelopersData> developersData) {

                //Followers
                mFollowerTV.setText(String.format("%s Followers", (developersData.get(0).getNoOfFollowers() == null)? "0" :  developersData.get(0).getNoOfFollowers()));

                //Workers/Brokers
                mBrokersCountTV.setText(String.format("%s Brokers worked in", (developersData.get(0).getNoOfBrokers() == null)? "0" :  developersData.get(0).getNoOfBrokers()));

                //Images
                if (developersData.get(0).getLogo() == null){
                    mDeveloperImage.setImageResource(R.drawable.ic_developer_icon_color);
                }else{
                    Picasso.get().load(developersData.get(0).getLogo().toString()).placeholder(R.drawable.ic_developer_icon_color).centerCrop().fit().into(mDeveloperImage);
                }

                //EstateName
                mDeveloperNameTV.setText(developersData.get(0).getTitle());

                //follow btn
                new FollowersDatabaseHelper().readFollowerWithToAndFromAndType(new FollowersDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<FollowersData> usersData) {
                        if (!usersData.isEmpty()){
                            mFollowBtn.setChecked(true);
                        }else {
                            mFollowBtn.setChecked(false);
                        }
                    }

                    @Override
                    public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                    }

                    @Override
                    public void DataIsDeleted(List<FollowPostResponseUtils> responseUtils) {

                    }

                }, DevelopersDetailActivity.this,developerID, user.getResponse().getData().getUser().getUserId(),"UserToDeveloper");


                //Project Completed
                mProjectCompletedTV.setText((developersData.get(0).getNoOfProjectCompleted() == null)? " 0" :  " "+developersData.get(0).getNoOfProjectCompleted().toString());

                //Project In Progress
                mPinProgressTV.setText((developersData.get(0).getNoOfProjectInProgress() == null)? " 0" :  " "+developersData.get(0).getNoOfProjectInProgress().toString());

                //Project OFF PLAN
                mProjectOFFPlanTV.setText((developersData.get(0).getNoOfProjectOffPlan() == null)? " 0" :  " "+developersData.get(0).getNoOfProjectOffPlan().toString());

                //Ratting Average
                mAverageRatingTV.setText((developersData.get(0).getAverageRating() == null)? "0" :  " "+developersData.get(0).getAverageRating().toString());

                //Set Rating
                mRating.setRating((developersData.get(0).getAverageRating() == null)? (float) 0 : (float) developersData.get(0).getAverageRating());
                LayerDrawable stars = (LayerDrawable) mRating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, this, developerID);
    }

    private void implementTabs(String developerID) {

        Bundle bundle = new Bundle();
        bundle.putString("developerID", developerID );

        fragment = new DeveloperInProgressTabFragment();
        //Setting Estate id in bundles
        fragment.setArguments(bundle);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DeveloperInProgressTabFragment();
                        break;
                    case 1:
                        fragment = new DeveloperCompletedTabFragment();
                        break;
                    case 2:
                        fragment = new DeveloperOffPlanTabFragment();
                        break;
                    case 3:
                        fragment = new DeveloperReviewTabFragment();
                        break;
                    case 4:
                        fragment = new DeveloperBrokersTabFragment();
                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                //Setting Estate id in bundles
                fragment.setArguments(bundle);

                ft.replace(R.id.framelayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
