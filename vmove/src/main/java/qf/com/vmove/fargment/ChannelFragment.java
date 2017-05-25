package qf.com.vmove.fargment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import qf.com.vmove.R;
import qf.com.vmove.beans.ChanneGried;
import qf.com.vmove.utils.NetworkUtil;
import qf.com.vmove.utils.NfAdapter;


public class ChannelFragment extends Fragment {
    private GridView gv;
    private NfAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater1=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_channel,container,false);
        gv= (GridView) view.findViewById(R.id.cf_gv);
        adapter = new NfAdapter(new ArrayList<>(), getActivity());
        gv.setAdapter(adapter);
        into();
//        new NetworkUtil<MovieList>(MovieList.class, new NetworkUtil.CallBack<MovieList>() {
//            @Override
//            public void onSucceed(MovieList movieList) {
//                List<Object> list = new ArrayList<>();
//                String data=null;
//                if (adapter.getCount() > 0){
//                    MovieList.MovieBean item = (MovieList.MovieBean) adapter.getItem(adapter.getCount() - 1);
//                    data = format.format(new Date(Long.parseLong(item.getPublish_time()) * 1000));
//                }else {
//                    data = format.format(new Date());
//                }
//
//                for (MovieList.MovieBean bean : movieList.getData()) {
//                    String temp =format.format(new Date(Long.parseLong(bean.getPublish_time()) * 1000));
//                    if (!data.equals(temp)) {
//                        PublishData publishData = new PublishData();
//                        publishData.setDate(temp);
//                        list.add(publishData);
//                        data = temp;
//                    }
//                    list.add(bean);
//                }
//                adapter.addAll(list);
//            }
//            @Override
//            public void onError(Exception e) {
//
//            }
//        }).execute(string);
        return view;
    }

    private void into(){
        new NetworkUtil<ChanneGried>(ChanneGried.class, new NetworkUtil.CallBack<ChanneGried>() {
            @Override
            public void onSucceed(ChanneGried channeGried) {
                List<Object> list =new ArrayList<>();

                for (ChanneGried.ChanneBain channeBain : channeGried.getData()) {
                    list.add(channeBain);

                }
                adapter.addAll(list);
            }

            @Override
            public void onError(Exception e) {

            }
        }).execute("http://app.vmoiver.com/apiv3/cate/getList");
    }
}
