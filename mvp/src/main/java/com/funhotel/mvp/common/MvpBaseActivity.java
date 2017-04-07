package com.funhotel.mvp.common;


import android.os.Bundle;
import android.support.annotation.LayoutRes;

import butterknife.ButterKnife;

public abstract class MvpBaseActivity<V extends BaseView, P extends BasePresenter>  extends BaseActivity implements BaseView {

    private V view;
    private P presenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        if (presenter == null) {
            presenter = getPresenter();
        }
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    protected abstract void initView();

    @LayoutRes
    protected abstract int getLayout();


    protected  abstract P getPresenter();



}
