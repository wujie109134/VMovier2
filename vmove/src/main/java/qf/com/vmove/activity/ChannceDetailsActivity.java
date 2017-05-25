package qf.com.vmove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qf.com.vmove.R;
import qf.com.vmove.beans.MovieList;
import qf.com.vmove.utils.NetworkUtil;
import qf.com.vmove.utils.NfAdapter;

public class ChannceDetailsActivity extends AppCompatActivity implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {
    private ListView lv;
    private boolean flag = true;
    private List<Object> list;
    private NfAdapter adapter;
    private TextView tv;
    private int p=1;
    private String string;
    private int cateid;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channce_details);

        Intent intent = getIntent();
        String catename = intent.getStringExtra("catename");
        cateid = intent.getIntExtra("cateid", 0);
         string="http://app.vmoiver.com/apiv3/post/getPostInCate?cateid="+cateid+"?p="+p;
        srl= (SwipeRefreshLayout) findViewById(R.id.cf_a_srl);
        tv= (TextView) findViewById(R.id.cf_a_tv);
        tv.setText(catename);
        lv= (ListView) findViewById(R.id.cf_a_lv);
        adapter=new NfAdapter(new ArrayList<>(),ChannceDetailsActivity.this);
        lv.setAdapter(adapter);
        see(string);

        lv.setOnScrollListener(this);
        srl.setOnRefreshListener(this);




    }
    //包装的开启异步任务下载数据并把数据添加到andpter上
    public void see(String s){
        new NetworkUtil<MovieList>(MovieList.class, new NetworkUtil.CallBack<MovieList>() {
            @Override
            public void onSucceed(MovieList movieList) {
                list = new ArrayList<>();
                for (MovieList.MovieBean bean : movieList.getData()) {
                    list.add(bean);
                }
                adapter.addAll(list);
                flag = false;


            }

            @Override
            public void onError(Exception e) {

            }
        }).execute(s);
    }
    public  void but(View v){
        finish();
    }
   //lv的下拉加载的2个监听方法
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (flag && firstVisibleItem + visibleItemCount > totalItemCount-2) {
            p++;
            string="http://app.vmoiver.com/apiv3/post/getPostInCate?cateid="+cateid+"?p="+p;
            see(string);
        }

    }
    //srl的上拉刷新的监听方法
    @Override
    public void onRefresh() {
         //先清空数据源
            adapter.clear();
        //重新加载第一页数据
            p = 1;
            string="http://app.vmoiver.com/apiv3/post/getPostInCate?cateid="+cateid+"?p="+p;
            see(string);
        //刷新后关闭进度条（就是通知别人我刷新好了）
            srl.setRefreshing(false);
        }
}
