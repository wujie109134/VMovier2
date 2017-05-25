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

        vp= (ViewPager) findViewById(R.id.main_vp);

        rg= (RadioGroup) findViewById(R.id.main_rg);
        but= (RadioButton) findViewById(R.id.main_rb01);
        iv= (ImageView) findViewById(R.id.main_iv);
       but.post(new Runnable() {
            @Override
            public void run() {
               ViewGroup.LayoutParams params=  iv.getLayoutParams();
                params.width=but.getWidth();
                iv.setLayoutParams(params);
            }
        });

        vp.setAdapter(new ManAdapter(getSupportFragmentManager()));






        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ViewCompat.setTranslationX(iv,iv.getWidth()*(position+positionOffset));

            }

            @Override
            public void onPageSelected(int position) {
             rg.check(rg.getChildAt(position).getId());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.main_rb01:
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
        set.playSequentially(ObjectAnimator.ofFloat(but, "translationY", 0, but.getHeight()).setDuration(5000),
                ObjectAnimator.ofObject(but, Property.of(TextView.class, CharSequence.class, "text"),
                        new TypeEvaluator<CharSequence>(){

                            @Override
                            public CharSequence evaluate(float fraction, CharSequence startValue, CharSequence endValue) {
                                return fraction==1?endValue:startValue;
                            }
                        },but.getText(),title).setDuration(0),
                ObjectAnimator.ofFloat(but, "translationY", -but.getHeight(), 0).setDuration(5000)
        );
        set.start();

    }
}
