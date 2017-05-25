package qf.com.vmove.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wujie on 2017/5/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindingView {
    int id();
    String fieldName();
    Class<? extends NfAdapter.ViewBinder> binder() default NfAdapter.ViewBinder.class;
}
