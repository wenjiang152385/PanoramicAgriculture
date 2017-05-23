//package com.oraro.panoramicagriculture.ui.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentTransaction;
//import android.view.KeyEvent;
//
//import com.oraro.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.oraro.panoramicagriculture.R;
//import com.oraro.panoramicagriculture.presenter.Presenter;
//import com.oraro.panoramicagriculture.ui.fragment.GoudeMapFragment;
//import com.oraro.panoramicagriculture.ui.fragment.SlidingMenufragment;
//import com.oraro.panoramicagriculture.ui.view.MySlidingMenu;
//import com.oraro.panoramicagriculture.utils.CommonUtils;
//
///**
// * Created by Administrator on 2017/5/18 0018.
// *
// * @author
// */
//public class GoudeMapActivity extends BaseActivity<Presenter> {
//    private SlidingMenufragment mSlidingMenuFragment;
//    private MySlidingMenu menu;
//    private static final int MSG_QUIT_APP = 1;
//    private boolean mIsClickBackBtn = false;
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_QUIT_APP:
//                    mIsClickBackBtn = false;
//                    break;
//            }
//        }
//    };
//    private GoudeMapFragment ordinaryMapFragment;
//
//    @Override
//    public Presenter createPresenter() {
//        return null;
//    }
//
//    @Override
//    public BaseActivity getUi() {
//        return this;
//    }
//
//    @Override
//    public void initView() {
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_map);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////        }
//        menu = new MySlidingMenu(this);
//        menu.setMode(SlidingMenu.LEFT);
//        //设置触摸屏幕的模式
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
//
//        // 设置滑动菜单视图的宽度
//        // UIUtils mUiUtils = new UIUtils();
//        menu.setBehindOffset(300);
//        // 设置渐入渐出效果的值
//        menu.setFadeDegree(0.35f);
//        /**
//         * SLIDING_WINDOW：菜单栏里不包括ActionBar或标题
//         * SLIDING_CONTENT：菜单栏里包括ActionBar或标题
//         */
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        //为侧滑菜单设置布局
//        menu.setMenu(R.layout.left_menu_frame);
//
//        // menu.setOnClosedListener(mOnSlidingMenuClosed);
//
//        mSlidingMenuFragment = new SlidingMenufragment();
//        // 将侧滑栏的mSlidingMenuFragment类填充到侧滑栏的容器的布局文件中
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction();
//        transaction.replace(R.id.id_left_menu_frame, mSlidingMenuFragment, "Slidingmenu").commit();
//        ordinaryMapFragment = new GoudeMapFragment();
//        transaction=getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_1, ordinaryMapFragment,"ordinaryMapFragment").commit();
//        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
//            @Override
//            public void onOpened() {
//                if ( ordinaryMapFragment.pointInfoWindow.isShowing()) {
//                    ordinaryMapFragment. pointInfoWindow.dismiss();
//                }
//                if (ordinaryMapFragment.pointListWindow.isShowing()) {
//                    ordinaryMapFragment. pointListWindow.dismiss();
//                }
//            }
//        });
//
//        ordinaryMapFragment.setmenu(menu);
//    }
//    public void showSlidingMenu() {
//        menu.showMenu();
//    }
//
//    public void hideSlidingMenu() {
//        menu.toggle(false);
//    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                if (!mIsClickBackBtn) {
//                    CommonUtils.Toast(getUi(), getResources().getString(R.string.quit_app_message));
//                    mHandler.sendEmptyMessageDelayed(MSG_QUIT_APP, 3000);
//                    mIsClickBackBtn = true;
//                    return true;
//                }
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//}
