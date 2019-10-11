package brokerscirlce.com.Activities.Brokers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.Activities.DashboardActivityTwo;
import brokerscirlce.com.Activities.Estates.EstateDetailActivity;
import brokerscirlce.com.Activities.LoginActivity;
import brokerscirlce.com.Activities.ProfileFollowersActivity;
import brokerscirlce.com.api_helpers.FollowersDatabaseHelper;
import brokerscirlce.com.interfaces.IResult;
import brokerscirlce.com.model.Followers.FollowPostResponseUtils;
import brokerscirlce.com.model.Followers.FollowersData;
import brokerscirlce.com.model.login_user.LoginUser;
import brokerscirlce.com.services.VolleyService;
import brokerscirlce.com.util.Constant;
import brokerscirlce.com.util.CustomDialogBox;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfileAboutTabFragment;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfileFilesTabFragment;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfileGalleryTabFragment;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfileListingTab;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfilePastDealingTabFragment;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfileReviewTabFragment;
import brokerscirlce.com.fragments.TabsFragments.ProfileTabs.ProfileVideosTabFragment;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.api_helpers.PhoneDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Phone.PhoneData;
import brokerscirlce.com.R;
import brokerscirlce.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

public class BrokerProfileActivity extends AppCompatActivity {

    private static final String TAG = "BrokerProfileActivity";
    private Helper helper;
    LoginUser user;

    private ShimmerFrameLayout mShimmerViewContainer;

    private LinearLayout mBackButton;
    private EditText mSearchLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mChatBtn, mCountyFlag;
    private TextView mFollowesTV, mNameTv, mEstateNameTV, mListingCountTV, mBtoBDealingTV, mExperienceTV, mRatingAverageTV;
    private CircleImageView mProfileImage;
    private ToggleButton mSMSBtn, mCallBtn, mFollowBtn;
    private RatingBar mRating;

    //AlertDialog
    private Dialog dialog;
    //Dialog Content
    LinearLayout layoutInfo;
    CircleImageView dialogImage;
    ImageView dialogIcon;
    TextView dialogName, dialogEstate, dialogGoto, dialogDecline;
    //End Dialog Content

    //Click animation instance
    ScaleAnimation scaleAnimation = null;

    //For Tabs
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String brokerid = "";
    String followid = "";
    //String userid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new Helper(this);
        user = helper.getLoggedInUser();

        if (user == null) {    //Check if user if logged in
            //Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            Intent intent = new Intent(BrokerProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            brokerid = getIntent().getStringExtra("brokerid");
            //userid = user.getResponse().getData().getUser().getUserId();
            if (TextUtils.isEmpty(brokerid)){
                onBackPressed();
                Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
            }else {
                setContentView(R.layout.broker_profile_activity);

                //Getting layout ids
                mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
                mSearchLayout = findViewById(R.id.et_search);
                mTabLayout = findViewById(R.id.tablayout);
                frameLayout=findViewById(R.id.framelayout);
                mChatBtn = findViewById(R.id.img_chat);
                mBackButton = findViewById(R.id.btn_back);
                mFollowesTV = findViewById(R.id.tv_followers);
                mProfileImage = findViewById(R.id.img_profile);
                mCountyFlag = findViewById(R.id.img_flag);
                mNameTv = findViewById(R.id.tv_name);
                mEstateNameTV = findViewById(R.id.tv_estatename);
                mSMSBtn = findViewById(R.id.btn_sms);
                mCallBtn = findViewById(R.id.btn_call);
                mFollowBtn = findViewById(R.id.btn_follow);
                mListingCountTV = findViewById(R.id.tv_listingcount);
                mBtoBDealingTV = findViewById(R.id.tv_btobdealing_count);
                mExperienceTV = findViewById(R.id.tv_experience_count);
                mRatingAverageTV = findViewById(R.id.tv_rattingcount);
                mRating = findViewById(R.id.rating);
                //End Getting layout ids

                //Info Dialog ///////////////////////////////////////////////////////////////////////////////////////////////////////
                // Create custom dialog object
                dialog = new Dialog(BrokerProfileActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_info_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //Getting Dialog ids
                layoutInfo = dialog.findViewById(R.id.layoutinfo);
                dialogImage = dialog.findViewById(R.id.img_profile);
                dialogName = dialog.findViewById(R.id.tv_name);
                dialogEstate = dialog.findViewById(R.id.tv_estatename);
                dialogIcon = dialog.findViewById(R.id.icon_content);
                dialogGoto = dialog.findViewById(R.id.intent_btn);
                dialogDecline = dialog.findViewById(R.id.decline_btn);
                //End Dialog ids
                //End Info Dialog ///////////////////////////////////////////////////////////////////////////////////////////////////////

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
                        Intent intent = new Intent(BrokerProfileActivity.this, ChatActivity.class);
                        startActivity(intent);
                    }
                });

