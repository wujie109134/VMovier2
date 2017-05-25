package qf.com.vmove.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import qf.com.vmove.R;
import qf.com.vmove.beans.MovieList;
import qf.com.vmove.utils.BindingView;
import qf.com.vmove.utils.NfAdapter;

/**
 * Created by wujie on 2017/5/24.
 */

public class MovierHolder {
    @BindingView(id= R.id.nf_image, fieldName = "image")
    public ImageView image;
    @BindingView(id = R.id.nf_duration, fieldName = "duration" ,binder = DurationBinder.class)
    public TextView duration;
    @BindingView(id = R.id.nf_title, fieldName = "title")
    public TextView title;
    @BindingView(id = R.id.nf_catename, fieldName = "cates", binder = CateBinder.class)
    private TextView cates;

    @BindingView(id = R.id.nf_itim, fieldName = "publish_time",binder = ItemBinder.class)
    private View item;

    public static class DurationBinder implements NfAdapter.ViewBinder<TextView, String> {
        @Override
        public void onBind(TextView view, String data) {
            long l = Long.parseLong(data);
            view.setText(String.format("%02d'%02d\"", l / 60, l % 60));
        }
    }
    public static class CateBinder implements NfAdapter.ViewBinder<TextView, List<MovieList.MovieBean.CatesBean>> {

        @Override
        public void onBind(TextView view, List<MovieList.MovieBean.CatesBean> data) {
            view.setText(data.get(0).getCatename());
        }
    }
    public static class ItemBinder implements NfAdapter.ViewBinder<View, String> {

        @Override
        public void onBind(View view, String data) {
            SimpleDateFormat format = new SimpleDateFormat("MMM.dd", Locale.ENGLISH);
            view.setContentDescription(format.format(new Date(Long.parseLong(data) * 1000)));
        }
    }

}
