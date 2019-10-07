package brokerscirlce.com.Activities.Developers;

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

import java.util.List;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.util.CustomDialogBox;
import brokerscirlce.com.fragments.TabsFragments.DeveloperTabs.DeveloperBrokersTabFragment;
import brokerscirlce.com.fragments.TabsFragments.DeveloperTabs.DeveloperCompletedTabFragment;
import brokerscirlce.com.fragments.TabsFragments.DeveloperTabs.DeveloperInProgressTabFragment;
import brokerscirlce.com.fragments.TabsFragments.DeveloperTabs.DeveloperOffPlanTabFragment;
import brokerscirlce.com.fragments.TabsFragments.DeveloperTabs.DeveloperReviewTabFragment;
import brokerscirlce.com.api_helpers.DevelopersDatabaseHelper;
import brokerscirlce.com.api_helpers.ExternalLinkDatabaseHelper;
import brokerscirlce.com.api_helpers.PhoneDatabaseHelper;
import brokerscirlce.com.model.Developers.DevelopersData;
import brokerscirlce.com.model.ExternelLink.ExternalLinkData;
import brokerscirlce.com.model.Phone.PhoneData;
import brokerscirlce.com.R;

public class DevelopersDetailActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_detail_activity);

        //Getting Id of from intent
        String developerID = getIntent().getStringExtra("developerid");

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
        mFollowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //animation
                compoundButton.startAnimation(scaleAnimation);
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
