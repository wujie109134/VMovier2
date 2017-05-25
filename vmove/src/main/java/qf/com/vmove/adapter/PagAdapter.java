package qf.com.vmove.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import qf.com.vmove.MainActivity;
import qf.com.vmove.R;

/**
 * Created by wujie on 2017/5/22.
 */

public class PagAdapter extends PagerAdapter implements View.OnClickListener{
    private Context context;
    private List<View> list=new ArrayList<>();

    public PagAdapter(Context context) {
        this.context = context;
        LayoutInflater inflater=LayoutInflater.from(context);
        list.add(inflater.inflate(R.layout.pager_iv01,null));
        list.add(inflater.inflate(R.layout.pager_iv02,null));
        View view=(inflater.inflate(R.layout.pager_iv03,null));
        view.setOnClickListener(this);
        list.add(view);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(list.get(position));
    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity)context).finish();
    }
}
