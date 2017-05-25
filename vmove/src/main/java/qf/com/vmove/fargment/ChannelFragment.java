package qf.com.vmove.fargment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import qf.com.vmove.R;
import qf.com.vmove.activity.ChannceDetailsActivity;
import qf.com.vmove.beans.ChanneGried;
import qf.com.vmove.utils.NetworkUtil;
import qf.com.vmove.utils.NfAdapter;


public class ChannelFragment extends Fragment {
    private GridView gv;
    private NfAdapter adapter;
    private List<Object> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater1=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_channel,container,false);
        gv= (GridView) view.findViewById(R.id.cf_gv);
        adapter = new NfAdapter(new ArrayList<>(), getActivity());
        gv.setAdapter(adapter);
        into();



            gv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //得到intent对象
                    Intent intent = new Intent(getActivity(), ChannceDetailsActivity.class);
                    //adapter.getItem(position)得到点击条目的数据对象  需要强转
                    ChanneGried.ChanneBain item = (ChanneGried.ChanneBain) adapter.getItem(position);
                    //通过对象得到是是体类的属性
                    int  cateid = Integer.parseInt(item.getCateid());
                    String catename = item.getCatename();
                    //Intent传值
                    intent.putExtra("cateid",cateid);
                    intent.putExtra("catename", catename);
                    //启动Intent
                    startActivity(intent);
                }
            });
        return view;
    }

    private void into(){
        new NetworkUtil<ChanneGried>(ChanneGried.class, new NetworkUtil.CallBack<ChanneGried>() {
            @Override
            public void onSucceed(ChanneGried channeGried) {
                list =new ArrayList<>();
                //循环的实体类数据  把里面的内部类添加到list集合中
                for (ChanneGried.ChanneBain channeBain : channeGried.getData()) {
                    list.add(channeBain);
                }
                //把list集合添加到adapter中 这是adapter数据源
                adapter.addAll(list);
            }

            @Override
            public void onError(Exception e) {

            }
        }).execute("http://app.vmoiver.com/apiv3/cate/getList");
    }
}
