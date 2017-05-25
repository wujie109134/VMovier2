package qf.com.vmove.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import qf.com.vmove.fargment.ChannelFragment;
import qf.com.vmove.fargment.NewestFragment;

/**
 * Created by wujie on 2017/5/22.
 */

public class ManAdapter extends FragmentPagerAdapter{

    private List<Fragment> list=new ArrayList<>();


    public ManAdapter(FragmentManager fm) {
        super(fm);
        list.add(new NewestFragment());
        list.add(new ChannelFragment());

    }


    @Override
    public int getCount() {


        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
}
