package qf.com.vmove.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import qf.com.vmove.R;
import qf.com.vmove.utils.BindingView;

/**
 * Created by wujie on 2017/5/25.
 */

public class CfHolder {
    @BindingView(id = R.id.cf_icon, fieldName = "icon")
    private ImageView icon;
    @BindingView(id = R.id.cf_catename, fieldName = "catename")
    private TextView catename;

}
