package qf.com.vmove.adapter;

import android.widget.TextView;

import qf.com.vmove.R;
import qf.com.vmove.utils.BindingView;
import qf.com.vmove.utils.NfAdapter;

/**
 * Created by wujie on 2017/5/24.
 */

public class DateHolder {
    @BindingView(id = R.id.date_text, fieldName = "data", binder = ItemBinder.class)
    public TextView text;
    public static class ItemBinder implements NfAdapter.ViewBinder<TextView, String>{

        @Override
        public void onBind(TextView view, String data) {
            view.setText("-"+data+"-");
            view.setContentDescription(data);

        }
    }
}
