package brokerscirlce.com.Activities.Estates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.Views.DrawingPanel;
import brokerscirlce.com.Views.MapFragment;
import brokerscirlce.com.adapters.OwnersRecyclerview_Config;
import brokerscirlce.com.api_helpers.AddressAPIHelper;
import brokerscirlce.com.api_helpers.OwnerDatabaseHelper;
import brokerscirlce.com.model.Addresses.AddressesData;
import brokerscirlce.com.model.Owner.OwnerData;
import brokerscirlce.com.R;
import brokerscirlce.com.util.GoogleMapsUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAllOwnersEstateActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener, PullRefreshLayout.OnRefreshListener {

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

    //Bottom sheet content
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
    private CircleImageView mProfileBottomSheet;
    private ImageView mOnlineStatus;
    private ToggleButton mFavoriteBTN;
    private TextView mNameTV, mEstateNameTV, mTotalBuyTV, mTotalSellTV, mTotalRentTV;

    List<OwnerData> ownerDataList;
    OwnerData ownerData;
    List<AddressesData> addressesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owners_estate_activity);

        ownerDataList = new ArrayList<>();
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
        mProfileBottomSheet =  findViewById(R.id.profile_image);
        mOnlineStatus =  findViewById(R.id.online_icon);
        mNameTV =  findViewById(R.id.tv_name);
        mEstateNameTV =  findViewById(R.id.tv_estate);
        mTotalBuyTV =  findViewById(R.id.tv_buy_count);
        mTotalSellTV =  findViewById(R.id.tv_sell_count);
        mTotalRentTV =  findViewById(R.id.tv_rent_count);
        mFavoriteBTN =  findViewById(R.id.btn_favorite);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mBottomSheet  = mView.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomsheetlayout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        //Implement Recyclerview
        new OwnerDatabaseHelper().readOwnerList(new OwnerDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<OwnerData> ownerData) {
                new OwnersRecyclerview_Config().setConfig(mRecyclerview, ViewAllOwnersEstateActivity.this, ownerData);
                ownerDataList.clear();
                ownerDataList = ownerData;
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, ViewAllOwnersEstateActivity.this);

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
        //Implement Recyclerview
        new OwnerDatabaseHelper().readOwnerList(new OwnerDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<OwnerData> ownerData) {
                new OwnersRecyclerview_Config().setConfig(mRecyclerview, ViewAllOwnersEstateActivity.this, ownerData);
                ownerDataList.clear();
                ownerDataList = ownerData;
                //Stop refreshing
                mRefreshLayout.setRefreshing(false);
            }
        }, ViewAllOwnersEstateActivity.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_chat:
                intent = new Intent(ViewAllOwnersEstateActivity.this, ChatActivity.class);
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

        // get addresses
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
                    }, ViewAllOwnersEstateActivity.this, "Owner");
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
                    polygonOptions.strokeColor(ContextCompat.getColor(ViewAllOwnersEstateActivity.this, R.color.colorPrimary));
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
                for ( OwnerData owner : ownerDataList) {
                    if (owner.getId().equals(marker.getTag())){
                        ownerData = owner;
                    }
                }

                if (ownerData != null){
                    setOwnerBottomSheetData(ownerData);
                }else {
                    Toast.makeText(ViewAllOwnersEstateActivity.this, "No Details Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }

    private void setOwnerBottomSheetData(OwnerData ownerData) {
//        //Profile image
//        if (ownerData.getPicture() != null && !ownerData.getPicture().equals("")){
//            Picasso.get().load(ownerData.getPicture()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
//        }else{
//            mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
//        }
//
//        //Status Online
////            if (ownerData.getStatus().equals("Active")){
////                mOnlineStatus.setVisibility(View.VISIBLE);
////            }else {
////                mOnlineStatus.setVisibility(View.GONE);
////            }
//
//        //Name / Title
//        if (!ownerData.getTitle().equals("") && ownerData.getTitle() != null ){
//            mNameTV.setText(ownerData.getTitle());
//        }else {
//            mNameTV.setVisibility(View.GONE);
//        }
//
//        //EstateName
//        if (!ownerData.getRealEstateName().equals("") && ownerData.getRealEstateName() != null){
//            mEstateNameTV.setText(ownerData.getRealEstateName());
//        }else {
//            mEstateNameTV.setVisibility(View.GONE);
//        }
//
//        //Rating bar
//        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
//        if (ownerData.getAverageRating() != null && !ownerData.getAverageRating().equals("")){
//            ratingBar.setRating((float) ownerData.getAverageRating());
//        }else{
//            ratingBar.setRating(0);
//        }
//
//        //Reviews
//        if (ownerData.getNoOfReviews() != null && !ownerData.getNoOfReviews().equals("")){
//            mReviews.setText(String.format("%s Reviews", ownerData.getNoOfReviews().toString()));
//        }else{
//            mReviews.setText("0 Reviews");
//        }
//
//        //buy Count
//        if (ownerData.getNoOfBuy() != null && !ownerData.getNoOfBuy().equals("")){
//            mTotalBuyTV.setText(String.format("Buy: %s", ownerData.getNoOfBuy()));
//        }else {
//            mTotalBuyTV.setText("Buy: 0");
//        }
//        //Sell Count
//        if (ownerData.getNoOfSell() != null && !ownerData.getNoOfSell().equals("")){
//            mTotalSellTV.setText(String.format("Sell: %s", ownerData.getNoOfSell()));
//        }else {
//            mTotalSellTV.setText("Sell: 0");
//        }
//
//        //Rent Count
//        if (ownerData.getNoOfRent() != null && !ownerData.getNoOfRent().equals("")){
//            mTotalRentTV.setText(String.format("Sell: %s", ownerData.getNoOfRent()));
//        }else {
//            mTotalRentTV.setText("Rent: 0");
//        }
//
//        //Experience
//        if (ownerData.getExperience() != null && !ownerData.getExperience().equals("")){
//            mExperienceTV.setText(String.format("%s years experience", ownerData.getExperience().toString()));
//        }else{
//            mExperienceTV.setText("Less then 1 year experience");
//        }
//
//        //Experience
//        if (ownerData.getExperience() != null && !ownerData.getExperience().equals("")){
//            mExperienceTV.setText(String.format("%s years experience", ownerData.getExperience().toString()));
//        }else{
//            mExperienceTV.setText("Less then 1 year experience");
//        }
//
//        //Location Address
//        new AddressAPIHelper().readRefrenceWithTypeAdressesData(new AddressAPIHelper.DataStatus() {
//            @Override
//            public void DataIsLoaded(List<AddressesData> addressesData) {
//                if (addressesData.isEmpty()){
//                    mLocationTV.setText("Not found");
//                }else {
//                    mLocationTV.setText(addressesData.get(0).getLocationTitle() +" "
//                            +addressesData.get(0).getAreaTitle()+" "
//                            +addressesData.get(0).getAreaTitle()+" "
//                            +addressesData.get(0).getCityTitle()+" "
//                            +addressesData.get(0).getProvinceTitle()+" "
//                            +addressesData.get(0).getCountryTitle()
//                    );
//                }
//            }
//        }, ViewAllBrokersActivity.this, ownerData.getId(), "Broker");
//
//
//        //Number of Followers
//        if (ownerData.getNoOfFollowers() != null && !ownerData.getNoOfFollowers().equals("")){
//            int followers = (int) ownerData.getNoOfFollowers();
//            String followerSting = (followers > 1)? followers+" Followers" : followers+" Follower";
//            mFollowesTV.setText(followerSting);
//        }else{
//            mFollowesTV.setText("0 Follower");
//        }
//
//        //Number of Following
//        mFollowingTV.setText("0 Following");
//
//        // Actions Estate Click Clicklistners
//        mEstateNameTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewAllBrokersActivity.this, EstateDetailActivity.class);
//                intent.putExtra("estateid",ownerData.getCreatedByCompId());
//                startActivity(intent);
//            }
//        });
//
//        //Favorite Button Click
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
//        scaleAnimation.setDuration(500);
//        BounceInterpolator bounceInterpolator = new BounceInterpolator();
//        scaleAnimation.setInterpolator(bounceInterpolator);
//        mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                //animation
//                compoundButton.startAnimation(scaleAnimation);
//            }
//        });
//
//        // Actions Profile button Click Clicklistners
//        mProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewAllBrokersActivity.this, BrokerProfileActivity.class);
//                intent.putExtra("brokerid",ownerData.getId());
//                startActivity(intent);
//            }
//        });
//
//        mMessageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewAllBrokersActivity.this, ChatActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
