package qf.com.vmove.beans;

import qf.com.vmove.R;
import qf.com.vmove.adapter.DateHolder;
import qf.com.vmove.utils.AdapterData;

/**
 * Created by wujie on 2017/5/24.
 */
@AdapterData(layoutId = R.layout.item_date, viewHolder = DateHolder.class)
public class PublishData {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