                //Follow click
//                mFollowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if (isChecked){
//                            followingBroker();
//                        }else {
//                            unfollowBroker();
//                        }
//                    }
//                });

                mFollowBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(scaleAnimation);
                        if (mFollowBtn.isChecked()){
                            followingBroker();
                        }else {
                            unfollowBroker();
                        }
                    }
                });

                implementTabs(brokerid);

                //Get data from urls
                getData(brokerid);
                //Get Phone details
                getPhoneNumber(brokerid);

            }
        }
    }

    private void unfollowBroker() {

        new FollowersDatabaseHelper().readFollowedFromWithFollowerID_AND_Type(new FollowersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FollowersData> usersData) {
                if (!usersData.isEmpty()){
                    for ( FollowersData followersData : usersData) {
                        if (followersData.getFollowedFromId().equals(user.getResponse().getData().getUser().getUserId())){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("id",followersData.getId());
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
                                        Toast.makeText(BrokerProfileActivity.this, "Successfully UnFollow", Toast.LENGTH_SHORT).show();
                                        mFollowBtn.setChecked(false);
                                    }else {
                                        Toast.makeText(BrokerProfileActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                                        mFollowBtn.setChecked(true);
                                    }
                                }
                            }, BrokerProfileActivity.this,params);

                        }
                    }
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

        }, BrokerProfileActivity.this,user.getResponse().getData().getUser().getUserId(),"UserToBroker");
    }

    private void followingBroker() {

        Map<String,String> params = new HashMap<String, String>();

        params.put("followed_from_id",user.getResponse().getData().getUser().getUserId());
        params.put("followed_to_id",brokerid);
        params.put("followed_type","UserToBroker");
        params.put("created_by_comp_id",user.getResponse().getData().getUser().getCompId());
        params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

        new FollowersDatabaseHelper().PostFollower(new FollowersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FollowersData> usersData) {

            }

            @Override
            public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                if (responseUtils.get(0).getError().equals("false")){
                    Toast.makeText(BrokerProfileActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                    mFollowBtn.setChecked(true);
                }else {
                    Toast.makeText(BrokerProfileActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    mFollowBtn.setChecked(false);
                }
            }

            @Override
            public void DataIsDeleted(List<FollowPostResponseUtils> usersData) {

            }
        },BrokerProfileActivity.this,params);

    }

    private void getPhoneNumber(String brokerid) {
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
                            Toast.makeText(BrokerProfileActivity.this, "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mSMSBtn.setChecked(true);
                            new CustomDialogBox().showSMSDialog(BrokerProfileActivity.this, phoneData);
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
                            Toast.makeText(BrokerProfileActivity.this, "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mCallBtn.setChecked(true);
                            new CustomDialogBox().showCallDialog(BrokerProfileActivity.this, phoneData);
                        }
                    }
                });
            }
        }, this, brokerid,"Broker");
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

    private void getData(String brokerid) {
        new BrokersDatabaseHelper().readBrokersDetail(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                if (brokersUtils.isEmpty()){
                    onBackPressed();
                    Toast.makeText(BrokerProfileActivity.this, "No User Found", Toast.LENGTH_SHORT).show();
                }else {
                    //Followers
                    mFollowesTV.setText(String.format("%s Followers", (brokersUtils.get(0).getNoOfFollowers() == null)? "0" :  brokersUtils.get(0).getNoOfFollowers()));
                    //Profile image
                    String profileImage = brokersUtils.get(0).getPicture();
                    if (profileImage.equals("")){
                        mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
                        //Info Dialog Profile Image
                        dialogImage.setImageResource(R.drawable.ic_user_icon_colored);
                    }else{
                        Picasso.get().load(profileImage).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
                        //Info Dialog Profile Image
                        Picasso.get().load(profileImage).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(dialogImage);
                    }

                    // Country Flag


                    //Brokers Name
                    mNameTv.setText(brokersUtils.get(0).getTitle());

                    //Info Dialog Name
                    dialogName.setText(brokersUtils.get(0).getTitle());

                    //Estate Name
                    if (brokersUtils.get(0).getRealEstateName() == null){
                        mEstateNameTV.setVisibility(View.GONE);
                        //Info Dialog Estate Name
                        dialogEstate.setVisibility(View.GONE);
                    }else {
                        mEstateNameTV.setText(brokersUtils.get(0).getRealEstateName());
                        //Info Dialog Estate Name
                        dialogEstate.setText(brokersUtils.get(0).getRealEstateName());
                    }

                    //follow btn
                    new FollowersDatabaseHelper().readFollowedFromWithFollowerID_AND_Type(new FollowersDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<FollowersData> usersData) {
                            if (!usersData.isEmpty()){
                                for ( FollowersData followersData : usersData) {
                                    if (followersData.getFollowedFromId().equals(user.getResponse().getData().getUser().getUserId())){
                                        mFollowBtn.setChecked(true);
                                    }
                                }
                            }
                        }

                        @Override
                        public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                        }

                        @Override
                        public void DataIsDeleted(List<FollowPostResponseUtils> responseUtils) {

                        }

                    }, BrokerProfileActivity.this,user.getResponse().getData().getUser().getUserId(),"UserToBroker");

                    //Listings
                    mListingCountTV.setText(" 0");

                    //Dealing
                    mBtoBDealingTV.setText((brokersUtils.get(0).getNoOfDeal() == null)? "0" : brokersUtils.get(0).getNoOfDeal().toString());

                    //Experience
                    mExperienceTV.setText((brokersUtils.get(0).getExperience() == null)? "0" : brokersUtils.get(0).getExperience().toString());

                    //Rating average
                    mRatingAverageTV.setText((brokersUtils.get(0).getAverageRating() == null)? "0" : brokersUtils.get(0).getAverageRating().toString());

                    //Set Rating
                    mRating.setRating((brokersUtils.get(0).getAverageRating() == null)? (float) 0 : (float) brokersUtils.get(0).getAverageRating());
                    LayerDrawable stars = (LayerDrawable) mRating.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                    mEstateNameTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(BrokerProfileActivity.this, EstateDetailActivity.class);
                            intent.putExtra("estateid", brokersUtils.get(0).getCreatedByCompId());
                            startActivity(intent);

                        }
                    });

                    mFollowesTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BrokerProfileActivity.this, ProfileFollowersActivity.class);
                            intent.putExtra("brokerid", brokerid);
                            startActivity(intent);
                        }
                    });

                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }
        },this,brokerid);
    }

    private void implementTabs(String brokerid) {

        Bundle bundle = new Bundle();
        bundle.putString("brokerid", brokerid);

        fragment = new ProfileListingTab();
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
                        fragment = new ProfileListingTab();
                        break;
                    case 1:
                        fragment = new ProfilePastDealingTabFragment();
                        break;
                    case 2:
                        fragment = new ProfileReviewTabFragment();
                        break;
                    case 3:
                        fragment = new ProfileGalleryTabFragment();
                        break;
                    case 4:
                        fragment = new ProfileVideosTabFragment();
                        break;
                    case 5:
                        fragment = new ProfileFilesTabFragment();
                        break;
                    case 6:
                        fragment = new ProfileAboutTabFragment();
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
