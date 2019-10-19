package brokerscircle.com.Activities.Brokers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.Activities.Estates.EstateDetailActivity;
import brokerscircle.com.Activities.LoginActivity;
import brokerscircle.com.Views.DrawingPanel;
import brokerscircle.com.Views.MapFragment;
import brokerscircle.com.adapters.BrokersRecyclerview_Config;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.api_helpers.FavoriteDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.R;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.GoogleMapsUtils;
import brokerscircle.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAllBrokersActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        View.OnClickListener,
        PullRefreshLayout.OnRefreshListener {

    private static final String TAG = "ViewAllBrokersActivity";
    private Helper helper;
    LoginUser user;

    //Google Maps Contents
    private FrameLayout flMapContainer;
    private GoogleMap mMap;
    private ProgressBar progressBar;
    private LinearLayout llMapActionContainer;
    private DrawingPanel drawingpanel;

    private ArrayList<LatLng> latLngList;
    private PolygonOptions polygonOptions;
    private Projection projection;
    private LatLngBounds bounds;
    double maxDistanceFromCenter;
    private LatLng latLng;
    private LatLngBounds latLngBounds;

    private BottomSheetBehavior bottomSheetBehavior;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout layout_List;
    private CoordinatorLayout layout_Map;

    private EditText mEdittextSearch;
    private ToggleButton btn_switch;
    private ImageView btn_chat;
    private RoundedImageView btn_profile;
    private LinearLayout mBackButton;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;

    //Bottom Sheet Content
    private CircleImageView mProfileImage;
    private ImageView mOnlineStatus;
    private ToggleButton mFavoriteBTN;
    private TextView mNameTV, mEstateNameTV, mReviews, mTotalBuyTV, mTotalSellTV, mTotalRentTV;
    private TextView mExperienceTV, mLocationTV, mFollowesTV, mFollowingTV;
    private RatingBar ratingBar;
    private Button mProfileButton, mCallButton, mMessageButton, mGetDirectionButton;

    List<BrokersData> brokersDataList;
    BrokersData brokersData;
    List<AddressesData> addressesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new Helper(this);
        user = helper.getLoggedInUser();

        if (user == null) {    //Check if user if logged in
            //Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            Intent intent = new Intent(ViewAllBrokersActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.view_all_brokers_activity);

            brokersDataList = new ArrayList<>();
            addressesDataList = new ArrayList<>();

            initAppBar();

            initContents();

            initBottomSheet();
        }
    }

    private void initAppBar() {

        mEdittextSearch = findViewById(R.id.et_search);
        btn_switch = findViewById(R.id.btn_switch);
        btn_chat = findViewById(R.id.btn_chat);
        btn_profile = findViewById(R.id.btn_profile);
        mBackButton = findViewById(R.id.btn_back);

        //back listener
        mBackButton.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_profile.setOnClickListener(this);


        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    layout_Map.setVisibility(View.VISIBLE);
                    layout_List.setVisibility(View.GONE);
                }else {
                    layout_Map.setVisibility(View.GONE);
                    layout_List.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initContents() {

        //Getting IDs
        layout_List = findViewById(R.id.layout_list);
        layout_Map = findViewById(R.id.layout_map);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);

        mRefreshLayout.setOnRefreshListener(this);

        initMapsControls();

        //Implement Recyclerview
        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                new BrokersRecyclerview_Config().setConfig(mRecyclerview,ViewAllBrokersActivity.this,brokersUtils, mEdittextSearch);
                brokersDataList.clear();
                brokersDataList = brokersUtils;
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

        },this);
    }

    private void initBottomSheet() {

        //Bottom sheet content
        mProfileImage =  findViewById(R.id.profile_image);
        mOnlineStatus =  findViewById(R.id.online_icon);
        mNameTV =  findViewById(R.id.tv_name);
        mEstateNameTV =  findViewById(R.id.tv_estate);
        ratingBar =  findViewById(R.id.rating);
        mReviews =  findViewById(R.id.tv_review);
        mTotalBuyTV =  findViewById(R.id.tv_buy_count);
        mTotalSellTV =  findViewById(R.id.tv_sell_count);
        mTotalRentTV =  findViewById(R.id.tv_rent_count);
        mFavoriteBTN =  findViewById(R.id.btn_favorite);
        mExperienceTV =  findViewById(R.id.tv_experience);
        mLocationTV =  findViewById(R.id.tv_location);
        mFollowesTV =  findViewById(R.id.tv_followers);
        mFollowingTV =  findViewById(R.id.tv_following);
        mProfileButton =  findViewById(R.id.btn_view_profile);
        mCallButton =  findViewById(R.id.btn_call);
        mMessageButton =  findViewById(R.id.btn_message);
        mGetDirectionButton =  findViewById(R.id.btn_get_direction);


        //mBottomSheet  = mView.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomsheetlayout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    private void initMapsControls() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        llMapActionContainer = (LinearLayout) findViewById(R.id.llMapActionContainer);
        flMapContainer = (FrameLayout) findViewById(R.id.flMapContainer);
        ToggleButton mDrawToggle = findViewById(R.id.btn_draw);

        drawingpanel = new DrawingPanel(this);
        drawingpanel.setVisibility(View.GONE);
        drawingpanel.setBackgroundColor(Color.parseColor("#50000000"));
        flMapContainer.addView(drawingpanel);

        mDrawToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    onDrawOnMapClick();
                }else {
                    onRedoSearchClick();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);


        ((MapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new MapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                if (llMapActionContainer.getVisibility() == View.GONE) {
                    llMapActionContainer.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn).playOn(llMapActionContainer);

                    GoogleMapsUtils.setFadeOutAfterSomeTime(llMapActionContainer);
                }
            }
        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (mMap != null) {
                    // get  addresses
                    new AddressAPIHelper().readRefrenceTypeAdressesData(new AddressAPIHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<AddressesData> addressesDataList1) {
                            addressesDataList.clear();
                            addressesDataList = addressesDataList1;
                            if (addressesDataList != null){
                                GoogleMapsUtils.addMarkers(mMap, addressesDataList, latLngBounds);
                            }
                        }
                    }, ViewAllBrokersActivity.this, "Broker");
                }
            }
        });

        drawingpanel.setOnDragListener(new DrawingPanel.OnDragListener() {
            @Override
            public void onDragEvent(MotionEvent motionEvent) {

                //Track touch point
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                int x_co = Integer.parseInt(String.valueOf(Math.round(x)));
                int y_co = Integer.parseInt(String.valueOf(Math.round(y)));

                //get Projection from google map
                projection = mMap.getProjection();
                Point x_y_points = new Point(x_co, y_co);
                LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);

                if (latLngList == null)
                    latLngList = new ArrayList<>();
                latLngList.add(latLng);

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mMap.clear();

                    //Join all points and draw polygon
                    polygonOptions.strokeWidth(10);
                    polygonOptions.strokeColor(ContextCompat.getColor(ViewAllBrokersActivity.this, R.color.colorPrimary));
                    polygonOptions.addAll(latLngList);
                    mMap.addPolygon(polygonOptions);

                    drawingpanel.setVisibility(View.GONE);

                    //find radius and center from drawing
                    LatLng latLng1 = GoogleMapsUtils.getPolygonCenterPoint(latLngList, bounds, latLng);
                    LatLng latLngxmin = projection.fromScreenLocation(drawingpanel.getPointXMin());
                    LatLng latLngxmax = projection.fromScreenLocation(drawingpanel.getPointxMax());
                    LatLng latLngymin = projection.fromScreenLocation(drawingpanel.getPointYmin());
                    LatLng latLngymax = projection.fromScreenLocation(drawingpanel.getPointYmax());


                    if (drawingpanel.getPointXMin().x != 0 && drawingpanel.getPointXMin().y != 0)
                        maxDistanceFromCenter = GoogleMapsUtils.distance(latLng1.latitude, latLng1.longitude, latLngxmin.latitude, latLngxmin.longitude);

                    double tempdistance = 0;
                    if (drawingpanel.getPointxMax().x != 0 && drawingpanel.getPointxMax().y != 0)
                        tempdistance = GoogleMapsUtils.distance(latLng1.latitude, latLng1.longitude, latLngxmax.latitude, latLngxmax.longitude);
                    if (tempdistance > maxDistanceFromCenter)
                        maxDistanceFromCenter = tempdistance;

                    if (drawingpanel.getPointYmin().x != 0 && drawingpanel.getPointYmin().y != 0)
                        tempdistance = GoogleMapsUtils.distance(latLng1.latitude, latLng1.longitude, latLngymin.latitude, latLngymin.longitude);
                    if (tempdistance > maxDistanceFromCenter)
                        maxDistanceFromCenter = tempdistance;

                    if (drawingpanel.getPointYmax().x != 0 && drawingpanel.getPointYmax().y != 0)
                        tempdistance = GoogleMapsUtils.distance(latLng1.latitude, latLng1.longitude, latLngymax.latitude, latLngymax.longitude);

                    if (tempdistance > maxDistanceFromCenter)
                        maxDistanceFromCenter = tempdistance;

                    drawingpanel.clear();

                    latLngBounds = GoogleMapsUtils.toBounds(latLng, maxDistanceFromCenter);

                    if (addressesDataList != null)
                        GoogleMapsUtils.addMarkers(mMap, addressesDataList, latLngBounds);
                }

            }
        });
    }

    public void onRedoSearchClick() {

        if (latLngList != null)
            latLngList.clear();
        mMap.clear();

        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;
        latLng = latLngBounds.getCenter();
        maxDistanceFromCenter = GoogleMapsUtils.distance(latLngBounds.getCenter().latitude, latLngBounds.getCenter().longitude, visibleRegion.farRight.latitude, visibleRegion.farRight.longitude);

        this.latLngBounds = null;
        GoogleMapsUtils.addMarkers(mMap, addressesDataList, latLngBounds);
    }

    public void onDrawOnMapClick() {

        if (latLngList != null)
            latLngList.clear();
        mMap.clear();
        llMapActionContainer.setVisibility(View.GONE);
        drawingpanel.setVisibility(View.VISIBLE);
        drawingpanel.clear();
        polygonOptions = new PolygonOptions();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude)));
            for ( BrokersData broker : brokersDataList) {
                if (broker.getId().equals(marker.getTag())){
                    brokersData = broker;
                }
            }

            if (brokersData != null){
                setBrokerBottomSheetData(brokersData);
            }else {
                Toast.makeText(ViewAllBrokersActivity.this, "No Details Found", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start Shimmer Effect's animation before data is loaded to Recyclerview
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        // Stopping Shimmer Effect's animation after data is loaded to ListView
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        super.onPause();
    }

    @Override
    public void onRefresh() {
        //Implement Recyclerview
        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                new BrokersRecyclerview_Config().setConfig(mRecyclerview,ViewAllBrokersActivity.this,brokersUtils, mEdittextSearch);
                brokersDataList.clear();
                brokersDataList = brokersUtils;
                //Stop refreshing
                mRefreshLayout.setRefreshing(false);
            }
        },ViewAllBrokersActivity.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_chat:
                intent = new Intent(ViewAllBrokersActivity.this, ChatActivity.class);
                startActivity(intent);
                //TODO Listeners
                break;
            case R.id.btn_profile:
                //TODO Listeners
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setBrokerBottomSheetData(BrokersData brokersData) {

        //Profile image
        if (brokersData.getPicture() != null && !brokersData.getPicture().equals("")){
            Picasso.get().load(brokersData.getPicture()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
        }else{
            mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
        }

        //Status Online
//            if (brokersData.getStatus().equals("Active")){
//                mOnlineStatus.setVisibility(View.VISIBLE);
//            }else {
//                mOnlineStatus.setVisibility(View.GONE);
//            }

        //Name / Title
        if (!brokersData.getTitle().equals("") && brokersData.getTitle() != null ){
            mNameTV.setText(brokersData.getTitle());
        }else {
            mNameTV.setVisibility(View.GONE);
        }

        //EstateName
        if (!brokersData.getRealEstateName().equals("") && brokersData.getRealEstateName() != null){
            mEstateNameTV.setText(brokersData.getRealEstateName());
        }else {
            mEstateNameTV.setVisibility(View.GONE);
        }

        new FavoriteDatabaseHelper().readFavoriteWithCreatedUserIdAndReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FavoriteData> favoriteData) {
                if (favoriteData.isEmpty()){
                    mFavoriteBTN.setChecked(false);
                }else {
                    mFavoriteBTN.setChecked(true);
                }
            }

            @Override
            public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

            }

            @Override
            public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

            }
        }, ViewAllBrokersActivity.this,user.getResponse().getData().getUser().getUserId(),brokersData.getId(), "Broker");

        //Rating bar
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
        if (brokersData.getAverageRating() != null && !brokersData.getAverageRating().equals("")){
            ratingBar.setRating((float) brokersData.getAverageRating());
        }else{
            ratingBar.setRating(0);
        }

        //Reviews
        if (brokersData.getNoOfReviews() != null && !brokersData.getNoOfReviews().equals("")){
            mReviews.setText(String.format("%s Reviews", brokersData.getNoOfReviews().toString()));
        }else{
            mReviews.setText("0 Reviews");
        }

        //buy Count
        if (brokersData.getNoOfBuy() != null && !brokersData.getNoOfBuy().equals("")){
            mTotalBuyTV.setText(String.format("Buy: %s", brokersData.getNoOfBuy()));
        }else {
            mTotalBuyTV.setText("Buy: 0");
        }
        //Sell Count
        if (brokersData.getNoOfSell() != null && !brokersData.getNoOfSell().equals("")){
            mTotalSellTV.setText(String.format("Sell: %s", brokersData.getNoOfSell()));
        }else {
            mTotalSellTV.setText("Sell: 0");
        }

        //Rent Count
        if (brokersData.getNoOfRent() != null && !brokersData.getNoOfRent().equals("")){
            mTotalRentTV.setText(String.format("Sell: %s", brokersData.getNoOfRent()));
        }else {
            mTotalRentTV.setText("Rent: 0");
        }

        //Experience
        if (brokersData.getExperience() != null && !brokersData.getExperience().equals("")){
            mExperienceTV.setText(String.format("%s years experience", brokersData.getExperience().toString()));
        }else{
            mExperienceTV.setText("Less then 1 year experience");
        }

        //Experience
        if (brokersData.getExperience() != null && !brokersData.getExperience().equals("")){
            mExperienceTV.setText(String.format("%s years experience", brokersData.getExperience().toString()));
        }else{
            mExperienceTV.setText("Less then 1 year experience");
        }

        //Location Address
        new AddressAPIHelper().readRefrenceWithTypeAdressesData(new AddressAPIHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<AddressesData> addressesData) {
                if (addressesData.isEmpty()){
                    mLocationTV.setText("Not found");
                }else {
                    mLocationTV.setText(addressesData.get(0).getLocationTitle() +" "
                            +addressesData.get(0).getAreaTitle()+" "
                            +addressesData.get(0).getAreaTitle()+" "
                            +addressesData.get(0).getCityTitle()+" "
                            +addressesData.get(0).getProvinceTitle()+" "
                            +addressesData.get(0).getCountryTitle()
                    );
                }
            }
        }, ViewAllBrokersActivity.this, brokersData.getId(), "Broker");


        //Number of Followers
        if (brokersData.getNoOfFollowers() != null && !brokersData.getNoOfFollowers().equals("")){
            int followers = (int) brokersData.getNoOfFollowers();
            String followerSting = (followers > 1)? followers+" Followers" : followers+" Follower";
            mFollowesTV.setText(followerSting);
        }else{
            mFollowesTV.setText("0 Follower");
        }

        //Number of Following
        mFollowingTV.setText("0 Following");

        // Actions Estate Click Clicklistners
        mEstateNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllBrokersActivity.this, EstateDetailActivity.class);
                intent.putExtra("estateid",brokersData.getCreatedByCompId());
                startActivity(intent);
            }
        });

        //Favorite Button Click
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        //Follow click
        mFavoriteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(scaleAnimation);
                if (mFavoriteBTN.isChecked()){
                    FavoriteMethod();
                }else {
                    deteleFavoriteMethod();
                }
            }
        });

        // Actions Profile button Click Clicklistners
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllBrokersActivity.this, BrokerProfileActivity.class);
                intent.putExtra("brokerid",brokersData.getId());
                startActivity(intent);
            }
        });

        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllBrokersActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void deteleFavoriteMethod() {

        new FavoriteDatabaseHelper().readFavoriteWithCreatedUserIdAndReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FavoriteData> favoriteData) {
                if (!favoriteData.isEmpty()){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id",favoriteData.get(0).getId());
                    params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

                    new FavoriteDatabaseHelper().deleteFavorite(new FavoriteDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<FavoriteData> favoriteData) {

                        }

                        @Override
                        public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

                        }

                        @Override
                        public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

                        }
                    },ViewAllBrokersActivity.this, params);
                }
            }

            @Override
            public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

            }

            @Override
            public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {
                if (responseUtils.get(0).getError().equals("false")){
                    Toast.makeText(ViewAllBrokersActivity.this, "Successfully Removed", Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(false);
                }else {
                    Toast.makeText(ViewAllBrokersActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(true);
                }
            }
        }, ViewAllBrokersActivity.this,user.getResponse().getData().getUser().getUserId(),brokersData.getId(), "Broker");
    }

    private void FavoriteMethod() {

        Map<String,String> params = new HashMap<String, String>();

        params.put("reference_id",brokersData.getId());
        params.put("reference_type","Broker");
        params.put("created_by_comp_id",user.getResponse().getData().getUser().getCompId());
        params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

        new FavoriteDatabaseHelper().postFavorite(new FavoriteDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FavoriteData> favoriteData) {

            }

            @Override
            public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {
                if (responseUtils.get(0).getError().equals("false")){
                    Toast.makeText(ViewAllBrokersActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(true);
                }else {
                    Toast.makeText(ViewAllBrokersActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(false);
                }
            }

            @Override
            public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

            }
        },ViewAllBrokersActivity.this, params);
    }

}
