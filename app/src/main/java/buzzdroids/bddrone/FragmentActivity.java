package buzzdroids.bddrone;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import buzzdroids.bddrone.fragments.DroneFollowFragment;
import buzzdroids.bddrone.fragments.BeaconListFragment;

/**
 * Created by Adam Piech on 2017-06-01.
 */

public class FragmentActivity extends FragmentStatePagerAdapter {

    private Configuration conf;
    private String fragmentTitle[] = {"Follow Drone", "BeaconList"};
    private int numberOfFragments = 2;

    public FragmentActivity(FragmentManager fragmentManager, Configuration conf) {
        super(fragmentManager);
        this.conf = conf;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DroneFollowFragment();
            case 1:
                return new BeaconListFragment();
        }
        return null;
    }

    @Override
    public float getPageWidth(int position) {
        float nbPages;
        if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            nbPages = 1;
        } else {
            nbPages = 1;
        }
        return (1 / nbPages);
    }

    @Override
    public int getCount() {
        return numberOfFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle[position];
    }

}
