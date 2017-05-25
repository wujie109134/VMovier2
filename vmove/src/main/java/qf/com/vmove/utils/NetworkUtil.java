package qf.com.vmove.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wujie on 2017/5/23.
 */

public class NetworkUtil<T> extends AsyncTask<String,Void,Object>{
    private Class<T> type;
    private CallBack<T> callBack;

    public NetworkUtil(Class<T> type, CallBack<T> callBack) {
        this.type = type;
        this.callBack = callBack;
    }

    @Override
    protected Object doInBackground(String... params) {
        try {
            HttpURLConnection connection= (HttpURLConnection) new URL(params[0]).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode()==200){
                InputStream is=connection.getInputStream();
                byte[] b=new byte[10 << 10];
                int len=0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len=is.read(b))!=-1){
                    bos.write(b,0,len);
                }
                return new Gson().fromJson(bos.toString(),type);

            }

        }catch (Exception e){
            return e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof Exception){
              callBack.onError((Exception) o);
        }else {
              callBack.onSucceed((T) o);
        }

    }

    public interface CallBack<R>{
        void onSucceed(R r);
        void onError(Exception e);

    }
}
