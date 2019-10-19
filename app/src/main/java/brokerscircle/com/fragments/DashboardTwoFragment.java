package brokerscircle.com.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import brokerscircle.com.Activities.Brokers.BrokerProfileActivity;
import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.Activities.Developers.CreateDeveloperActivity;
import brokerscircle.com.Activities.Estates.CreateEstateActivity;
import brokerscircle.com.Activities.Get_Direction_Activity;
import brokerscircle.com.Activities.JobBroard.CreateJobsActivity;
import brokerscircle.com.Activities.Projects.CreateProjectActivity;
import brokerscircle.com.Activities.Properties.ListPropertyOptions;
import brokerscircle.com.Activities.Properties.PropertySearchActivity;
import brokerscircle.com.R;
import brokerscircle.com.Views.DrawingPanel;
import brokerscircle.com.Views.MapFragment;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.CurrencyArrayAdapter;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.UnitArrayAdapter;
import brokerscircle.com.adapters.PropertyDashboardTwoListRecyclerview_config;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.CurrencyApiHelper;
import brokerscircle.com.api_helpers.PhoneDatabaseHelper;
import brokerscircle.com.api_helpers.Property.PropertyChildCategoryApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscircle.com.api_helpers.Property.PropertyFeaturesApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyNearByApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyParentCatergoryApiHelper;
import brokerscircle.com.api_helpers.UnitApiHelper;
import brokerscircle.com.api_helpers.UsersDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Addresses.ClusterMapAddress;
import brokerscircle.com.model.Currency.CurrencyData;
import brokerscircle.com.model.Phone.PhoneData;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.model.Property.PropertyFeatures.PropertyFeaturesData;
import brokerscircle.com.model.Property.PropertyFeatures.PropertyFeaturesUtils;
import brokerscircle.com.model.Property.PropertyNearBy.PropertyNearByData;
import brokerscircle.com.model.Property.PropertyNearBy.PropertyNearByUtils;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryData;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryUtils;
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryData;
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryUtils;
import brokerscircle.com.model.Units.UnitsData;
import brokerscircle.com.model.Users.UsersData;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.CustomDialogBox;
import brokerscircle.com.util.GoogleMapsUtils;
import brokerscircle.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.Locale.getDefault;

