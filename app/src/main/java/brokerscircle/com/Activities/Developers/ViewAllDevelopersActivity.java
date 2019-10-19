package brokerscircle.com.Activities.Developers;

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
import brokerscircle.com.Activities.LoginActivity;
import brokerscircle.com.Views.DrawingPanel;
import brokerscircle.com.Views.MapFragment;
import brokerscircle.com.adapters.DevelopersRecyclerview_Config;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.DevelopersDatabaseHelper;
import brokerscircle.com.api_helpers.FavoriteDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Developers.DevelopersData;
import brokerscircle.com.R;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.GoogleMapsUtils;
import brokerscircle.com.util.Helper;

public class ViewAllDevelopersActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener, PullRefreshLayout.OnRefreshListener  {

    private static final String TAG = "ViewAllDevelopersActivity";
    private Helper helper;
    LoginUser user;

    //Google Maps Contents
    private GoogleMap mMap;
    private FrameLayout flMapContainer;
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

    //Bottom Sheet items
    private BottomSheetBehavior bottomSheetBehavior;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout layout_List;
    private CoordinatorLayout layout_Map;
    private EditText mEdittextSearch;
    private ToggleButton btn_switch;
    private ImageView btn_chat;
    private RoundedImageView btn_profile;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;
    private LinearLayout mBackButton;

    //Bottom Sheet Content
    private RoundedImageView mImage;
    private ImageView mTrustedEstate;
    private ToggleButton mFavoriteBTN;
    private TextView mNameTV, mSinceTV, mReviews, mCompletedTV, mOffPlanTV, mInProgressTV;
    private TextView mTotalBrokersTV, mLocationTV, mFollowesTV;
    private RatingBar ratingBar;
    private Button mDetailButton, mCallButton, mMessageButton, mGetDirectionButton;

    List<DevelopersData> developersDataList;
    DevelopersData developersData;
    List<AddressesData> addressesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new Helper(this);
        user = helper.getLoggedInUser();

