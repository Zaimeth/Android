package com.example.fauzi.infofilm;

/**
 * Created by Fauzi on 12/1/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Aiz_id on 30/11/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tabmenu1 tabmenu1 = new tabmenu1();
                return tabmenu1;
            case 1:
                tabmenu2 tabmenu2 = new tabmenu2();
                return tabmenu2;
            case 2:
                tabmenu3 tabmenu3 = new tabmenu3();
                return tabmenu3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Popular";
            case 1:
                return "New Coming";
            case 2:
                return "UpComing";
        }
        return null;
    }
}