public class DashboardTwoFragment extends Fragment implements OnMapReadyCallback,
        ClusterManager.OnClusterItemClickListener<ClusterMapAddress>,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View mView;

    private Helper helper;
    private LoginUser user = null;

    CoordinatorLayout mListLayout, mMapLayout;

    //Toolbar
    private ImageView mMenuIconD1;
    private ImageView mChatButtonD1;
    private ToggleButton mswitcherToggle;
    private LinearLayout mMenuBtnLayoutD1;
    private EditText mSearchEdittext;
    private RoundedImageView mProfileImageD1;


    private DrawerLayout drawer;
    private FloatingActionMenu mFabMenu;

    //content layout list
    private RecyclerView mRecyclerview;

    private static final String TAG = "DashboardTwoFragmentMap";
    private static final int MY_PERMISSIONS_LOCATION = 100;

    //private LinearLayout mBottomSheet;
    private GoogleMap mMap;
    private FrameLayout flMapContainer;
    private ProgressBar progressBar;
    private LinearLayout llMapActionContainer;
    private DrawingPanel drawingpanel;
    private ToggleButton mDrawToggle;
    private ToggleButton mNearByToggle;
    private LatLng mCurrentLocationLatLng;
    //for current location
    private LocationManager locationManager;

    //Google Maps Clustor manager
    ClusterManager<ClusterMapAddress> clusterManager;
    List<ClusterMapAddress> clusterItems;

    //Distance Dialog Content
    private FrameLayout distance_select_container;
    private RelativeLayout dialog_container;
    private BubbleSeekBar bubbleSeekBar;
    private Button mClearDialogButton, mSubmitDialogButton;
    private int fianlSelectedDistance = 0;

    private ArrayList<LatLng> latLngList;
    private PolygonOptions polygonOptions;
    private Projection projection;
    private LatLngBounds bounds;
    double maxDistanceFromCenter;
    private LatLng latLng;
    private LatLngBounds latLngBounds;

    //Bottom sheet contents
    private CircleImageView mBrokerProfileImage;
    private TextView mBrokerName, mEstateName;
    private RatingBar mBrokerRating;
    private BottomSheetBehavior bottomSheetBehavior;
    private RoundedImageView mPropertyImage;
    private TextView mTitle, mAddress, mNoBed, mNoBath, mNoUnit, mTime, mPrice;
    private ToggleButton mFavoriteBTN, mGetDirectionBTN, mCallBtn, mSMSBtn, mAudioBtn;
    private LinearLayout mAudioBtnLayout;

    List<PropertyData> propertyDataList;
    PropertyData propertyData;
    List<AddressesData> addressesDataList;
    AddressesData addressesData;

    //Filters
    private LinearLayout property_filters_button;
    private ToggleButton listingToggle, priceToggle, homeTypeToggle, moreToggle;
    private LinearLayout filtersLayoutMain, listing_filter_layout, price_filter_layout, hometype_filter_layout, more_filter_layout;
    private ToggleButton mFilterListingForWantedToggle;
    private LinearLayout mForwantedfilterLayout;
    private CrystalRangeSeekbar priceRangeSeekBar;
    private TextView tv_pricerange;

    //More Filter Contents
    private Dialog dialog;
    private ListView mDialoglistView;
    private TextView mDialogTitle;
    List<CurrencyData> currencyDataList;
    CurrencyData currencyData = null;
    List<UnitsData> unitsDataList;
    UnitsData unitsData = null;

    private RelativeLayout mFilterProperty;

    private ViewGroup propertyTypesChecks;
    private ViewGroup propertyTypesChildChecks;
    private ViewGroup bedroomsChecks;
    private ViewGroup bathroomsChecks;
    private LinearLayout featuresChecks;
    private LinearLayout nearByChecks;

    private TextView mChangeCurrency;
    private TextView mCurrencyText;
    private TextView mChangeAreaUnit;
    private TextView mAreaTextUnit;

    String[] bedrooms = new String[] {"Studio", "1", "2", "3", "4", "5", "6", "7", "8", "9+"};
    String[] bathrooms = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9+"};

    List<PropertyParentCategoryData> propertyParentCategoryDataList;
    PropertyParentCategoryData propertyParentCategoryData = null;
    List<PropertyChildCategoryData> propertyChildCategoryDataList;
    PropertyChildCategoryData propertyChildCategoryData = null;

    List<PropertyFeaturesData> propertyFeaturesDataSelectedList;
    PropertyFeaturesData propertyFeaturesData = null;
    List<PropertyNearByData> propertyNearByDataSelectedList;
    PropertyNearByData propertyNearByData = null;
    //More filter Contents End

    public DashboardTwoFragment() {
        // Required empty public constructor
    }


    public static DashboardTwoFragment newInstance(String param1, String param2) {
        DashboardTwoFragment fragment = new DashboardTwoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        helper = new Helper(getContext());
        user = helper.getLoggedInUser();
        // Inflate the layout for this fragment
        if (mView == null)
            mView = inflater.inflate(R.layout.fragment_dashboard_two, container, false);

        mListLayout = mView.findViewById(R.id.list_view_container);
        mMapLayout = mView.findViewById(R.id.map_view_container);
        drawer = getActivity().findViewById(R.id.drawer_layout);

        propertyDataList = new ArrayList<>();
        addressesDataList = new ArrayList<>();

        initAppbar();

        mRecyclerview = mView.findViewById(R.id.recyclerview);
        new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                propertyDataList.clear();
                propertyDataList = propertyData;
                new PropertyDashboardTwoListRecyclerview_config().setConfig(mRecyclerview,getContext(),propertyDataList);
            }
        }, getContext());

        initMapsControls();

        initBottomSheet();

        fabCreateInit();

        filterBarInit();

        priceFilterInit();

        moreFilterInit();

        return mView;
    }

    private void initAppbar() {
        mMenuBtnLayoutD1 = mView.findViewById(R.id.menubtn_layout);
        mMenuIconD1 = mView.findViewById(R.id.menuIcon);
        mSearchEdittext = mView.findViewById(R.id.et_search);
        mswitcherToggle = mView.findViewById(R.id.btn_switch);
        mChatButtonD1 = mView.findViewById(R.id.img_chat);
        mProfileImageD1 = mView.findViewById(R.id.profile_image);

        mswitcherToggle.setOnCheckedChangeListener(this);

        //Toolbar profile image Profile Image
        if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
            mProfileImageD1.setImageResource(R.drawable.ic_user_icon_colored);
        }else {
            Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_colored).into(mProfileImageD1);
        }

        //on menu click nav drawer open
        mMenuBtnLayoutD1.setOnClickListener(this);
        //on search click open search screen
        mSearchEdittext.setOnClickListener(this);
        //Profile image click listener
        mProfileImageD1.setOnClickListener(this);
        //Chat Image Click listener
        mChatButtonD1.setOnClickListener(this);
    }

    private void fabCreateInit() {

        mFabMenu = mView.findViewById(R.id.material_design_android_floating_action_menu);

        //Fab menu
        FloatingActionButton createProperty = mView.findViewById(R.id.floating_menu_create_property);
        FloatingActionButton createProject = mView.findViewById(R.id.floating_menu_create_project);
        FloatingActionButton createJob = mView.findViewById(R.id.floating_menu_create_job);
        FloatingActionButton createDeveloper = mView.findViewById(R.id.floating_menu_create_developer);
        FloatingActionButton createEstate = mView.findViewById(R.id.floating_menu_create_estate);
        createProperty.setOnClickListener(this);
        createProject.setOnClickListener(this);
        createJob.setOnClickListener(this);
        createDeveloper.setOnClickListener(this);
        createEstate.setOnClickListener(this);
    }

    private void initBottomSheet() {
        //Bottom Sheet

        mBrokerProfileImage =  mView.findViewById(R.id.profile_images);
        mBrokerName =  mView.findViewById(R.id.tv_broker_name);
        mEstateName =  mView.findViewById(R.id.tv_estate);
        mBrokerRating =  mView.findViewById(R.id.rating);
        mPropertyImage =  mView.findViewById(R.id.post_image);
        mPropertyImage = mView.findViewById(R.id.post_image);
        mTitle = mView.findViewById(R.id.tv_title);
        mPrice = mView.findViewById(R.id.tv_price);
        mAddress = mView.findViewById(R.id.tv_address);
        mNoBed = mView.findViewById(R.id.tv_beds);
        mNoBath = mView.findViewById(R.id.tv_baths);
        mNoUnit = mView.findViewById(R.id.tv_units);
        mCallBtn = mView.findViewById(R.id.btn_call);
        mFavoriteBTN = mView.findViewById(R.id.btn_favorite);
        mGetDirectionBTN = mView.findViewById(R.id.btn_get_direction);
        mSMSBtn = mView.findViewById(R.id.btn_sms);
        mAudioBtn = mView.findViewById(R.id.btn_audio);
        mAudioBtnLayout = mView.findViewById(R.id.layoutAudio);
        mTime = mView.findViewById(R.id.tv_time);

        //mBottomSheet  = mView.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(mView.findViewById(R.id.bottomSheetLayout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void priceFilterInit() {

        priceRangeSeekBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tv_pricerange.setText(String.format("%s - %s", minValue, maxValue));
            }
        });

    }

    private void filterBarInit() {

        property_filters_button = mView.findViewById(R.id.property_filters);
        listingToggle = mView.findViewById(R.id.btn_listing);
        priceToggle = mView.findViewById(R.id.btn_price);
        homeTypeToggle = mView.findViewById(R.id.btn_home_type);
        moreToggle = mView.findViewById(R.id.btn_more);
        filtersLayoutMain = mView.findViewById(R.id.filters_layout);
        listing_filter_layout = mView.findViewById(R.id.listing_filter_layout);
        price_filter_layout = mView.findViewById(R.id.price_filter_layout);
        hometype_filter_layout = mView.findViewById(R.id.hometype_filter_layout);
        more_filter_layout = mView.findViewById(R.id.more_filter_layout);
        mFilterListingForWantedToggle = mView.findViewById(R.id.btn_filter_for_wanted_listing);
        mForwantedfilterLayout = mView.findViewById(R.id.forwanted_listing_layout_filter);
        priceRangeSeekBar = mView.findViewById(R.id.pricerangeseekbar);
        tv_pricerange = mView.findViewById(R.id.tv_pricerange);

        //Filter buttons listners
        listingToggle.setOnCheckedChangeListener(this);
        mFilterListingForWantedToggle.setOnCheckedChangeListener(this);
        priceToggle.setOnCheckedChangeListener(this);
        homeTypeToggle.setOnCheckedChangeListener(this);
        moreToggle.setOnCheckedChangeListener(this);

    }

    private void moreFilterInit() {
        propertyParentCategoryDataList = new ArrayList<>();
        propertyChildCategoryDataList = new ArrayList<>();

        propertyFeaturesDataSelectedList = new ArrayList<>();
        propertyNearByDataSelectedList = new ArrayList<>();

        unitsDataList = new ArrayList<>();
        currencyDataList = new ArrayList<>();

        propertyTypesChecks = mView.findViewById(R.id.property_types_radio_group);
        propertyTypesChildChecks = mView.findViewById(R.id.types_child_radio_group);
        bedroomsChecks = mView.findViewById(R.id.bedroom_radio_group);
        bathroomsChecks = mView.findViewById(R.id.bathroom_radio_group);
        featuresChecks = mView.findViewById(R.id.features_view);
        nearByChecks = mView.findViewById(R.id.nearby_view);

        mChangeCurrency = mView.findViewById(R.id.txt_change_currency);
        mCurrencyText = mView.findViewById(R.id.txt_currency);
        mChangeAreaUnit = mView.findViewById(R.id.txt_change_area_unit);
        mAreaTextUnit = mView.findViewById(R.id.txt_area_unit);

        mChangeCurrency.setOnClickListener(this);
        mChangeAreaUnit.setOnClickListener(this);

        dialog = new Dialog(getContext());
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_radio_group_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialoglistView = dialog.findViewById(R.id.list);
        mDialogTitle = dialog.findViewById(R.id.tv_title);

        mFilterProperty = mView.findViewById(R.id.btn_filter);

        mFilterProperty.setOnClickListener(this);

        //PropertyTypes Layout
        setPropertyTypesLayout();

        //PropertyTypesChild Layout
        new PropertyChildCategoryApiHelper().readPropertyChildCatergoryList(new PropertyChildCategoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyChildCategoryUtils> propertyChildCategoryUtils) {
                if (propertyChildCategoryUtils.get(0).getNoOfRecords() > 0){
                    propertyChildCategoryDataList.clear();
                    propertyChildCategoryDataList = propertyChildCategoryUtils.get(0).getData();
                }
            }
        }, getContext());

        //bedrooms Layout
        setBedroomsLayout();

        //bathrooms Layout
        setBathroomLayout();

        //Property Features Layout
        setPropertyFeaturesLayout();

        //Property Near By Layout
        setPropertyNearByLayout();

        //GetCurrenciew
        getCurrenriesData();

        //get area units
        getAreaUnits();
    }

    private void getCurrenriesData() {
        new CurrencyApiHelper().readCurrencyList(new CurrencyApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<CurrencyData> currencyData) {
                currencyDataList.clear();
                currencyDataList = currencyData;
            }
        }, getContext());
    }

    private void getAreaUnits() {
        new UnitApiHelper().readUnitsList(new UnitApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UnitsData> unitsData) {
                unitsDataList.clear();
                unitsDataList = unitsData;
            }
        }, getContext());
    }

    private void setPropertyTypesLayout() {
        new PropertyParentCatergoryApiHelper().readPropertyParentCatergoryList(new PropertyParentCatergoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyParentCategoryUtils> propertyParentCategoryUtils) {
                if (propertyParentCategoryUtils.get(0).getNoOfRecords() > 0 && !propertyParentCategoryUtils.isEmpty()){
                    propertyTypesChecks.removeAllViews();
                    for ( int i = 0; i < propertyParentCategoryUtils.get(0).getData().size(); i++) {
                        if (getContext() != null){
                            RadioButton button = new RadioButton(getContext());
                            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(210, 60);
                            if (i == 0){
                                //setting default check
                                button.setChecked(true);
                                button.setTextColor(getResources().getColor(R.color.colorWhite));
                                propertyParentCategoryData = propertyParentCategoryUtils.get(0).getData().get(i);
                                setPropertyTypesChildLayout(propertyParentCategoryData);
                                params.setMargins(40, 0, 0, 0);
                            }else {
                                params.setMargins(10, 0, 10, 0);
                            }
                            button.setLayoutParams(params);
                            button.setGravity(Gravity.CENTER);
                            button.setId(Integer.parseInt(propertyParentCategoryUtils.get(0).getData().get(i).getId()));
                            button.setText(propertyParentCategoryUtils.get(0).getData().get(i).getTitle());
                            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                            button.setButtonDrawable(null);
                            button.setPadding(0,0,0,0);

                            //Performing on Check
                            int finalI = i;
                            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (b){
                                        propertyParentCategoryData = propertyParentCategoryUtils.get(0).getData().get(finalI);
                                        setPropertyTypesChildLayout(propertyParentCategoryData);
                                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                                    }else {
                                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                                    }
                                }
                            });
                            propertyTypesChecks.addView(button);
                        }
                    }
                }
            }
        }, getContext());
    }

    private void setPropertyTypesChildLayout(PropertyParentCategoryData propertyParentCategoryData) {

        if (propertyParentCategoryData != null && !propertyChildCategoryDataList.isEmpty()){
            propertyTypesChildChecks.removeAllViews();
            for ( int i = 0; i < propertyChildCategoryDataList.size(); i++) {
                if (propertyChildCategoryDataList.get(i).getPropertyParentCategoryId().equals(propertyParentCategoryData.getId())){

                    RadioButton button = new RadioButton(getContext());
                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 0, 10, 0);
                    if (i == 0){

                        //setting default check
                        button.setChecked(true);
                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                        propertyChildCategoryData = propertyChildCategoryDataList.get(i);
                    }
                    button.setLayoutParams(params);
                    button.setGravity(Gravity.CENTER);
                    button.setId(Integer.parseInt(propertyChildCategoryDataList.get(i).getId()));
                    button.setText(propertyChildCategoryDataList.get(i).getTitle());
                    button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                    //button.setButtonDrawable(null);
                    button.setPadding(30,0,30,0);

                    //Performing on Check
                    int finalI = i;
                    button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b){
                                propertyChildCategoryData = propertyChildCategoryDataList.get(finalI);

                                button.setTextColor(getResources().getColor(R.color.colorWhite));
                            }else {
                                button.setTextColor(getResources().getColor(R.color.colorBlack));
                            }
                        }
                    });
                    propertyTypesChildChecks.addView(button);
                    //this is params for index one layout only
                    RadioGroup.LayoutParams paramindexone = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
                    paramindexone.setMargins(40, 0, 10, 0);
                    propertyTypesChildChecks.getChildAt(0).setLayoutParams(new RadioGroup.LayoutParams(paramindexone));
                }
            }
        }
    }

    private void setBedroomsLayout() {
        bedroomsChecks.removeAllViews();
        for (int i = 0; i < bedrooms.length; i++) {
            RadioButton button = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
            if (i == 0){
                button.setChecked(true);
                button.setTextColor(getResources().getColor(R.color.colorWhite));
                params.setMargins(40, 0, 0, 0);
            }else {
                params.setMargins(10, 0, 10, 0);
            }
            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setText(bedrooms[i]);
            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
            button.setButtonDrawable(null);
            button.setPadding(40,0,40,0);

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                    }else {
                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }
            });

            bedroomsChecks.addView(button);
        }

    }

    private void setBathroomLayout() {
        bathroomsChecks.removeAllViews();
        for (int i = 0; i < bathrooms.length; i++) {
            RadioButton button = new RadioButton(getContext());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
            if (i == 0){
                button.setChecked(true);
                button.setTextColor(getResources().getColor(R.color.colorWhite));
                params.setMargins(40, 0, 0, 0);
            }else {
                params.setMargins(10, 0, 10, 0);
            }
            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setText(bathrooms[i]);
            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
            button.setButtonDrawable(null);
            button.setPadding(40,0,40,0);

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                    }else {
                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }
            });

            bathroomsChecks.addView(button);
        }
    }

    private void setPropertyFeaturesLayout() {
        new PropertyFeaturesApiHelper().readPropertyFeaturesList(new PropertyFeaturesApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyFeaturesUtils> propertyFeaturesUtils) {
                if (propertyFeaturesUtils.get(0).getNoOfRecords() > 0 && !propertyFeaturesUtils.isEmpty()){
                    featuresChecks.removeAllViews();
                    propertyFeaturesDataSelectedList.clear();
                    for ( int i = 0; i < propertyFeaturesUtils.get(0).getData().size(); i++) {
                        if (getContext() != null){
                            CheckBox button = new CheckBox(getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            if (i == 0){
                                params.setMargins(40, 0, 0, 0);
                            }else {
                                params.setMargins(10, 0, 10, 0);
                            }
                            button.setLayoutParams(params);
                            button.setGravity(Gravity.CENTER);
                            button.setId(Integer.parseInt(propertyFeaturesUtils.get(0).getData().get(i).getId()));
                            button.setText(propertyFeaturesUtils.get(0).getData().get(i).getTitle());
                            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                            button.setButtonDrawable(null);
                            button.setPadding(30,0,30,0);

                            //Performing on Check
                            int finalI = i;
                            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (b){
                                        propertyFeaturesDataSelectedList.add(propertyFeaturesUtils.get(0).getData().get(finalI));
                                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                                    }else {
                                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                                    }
                                }
                            });
                            featuresChecks.addView(button);
                        }
                    }
                }
            }
        }, getContext());
    }

    private void setPropertyNearByLayout() {
        new PropertyNearByApiHelper().readPropertyNearByList(new PropertyNearByApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyNearByUtils> propertyNearByUtils) {
                if (propertyNearByUtils.get(0).getNoOfRecords() > 0 && !propertyNearByUtils.isEmpty()){
                    nearByChecks.removeAllViews();
                    propertyNearByDataSelectedList.clear();
                    for ( int i = 0; i < propertyNearByUtils.get(0).getData().size(); i++) {
                        if (getContext() != null){
                            CheckBox button = new CheckBox(getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            if (i == 0){
                                params.setMargins(40, 0, 0, 0);
                            }else {
                                params.setMargins(10, 0, 10, 0);
                            }
                            button.setLayoutParams(params);
                            button.setGravity(Gravity.CENTER);
                            button.setId(Integer.parseInt(propertyNearByUtils.get(0).getData().get(i).getId()));
                            button.setText(propertyNearByUtils.get(0).getData().get(i).getTitle());
                            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                            button.setButtonDrawable(null);
                            button.setPadding(30,0,30,0);

                            //Performing on Check
                            int finalI = i;
                            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (b){
                                        propertyNearByDataSelectedList.add(propertyNearByUtils.get(0).getData().get(finalI));
                                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                                    }else {
                                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                                    }
                                }
                            });
                            nearByChecks.addView(button);
                        }
                    }
                }
            }
        }, getContext());
    }

    private void initMapsControls() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        llMapActionContainer = mView.findViewById(R.id.llMapActionContainer);
        flMapContainer = mView.findViewById(R.id.flMapContainer);

        mDrawToggle = mView.findViewById(R.id.btn_draw);
        mNearByToggle = mView.findViewById(R.id.btn_near);

        drawingpanel = new DrawingPanel(getContext());
        drawingpanel.setVisibility(View.GONE);
        drawingpanel.setBackgroundColor(Color.parseColor("#50000000"));
        flMapContainer.addView(drawingpanel);

        mDrawToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    onDrawOnMapClick();
                } else {
                    onRedoSearchClick();
                }
            }
        });

        mNearByToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    initDialogControl();
                }else {
                    if (addressesDataList != null){
                        addMarkers();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).setListener(new MapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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
                    //get addresses
                    new AddressAPIHelper().readRefrenceTypeAdressesData(new AddressAPIHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<AddressesData> addressesDataList1) {
                            addressesDataList.clear();
                            addressesDataList = addressesDataList1;
                            if (addressesDataList != null){
                                addMarkers();
                            }
                        }
                    }, getContext(), "Property");
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
                    polygonOptions.strokeColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    polygonOptions.addAll(latLngList);
                    mMap.addPolygon(polygonOptions);

                    drawingpanel.setVisibility(View.GONE);

                    //find radius and center from drawing
                    LatLng latLng1 = GoogleMapsUtils.getPolygonCenterPoint(latLngList, bounds,latLng);
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
                        addMarkers();
                }
            }
        });
    }

    private void addMarkers() {

        int count = 0;
        mMap.clear();
        clusterManager = new ClusterManager<>(Objects.requireNonNull(getContext()), mMap);  // 3
        clusterManager.setRenderer(new MarkerClusterRenderer(getContext(), mMap, clusterManager, propertyDataList));
        clusterItems = new ArrayList<>();
        clusterItems.clear();

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.setOnClusterItemClickListener(this);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<PropertyData> propertyDataListBounded = new ArrayList<>();
        propertyDataListBounded.clear();
        for (int index = 0; index < addressesDataList.size(); index++) {
            LatLng getLatLng = new LatLng(Double.parseDouble(addressesDataList.get(index).getLatitude()), Double.parseDouble(addressesDataList.get(index).getLongitude()));

            if (latLngBounds != null) {
                if (latLngBounds.contains(getLatLng)) {
                    //add cluster item to list
                    clusterItems.add(new ClusterMapAddress(getLatLng, addressesDataList.get(index).getAreaTitle(), addressesDataList.get(index).getAreaTitle(), addressesDataList.get(index).getReferenceId()));

                    for (int i = 0; i<propertyDataList.size(); i++) {
                        if (addressesDataList.get(index).getReferenceId().equals(propertyDataList.get(i).getId())){
                            propertyDataListBounded.add(propertyDataList.get(i));
                        }
                    }
                }
            } else {
                //add cluster item to list
                clusterItems.add(new ClusterMapAddress(getLatLng, addressesDataList.get(index).getAreaTitle(), addressesDataList.get(index).getAreaTitle(), addressesDataList.get(index).getReferenceId()));
                builder.include(getLatLng);
                count++;

                for (int i = 0; i<propertyDataList.size(); i++) {
                    if (addressesDataList.get(index).getReferenceId().equals(propertyDataList.get(i).getId())){
                        propertyDataListBounded.add(propertyDataList.get(i));
                    }
                }
            }
        }


        new PropertyDashboardTwoListRecyclerview_config().setConfig(mRecyclerview,getContext(),propertyDataListBounded);

        if (count > 0 && latLngBounds == null) {
            //Cluster Manager
            clusterManager.addItems(clusterItems);// 4
            clusterManager.cluster();  // 5
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 80);
            mMap.animateCamera(cu);
        }else {
            //Cluster Manager
            clusterManager.addItems(clusterItems);
            clusterManager.cluster();  // 5
        }

    }

    private void onRedoSearchClick() {

        if (latLngList != null)
            latLngList.clear();
        mMap.clear();

        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;
        latLng = latLngBounds.getCenter();
        maxDistanceFromCenter = GoogleMapsUtils.distance(latLngBounds.getCenter().latitude, latLngBounds.getCenter().longitude, visibleRegion.farRight.latitude, visibleRegion.farRight.longitude);

        this.latLngBounds = null;
        addMarkers();
    }

    private void onDrawOnMapClick() {
        if (latLngList != null)
            latLngList.clear();
        mMap.clear();
        llMapActionContainer.setVisibility(View.GONE);
        drawingpanel.setVisibility(View.VISIBLE);
        drawingpanel.clear();
        polygonOptions = new PolygonOptions();
    }

    @Override
    public boolean onClusterItemClick(ClusterMapAddress clusterMapAddress) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else {
            if (!clusterMapAddress.getTag().equals("broker")){
                propertyData = null;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(clusterMapAddress.getPosition()));
                for ( PropertyData property : propertyDataList) {
                    if (property.getId().equals(clusterMapAddress.getTag())){
                        propertyData = property;
                    }
                }

                if (propertyData == null){
                    Toast.makeText(getContext(), "No Details Found", Toast.LENGTH_SHORT).show();
                }else {
                    setPropertyBottomSheetData(propertyData, clusterMapAddress.getPosition());
                }
            }
        }
        return true;
    }

    public class MarkerClusterRenderer extends DefaultClusterRenderer<ClusterMapAddress> {   // 1

        List<PropertyData> propertyDataList2 = new ArrayList<>();

        private MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<ClusterMapAddress> clusterManager, List<PropertyData> propertyDataList) {
            super(context, map, clusterManager);
            propertyDataList2 = propertyDataList;
        }

        @Override
        protected void onBeforeClusterItemRendered(ClusterMapAddress item, MarkerOptions markerOptions) { // 5
            IconGenerator iconGen = new IconGenerator(getContext());
            // Define the size you want from dimensions file
            int shapeSize = 4;
            String title = "";
            Drawable shapeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_marker_blue_icon, null);
            if (propertyDataList2 != null){
                for (PropertyData data :propertyDataList2){
                    if (data.getId().equals(item.getTag())){
                        title = data.getTitle();
                        switch (data.getPropertyPurposeId()){
                            case "3":
                                shapeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_marker_maroon_icon, null);
                                break;
                            case "4":
                                shapeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_marker_sky_blue_icon, null);
                                break;
                            case "5":
                                shapeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_marker_green_icon, null);
                                break;
                            default:
                                shapeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_marker_blue_icon, null);
                        }
                    }
                }
            }
            iconGen.setBackground(shapeDrawable);
            // Create a view container to set the size
            View view = new View(getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(shapeSize, shapeSize));
            iconGen.setContentView(view);
            // Create the bitmap
            Bitmap bitmap = iconGen.makeIcon();
            //Bitmap icon = iconGenerator.makeIcon();  // 7
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));  // 8
            markerOptions.title(item.getTitle());
            markerOptions.snippet(title);
        }
    }

    private void initDialogControl() {
        //mNearByToggle.setChecked(false);

        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_LOCATION);

        }else {
            //get location and add marker
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            //Getting Dialog ids
            distance_select_container = mView.findViewById(R.id.distance_select_container);
            dialog_container = mView.findViewById(R.id.dialog_container);
            bubbleSeekBar = mView.findViewById(R.id.distancerangeseekbar);
            mClearDialogButton = mView.findViewById(R.id.btn_close);
            mSubmitDialogButton = mView.findViewById(R.id.btn_submit);
            //End Dialog ids
            if (distance_select_container.getAlpha() == 1){
                YoYo.with(Techniques.SlideOutRight).duration(200).playOn(distance_select_container);
                //distance_select_container.setVisibility(View.GONE);
                //flMapContainer.setVisibility(View.VISIBLE);
            }else {
                YoYo.with(Techniques.SlideInRight).duration(200).playOn(distance_select_container);
                distance_select_container.setVisibility(View.VISIBLE);
                //flMapContainer.setVisibility(View.GONE);
            }


            bubbleSeekBar.getConfigBuilder()
                    .min(0)
                    .max(25)
                    .progress(0)
                    .sectionCount(5)
                    .seekBySection()
                    .trackColor(ContextCompat.getColor(getContext(), R.color.colorGrey))
                    .secondTrackColor(ContextCompat.getColor(getContext(), R.color.colorBlue))
                    .thumbColor(ContextCompat.getColor(getContext(), R.color.colorBlue))
                    .showSectionText()
                    .sectionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                    .sectionTextSize(12)
                    .showThumbText()
                    .thumbTextColor(ContextCompat.getColor(getContext(), R.color.colorBlue))
                    .thumbTextSize(9)
                    .showSectionMark()
                    .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                    .build();

            bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                }

                @Override
                public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                }

                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                    fianlSelectedDistance = progress;
                }
            });

            mClearDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNearByToggle.setChecked(false);
                    YoYo.with(Techniques.SlideOutRight).duration(200).playOn(distance_select_container);
                    //flMapContainer.setVisibility(View.VISIBLE);
                    //distance_select_container.setVisibility(View.GONE);
                }
            });

            mSubmitDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNearByToggle.setChecked(true);
                    fianlSelectedDistance = bubbleSeekBar.getProgress();

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(current);
                    circleOptions.radius(fianlSelectedDistance * 1000);
                    circleOptions.fillColor(R.color.colorOFFGrey);
                    circleOptions.strokeWidth(0).strokeColor(R.color.colorOFFGrey);
                    mMap.addCircle(circleOptions);
                    Geocoder geocoder = new Geocoder(getContext(), getDefault());

                    //Add Marker
                    mMap.addMarker(new MarkerOptions().position(current).snippet("You"));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(10).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    YoYo.with(Techniques.SlideOutRight).duration(200).playOn(distance_select_container);
                    //flMapContainer.setVisibility(View.VISIBLE);
                    //distance_select_container.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    @SuppressLint("MissingPermission")
                    Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(current).snippet("You"));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(14).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }else{
                    Toast.makeText(getContext(), "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    private void setPropertyBottomSheetData(PropertyData propertyData, LatLng position) {

        new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UsersData> usersData) {

                //Broker image
                if (usersData.get(0).getImg() != null){
                    Picasso.get().load(usersData.get(0).getImg().toString()).placeholder(R.drawable.ic_user_icon_white).centerCrop().fit().into(mBrokerProfileImage);
                }else{
                    mBrokerProfileImage.setImageResource(R.drawable.ic_user_icon_white);
                }

                //Broker Name
                if ((usersData.get(0).getFullName() == null) || (usersData.get(0).getFullName().equals(""))){
                    mBrokerName.setVisibility(View.GONE);
                }else {
                    mBrokerName.setText(usersData.get(0).getFullName());
                }

                //Estate Name
                if ((usersData.get(0).getCompanyName() == null) || (usersData.get(0).getCompanyName().equals(""))){
                    mEstateName.setVisibility(View.GONE);
                }else {
                    mEstateName.setText(usersData.get(0).getCompanyName());
                }

            }
        },getContext(), propertyData.getCreatedByUserId());


        //Property image
        if (propertyData.getThumbnail() != null || !propertyData.getThumbnail().equals("")){
            Picasso.get().load(propertyData.getThumbnail().toString()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mPropertyImage);
        }else{
            mPropertyImage.setImageResource(R.mipmap.mainscreenbackground);
        }
        //Time
        mTime.setText(Helper.covertTimeToText(propertyData.getCreatedAt()));

        //Property Description
        if ((propertyData.getTitle() == null) || (propertyData.getTitle().equals(""))){
            mTitle.setVisibility(View.GONE);
        }else {
            mTitle.setText(propertyData.getTitle());
        }

        //Location Address
        new AddressAPIHelper().readRefrenceWithTypeAdressesData(new AddressAPIHelper.DataStatus() {
            @SuppressLint("SetTextI18n")
            @Override
            public void DataIsLoaded(List<AddressesData> addressesData) {
                if (addressesData.isEmpty()){
                    mAddress.setText("Not found");
                }else {
                    mAddress.setText(addressesData.get(0).getLocationTitle() +" "
                            +addressesData.get(0).getAreaTitle()+" "
                            +addressesData.get(0).getAreaTitle()+" "
                            +addressesData.get(0).getCityTitle()+" "
                            +addressesData.get(0).getProvinceTitle()+" "
                            +addressesData.get(0).getCountryTitle()
                    );
                }
            }
        }, getContext(), propertyData.getId(), "Property");

        //Audio Visibility
        if ((propertyData.getAudioRecording() == null) || (propertyData.getAudioRecording().equals(""))){
            mAudioBtnLayout.setVisibility(View.GONE);
        }

        //Favorite Button Click
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        //Fav click
        mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //animation
                compoundButton.startAnimation(scaleAnimation);
            }
        });

        //get direction
        mGetDirectionBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //animation
                compoundButton.startAnimation(scaleAnimation);
                if (position != null){
                    Intent intent = new Intent(getContext(), Get_Direction_Activity.class);
                    intent.putExtra("targetPosition",position);
                    startActivity(intent);
                }
            }
        });

        //Audio click
        mAudioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //animation
                compoundButton.startAnimation(scaleAnimation);
            }
        });

        //Geting phone numbers and implement sms-BTN and Call-BTN Listner
        new PhoneDatabaseHelper().readPhoneList(new PhoneDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PhoneData> phoneData) {

                //sms click
                mSMSBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        //animation
                        compoundButton.startAnimation(scaleAnimation);
                        //Check if list is not null
                        if (phoneData.isEmpty()){
                            mSMSBtn.setChecked(false);
                            Toast.makeText(getContext(), "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mSMSBtn.setChecked(true);
                            new CustomDialogBox().showSMSDialog(getContext(), phoneData);
                        }
                    }
                });

                //Call click
                mCallBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        //animation
                        compoundButton.startAnimation(scaleAnimation);
                        //Check if list is not null
                        if (phoneData.isEmpty()){
                            mCallBtn.setChecked(false);
                            Toast.makeText(getContext(), "No phone number Available", Toast.LENGTH_SHORT).show();
                        }else{
                            mCallBtn.setChecked(true);
                            new CustomDialogBox().showCallDialog(getContext(), phoneData);
                        }
                    }
                });
            }
        }, getContext(), propertyData.getCreatedByUserId(), "Broker");
        //End layout filling

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        switch (id){
            case R.id.menubtn_layout:
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!drawer.isDrawerOpen(Gravity.LEFT)) drawer.openDrawer(Gravity.LEFT);
                else drawer.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.et_search :
                intent = new Intent(getContext(), PropertySearchActivity.class);
                startActivity(intent);
                break;
            case R.id.profile_image :
                intent = new Intent(getContext(), BrokerProfileActivity.class);
                intent.putExtra("brokerid",user.getResponse().getData().getUser().getUserId());
                startActivity(intent);
                break;
            case R.id.img_chat:
                startActivity(new Intent(getContext(), ChatActivity.class));
                break;
            case R.id.menubtn_layout_two:
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!drawer.isDrawerOpen(Gravity.LEFT)){
                    drawer.openDrawer(Gravity.LEFT);
                } else{
                    drawer.closeDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.floating_menu_create_property:
                intent = new Intent(getContext(), ListPropertyOptions.class);
                startActivity(intent);
                break;
            case R.id.floating_menu_create_project:
                intent = new Intent(getContext(), CreateProjectActivity.class);
                startActivity(intent);
                break;
            case R.id.floating_menu_create_job:
                intent = new Intent(getContext(), CreateJobsActivity.class);
                startActivity(intent);
                break;
            case R.id.floating_menu_create_developer:
                intent = new Intent(getContext(), CreateDeveloperActivity.class);
                startActivity(intent);
                break;
            case R.id.floating_menu_create_estate:
                intent = new Intent(getContext(), CreateEstateActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_change_area_unit:
                mDialogTitle.setText("Select Area Unit");
                UnitArrayAdapter unitArrayAdapter = new UnitArrayAdapter(getContext(),unitsDataList);
                mDialoglistView.setAdapter(unitArrayAdapter);
                dialog.show();
                mDialoglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        unitsData = (UnitsData) adapterView.getItemAtPosition(i);
                        Toast.makeText(getContext(), unitsData.getTitle(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.txt_change_currency:
                mDialogTitle.setText("Select Currency");
                CurrencyArrayAdapter currencyArrayAdapter = new CurrencyArrayAdapter(getContext(),currencyDataList);
                mDialoglistView.setAdapter(currencyArrayAdapter);
                dialog.show();
                mDialoglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        currencyData = (CurrencyData) adapterView.getItemAtPosition(position);
                        Toast.makeText(getContext(), currencyData.getTitle(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_filter:
                Toast.makeText(getContext(), "Data saved Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.btn_switch:
                if (b){
                    mListLayout.setVisibility(View.GONE);
                    mMapLayout.setVisibility(View.VISIBLE);
                    mFabMenu.setVisibility(View.GONE);
                }else {
                    mFabMenu.setVisibility(View.VISIBLE);
                    mListLayout.setVisibility(View.VISIBLE);
                    mMapLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_listing:
                if (b){
                    listingToggle.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceToggle.setChecked(false);
                    homeTypeToggle.setChecked(false);
                    moreToggle.setChecked(false);

                    price_filter_layout.setVisibility(View.GONE);
                    hometype_filter_layout.setVisibility(View.GONE);
                    more_filter_layout.setVisibility(View.GONE);
                    filtersLayoutMain.setVisibility(View.VISIBLE);
                    listing_filter_layout.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeIn).duration(200).playOn(filtersLayoutMain);
                }else {
                    listingToggle.setTextColor(getResources().getColor(R.color.colorBlue));
                    YoYo.with(Techniques.FadeOut).duration(200).playOn(filtersLayoutMain);
                    filtersLayoutMain.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_filter_for_wanted_listing:
                if (b){
                    mForwantedfilterLayout.setVisibility(View.VISIBLE);
                }else {
                    mForwantedfilterLayout.setVisibility(View.GONE);
                }
                break;

            case R.id.btn_price:
                if (b){
                    priceToggle.setTextColor(getResources().getColor(R.color.colorWhite));
                    listingToggle.setChecked(false);
                    homeTypeToggle.setChecked(false);
                    moreToggle.setChecked(false);

                    listing_filter_layout.setVisibility(View.GONE);
                    hometype_filter_layout.setVisibility(View.GONE);
                    more_filter_layout.setVisibility(View.GONE);
                    filtersLayoutMain.setVisibility(View.VISIBLE);
                    price_filter_layout.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.FadeIn).duration(200).playOn(filtersLayoutMain);
                }else {
                    priceToggle.setTextColor(getResources().getColor(R.color.colorBlue));
                    YoYo.with(Techniques.FadeOut).duration(200).playOn(filtersLayoutMain);
                    filtersLayoutMain.setVisibility(View.GONE);
                }
                break;

            case R.id.btn_home_type:
                if (b){
                    homeTypeToggle.setTextColor(getResources().getColor(R.color.colorWhite));
                    listingToggle.setChecked(false);
                    priceToggle.setChecked(false);
                    moreToggle.setChecked(false);

                    listing_filter_layout.setVisibility(View.GONE);
                    price_filter_layout.setVisibility(View.GONE);
                    more_filter_layout.setVisibility(View.GONE);
                    hometype_filter_layout.setVisibility(View.VISIBLE);
                    filtersLayoutMain.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.FadeIn).duration(200).playOn(filtersLayoutMain);
                }else {

                    filtersLayoutMain.setVisibility(View.GONE);
                    YoYo.with(Techniques.FadeOut).duration(200).playOn(filtersLayoutMain);
                    homeTypeToggle.setTextColor(getResources().getColor(R.color.colorBlue));
                }
                break;

            case R.id.btn_more:
                if (b){
                    moreToggle.setTextColor(getResources().getColor(R.color.colorWhite));
                    listingToggle.setChecked(false);
                    priceToggle.setChecked(false);
                    homeTypeToggle.setChecked(false);

                    listing_filter_layout.setVisibility(View.GONE);
                    price_filter_layout.setVisibility(View.GONE);
                    hometype_filter_layout.setVisibility(View.GONE);
                    filtersLayoutMain.setVisibility(View.VISIBLE);
                    more_filter_layout.setVisibility(View.VISIBLE);

                    YoYo.with(Techniques.SlideInRight).duration(200).playOn(filtersLayoutMain);
                }else {
                    YoYo.with(Techniques.SlideOutRight).duration(200).playOn(filtersLayoutMain);
                    filtersLayoutMain.setVisibility(View.GONE);
                    moreToggle.setTextColor(getResources().getColor(R.color.colorBlue));
                }
                break;
        }
    }
}
