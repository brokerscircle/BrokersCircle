package brokerscirlce.com.Activities.Estates;

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
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateAboutTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateBrokersTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateFilesTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateGalleryTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateListingTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstatePastDealingTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateReviewTabFragment;
import brokerscirlce.com.fragments.TabsFragments.EstateTabs.EstateVideoTabFragment;
import brokerscirlce.com.api_helpers.ExternalLinkDatabaseHelper;
import brokerscirlce.com.api_helpers.PhoneDatabaseHelper;
import brokerscirlce.com.api_helpers.RealEstateDatabaseHelper;
import brokerscirlce.com.model.ExternelLink.ExternalLinkData;
import brokerscirlce.com.model.Phone.PhoneData;
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.R;

public class EstateDetailActivity extends AppCompatActivity {

    //loading view
    private ShimmerFrameLayout mShimmerViewContainer;

    private EditText mSearchLayout;
    private TextView mFollowerTV, mBrokersCountTV, mEstateNameTV, mListingTV, mREtoREDealingTV, mSinceTV, mAverageRatingTV;
    private TabLayout mTabLayout;
    private ImageView mBackButton, mChatBtn;
    private RoundedImageView mEstateImage;
    private ToggleButton mWebsiteBTN, mSMSBtn, mCallBtn, mFollowBtn;
    private RatingBar mRating;

    //Click animation instance
    ScaleAnimation scaleAnimation = null;

    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String estateID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estate_detail_activity);

        //Getting Id of from intent
        String estateID = getIntent().getStringExtra("estateid");

        //Getting layout ids
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSearchLayout = findViewById(R.id.et_search);
        mTabLayout = findViewById(R.id.tablayout);
        frameLayout=findViewById(R.id.framelayout);
        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.img_chat);
        mFollowerTV = findViewById(R.id.tv_followers);
        mBrokersCountTV = findViewById(R.id.tv_brokers_count);
        mEstateImage = findViewById(R.id.img_profile);
        mEstateNameTV = findViewById(R.id.tv_estate_name);
        mWebsiteBTN = findViewById(R.id.btn_website);
        mSMSBtn = findViewById(R.id.btn_sms);
        mCallBtn = findViewById(R.id.btn_call);
        mFollowBtn = findViewById(R.id.btn_follow);
        mListingTV = findViewById(R.id.tv_listingcount);
        mREtoREDealingTV = findViewById(R.id.tv_retoredealing_count);
        mSinceTV = findViewById(R.id.tv_since);
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
                Intent intent = new Intent(EstateDetailActivity.this, ChatActivity.class);
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

        implementTabs(estateID);

        //Get data from urls
        getData(estateID);
        //Get Phone details
        getPhoneNumber(estateID);
        //get website Detail
        getWebsite(estateID);
    }

    private void getWebsite(String estateID) {

        new ExternalLinkDatabaseHelper().readExternalLinkWithReferenceID_AND_ReferenceType(new ExternalLinkDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ExternalLinkData> externalLinkData) {
                //websiteclick
                    mWebsiteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            //animation
                            compoundButton.startAnimation(scaleAnimation);
                            if (externalLinkData.isEmpty()){
                                mWebsiteBTN.setChecked(false);
                                Toast.makeText(EstateDetailActivity.this, "No website Available", Toast.LENGTH_SHORT).show();
                            }else {
                                mWebsiteBTN.setChecked(true);
                                new CustomDialogBox().showWebsiteDialog(EstateDetailActivity.this, externalLinkData);
                            }
                        }
                    });

            }
        }, EstateDetailActivity.this, estateID, "Company");

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
                            Toast.makeText(EstateDetailActivity.this, "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{

                            mSMSBtn.setChecked(true);
                            new CustomDialogBox().showSMSDialog(EstateDetailActivity.this, phoneData);
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
                            Toast.makeText(EstateDetailActivity.this, "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mCallBtn.setChecked(true);
                            new CustomDialogBox().showCallDialog(EstateDetailActivity.this, phoneData);
                        }
                    }
                });

            }
        }, this, brokerid,"Estate");
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

    private void getData(String estateID) {
        new RealEstateDatabaseHelper().readEstateDetail(new RealEstateDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<RealEstateData> realEstateData) {

                //Followers
                mFollowerTV.setText(String.format("%s Followers", (realEstateData.get(0).getNoOfFollowers() == null)? "0" :  realEstateData.get(0).getNoOfFollowers()));

                //Workers/Brokers
                mBrokersCountTV.setText(String.format("%s Brokers worked in", (realEstateData.get(0).getNoOfEmployee() == null)? "0" :  realEstateData.get(0).getNoOfEmployee()));

                //Images
                if (realEstateData.get(0).getLogo() == null){
                    mEstateImage.setImageResource(R.drawable.ic_real_state_icon_colored);
                }else{
                    Picasso.get().load(realEstateData.get(0).getLogo().toString()).placeholder(R.drawable.ic_real_state_icon_colored).centerCrop().fit().into(mEstateImage);
                }

                //EstateName
                mEstateNameTV.setText(realEstateData.get(0).getName());

                //Listing count
                mListingTV.setText(" 0");

                //Dealing
                mREtoREDealingTV.setText((realEstateData.get(0).getNoOfDeal() == null)? " 0" :  " "+realEstateData.get(0).getNoOfDeal().toString());

                //Since
                mSinceTV.setText((realEstateData.get(0).getSince() == null)? " 0" :  " "+realEstateData.get(0).getSince().toString());

                //Ratting Average
                mAverageRatingTV.setText((realEstateData.get(0).getAverageRating() == null)? "0" :  " "+realEstateData.get(0).getAverageRating().toString());

                //Set Rating
                mRating.setRating((realEstateData.get(0).getAverageRating() == null)? (float) 0 : (float) realEstateData.get(0).getAverageRating());
                LayerDrawable stars = (LayerDrawable) mRating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, this, estateID);

    }

    private void implementTabs(String estateID) {

        Bundle bundle = new Bundle();
        bundle.putString("estateID", estateID );

        fragment = new EstateListingTabFragment();
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
                        fragment = new EstateListingTabFragment();
                        break;
                    case 1:
                        fragment = new EstatePastDealingTabFragment();
                        break;
                    case 2:
                        fragment = new EstateReviewTabFragment();
                        break;
                    case 3:
                        fragment = new EstateBrokersTabFragment();
                        break;
                    case 4:
                        fragment = new EstateGalleryTabFragment();
                        break;
                    case 5:
                        fragment = new EstateVideoTabFragment();
                        break;
                    case 6:
                        fragment = new EstateFilesTabFragment();
                        break;
                    case 7:
                        fragment = new EstateAboutTabFragment();
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
