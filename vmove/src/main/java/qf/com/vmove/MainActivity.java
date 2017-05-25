package qf.com.vmove;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import qf.com.vmove.adapter.ManAdapter;
import qf.com.vmove.fargment.NewestFragment;

public class MainActivity extends AppCompatActivity implements NewestFragment.OnFragmentInteractionListener {
    private RadioGroup rg;
    private ViewPager vp;
    private ImageView iv;
    private RadioButton but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        //初始化控件
        vp= (ViewPager) findViewById(R.id.main_vp);
        rg= (RadioGroup) findViewById(R.id.main_rg);
        but= (RadioButton) findViewById(R.id.main_rb01);
        iv= (ImageView) findViewById(R.id.main_iv);
        //把Radiobutton的宽度赋值给白色滑动条 因为只有在显示的时候Radiobutton才会有宽度值 所以要用post
       but.post(new Runnable() {
            @Override
            public void run() {
                //得到宽度并赋值
               ViewGroup.LayoutParams params=  iv.getLayoutParams();
                params.width=but.getWidth();
                iv.setLayoutParams(params);
            }
        });
        //为ViewPage添加andapter  并启动adapter
        vp.setAdapter(new ManAdapter(getSupportFragmentManager()));

        //为ViewPager设置滑动监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //正在滑动的监听
            //左右还滑动的比例
            @Override                   //滑动的次数    //当前滑动的百分比（这个页面滑动了几分之几）
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //ViewCompat.setTranslationX平移得方法（需要移动得控件，移动得距离）
                ViewCompat.setTranslationX(iv,iv.getWidth()*(position+positionOffset));
            }

            //滑动完成的监听
             //position  滑动到第几个条目
            @Override
            public void onPageSelected(int position) {
                //rg.getChildAt(position).getId()需要被选中id
                //rg.check（被选中的ID）  选中的方法
               rg.check(rg.getChildAt(position).getId());

            }
             //开始滑动时的监听
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //RadioGroup设置点击事件监听
       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.main_rb01:
                        //ViewPager被选中的条目   参数是ViewPager的下标
                        vp.setCurrentItem(0);
                        break;
                    case R.id.main_rb02:
                        vp.setCurrentItem(1);
                        break;
                }
            }
        });


  }

    @Override
    public void onFragmentInteraction(String title, boolean isUp) {
        AnimatorSet set = new AnimatorSet();
        if(isUp){
            //动画  页面向下滑动时 rasiobutton向上滚动
            set.playSequentially(ObjectAnimator.ofFloat(but, "translationY", 0, -but.getHeight()).setDuration(500),
                    ObjectAnimator.ofObject(but, Property.of(TextView.class, CharSequence.class, "text"),
                            new TypeEvaluator<CharSequence>(){

                                @Override
                                public CharSequence evaluate(float fraction, CharSequence startValue, CharSequence endValue) {
                                    return fraction==1?endValue:startValue;
                                }
                            },but.getText(),title).setDuration(0),
                    ObjectAnimator.ofFloat(but, "translationY", but.getHeight(), 0).setDuration(500)
            );
            set.start();
        }else {
            set.playSequentially(ObjectAnimator.ofFloat(but, "translationY", 0, but.getHeight()).setDuration(500),
                    ObjectAnimator.ofObject(but, Property.of(TextView.class, CharSequence.class, "text"),
                            new TypeEvaluator<CharSequence>(){

                                @Override
                                public CharSequence evaluate(float fraction, CharSequence startValue, CharSequence endValue) {
                                    return fraction==1?endValue:startValue;
                                }
                            },but.getText(),title).setDuration(0),
                    ObjectAnimator.ofFloat(but, "translationY", -but.getHeight(), 0).setDuration(500)
            );
            set.start();
        }


    }
}
