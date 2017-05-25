package qf.com.vmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class GreetActivity extends AppCompatActivity implements ViewPropertyAnimatorListener {
    private ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greet);
        view = (ImageView) findViewById(R.id.greet_iv_01);

        ViewCompat.setPivotX(view,view.getWidth()/2);
        ViewCompat.setPivotY(view,view.getHeight()/2);
        ViewCompat.animate(view)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(2000)
                .setListener(GreetActivity.this)
                .start();




    }

    @Override
    public void onAnimationStart(View view) {

    }

    @Override
    public void onAnimationEnd(View view) {
        SharedPreferences sp=getSharedPreferences("dada",MODE_PRIVATE);
        boolean falg=sp.getBoolean("falg",true);
        if (falg){
          sp.edit().putBoolean("falg",false).commit();
            Intent intent=new Intent(GreetActivity.this,PagerActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent=new Intent(GreetActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onAnimationCancel(View view) {

    }
}
