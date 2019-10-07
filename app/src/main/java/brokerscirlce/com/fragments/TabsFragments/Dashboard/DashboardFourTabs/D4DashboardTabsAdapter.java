package brokerscirlce.com.fragments.TabsFragments.Dashboard.DashboardFourTabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class D4DashboardTabsAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public D4DashboardTabsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new D4BuyTabFragment();
            case 1:
                return new D4RentTabFragment();
            case 2:
                return new D4WantedTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}