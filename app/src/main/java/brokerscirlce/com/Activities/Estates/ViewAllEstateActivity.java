package brokerscirlce.com.Activities.Estates;

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
import java.util.List;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.Views.DrawingPanel;
import brokerscirlce.com.Views.MapFragment;
import brokerscirlce.com.adapters.RealEstateRecyclerview_Config;
import brokerscirlce.com.api_helpers.AddressAPIHelper;
import brokerscirlce.com.api_helpers.RealEstateDatabaseHelper;
import brokerscirlce.com.model.Addresses.AddressesData;
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.R;
import brokerscirlce.com.util.GoogleMapsUtils;

public class ViewAllEstateActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener, PullRefreshLayout.OnRefreshListener {

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
    private RoundedImageView mEstateImage;
    private ImageView mTrustedEstate;
    private ToggleButton mFavoriteBTN;
    private TextView mEstateNameTV, mSinceTV, mReviews, mTotalBrokersTV, mTotalBuyTV, mTotalSellTV, mTotalRentTV;
    private TextView mDealsTV, mListingTV, mLocationTV, mFollowesTV;
    private RatingBar ratingBar;
    private Button mDetailButton, mCallButton, mMessageButton, mGetDirectionButton;

    List<RealEstateData> realEstateDataList;
    RealEstateData realEstateData;
    List<AddressesData> addressesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_estate_activity);

        realEstateDataList = new ArrayList<>();
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
        mRefreshLayout.setOnRefreshListener(this);

        //Bottom sheet content
        mEstateImage =  findViewById(R.id.estate_image);
        mTrustedEstate =  findViewById(R.id.trusted_icon);
        mEstateNameTV =  findViewById(R.id.tv_estate_name);
        mSinceTV =  findViewById(R.id.tv_since);
        ratingBar =  findViewById(R.id.rating);
        mReviews =  findViewById(R.id.tv_review);
        mFavoriteBTN =  findViewById(R.id.btn_favorite);
        mDealsTV =  findViewById(R.id.tv_deals);
        mListingTV =  findViewById(R.id.tv_listing);
        mFollowesTV =  findViewById(R.id.tv_followers);
        mLocationTV =  findViewById(R.id.tv_location);
        mTotalBrokersTV =  findViewById(R.id.tv_brokers_count);
        mTotalBuyTV =  findViewById(R.id.tv_buy_count);
        mTotalSellTV =  findViewById(R.id.tv_sell_count);
        mTotalRentTV =  findViewById(R.id.tv_rent_count);
        mDetailButton =  findViewById(R.id.btn_details);
        mCallButton =  findViewById(R.id.btn_call);
        mMessageButton =  findViewById(R.id.btn_message);
        mGetDirectionButton =  findViewById(R.id.btn_get_direction);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mBottomSheet  = mView.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomsheetlayout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        //Implement Recyclerview
        new RealEstateDatabaseHelper().readEstateList(new RealEstateDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<RealEstateData> realEstateData) {
                new RealEstateRecyclerview_Config().setConfig(mRecyclerview,ViewAllEstateActivity.this,realEstateData, mEdittextSearch);
                realEstateDataList.clear();
                realEstateDataList = realEstateData;
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },this);

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
        // Start Shimmer Effect's animation before data is loaded to ListView
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
        new RealEstateDatabaseHelper().readEstateList(new RealEstateDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<RealEstateData> realEstateData) {
                new RealEstateRecyclerview_Config().setConfig(mRecyclerview,ViewAllEstateActivity.this,realEstateData, mEdittextSearch);
                realEstateDataList.clear();
                realEstateDataList = realEstateData;
                //Stop refreshing
                mRefreshLayout.setRefreshing(false);
            }
        },ViewAllEstateActivity.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_chat:
                intent = new Intent(ViewAllEstateActivity.this, ChatActivity.class);
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
                    }, ViewAllEstateActivity.this, "Company");
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
                    polygonOptions.strokeColor(ContextCompat.getColor(ViewAllEstateActivity.this, R.color.colorPrimary));
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
                for ( RealEstateData Estate : realEstateDataList) {
                    if (Estate.getId().equals(marker.getTag())){
                        realEstateData = Estate;
                    }
                }

                if (realEstateData != null){
                    setEstateBottomSheetData(realEstateData);
                }else {
                    Toast.makeText(ViewAllEstateActivity.this, "No Details Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setEstateBottomSheetData(RealEstateData realEstateData) {

        //Estate Logo
        if (realEstateData.getLogo() != null && !realEstateData.getLogo().equals("")){
            Picasso.get().load(realEstateData.getLogo().toString()).placeholder(R.drawable.ic_real_state_icon_colored).centerCrop().fit().into(mEstateImage);
        }else {
            mEstateImage.setImageResource(R.drawable.ic_real_state_icon_colored);
        }

        //Estate Name
        if (realEstateData.getName() != null && !realEstateData.getName().equals("")){
            mEstateNameTV.setText(realEstateData.getName());
        }else {
            mEstateNameTV.setVisibility(View.GONE);
        }

        //Since TV
        if (realEstateData.getSince() != null && !realEstateData.getSince().equals("")){
            mSinceTV.setText(realEstateData.getSince().toString());
        }else {
            mSinceTV.setVisibility(View.GONE);
        }

        //Rating bar
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
        if (realEstateData.getAverageRating() != null && !realEstateData.getAverageRating().equals("")){
            ratingBar.setRating((float) realEstateData.getAverageRating());
        }else{
            ratingBar.setRating(0);
        }

        //Reviews
        if (realEstateData.getNoOfReviews() != null && !realEstateData.getNoOfReviews().equals("")){
            mReviews.setText(String.format("%s Reviews", realEstateData.getNoOfReviews().toString()));
        }else{
            mReviews.setText("0 Reviews");
        }

        //Brokers Count
        if (realEstateData.getNoOfEmployee() != null && !realEstateData.getNoOfEmployee().equals("")){
            mTotalBuyTV.setText(String.format("Brokers: %s", realEstateData.getNoOfEmployee()));
        }else {
            mTotalBuyTV.setText("Buy: 0");
        }
        //buy Count
        if (realEstateData.getNoOfBuy() != null && !realEstateData.getNoOfBuy().equals("")){
            mTotalBuyTV.setText(String.format("Buy: %s", realEstateData.getNoOfBuy()));
        }else {
            mTotalBuyTV.setText("Buy: 0");
        }
        //Sell Count
        if (realEstateData.getNoOfSell() != null && !realEstateData.getNoOfSell().equals("")){
            mTotalSellTV.setText(String.format("Sell: %s", realEstateData.getNoOfSell()));
        }else {
            mTotalSellTV.setText("Sell: 0");
        }
        //Rent Count
        if (realEstateData.getNoOfRent() != null && !realEstateData.getNoOfRent().equals("")){
            mTotalRentTV.setText(String.format("Sell: %s", realEstateData.getNoOfRent()));
        }else {
            mTotalRentTV.setText("Rent: 0");
        }

        //Past dealing
        if (realEstateData.getNoOfDeal() != null && !realEstateData.getNoOfDeal().equals("")){
            mTotalRentTV.setText(String.format("%s past dealing", realEstateData.getNoOfDeal()));
        }else {
            mTotalRentTV.setText("0 past dealing");
        }

        //Listings
        mTotalRentTV.setText("0 Listings");
//            if (realEstateData.get() != null && !realEstateData.getNoOfDeal().equals("")){
//                mTotalRentTV.setText(String.format("%s Listings", realEstateData.getNoOfDeal()));
//            }else {
//                mTotalRentTV.setText("0 Listings");
//            }

        //Number of Followers
        if (realEstateData.getNoOfFollowers() != null && !realEstateData.getNoOfFollowers().equals("")){
            int followers = (int) realEstateData.getNoOfFollowers();
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
        }, ViewAllEstateActivity.this, realEstateData.getId(), "Company");

        //Favorite Button Click
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //animation
                compoundButton.startAnimation(scaleAnimation);
            }
        });

        // Actions Detail Button Click Clicklistners
        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllEstateActivity.this, EstateDetailActivity.class);
                intent.putExtra("estateid",realEstateData.getId());
                startActivity(intent);
            }
        });
        // Actions Message Button Click Clicklistners
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllEstateActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
