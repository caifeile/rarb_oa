package cn.rarb.rarb_oa.presenter.activityPresenter;

import android.os.Bundle;

import cn.rarb.rarb_oa.app.ServerAPI;
import cn.rarb.rarb_oa.model.entities.RespUser;
import cn.rarb.rarb_oa.view.activity.LoginActivity;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by thanatos on 16/2/23.
 */
public class LoginPresenter extends RxPresenter<LoginActivity> {

    private static final int REQUEST_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        this.<String, String, Object, Object>restartable(REQUEST_LOGIN,
                (username, password, o3, o4) -> {
                    return ServerAPI.getOSChinaAPI().login(username, password, 1)
                            .subscribeOn(Schedulers.io())
                            .compose(this.<RespUser>deliverLatestCache())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(split(
                                    (view, data)->{
                                        if (data.getResult().getErrorCode() == 1){
                                            view.onLoadSuccessful(data.getUser());
                                        }else {
                                            view.onLoadFailed(data.getResult().getErrorMessage());
                                        }
                                    },
                                    (view, error)->{
                                        error.printStackTrace();
                                        view.onLoadFailed(null);
                                    }));
                });
    }

    public void login(String username, String password) {
        start(REQUEST_LOGIN, username, password, null, null);
    }
}
