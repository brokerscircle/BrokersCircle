package brokerscirlce.com.fragments.TabsFragments.Dashboard.DashboardThreeTabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class D3DashboardTabsAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public D3DashboardTabsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new D3BuyTabFragment();
            case 1:
                return new D3RentTabFragment();
            case 2:
                return new D3WantedTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
