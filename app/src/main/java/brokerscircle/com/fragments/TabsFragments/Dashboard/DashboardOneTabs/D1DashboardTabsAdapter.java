package brokerscircle.com.fragments.TabsFragments.Dashboard.DashboardOneTabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class D1DashboardTabsAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public D1DashboardTabsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new D1BuyTapFragment();
            case 1:
                return new D1RentTabFragment();
            case 2:
                return new D1WantedTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
