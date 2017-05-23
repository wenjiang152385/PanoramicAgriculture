package com.oraro.panoramicagriculture.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.view.dialog.CustomProgressDialog;


/**
 * Created by Administrator on 2017/3/14 0014.
 */
public abstract class DetailFragment<P extends Presenter> extends Fragment implements View.OnTouchListener {

    private P mPresenter;
    private CustomProgressDialog mCustomProgressDialog;

    private GestureDetector mGestureDetector;

    protected Typeface mTfLight;

    protected Typeface mTfRegular;

    public abstract int getLayoutId();

    public abstract void initViews(View v);

    public abstract P createPresenter();

    public abstract DetailFragment getUi();

    public OnFlingListener mOnFlingListener;

    protected DetailFragment() {
        mPresenter = createPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mGestureDetector = new GestureDetector(getActivity(), new MyOnGestureListener());
        View view = inflater.inflate(getLayoutId(), container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        mOnFlingListener = onFlingListener;
    }

    //设置手势识别监听器
    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override//此方法必须重写且返回真，否则onFling不起效
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if ((e1.getX() - e2.getX() > 400) && Math.abs(velocityX) > 480) {
                if (null != mOnFlingListener)
                    mOnFlingListener.onFlingLeft();
                return true;
            } else if ((e2.getX() - e1.getX() > 400) && Math.abs(velocityX) > 480) {
                if (null != mOnFlingListener)
                    mOnFlingListener.onFlingRight();
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);//返回手势识别触发的事件
        return false;
    }


    public interface OnFlingListener {
        void onFlingLeft();

        void onFlingRight();
    }

    @Override
    public void onDestroy() {
        dismissProgress();
        super.onDestroy();
        mPresenter.onUiDestroy(getUi());
    }

    public P getPresenter() {
        return mPresenter;
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
    }}
