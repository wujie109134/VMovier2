package qf.com.vmove;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import qf.com.vmove.adapter.PagAdapter;

public class PagerActivity extends AppCompatActivity{
    private View iv01,iv02,iv03;
    private ViewPager vp;
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        vp= (ViewPager) findViewById(R.id.pager_vp);

        vp.setAdapter(new PagAdapter(PagerActivity.this));
        rg= (RadioGroup) findViewById(R.id.pager_rg);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              rg.check(rg.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });








    }

}
