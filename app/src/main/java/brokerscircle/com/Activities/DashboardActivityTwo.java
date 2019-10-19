package brokerscircle.com.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import brokerscircle.com.Activities.Brokers.SearchBrokerActivity;
import brokerscircle.com.Activities.Developers.SearchDeveloperActivity;
import brokerscircle.com.Activities.Estates.SearchEstateActivity;
import brokerscircle.com.Activities.Estates.ViewAllOwnersEstateActivity;
import brokerscircle.com.Activities.JobBroard.Search_Job_Activity;
import brokerscircle.com.Activities.Properties.PropertySearchActivity;
import brokerscircle.com.R;
import brokerscircle.com.fragments.JobsFragment;
import brokerscircle.com.fragments.NewsAndInfoFragment;
import brokerscircle.com.fragments.NotificationFragment;
import brokerscircle.com.fragments.ProjectFragment;
import brokerscircle.com.fragments.DashboardTwoFragment;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivityTwo extends AppCompatActivity {

    private Helper helper;
    private LoginUser user = null;

    private Toolbar toolbar;
    private ActionBar actionBar;

    private BottomNavigationView navigation;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        user = helper.getLoggedInUser();
        if (user == null){
            startActivity(new Intent(DashboardActivityTwo.this, LoginActivity.class));
            finish();
        }else {
            setContentView(R.layout.dashboard_two_activity);

            bottomNavigationInit();
            navigationDrawerInit();
        }
    }

    private void bottomNavigationInit() {

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // load the store fragment by default
        loadFragment(new DashboardTwoFragment());
    }

    private void navigationDrawerInit() {

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

        navigationView.inflateMenu(R.menu.drawer_menu);
        navigationView.setNavigationItemSelectedListener(mOnDrawerNavigationClickListenerDashboardOne);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
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
                case R.id.nav_search_job:
                    intent = new Intent(DashboardActivityTwo.this, Search_Job_Activity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_find_broker:
                    intent = new Intent(DashboardActivityTwo.this, SearchBrokerActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_real_state:
                    intent = new Intent(DashboardActivityTwo.this, SearchEstateActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_developers:
                    intent = new Intent(DashboardActivityTwo.this, SearchDeveloperActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_find_property:
                    intent = new Intent(DashboardActivityTwo.this, PropertySearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_suggestion:
                    intent = new Intent(DashboardActivityTwo.this,FollowersSuggestionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_find_owners:
                    intent = new Intent(DashboardActivityTwo.this, ViewAllOwnersEstateActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_favorite:
                    intent = new Intent(DashboardActivityTwo.this, FavoriteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_find_user:
                    intent = new Intent(DashboardActivityTwo.this,UsersActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_maps:
                    intent = new Intent(DashboardActivityTwo.this, MapActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_setting:
                    intent = new Intent(DashboardActivityTwo.this, SettingActivity.class);
                    startActivity(intent);
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
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new DashboardTwoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_news:
                    fragment = new NewsAndInfoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_jobs:
                    fragment = new JobsFragment();
                    loadFragment(fragment);
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
        transaction.commit();
    }
}
