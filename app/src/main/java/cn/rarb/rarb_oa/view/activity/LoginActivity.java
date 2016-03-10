package cn.rarb.rarb_oa.view.activity;

import android.support.v7.app.AppCompatActivity;

import org.thanatos.base.ui.activity.BaseHoldBackActivity;

import cn.rarb.rarb_oa.model.entities.User;
import cn.rarb.rarb_oa.presenter.activityPresenter.LoginPresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by cfl on 2016/3/4.
 */
@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseHoldBackActivity<LoginPresenter>{

    @Override
    protected String onSetTitle() {
        return null;
    }

    @Override
    protected int onBindLayout() {
        return 0;
    }

    public void onLoadSuccessful(User user){

    }

    public void onLoadFailed(String message){

    }
}
