package com.oraro.panoramicagriculture.ui.activity;

import com.oraro.panoramicagriculture.presenter.OrderPresenter;

/**
 * Created by Administrator on 2017/5/4 0004.
 *
 * @author
 */
public class OrderActivity extends BaseActivity<OrderPresenter> {
    @Override
    public OrderPresenter createPresenter() {
        return new OrderPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {

    }
}
