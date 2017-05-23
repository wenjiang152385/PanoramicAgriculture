package com.oraro.panoramicagriculture.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.view.dialog.CustomProgressDialog;

/**
 * *************************************************************
 * *
 * .=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-.       *
 * |                     ______                     |      *
 * |                  .-"      "-.                  |      *
 * |                 /            \                 |      *
 * |     _          |              |          _     |      *
 * |    ( \         |,  .-.  .-.  ,|         / )    |      *
 * |     > "=._     | )(__/  \__)( |     _.=" <     |      *
 * |    (_/"=._"=._ |/     /\     \| _.="_.="\_)    |      *
 * |           "=._"(_     ^^     _)"_.="           |      *
 * |               "=\__|IIIIII|__/="               |      *
 * |              _.="| \IIIIII/ |"=._              |      *
 * |    _     _.="_.="\          /"=._"=._     _    |      *
 * |   ( \_.="_.="     `--------`     "=._"=._/ )   |      *
 * |    > _.="                            "=._ <    |      *
 * |   (_/                                    \_)   |      *
 * |                                                |      *
 * '-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-='      *
 * *
 * LASCIATE OGNI SPERANZA, VOI CH'ENTRATE           *
 * *************************************************************
 */
public abstract class BaseFragment<P extends Presenter> extends Fragment {

    private P mPresenter;
    private CustomProgressDialog mCustomProgressDialog;

    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";

    public abstract P createPresenter();

    public abstract BaseFragment getUi();

    public abstract void initView();

    protected BaseFragment() {
        mPresenter = createPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
        initView();
    }

    @Override
    public void onDestroyView() {
        dismissProgress();
        super.onDestroyView();
        mPresenter.onUiDestroy(getUi());
    }
    public void showProgress() {
        showProgress(null);
    }
    public void showProgress(String paramString) {
        if (paramString == null) {
            paramString = getResources().getString(R.string.loading);
        }
        if (mCustomProgressDialog == null) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }
        mCustomProgressDialog.showProgess(paramString);
        return;
    }
    public void dismissProgress() {
        if (this.mCustomProgressDialog == null) {
            return;
        }
        if (mCustomProgressDialog.isShowing()) {
            this.mCustomProgressDialog.dismiss();
        }
        this.mCustomProgressDialog = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }


    public P getPresenter() {
        return mPresenter;
    }

}
