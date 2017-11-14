package com.example.administrator.mycampus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.mycampus.Fragmet.FoundFragment;
import com.example.administrator.mycampus.Fragmet.HelpFragment;
import com.example.administrator.mycampus.Fragmet.LostFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
        private final String[] title={"Lost","Found","Help"};
        private List<Fragment> fragments=new ArrayList<Fragment>();
        private Fragment mCurrentFragment;
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new LostFragment());
            fragments.add(new FoundFragment());
            fragments.add(new HelpFragment());
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment=(Fragment)object;
        super.setPrimaryItem(container, position, object);
    }
    public Fragment getCrurrentFragemt(){
        return mCurrentFragment;
    }
}

