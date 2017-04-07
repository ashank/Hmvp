package com.funhotel.mvp.common;

/**
 * Created by zhiyahan on 2017/3/24.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
