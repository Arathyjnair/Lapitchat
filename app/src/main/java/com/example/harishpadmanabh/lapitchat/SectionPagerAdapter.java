package com.example.harishpadmanabh.lapitchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ashni-PC on 2/26/2019.
 */

class SectionPagerAdapter extends FragmentPagerAdapter{
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                RequestsFragment nr=new RequestsFragment();
                    return nr;
            case 1:
                ChatsFragment cf=new ChatsFragment();
                return cf;
            case 2:
                FriendsFragment ff=new FriendsFragment();
                return ff;
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "REQUESTS";
            case 1:
                return "CHATS";
            case 2:
                return "FRIENDS";
                default:
                    return null;
        }
    }
}
