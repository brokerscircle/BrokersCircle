package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoriteBrokerFragment;
import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoriteDeveloperFragment;
import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoriteEstateFragment;
import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoriteJobsFragment;
import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoriteNewFragment;
import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoriteProjectFragment;
import brokerscirlce.com.fragments.TabsFragments.FavoriteTabs.FavoritePropertyFragment;
import brokerscirlce.com.R;
import brokerscirlce.com.model.login_user.LoginUser;
import brokerscirlce.com.util.Helper;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper;
    private LoginUser user = null;

    //Topbar
    private LinearLayout mBackButton;
    private RoundedImageView userProfileIMG;
    private TextView mTitleTextView;
    private ImageView mTitleImage;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FavoriteTabsAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        user = helper.getLoggedInUser();
        if (user == null){
            startActivity(new Intent(FavoriteActivity.this, LoginActivity.class));
            finish();
        }else {
            setContentView(R.layout.favorite_activity);

            initAppBar();

            viewPager = findViewById(R.id.viewpager);
            tabLayout = findViewById(R.id.tablayout);

            //Tabs Adapter
            pageAdapter = new FavoriteTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(pageAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        }
    }

    private void initAppBar() {
        //Geting IDs
        mBackButton = findViewById(R.id.btn_back);
        mBackButton = findViewById(R.id.btn_back);
        userProfileIMG = findViewById(R.id.profile_image);
        mTitleImage = findViewById(R.id.titleimage);
        mTitleTextView = findViewById(R.id.tv_title);

        //User Profile Image
        if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
            userProfileIMG.setImageResource(R.drawable.ic_user_icon_white);
        }else {
            Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_white).into(userProfileIMG);
        }

        //back listener
        mBackButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    private class FavoriteTabsAdapter  extends FragmentPagerAdapter {
        private int numOfTabs;
        public FavoriteTabsAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoriteBrokerFragment();
                case 1:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoriteEstateFragment();
                case 2:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoriteDeveloperFragment();
                case 3:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoritePropertyFragment();
                case 4:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoriteProjectFragment();
                case 5:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoriteNewFragment();
                case 6:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return new FavoriteJobsFragment();
                default:
                    mTitleImage.setImageResource(R.drawable.ic_favorite_icon);
                    mTitleTextView.setText("Favorites");
                    return null;
            }
        }
        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}
