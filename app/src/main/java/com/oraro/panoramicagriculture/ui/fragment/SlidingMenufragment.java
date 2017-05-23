//package com.oraro.panoramicagriculture.ui.fragment;
//
//import android.app.Activity;
//import android.content.ContentUris;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
//import com.oraro.panoramicagriculture.R;
//import com.oraro.panoramicagriculture.common.Constants;
//import com.oraro.panoramicagriculture.data.dao.DaoSession;
//import com.oraro.panoramicagriculture.data.entity.UserEntity;
//import com.oraro.panoramicagriculture.presenter.SlidingMenuPresenter;
//import com.oraro.panoramicagriculture.ui.activity.FarmManagerActivity;
//import com.oraro.panoramicagriculture.ui.activity.GoudeMapActivity;
//import com.oraro.panoramicagriculture.ui.activity.LoginActivity;
//import com.oraro.panoramicagriculture.ui.activity.MessageListActivity;
//import com.oraro.panoramicagriculture.ui.activity.PersonalInfoActivity;
//import com.oraro.panoramicagriculture.ui.activity.RegisterActivity;
//import com.oraro.panoramicagriculture.ui.activity.SettingsActivity;
//import com.oraro.panoramicagriculture.ui.activity.UserListActivity;
//import com.oraro.panoramicagriculture.utils.UIManager;
//
///**
// * Created by Administrator on 2017/5/15 0015.
// *
// * @author
// */
//public class SlidingMenufragment extends BaseFragment<SlidingMenuPresenter> implements View.OnClickListener {
//
//    private View view;
//    private String[] texts = new String[]{"新建农场", "新建销售点", "新建配送中心", "添加用户"};
//    private int[] imageviews = {R.mipmap.ic_navigation_new_farm, R.mipmap.ic_navigation_purchase
//            , R.mipmap.ic_dc, R.mipmap.ic_navigation_user};
//    private FrameLayout fr_settings;
//    private LinearLayout drawer_user_layout;
//    private Button bt_resigter;
//    private DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
//    public TextView drawerNickname;
//    public SimpleDraweeView drawerUserIcon;
//    public ImageView mImgUserRole;
//    public GoudeMapFragment ordinaryMapFragment;
//    private LinearLayout ll_slidmenu;
//    private LinearLayout ll_slidmenu1;
//    private LinearLayout ll_slidmenu2;
//    private LinearLayout ll_slidmenu3;
//    private LinearLayout ll_slidmenu4;
//    private TextView tv_slidmenu1;
//    private TextView tv_slidmenu2;
//    private TextView tv_slidmenu3;
//    private TextView tv_slidmenu4;
//    private ImageView iv_slidmenu1;
//    private ImageView iv_slidmenu2;
//    private ImageView iv_slidmenu3;
//    private ImageView iv_slidmenu4;
//    private ImageView iv1;
//    private ImageView iv2;
//    private ImageView iv3;
//    private ImageView iv4;
//    private GoudeMapActivity ordinaryMap1Activity;
//    private FrameLayout drawer_notification_icon;
//
//    @Override
//    public SlidingMenuPresenter createPresenter() {
//        return new SlidingMenuPresenter();
//    }
//
//    @Override
//    public BaseFragment getUi() {
//        return this;
//    }
//
//    @Override
//    public void initView() {
//        fr_settings = (FrameLayout) view.findViewById(R.id.navigation_settings_fl);
//        drawer_user_layout = (LinearLayout) view.findViewById(R.id.drawer_user_layout);
//        fr_settings.setOnClickListener(this);
//        drawer_user_layout.setOnClickListener(this);
//        bt_resigter = (Button) view.findViewById(R.id.bt_save);
//        drawer_notification_icon = (FrameLayout) view.findViewById(R.id.navigation_message);
//        drawer_notification_icon.setOnClickListener(this);
//        bt_resigter.setOnClickListener(this);
//        drawerNickname = (TextView) view.findViewById(R.id.drawer_user_nick);
//        drawerUserIcon = (SimpleDraweeView) view.findViewById(R.id.drawer_user_icon);
//        mImgUserRole = (ImageView) view.findViewById(R.id.img_user_role);
//        ll_slidmenu = (LinearLayout) view.findViewById(R.id.ll_slidmenu);
//        ll_slidmenu1 = (LinearLayout) view.findViewById(R.id.ll_slidmenu1);
//        ll_slidmenu2 = (LinearLayout) view.findViewById(R.id.ll_slidmenu2);
//        ll_slidmenu3 = (LinearLayout) view.findViewById(R.id.ll_slidmenu3);
//        ll_slidmenu4 = (LinearLayout) view.findViewById(R.id.ll_slidmenu4);
//        tv_slidmenu1 = (TextView) view.findViewById(R.id.tv_slidmenu1);
//        tv_slidmenu2 = (TextView) view.findViewById(R.id.tv_slidmenu2);
//        tv_slidmenu3 = (TextView) view.findViewById(R.id.tv_slidmenu3);
//        tv_slidmenu4 = (TextView) view.findViewById(R.id.tv_slidmenu4);
//        iv_slidmenu1 = (ImageView) view.findViewById(R.id.iv_slidmenu1);
//        iv_slidmenu2 = (ImageView) view.findViewById(R.id.iv_slidmenu2);
//        iv_slidmenu3 = (ImageView) view.findViewById(R.id.iv_slidmenu3);
//        iv_slidmenu4 = (ImageView) view.findViewById(R.id.iv_slidmenu4);
//        iv1 = (ImageView) view.findViewById(R.id.iv1);
//        iv2 = (ImageView) view.findViewById(R.id.iv2);
//        iv3 = (ImageView) view.findViewById(R.id.iv3);
//        iv4 = (ImageView) view.findViewById(R.id.iv4);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.leftmenu, null);
//        return view;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ordinaryMap1Activity = (GoudeMapActivity) activity;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.navigation_message:
//                if (PanoramicAgricultureApplication.isLogined()) {
//                    startActivity(new Intent(getActivity(), MessageListActivity.class));
//                } else {
//                    UIManager.jump2login(getActivity());
//                }
//                break;
//            case R.id.navigation_settings_fl:
//                startActivity(new Intent(getActivity(), SettingsActivity.class));
//                break;
//            case R.id.drawer_user_layout:
//                if (PanoramicAgricultureApplication.isLogined()) {
//                    startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//                break;
//            case R.id.bt_save:
//                ordinaryMap1Activity.hideSlidingMenu();
//                if (PanoramicAgricultureApplication.isLogined()) {
//                    daoSession.getUserEntityDao().deleteByKey(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId());
//                    PanoramicAgricultureApplication.LOCAL_LOGINED_USER = null;
//                    drawerNickname.setText(getString(R.string.not_logged));
//                    //drawerUserIcon.setImageResource(R.mipmap.user_icon);
//                    drawerUserIcon.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.ic_person_info)).build());
//
//                    ordinaryMapFragment = (GoudeMapFragment) getActivity().getSupportFragmentManager().findFragmentByTag("ordinaryMapFragment");
//                    ordinaryMapFragment.refreshScreenMarker();
//                }
//
//                initMenuByUserEntitiy(PanoramicAgricultureApplication.LOCAL_LOGINED_USER);
//                if (ordinaryMapFragment.displaydata != null) {
//                    ordinaryMapFragment.displaydata.setVisibility(View.GONE);
//                }
//
//                break;
//
//        }
//    }
//
//    public void setView() {
//        ll_slidmenu.setVisibility(View.VISIBLE);
//        ll_slidmenu1.setVisibility(View.VISIBLE);
//        tv_slidmenu1.setVisibility(View.VISIBLE);
//        iv_slidmenu1.setVisibility(View.VISIBLE);
//        iv1.setVisibility(View.VISIBLE);
//        ll_slidmenu2.setVisibility(View.GONE);
//        ll_slidmenu3.setVisibility(View.GONE);
//        ll_slidmenu4.setVisibility(View.GONE);
//        iv2.setVisibility(View.GONE);
//        iv3.setVisibility(View.GONE);
//        iv4.setVisibility(View.GONE);
//    }
//
//    public void initMenuByUserEntitiy(UserEntity entity) {
//        if (null == entity) {
//            //如果没有登录
//            ll_slidmenu.setVisibility(View.GONE);
//        } else {
//            if (Constants.ROLE_FARMER == entity.getUserRole()) {
//                //如果是农户
//                setView();
//                tv_slidmenu1.setText(getString(R.string.my_farm));
//                iv_slidmenu1.setBackgroundResource(R.mipmap.farm_icon_white);
//                ll_slidmenu1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (PanoramicAgricultureApplication.isLogined()) {
//                            startActivity(new Intent(getActivity(), FarmManagerActivity.class));
//                        } else {
//                            UIManager.jump2login(getActivity());
//                        }
//                    }
//                });
//            } else if (Constants.ROLE_PURCHASER == entity.getUserRole()) {
//                //如果是销售中心
//                setView();
//                tv_slidmenu1.setText(getString(R.string.my_purchaser));
//                iv_slidmenu1.setBackgroundResource(R.mipmap.ic_navigation_purchase);
//                ll_slidmenu1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (PanoramicAgricultureApplication.isLogined()) {
//                            startActivity(new Intent(getActivity(), FarmManagerActivity.class));
//                        } else {
//                            UIManager.jump2login(getActivity());
//                        }
//                    }
//                });
//            } else if (Constants.ADMIN == entity.getUserRole()) {
//                ll_slidmenu.setVisibility(View.VISIBLE);
//                ll_slidmenu1.setVisibility(View.VISIBLE);
//                ll_slidmenu2.setVisibility(View.VISIBLE);
//                ll_slidmenu3.setVisibility(View.VISIBLE);
//                ll_slidmenu4.setVisibility(View.VISIBLE);
//                tv_slidmenu2.setVisibility(View.VISIBLE);
//                iv_slidmenu2.setVisibility(View.VISIBLE);
//                tv_slidmenu3.setVisibility(View.VISIBLE);
//                iv_slidmenu3.setVisibility(View.VISIBLE);
//                tv_slidmenu4.setVisibility(View.VISIBLE);
//                iv_slidmenu4.setVisibility(View.VISIBLE);
//                tv_slidmenu1.setVisibility(View.VISIBLE);
//                iv_slidmenu1.setVisibility(View.VISIBLE);
//                iv1.setVisibility(View.VISIBLE);
//                iv2.setVisibility(View.VISIBLE);
//                iv3.setVisibility(View.VISIBLE);
//                iv4.setVisibility(View.VISIBLE);
//                tv_slidmenu1.setText(getString(R.string.new_farm));
//                iv_slidmenu1.setBackgroundResource(R.mipmap.ic_navigation_new_farm);
//                tv_slidmenu2.setText(getString(R.string.new_purchasing_point));
//                iv_slidmenu2.setBackgroundResource(R.mipmap.ic_navigation_purchase);
//                tv_slidmenu3.setText(getString(R.string.new_dc));
//                iv_slidmenu3.setBackgroundResource(R.mipmap.ic_dc);
//                tv_slidmenu4.setText(getString(R.string.new_user));
//                iv_slidmenu4.setBackgroundResource(R.mipmap.ic_navigation_user);
//                ll_slidmenu1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent farmIntent = new Intent(getActivity(), UserListActivity.class);
//                        farmIntent.putExtra("userRole", Constants.ROLE_FARMER);
//                        startActivity(farmIntent);
//                    }
//                });
//                ll_slidmenu2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent purchaserIntent = new Intent(getActivity(), UserListActivity.class);
//                        purchaserIntent.putExtra("userRole", Constants.ROLE_PURCHASER);
//                        startActivity(purchaserIntent);
//                    }
//                });
//                ll_slidmenu3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent dcIntent = new Intent(getActivity(), UserListActivity.class);
//                        dcIntent.putExtra("userRole", Constants.ROLE_DC);
//                        startActivity(dcIntent);
//                    }
//                });
//                ll_slidmenu4.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(getActivity(), RegisterActivity.class));
//                    }
//                });
//            } else if (Constants.ROLE_DC == entity.getUserRole()) {
//                setView();
//                tv_slidmenu1.setText(getString(R.string.my_dc));
//                iv_slidmenu1.setBackgroundResource(R.mipmap.ic_dc);
//                ll_slidmenu1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (PanoramicAgricultureApplication.isLogined()) {
//                            startActivity(new Intent(getActivity(), FarmManagerActivity.class));
//                        } else {
//                            UIManager.jump2login(getActivity());
//                        }
//                    }
//                });
//            }
//        }
//    }
//
//}
