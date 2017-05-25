package qf.com.vmove.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wujie on 2017/5/23.
 */

public class NfAdapter<D> extends BaseAdapter{
    private List<D> list;
    private Context context;
    private int typeCount;
    private Map<Integer, Integer> map;


    public NfAdapter(List<D> list, Context context) {
     this(list,context,1);
    }

    public NfAdapter(List<D> list, Context context, int typeCount) {
        this.list = list;
        this.context = context;
        this.typeCount = typeCount;
        map = new HashMap<>();

    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //得到注解的对象
        AdapterData data=list.get(position).getClass().getAnnotation(AdapterData.class);
        if (convertView == null) {
            if (data != null) {
                //通过对象得到调用里面的方法data.layoutId()   得到你先显示的在那个layout上
                convertView=LayoutInflater.from(context).inflate(data.layoutId(),parent,false);
                try {
                    Object o=data.viewHolder().newInstance();
                    //得到viewHolder里的所有控件
                    Field[] fields = data.viewHolder().getDeclaredFields();
                    //遍历得到viewholder的每个控件
                    for(Field field : fields){
                        BindingView bindingView = field.getAnnotation(BindingView.class);
                        if (bindingView!=null) {
                            field.setAccessible(true);
                            field.set(o, convertView.findViewById(bindingView.id()));
                        }
                    }
                       convertView.setTag(o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Object holder=convertView.getTag();
        D d=list.get(position);
        Field[] fields = holder.getClass().getDeclaredFields();
        try {
        for (Field field : fields) {
            BindingView binding = field.getAnnotation(BindingView.class);
                if (binding != null) {
                    field.setAccessible(true);
                    Object view = field.get(holder);
                    Field declaredField = d.getClass().getDeclaredField(binding.fieldName());
                    declaredField.setAccessible(true);
                    Object temp = declaredField.get(d);
                    if (binding.binder() != ViewBinder.class){
                        binding.binder().newInstance().onBind(view, temp);

                    }else if (temp instanceof String) {
                        if (view instanceof TextView) {
                            ((TextView)view).setText((String)temp);
                        }else if (view instanceof ImageView){
                            Picasso.with(context).load((String)temp).into((ImageView) view);
                        }
                    }
                }
            }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return convertView;
    }
//多布局需要添加的两个方法
    @Override
    public int getViewTypeCount() {
        return typeCount;
    }

    @Override
    public int getItemViewType(int position) {
        AdapterData data = list.get(position).getClass().getAnnotation(AdapterData.class);
        if (data != null) {
            int layoutId =data.layoutId();
            if (!map.containsKey(layoutId)){
                map.put(layoutId, map.size());
            }
            return map.get(layoutId);
        }

        return super.getItemViewType(position);
    }

    public void addAll(Collection<? extends D> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public interface ViewBinder<V, D>{
        void  onBind(V view, D data);
    }

}
