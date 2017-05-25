package qf.com.vmove.fargment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import qf.com.vmove.R;
import qf.com.vmove.beans.MovieList;
import qf.com.vmove.beans.PublishData;
import qf.com.vmove.utils.NetworkUtil;
import qf.com.vmove.utils.NfAdapter;


public class NewestFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private boolean isLoading = true;
    private NfAdapter adapter;
    private ListView lv;
    private int page=1;
    private String string="http://app.vmoiver.com/apiv3/post/getPostByTab?";
    private String string2="http://app.vmoiver.com/apiv3/post/getPostByTab?p=";
    private SwipeRefreshLayout swipe;
    private String data;
    private SimpleDateFormat format =new SimpleDateFormat ("MMM.dd",Locale.ENGLISH);


    public NewestFragment(){
        data=format.format(new Date());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LayoutInflater inflater1=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_newest,container,false);
        lv= (ListView) view.findViewById(R.id.nf_lv);
        adapter = new NfAdapter(new ArrayList<>(), getActivity(),2);
        lv.setAdapter(adapter);
        indata(string);
        swipe = (SwipeRefreshLayout) view;
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                page=1;
                String string3=string2+page;
                indata(string3);
                swipe.setRefreshing(false);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                View v = view.getChildAt(0);
                if (v !=null){
                    String time = (String) v.getContentDescription();
                    if (!time.equals(data)){
                        try {
                            mListener.onFragmentInteraction(time.equals(format.format(new Date())) ?"最新" : time
                                    ,format.parse(time).compareTo(format.parse(data))<0);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        data=time;
                    }

                }
                if (isLoading && firstVisibleItem+visibleItemCount>totalItemCount-2) {
                    page++;
                    String phat=string2+page;
                    indata(phat);
                }
            }
        });

        return view;
    }

        public  void indata(String string){
            new NetworkUtil<MovieList>(MovieList.class, new NetworkUtil.CallBack<MovieList>() {
                @Override
                public void onSucceed(MovieList movieList) {
                    List<Object> list = new ArrayList<>();
                    String data=null;
                    if (adapter.getCount() > 0){
                        MovieList.MovieBean item = (MovieList.MovieBean) adapter.getItem(adapter.getCount() - 1);
                        data = format.format(new Date(Long.parseLong(item.getPublish_time()) * 1000));
                    }else {
                        data = format.format(new Date());
                    }

                    for (MovieList.MovieBean bean : movieList.getData()) {
                        String temp =format.format(new Date(Long.parseLong(bean.getPublish_time()) * 1000));
                        if (!data.equals(temp)) {
                            PublishData publishData = new PublishData();
                            publishData.setDate(temp);
                            list.add(publishData);
                            data = temp;
                        }
                        list.add(bean);
                    }
                    adapter.addAll(list);
                    isLoading = false;
                }
                    @Override
                    public void onError(Exception e) {
                    }
                }).execute(string);

}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title, boolean isUp);
    }
}
