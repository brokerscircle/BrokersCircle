package brokerscircle.com.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Gravity;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.Activities.Brokers.BrokerProfileActivity;
import brokerscircle.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.Activities.Developers.ViewAllDevelopersActivity;
import brokerscircle.com.Activities.Estates.ViewAllEstateActivity;
import brokerscircle.com.Activities.Estates.ViewAllOwnersEstateActivity;
import brokerscircle.com.Activities.Projects.ViewAllProjectsActivity;
import brokerscircle.com.Activities.Properties.ViewAllPropertiesActivity;
import brokerscircle.com.adapters.AddRecyclerview_horizontal_config;
import brokerscircle.com.adapters.BrokersDashboardRecyclerview_Config_Two;
import brokerscircle.com.adapters.FollowersSuggestion_Horizontal_Recyclerview_Config;
import brokerscircle.com.adapters.JobsRecyclerview_Horizontal_Config;
import brokerscircle.com.adapters.MapTypesRecyclerview_Config;
import brokerscircle.com.adapters.NewsRecyclerview_Horizontal_Config;
import brokerscircle.com.adapters.ProjectRecyclerview_Horizontal_Config;
import brokerscircle.com.adapters.PropertyListRecyclerview_config_Four_small;
import brokerscircle.com.api_helpers.Jobs.JobsDatabaseHelper;
import brokerscircle.com.fragments.DashboardFiveFragment;
import brokerscircle.com.fragments.DashboardFragment;
import brokerscircle.com.fragments.DashboardSixFragment;
import brokerscircle.com.fragments.DashboardTwoFragmentV2;
import brokerscircle.com.fragments.NewsAndInfoFragment;
import brokerscircle.com.fragments.NotificationFragment;
import brokerscircle.com.fragments.ProjectFragment;
import brokerscircle.com.fragments.DashboardThreeFragment;
import brokerscircle.com.fragments.DashboardFourFragment;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.api_helpers.Map.MapTypesDatabaseHelper;
import brokerscircle.com.api_helpers.NewsDatabaseHelper;
import brokerscircle.com.api_helpers.ProjectDatabaseHelper;
import brokerscircle.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscircle.com.api_helpers.UsersDatabaseHelper;
import brokerscircle.com.model.AddUtil;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.model.Map.MapTypes.MapTypesData;
import brokerscircle.com.model.News.NewsData;
import brokerscircle.com.model.Project.ProjectData;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.R;
import brokerscircle.com.model.Users.UsersData;
import brokerscircle.com.model.jobs.JobData;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.sqlite.DatabaseManager;
import brokerscircle.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper;
    private LoginUser user = null;

    private CoordinatorLayout dashboardContentOne, dashboardContentTwo;
    private Toolbar toolbar;
    private ActionBar actionBar;

    //Dashbboard one contents
    private ImageView mMenuIconD1;
    private ImageView mChatButtonD1;
    private LinearLayout mMenuBtnLayoutD1;
    private RelativeLayout mSearchLayoutD1;
    private CircleImageView mProfileImageD1;
    private Fragment fragment;
    private BottomNavigationView navigation;
    private DrawerLayout drawer;

    //Dashboard Two contents
    private LinearLayout mMenuBtnLayoutD2;
    private ImageView mMenuIconD2;
    private ImageView mNotificationD2;
    private ImageView mChatButtonD2;
    private CircleImageView mProfileImageD2;
    private RecyclerView mBrokersRecyclerview, mBannerAddRecyclerview, mPropertiesRecyclerview, mLocationsRecyclerview, mSuggestionRecyclerview, mProjectsRecyclerview, mNewRecyclerview, mJobsRecyclerview;
    private TextView  mBrokersViewallTV, mPropertiesViewallTV, mLocationsViewallTV, mSuggestionViewallTV, mProjectsViewallTV, mNewsViewallTV, mJobsViewallTV;
    private ToggleButton mBrokersShowHideTV;
    private RelativeLayout mBrokersLayout, mBannersLayout, mPropertiesLayout, mLocationLayout, mSuggestionLayout, mProjectLayout, mNewsLayout, mjobsLayout;
    private FloatingActionButton mCreateNewFabButton;

    String dashboard = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        user = helper.getLoggedInUser();
        if (user == null){
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }else {
            setContentView(R.layout.activity_dashboard);

            //sqliteCreateDatabase();

            //Drawer Navigation
            //actionBar.setDisplayHomeAsUpEnabled(true);
            drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            View headerview = navigationView.getHeaderView(0);
            navigationView.setItemIconTintList(null);

            CircleImageView drawerProfile = headerview.findViewById(R.id.img_drawer_profile);
            TextView drawerName = headerview.findViewById(R.id.tv_drawer_name);
            TextView drawerRealEstate = headerview.findViewById(R.id.tv_drawer_estatename);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //Drawer Profile Image
            if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
                drawerProfile.setImageResource(R.drawable.ic_user_icon_colored);
            }else {
                Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_colored).into(drawerProfile);
            }
            //Drawer Name
            drawerName.setText(user.getResponse().getData().getUser().getUserFullName());
            //Drawer Real Estate
            drawerRealEstate.setText(user.getResponse().getData().getUser().getCompName());

            //Getting Ids
            dashboardContentOne = findViewById(R.id.dashboard_content_one);
            dashboardContentTwo = findViewById(R.id.dashboard_content_two);

            /**
             *  One dashboard Contents
             */
            navigationView.inflateMenu(R.menu.drawer_menu_one);
            navigationView.setNavigationItemSelectedListener(mOnDrawerNavigationClickListenerDashboardOne);
            //Dashboard One Content IDs
            mMenuBtnLayoutD1 = findViewById(R.id.menubtn_layout);
            mMenuIconD1 = findViewById(R.id.menuIcon);
            mSearchLayoutD1 = findViewById(R.id.layout_search);
            mChatButtonD1 = findViewById(R.id.img_chat);
            mProfileImageD1 = findViewById(R.id.profile_image);

            //Toolbar profile image Profile Image
            if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
                mProfileImageD1.setImageResource(R.drawable.ic_user_icon_colored);
            }else {
                Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_colored).into(mProfileImageD1);
            }

            //on menu click nav drawer open
            mMenuBtnLayoutD1.setOnClickListener(this);
            //Profile image click listener
            mProfileImageD1.setOnClickListener(this);
            //Chat Image Click listener
            mChatButtonD1.setOnClickListener(this);

            navigation = findViewById(R.id.bottom_navigation);
            navigation.setItemIconTintList(null);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            // load the store fragment by default
            loadFragment(new DashboardFragment());
            //Dashboard Two Contents
            implementDashboardTwoData();
        }
    }

    private void sqliteCreateDatabase() {

        List<String> tableName  = new ArrayList<String>();
        tableName.add("CREATE TABLE setting(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dashboard TEXT, selection BOOLEAN)");

        tableName.add("CREATE TABLE dashboard_styles(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "style INTEGER, selection BOOLEAN, dashboard_id INTEGER FORIGN KEY)");

        //DatabaseManager databaseManager = new DatabaseManager(DashboardActivity.this,  "BrokersHub", 1,null, tableName);
        DatabaseManager databaseManager = new DatabaseManager(DashboardActivity.this,"BrokersHub",1,tableName,tableName);
        databaseManager.openDB();

        //databaseManager.update()
        databaseManager.closeDB();
    }

    private void implementDashboardTwoData() {

        //DashboardTwoContent ids
        mMenuBtnLayoutD2 = findViewById(R.id.menubtn_layout_two);
        mNotificationD2 = findViewById(R.id.icon_notification);
        mChatButtonD2 = findViewById(R.id.img_chattwo);
        mProfileImageD2 = findViewById(R.id.profile_imagetwo);
        mCreateNewFabButton = findViewById(R.id.createfab);
        mBrokersRecyclerview = findViewById(R.id.recyclerview_broker);
        mBannerAddRecyclerview = findViewById(R.id.recycleview_banners);
        mPropertiesRecyclerview = findViewById(R.id.recycleview_properties);
        mLocationsRecyclerview = findViewById(R.id.recycleview_locations);
        mSuggestionRecyclerview = findViewById(R.id.recycleview_suggestion);
        mProjectsRecyclerview = findViewById(R.id.recycleview_projects);
        mNewRecyclerview = findViewById(R.id.recycleview_news);
        mJobsRecyclerview = findViewById(R.id.recycleview_jobs);
        mBrokersShowHideTV = findViewById(R.id.showhide);
        mBrokersLayout = findViewById(R.id.layoutbrokers);
        mPropertiesLayout = findViewById(R.id.layoutpropeties);
        mSuggestionLayout = findViewById(R.id.layoutsuggestions);
        mLocationLayout = findViewById(R.id.layoutlocations);
        mProjectLayout = findViewById(R.id.layoutprojects);
        mNewsLayout = findViewById(R.id.layoutnews);
        mBrokersViewallTV = findViewById(R.id.tv_broker_viewall);
        mPropertiesViewallTV = findViewById(R.id.tv_viewall_properties);
        mSuggestionViewallTV = findViewById(R.id.tv_viewall_suggestion);
        mLocationsViewallTV = findViewById(R.id.tv_viewall_location);
        mProjectsViewallTV = findViewById(R.id.tv_viewall_projects);
        mNewsViewallTV = findViewById(R.id.tv_viewall_news);
        mJobsViewallTV = findViewById(R.id.tv_viewall_jobs);

        //Toolbar profile image Profile Image
        if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
            mProfileImageD2.setImageResource(R.drawable.ic_user_icon_colored);
        }else {
            Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_colored).into(mProfileImageD2);
        }

        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                if ((brokersUtils.size())>0){
                    mBrokersShowHideTV.setVisibility(View.VISIBLE);
                    mBrokersLayout.setVisibility(View.VISIBLE);
                }
                new BrokersDashboardRecyclerview_Config_Two().setConfig(mBrokersRecyclerview,DashboardActivity.this,brokersUtils);
            }
        },DashboardActivity.this);

        //ADDS Banner Recyclerview
        List<AddUtil> bannerList = new ArrayList<>();
        bannerList.add(new AddUtil(R.mipmap.b1, "Add One"));
        bannerList.add(new AddUtil(R.mipmap.b2, "Add Two"));
        bannerList.add(new AddUtil(R.mipmap.b3, "Add Three"));

        //Advertisement Banner
        new AddRecyclerview_horizontal_config().setConfig(mBannerAddRecyclerview,DashboardActivity.this, bannerList);

        // Property recyclerview
        new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                if ((propertyData.size())>0){
                    mPropertiesLayout.setVisibility(View.VISIBLE);
                }
                new PropertyListRecyclerview_config_Four_small().setConfig(mPropertiesRecyclerview,DashboardActivity.this,propertyData);
            }
        }, DashboardActivity.this);

        //Area Recyclerview
        new MapTypesDatabaseHelper().readMapsList(new MapTypesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapTypesData> mapsData) {
                if ((mapsData.size())>0){
                    mLocationLayout.setVisibility(View.VISIBLE);
                }
                new MapTypesRecyclerview_Config().setConfig(mLocationsRecyclerview, DashboardActivity.this, mapsData);
            }
        }, DashboardActivity.this);

        new UsersDatabaseHelper().readUserList(new UsersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UsersData> usersData) {
                if ((usersData.size())>0){
                    mSuggestionLayout.setVisibility(View.VISIBLE);
                }
                new FollowersSuggestion_Horizontal_Recyclerview_Config().setConfig(mSuggestionRecyclerview,DashboardActivity.this, usersData);
            }
        }, DashboardActivity.this);


        new ProjectDatabaseHelper().readProjectList(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                if ((projectData.size())>0){
                    mProjectLayout.setVisibility(View.VISIBLE);
                }
                new ProjectRecyclerview_Horizontal_Config().setConfig(mProjectsRecyclerview,DashboardActivity.this,projectData);
            }
        }, DashboardActivity.this);

        new NewsDatabaseHelper().readNewsList(new NewsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<NewsData> newsUtils) {
                if ((newsUtils.size())>0){
                    mNewsLayout.setVisibility(View.VISIBLE);
                }
                new NewsRecyclerview_Horizontal_Config().setConfig(mNewRecyclerview,DashboardActivity.this,newsUtils);
            }
        },DashboardActivity.this);

        //Jobs
        new JobsDatabaseHelper().readJobList(new JobsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<JobData> jobData) {
                new JobsRecyclerview_Horizontal_Config().setConfig(mJobsRecyclerview, DashboardActivity.this,jobData);

            }
        }, DashboardActivity.this);

        mBrokersShowHideTV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mBrokersShowHideTV.setTextColor(getResources().getColor(R.color.colorRed));
                    mBrokersLayout.setVisibility(View.GONE);
                }else {
                    mBrokersShowHideTV.setTextColor(getResources().getColor(R.color.colorDark));
                    mBrokersLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mBrokersViewallTV.setOnClickListener(this);
        mPropertiesViewallTV.setOnClickListener(this);
        mSuggestionViewallTV.setOnClickListener(this);
        mProjectsViewallTV.setOnClickListener(this);
        //on menu click nav drawer open
        mMenuBtnLayoutD2.setOnClickListener(this);
        //Profile image click listener
        mProfileImageD2.setOnClickListener(this);
        //Chat Image Click listener
        mChatButtonD2.setOnClickListener(this);
        //Create Fab click
        mCreateNewFabButton.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //For Dashnoard One
    NavigationView.OnNavigationItemSelectedListener mOnDrawerNavigationClickListenerDashboardOne
            = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            Intent intent;

            switch (id){
                case R.id.nav_search:
                    intent = new Intent(DashboardActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_suggestion:
                    intent = new Intent(DashboardActivity.this,FollowersSuggestionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_drawer_notification:
                    fragment = new NotificationFragment();
                    loadFragment(fragment);
                    break;
                case R.id.nav_find_owners:
                    intent = new Intent(DashboardActivity.this, ViewAllOwnersEstateActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_find_broker:
                    intent = new Intent(DashboardActivity.this, ViewAllBrokersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_real_state:
                    intent = new Intent(DashboardActivity.this, ViewAllEstateActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_developers:
                    intent = new Intent(DashboardActivity.this, ViewAllDevelopersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_find_property:
                    intent = new Intent(DashboardActivity.this, ViewAllPropertiesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_invite_broker:
                    break;
                case R.id.nav_projects:
                    fragment = new ProjectFragment();
                    loadFragment(fragment);
                    break;
                case R.id.nav_favorite:
                    intent = new Intent(DashboardActivity.this, FavoriteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_newsandinfo:
                    fragment = new NewsAndInfoFragment();
                    loadFragment(fragment);
                    break;
                case R.id.nav_find_user:
                    intent = new Intent(DashboardActivity.this,UsersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_maps:
                    intent = new Intent(DashboardActivity.this, MapActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_setting:
                    intent = new Intent(DashboardActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.dashboard_one:
                    dashboard = "one";
                    fragment = new DashboardFragment();
                    loadFragment(fragment);
                    break;
                case R.id.dashboard_two:
                    dashboard = "two";
                    fragment = new DashboardTwoFragmentV2();
                    loadFragment(fragment);
                    break;
                case R.id.dashboard_three:
                    dashboard = "three";
                    fragment = new DashboardThreeFragment();
                    loadFragment(fragment);
                    break;
                case R.id.dashboard_four:
                    dashboard = "four";
                    fragment = new DashboardFourFragment();
                    loadFragment(fragment);
                    break;
                case R.id.dashboard_five:
                    dashboard = "five";
                    fragment = new DashboardFiveFragment();
                    loadFragment(fragment);
                    break;
                case R.id.dashboard_six:
                    dashboard = "five";
                    fragment = new DashboardSixFragment();
                    loadFragment(fragment);
                    break;
                case R.id.dashboard_style_one:
                    dashboard = "dashboardtwo";
                    dashboardContentOne.setVisibility(View.VISIBLE);
                    dashboardContentTwo.setVisibility(View.GONE);
                    break;
                case R.id.dashboard_style_two:
                    dashboard = "dashboardtwo";
                    dashboardContentOne.setVisibility(View.GONE);
                    dashboardContentTwo.setVisibility(View.VISIBLE);
                    break;
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    switch (dashboard){
                        case "one":
                            fragment = new DashboardFragment();
                            break;
                        case "two":
                            fragment = new DashboardTwoFragmentV2();
                            break;
                        case "three":
                            fragment = new DashboardThreeFragment();
                            break;
                        case "four":
                            fragment = new DashboardFourFragment();
                            break;
                        case "five":
                            fragment = new DashboardFiveFragment();
                            break;
                        case "six":
                            fragment = new DashboardSixFragment();
                            break;
                        default:
                            fragment = new DashboardFragment();
                    }
                    fragment = new DashboardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_news:
                    fragment = new NewsAndInfoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_jobs:
//                    Intent intent = new Intent(DashboardActivity.this,CreateActivity.class);
//                    startActivity(intent);
//                    finish();
                    return true;
                case R.id.nav_notification:
                    fragment = new NotificationFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_project:
                    fragment = new ProjectFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
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
            case R.id.profile_image :
                intent = new Intent(DashboardActivity.this, BrokerProfileActivity.class);
                intent.putExtra("brokerid",user.getResponse().getData().getUser().getUserId());
                startActivity(intent);
                break;
            case R.id.img_chat:
                startActivity(new Intent(DashboardActivity.this, ChatActivity.class));
                break;
            case R.id.menubtn_layout_two:
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!drawer.isDrawerOpen(Gravity.LEFT)){
                    drawer.openDrawer(Gravity.LEFT);
                } else{
                    drawer.closeDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.icon_notification:
                //Todo something
                break;
            case R.id.img_chattwo:
                startActivity(new Intent(DashboardActivity.this, ChatActivity.class));
                break;
            case R.id.profile_imagetwo:
                intent = new Intent(DashboardActivity.this, BrokerProfileActivity.class);
                intent.putExtra("brokerid",user.getResponse().getData().getUser().getUserId());
                startActivity(intent);
                break;
            case R.id.createfab:
                startActivity(new Intent(DashboardActivity.this, CreateActivity.class));
                finish();
                break;
            case R.id.tv_broker_viewall :
                startActivity(new Intent(DashboardActivity.this, ViewAllBrokersActivity.class));
                break;
            case R.id.tv_viewall_properties :
                startActivity(new Intent(DashboardActivity.this, ViewAllPropertiesActivity.class));
                break;
            case R.id.tv_viewall_suggestion :
                startActivity(new Intent(DashboardActivity.this, FollowersSuggestionActivity.class));
                break;
            case R.id.tv_viewall_location :
                startActivity(new Intent(DashboardActivity.this, MapActivity.class));
                break;
            case R.id.tv_viewall_projects :
                startActivity(new Intent(DashboardActivity.this, ViewAllProjectsActivity.class));
                break;
            case R.id.tv_viewall_news :
                break;
            case R.id.tv_viewall_jobs :
                break;
        }
    }
}