        if (user == null) {    //Check if user if logged in
            //Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            Intent intent = new Intent(ViewAllDevelopersActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.view_all_developers_activity);

            developersDataList = new ArrayList<>();
            addressesDataList = new ArrayList<>();

            //Getting IDs
            layout_List = findViewById(R.id.layout_list);
            layout_Map = findViewById(R.id.layout_map);
            mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
            mEdittextSearch = findViewById(R.id.et_search);
            btn_switch = findViewById(R.id.btn_switch);
            btn_chat = findViewById(R.id.btn_chat);
            btn_profile = findViewById(R.id.btn_profile);
            mBackButton = findViewById(R.id.btn_back);
            mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            mRecyclerview = findViewById(R.id.recyclerview);

            //Bottom sheet content
            mImage =  findViewById(R.id.imageview);
            mTrustedEstate =  findViewById(R.id.trusted_icon);
            mNameTV =  findViewById(R.id.tv_name);
            mSinceTV =  findViewById(R.id.tv_since);
            ratingBar =  findViewById(R.id.rating);
            mReviews =  findViewById(R.id.tv_review);
            mFavoriteBTN =  findViewById(R.id.btn_favorite);
            mCompletedTV =  findViewById(R.id.tv_completed);
            mOffPlanTV =  findViewById(R.id.tv_off_plan);
            mInProgressTV =  findViewById(R.id.tv_in_progress);
            mTotalBrokersTV =  findViewById(R.id.tv_brokers);
            mFollowesTV =  findViewById(R.id.tv_followers);
            mLocationTV =  findViewById(R.id.tv_location);
            mDetailButton =  findViewById(R.id.btn_details);
            mCallButton =  findViewById(R.id.btn_call);
            mMessageButton =  findViewById(R.id.btn_message);
            mGetDirectionButton =  findViewById(R.id.btn_get_direction);

            //mBottomSheet  = mView.findViewById(R.id.bottom_sheet);
            bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomsheetlayout));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            new DevelopersDatabaseHelper().readDeveloperssList(new DevelopersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<DevelopersData> developersData) {
                    new DevelopersRecyclerview_Config().setConfig(mRecyclerview,ViewAllDevelopersActivity.this, developersData, mEdittextSearch);
                    developersDataList.clear();
                    developersDataList = developersData;
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            },this);

            mRefreshLayout.setOnRefreshListener(this);
            //back listener
            mRefreshLayout.setOnRefreshListener(this);
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

            initMapsControls();
        }
    }

    private void initMapsControls() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        llMapActionContainer = findViewById(R.id.llMapActionContainer);
        flMapContainer =  findViewById(R.id.flMapContainer);
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
        new DevelopersDatabaseHelper().readDeveloperssList(new DevelopersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<DevelopersData> developersData) {
                new DevelopersRecyclerview_Config().setConfig(mRecyclerview,ViewAllDevelopersActivity.this, developersData, mEdittextSearch);
                developersDataList.clear();
                developersDataList = developersData;
                // Stopping Refreshing
                mRefreshLayout.setRefreshing(false);
            }
        },ViewAllDevelopersActivity.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_chat:
                intent = new Intent(ViewAllDevelopersActivity.this, ChatActivity.class);
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
                    }, ViewAllDevelopersActivity.this, "PropertyDeveloper");
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
                    polygonOptions.strokeColor(ContextCompat.getColor(ViewAllDevelopersActivity.this, R.color.colorPrimary));
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
            if (marker.getTag() != "broker"){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude)));
                for ( DevelopersData developer : developersDataList) {
                    if (developer.getId().equals(marker.getTag())){
                        developersData = developer;
                    }
                }

                if (developersData != null){
                    setDeveloperBottomSheetData(developersData);
                }else {
                    Toast.makeText(ViewAllDevelopersActivity.this, "No Details Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setDeveloperBottomSheetData(DevelopersData developersData) {
        //Estate Logo
        if (developersData.getLogo() != null && !developersData.getLogo().equals("")){
            Picasso.get().load(developersData.getLogo().toString()).placeholder(R.drawable.ic_real_state_icon_colored).centerCrop().fit().into(mImage);
        }else {
            mImage.setImageResource(R.drawable.ic_real_state_icon_colored);
        }

        //Estate Name
        if (developersData.getTitle() != null && !developersData.getTitle().equals("")){
            mNameTV.setText(developersData.getTitle());
        }else {
            mNameTV.setVisibility(View.GONE);
        }

        //Since TV
        if (developersData.getSince() != null && !developersData.getSince().equals("")){
            mSinceTV.setText(developersData.getSince().toString());
        }else {
            mSinceTV.setVisibility(View.GONE);
        }

        //Rating bar
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
        if (developersData.getAverageRating() != null && !developersData.getAverageRating().equals("")){
            ratingBar.setRating((float) developersData.getAverageRating());
        }else{
            ratingBar.setRating(0);
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
        }, ViewAllDevelopersActivity.this,user.getResponse().getData().getUser().getUserId(),developersData.getId(), "Developer");


        //Reviews
        if (developersData.getNoOfReviews() != null && !developersData.getNoOfReviews().equals("")){
            mReviews.setText(String.format("%s Reviews", developersData.getNoOfReviews().toString()));
        }else{
            mReviews.setText("0 Reviews");
        }

        //Project Completed Count
        if (developersData.getNoOfProjectCompleted() != null && !developersData.getNoOfProjectCompleted().equals("")){
            mCompletedTV.setText(String.format("Completed: %s", developersData.getNoOfProjectCompleted()));
        }else {
            mCompletedTV.setText("Completed: 0");
        }

        //Project OffPlan Count
        if (developersData.getNoOfProjectOffPlan() != null && !developersData.getNoOfProjectOffPlan().equals("")){
            mOffPlanTV.setText(String.format("Off Plan: %s", developersData.getNoOfProjectOffPlan()));
        }else {
            mOffPlanTV.setText("Off Plan: 0");
        }

        //Project InProgress Count
        if (developersData.getNoOfProjectInProgress() != null && !developersData.getNoOfProjectInProgress().equals("")){
            mInProgressTV.setText(String.format("In Progress: %s", developersData.getNoOfProjectInProgress()));
        }else {
            mInProgressTV.setText("In Progress: 0");
        }

        //Brokers Count
        if (developersData.getNoOfBrokers() != null && !developersData.getNoOfBrokers().equals("")){
            mTotalBrokersTV.setText(String.format("%s Brokers", developersData.getNoOfBrokers()));
        }else {
            mTotalBrokersTV.setText("0 Broker");
        }

        //Number of Followers
        if (developersData.getNoOfFollowers() != null && !developersData.getNoOfFollowers().equals("")){
            int followers = (int) developersData.getNoOfFollowers();
            String followerSting = (followers > 1)? followers+" Followers" : followers+" Follower";
            mFollowesTV.setText(followerSting);
        }else{
            mFollowesTV.setText("0 Follower");
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
        }, ViewAllDevelopersActivity.this, developersData.getId(), "PropertyDeveloper");

        //Favorite Button Click
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        //Fav click
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

        // Actions Detail Button Click Clicklistners
        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllDevelopersActivity.this, DevelopersDetailActivity.class);
                intent.putExtra("developerid",developersData.getId());
                startActivity(intent);
            }
        });
        // Actions Message Button Click Clicklistners
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllDevelopersActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        //Hide bottom sheet
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
                    },ViewAllDevelopersActivity.this, params);
                }
            }

            @Override
            public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

            }

            @Override
            public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {
                if (responseUtils.get(0).getError().equals("false")){
                    Toast.makeText(ViewAllDevelopersActivity.this, "Successfully Removed", Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(false);
                }else {
                    Toast.makeText(ViewAllDevelopersActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(true);
                }
            }
        }, ViewAllDevelopersActivity.this,user.getResponse().getData().getUser().getUserId(),developersData.getId(), "Developer");
    }

    private void FavoriteMethod() {

        Map<String,String> params = new HashMap<String, String>();

        params.put("reference_id",developersData.getId());
        params.put("reference_type","Developer");
        params.put("created_by_comp_id",user.getResponse().getData().getUser().getCompId());
        params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

        new FavoriteDatabaseHelper().postFavorite(new FavoriteDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FavoriteData> favoriteData) {

            }

            @Override
            public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {
                if (responseUtils.get(0).getError().equals("false")){
                    Toast.makeText(ViewAllDevelopersActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(true);
                }else {
                    Toast.makeText(ViewAllDevelopersActivity.this, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    mFavoriteBTN.setChecked(false);
                }
            }

            @Override
            public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

            }
        },ViewAllDevelopersActivity.this, params);
    }
}
