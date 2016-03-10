package cn.rarb.rarb_oa.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.squareup.leakcanary.LeakCanary;

import cn.rarb.rarb_oa.model.entities.User;

/**
 * Created by cfl on 2016/2/29.
 */
public class MyAppliction extends Application {
    public static Context context;
    public static Resources resources;
    public static SharedPreferences preferences;
    public static User LOCAL_LOGINED_USER;
    public static MyAppliction instance;

    public static MyAppliction getInstance() {return instance;}

    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        resources = getResources();
        preferences = getSharedPreferences(context.getPackageName(), 0);
        LeakCanary.install(this);
    }

    public static PackageInfo getPackageInfo(){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isLogined() {return LOCAL_LOGINED_USER != null;}

}
