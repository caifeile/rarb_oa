package cn.rarb.rarb_oa.app;

import android.content.SharedPreferences;
import android.util.Log;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.thanatos.base.model.SharePreferenceManager;
import org.thanatos.base.model.SharePreferenceManager.LocalUser;
import java.util.Locale;
import cn.rarb.rarb_oa.model.entities.RespUser;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.SimpleXmlConverterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by thanatos on 15/12/22.
 */
public class ServerAPI {

    private static OSChinaAPI osChinaAPI;
    private static String cookies;

    public static OSChinaAPI getOSChinaAPI() {
        if (osChinaAPI == null) {
            OkHttpClient httpClient = new OkHttpClient();

            httpClient.interceptors().add(chain -> {
                if (chain.request().url().getPath().equals("/action/api/login_validate")){
                    Response response = chain.proceed(chain.request());
                    String cookies = response.header("Set-Cookie");
                    Log.d("thanatos", "Cookie is " + cookies);
                    SharedPreferences.Editor editor = SharePreferenceManager
                            .getLocalUser(MyAppliction.context).edit();
                    editor.putString(LocalUser.KEY_COOKIES, cookies);
                    editor.apply();
                    clearCookies();
                    return response;
                }else{
                    return chain.proceed(chain.request());
                }
            });

            httpClient.interceptors().add(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept-Language", Locale.getDefault().toString())
                        .header("Host", "www.oschina.net")
                        .header("Connection", "Keep-Alive")
                        .header("Cookie", cookies == null ? getCookies() : cookies)
                        .header("User-Agent", getUserAgent())
                        .build();
                Log.d("thanatos", "The Cookie is " + request.header("Cookie"));
                Log.d("thanatos", "访问网络地址: " + request.urlString());
                return chain.proceed(request);
            });

            osChinaAPI = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create(new Persister(new AnnotationStrategy())))
                    .baseUrl("http://www.oschina.net/")
                    .client(httpClient)
                    .build()
                    .create(OSChinaAPI.class);
        }
        return osChinaAPI;
    }

    private static String getCookies(){
        if (cookies == null) {
            SharedPreferences preferences = SharePreferenceManager.getLocalUser(MyAppliction.context);
            return cookies = preferences.getString(LocalUser.KEY_COOKIES, "");
        }
        return cookies;
    }

    public static void clearCookies(){
        cookies = null;
    }

    public static String getUserAgent() {
        return new StringBuilder("OSChina.NET")
                .append('/' + MyAppliction.getPackageInfo().versionName + '_' + MyAppliction.getPackageInfo().versionCode)// app版本信息
                .append("/Android")// 手机系统平台
                .append("/" + android.os.Build.VERSION.RELEASE)// 手机系统版本
                .append("/" + android.os.Build.MODEL) // 手机型号
                .append("/" + "WhenYouSawIt,Well!Bingo!!") // 客户端唯一标识
                .toString();
    }


    public interface OSChinaAPI {
        // ------------ 用户api -------------

        /**
         * 登陆
         *
         * @param username  username
         * @param password  password, whether it encoded by md5 or not
         * @param keepLogin keep login, does it mean the session remember user login?
         * @return
         */
        @POST("/action/api/login_validate")
        Observable<RespUser> login(
                @Query("username") String username,
                @Query("pwd") String password,
                @Query("keep_login") int keepLogin
        );
//
//        /**
//         * 得到用户的信息,这个api回连同用户的动态也一起得到
//         * @param uid user id
//         * @param hid his id
//         * @param hname his name
//         * @param pageIndex page index
//         * @param pageSize page size
//         * @return
//         */
//        @GET("/action/api/user_information")
//        Observable<RespUserInfo> getUserInfo(
//                @Query("uid") long uid,
//                @Query("hisuid") long hid,
//                @Query("hisname") String hname,
//                @Query("pageIndex") int pageIndex,
//                @Query("pageSize") int pageSize
//        );

    }


}
