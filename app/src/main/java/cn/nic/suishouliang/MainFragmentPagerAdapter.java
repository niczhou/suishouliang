package cn.nic.suishouliang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by nic on 2018/3/6.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private List<Fragment> list;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager,List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fm=fragmentManager;
        this.list=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
