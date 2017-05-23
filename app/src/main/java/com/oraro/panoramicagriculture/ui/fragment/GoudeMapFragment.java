//package com.oraro.panoramicagriculture.ui.fragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.CameraUpdate;
//import com.amap.api.maps2d.CameraUpdateFactory;
//import com.amap.api.maps2d.LocationSource;
//import com.amap.api.maps2d.model.BitmapDescriptorFactory;
//import com.amap.api.maps2d.model.CameraPosition;
//import com.amap.api.maps2d.model.LatLng;
//import com.amap.api.maps2d.model.Marker;
//import com.amap.api.maps2d.model.MyLocationStyle;
//import com.amap.api.services.district.DistrictItem;
//import com.amap.api.services.district.DistrictResult;
//import com.amap.api.services.district.DistrictSearch;
//import com.amap.api.services.weather.LocalWeatherForecastResult;
//import com.amap.api.services.weather.LocalWeatherLive;
//import com.amap.api.services.weather.LocalWeatherLiveResult;
//import com.amap.api.services.weather.WeatherSearch;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
//import com.facebook.drawee.generic.RoundingParams;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.google.gson.JsonObject;
//import com.igexin.sdk.PushManager;
//import com.oraro.citypickerview.widget.CityPicker;
//import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
//import com.oraro.panoramicagriculture.R;
//import com.oraro.panoramicagriculture.common.Constants;
//import com.oraro.panoramicagriculture.data.dao.DCEntityDao;
//import com.oraro.panoramicagriculture.data.dao.DaoSession;
//import com.oraro.panoramicagriculture.data.dao.FarmEntityDao;
//import com.oraro.panoramicagriculture.data.dao.PurchasingPointDao;
//import com.oraro.panoramicagriculture.data.dao.UserEntityDao;
//import com.oraro.panoramicagriculture.data.entity.DCEntity;
//import com.oraro.panoramicagriculture.data.entity.FarmEntity;
//import com.oraro.panoramicagriculture.data.entity.PurchasingPoint;
//import com.oraro.panoramicagriculture.data.entity.UserEntity;
//import com.oraro.panoramicagriculture.presenter.GoudeMapPresenter;
//import com.oraro.panoramicagriculture.ui.activity.DcActivity;
//import com.oraro.panoramicagriculture.ui.activity.FarmActivity;
//import com.oraro.panoramicagriculture.ui.activity.GoudeMapActivity;
//import com.oraro.panoramicagriculture.ui.activity.LoginActivity;
//import com.oraro.panoramicagriculture.ui.activity.NewFarmActivity;
//import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
//import com.oraro.panoramicagriculture.ui.activity.PersonalInfoActivity;
//import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
//import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
//import com.oraro.panoramicagriculture.ui.adapter.PointListAdapter;
//import com.oraro.panoramicagriculture.ui.service.PAIntentService;
//import com.oraro.panoramicagriculture.ui.service.PushService;
//import com.oraro.panoramicagriculture.ui.view.ErrorLayout;
//import com.oraro.panoramicagriculture.ui.view.MyMapView;
//import com.oraro.panoramicagriculture.ui.view.MySlidingMenu;
//import com.oraro.panoramicagriculture.ui.view.SupplyAndDemandView;
//import com.oraro.panoramicagriculture.utils.CommonUtils;
//import com.oraro.panoramicagriculture.utils.LogUtils;
//import com.oraro.panoramicagriculture.utils.MapHelperManager;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2017/5/18 0018.
// *
// * @author
// */
//public class GoudeMapFragment extends BaseFragment<GoudeMapPresenter> implements View.OnClickListener, AMap.OnMarkerDragListener, AMap.OnMarkerClickListener, AMap.OnMapLongClickListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener, AMap.OnInfoWindowClickListener, LocationSource, AMapLocationListener, DistrictSearch.OnDistrictSearchListener, WeatherSearch.OnWeatherSearchListener {
//    private final String TAG = this.getClass().getSimpleName();
//    private View view;
//    private TextView mSupplyAndDemandTv;
//    private SupplyAndDemandView mSupplyAndDemandView;
//    private FrameLayout mDrawer;
//    private FrameLayout mToolbar;
//    private TextView toolbarTitle;
//    private RelativeLayout toolbarTitleLayout;
//    public GoudeMapActivity mActivity;
//    private View popupListLayout;
//    private RecyclerView pointListView;
//    private ErrorLayout mErrorLayout;
//    private PointListAdapter pointListAdapter;
//    public PopupWindow pointListWindow;
//    private View pointInfoLayout;
//    private RelativeLayout pointPopupLayout;
//    private LinearLayout pointIntroductionLayout;
//    private SimpleDraweeView pointMainPictrue;
//    private ImageView pointLevelImage;
//    private TextView temperatureText;
//    private TextView pointAreaText;
//    private TextView pointCropNumText;
//    private TextView pointDescText;
//    public PopupWindow pointInfoWindow;
//    private MapHelperManager mapHelperManager;
//    private DaoSession daoSession;
//    private MenuItem displayDataMenu;
//    private String province;
//    private String city;
//    private LocationSource.OnLocationChangedListener locationChangedListener;
//    private AMapLocationClient aMapLocationClient;
//    private AMapLocationClientOption aMapLocationClientOption;
//    private AMap aMap;
//    public MyMapView mapView;
//    private Marker currentMarker;
//    private CityPicker cityPicker;
//    private Marker newFarmMarker;
//    private ImageView iv_yaw;
//    public ImageView displaydata;
//    public SlidingMenufragment slidingMenufragment;
//    private RelativeLayout rl_title;
//    private  MySlidingMenu slidingMenu;
//    @Override
//    public GoudeMapPresenter createPresenter() {
//        return new GoudeMapPresenter();
//    }
//
//    @Override
//    public BaseFragment getUi() {
//        return this;
//    }
//
//    @Override
//    public void initView() {
//
//    }
//    public TextView getSupplyAndDemandTv() {
//        return mSupplyAndDemandTv;
//    }
//    public SupplyAndDemandView getSupplyAndDemandView() {
//        return mSupplyAndDemandView;
//    }
//    public LatLng getCurrentLatLng() {
//        return currentLatLng;
//    }
//    public void setmenu(MySlidingMenu mySlidingMenu){
//        slidingMenu=mySlidingMenu;
//    }
//    private LatLng currentLatLng;
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mActivity= (GoudeMapActivity) context;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_goude_map, null);
//        slidingMenufragment = (SlidingMenufragment) getActivity().getSupportFragmentManager().findFragmentByTag("Slidingmenu");
//        mSupplyAndDemandTv = (TextView) view.findViewById(R.id.ordinary_supply_and_deman_tv);
//        mSupplyAndDemandTv.setOnClickListener(this);
//        mSupplyAndDemandView = (SupplyAndDemandView) view.findViewById(R.id.ordinary_supply_and_deman_view);
//        mSupplyAndDemandView.setOnClickListener(this);
//        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
//
//        mDrawer = (FrameLayout) view.findViewById(R.id.drawer);
//        mToolbar = (FrameLayout) view.findViewById(R.id.toolbar);
//        toolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
//        toolbarTitleLayout = (RelativeLayout) view.findViewById(R.id.toolbar_title_layout);
//        toolbarTitleLayout.setOnClickListener(this);
//        displaydata = (ImageView) view.findViewById(R.id.display_data);
//        iv_yaw = (ImageView) view.findViewById(R.id.iv_yaw);
//        displaydata.setOnClickListener(this);
//        iv_yaw.setOnClickListener(this);
//        rl_title.setVisibility(View.VISIBLE);
//        toolbarTitleLayout.setVisibility(View.VISIBLE);
//        mToolbar.setVisibility(View.VISIBLE);
////        mActivity.setSupportActionBar(mToolbar);
////       mActivity. getSupportActionBar().setDisplayShowTitleEnabled(false);
////        getSupportActionBar().hide();
//
//        //给地图控件赋值
//        mapView = (MyMapView) view.findViewById(R.id.oraro_map_view);
//        //把地图控件设置给侧滑栏控件
////        mDrawer.setMyMapView(mapView);
//        slidingMenu.setMyMapView(mapView);
//
////        ActionBarDrawerToggle  mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawer, mToolbar, R.string.app_name, R.string.app_name) {
////            @Override
////            public void onDrawerOpened(View drawerView) {
////                if (pointListWindow != null && pointListWindow.isShowing()) {
////                    pointListWindow.dismiss();
////                }
////                if (pointInfoWindow != null && pointInfoWindow.isShowing()) {
////                    pointInfoWindow.dismiss();
////                }
////                super.onDrawerOpened(drawerView);
////            }
////
////            @Override
////            public void onDrawerSlide(View drawerView, float slideOffset) {
////                super.onDrawerSlide(drawerView, slideOffset);
////            }
////
////            @Override
////            public void onDrawerClosed(View drawerView) {
////                super.onDrawerClosed(drawerView);
////            }
////        };
////
////        mDrawerToggle.syncState();
////        mDrawer.setDrawerListener(mDrawerToggle);
////        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
////
////        //把侧滑菜单控件设置给侧滑栏控件
////        mDrawer.setNavigationView(mNavigationView);
////
////        mNavigationView.setNavigationItemSelectedListener(this);
////
////        mNavigationView.getHeaderView(0).findViewById(R.id.drawer_user_icon).setOnClickListener(this);
////        mNavigationView.getHeaderView(0).findViewById(R.id.drawer_user_layout).setOnClickListener(this);
////        mNavigationView.getHeaderView(0).findViewById(R.id.navigation_settings_fl).setOnClickListener(this);
////
////        drawerNickname = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_user_nick);
////        drawerUserIcon = (SimpleDraweeView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_user_icon);
////        mImgUserRole = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.img_user_role);
//
//
//        mapView.onCreate(savedInstanceState);
//        aMap = mapView.getMap();
//        aMap.getUiSettings().setZoomGesturesEnabled(true);
//        aMap.getUiSettings().setScaleControlsEnabled(true);
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);
//        aMap.setOnMapLongClickListener(this);
//        aMap.setOnMarkerClickListener(this);
//        aMap.setOnMarkerDragListener(this);
//        aMap.setInfoWindowAdapter(this);
//        aMap.setOnMapClickListener(this);
//        aMap.setOnInfoWindowClickListener(this);
//
//        //判断6.0是否有定位权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !CommonUtils.selfPermissionGranted(mActivity, Constants.ACCESS_COARSE_LOCATION)) {
//            requestPermissions(new String[]{Constants.ACCESS_COARSE_LOCATION}, Constants.ACCESS_COARSE_LOCATION_REQUEST_CODE);
//        } else {
//            PushManager.getInstance().initialize(mActivity.getApplicationContext(), PushService.class);
//            PushManager.getInstance().registerPushIntentService(mActivity.getApplicationContext(), PAIntentService.class);
//            aMap.setLocationSource(this);
//            aMap.setMyLocationEnabled(true);
//        }
//
//
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
//        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
//        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
//        aMap.setMyLocationStyle(myLocationStyle);
//
//        mapHelperManager = MapHelperManager.getInstance();
//        daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
//
//        popupListLayout = LayoutInflater.from(mActivity).inflate(R.layout.point_popup_list_layout, null);
//        pointListView = (RecyclerView) popupListLayout.findViewById(R.id.point_list_view);
//        pointListView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
//        mErrorLayout = (ErrorLayout) popupListLayout.findViewById(R.id.error_frame);
//        pointListAdapter = new PointListAdapter(mActivity, BaseListAdapter.NEITHER,this);
//        pointListView.setAdapter(pointListAdapter);
//        pointListAdapter.setState(BaseListAdapter.STATE_NO_MORE);
//        pointListWindow = new PopupWindow(popupListLayout, LinearLayout.LayoutParams.MATCH_PARENT,
//                getResources().getDisplayMetrics().heightPixels / 3, false);
//        pointListWindow.setAnimationStyle(com.oraro.citypickerview.R.style.AnimBottom);
//
//        pointListWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                mSupplyAndDemandView.setVisibility(View.GONE);
//            }
//        });
//
//        pointInfoLayout = LayoutInflater.from(mActivity).inflate(R.layout.point_popup_info_layout, null);
//        pointPopupLayout = (RelativeLayout) pointInfoLayout.findViewById(R.id.point_popup_layout);
//        pointIntroductionLayout = (LinearLayout) pointInfoLayout.findViewById(R.id.point_introduction_layout);
//        pointMainPictrue = (SimpleDraweeView) pointInfoLayout.findViewById(R.id.point_main_pic);
//        pointLevelImage = (ImageView) pointInfoLayout.findViewById(R.id.point_grade_pic);
//        temperatureText = (TextView) pointInfoLayout.findViewById(R.id.tv_point_temperature);
//        pointAreaText = (TextView) pointInfoLayout.findViewById(R.id.tv_point_area);
//        pointCropNumText = (TextView) pointInfoLayout.findViewById(R.id.tv_point_crop_num);
//        pointDescText = (TextView) pointInfoLayout.findViewById(R.id.tv_point_desc);
//        pointPopupLayout.setOnClickListener(this);
//
//        pointInfoWindow = new PopupWindow(pointInfoLayout, getResources().getDisplayMetrics().widthPixels - 40, RelativeLayout.LayoutParams.WRAP_CONTENT, false);
//        pointInfoWindow.setAnimationStyle(com.oraro.citypickerview.R.style.AnimBottom);
//        return view;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case Constants.ACCESS_COARSE_LOCATION_REQUEST_CODE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 权限请求成功的操作
//                    aMap.setLocationSource(this);
//                    aMap.setMyLocationEnabled(true);
//                    PushManager.getInstance().initialize(mActivity.getApplicationContext(), PushService.class);
//                    PushManager.getInstance().registerPushIntentService(mActivity.getApplicationContext(), PAIntentService.class);
//                } else {
//                    // 权限请求失败的操作
//                }
//                return;
//            }
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (pointInfoWindow.isShowing()) {
//            pointInfoWindow.dismiss();
//        }
//        if (pointListWindow.isShowing()) {
//            pointListWindow.dismiss();
//        }
//        super.onDestroy();
//        if (null != aMapLocationClient) {
//            aMapLocationClient.unRegisterLocationListener(this);
//            aMapLocationClient.onDestroy();
//        }
//        locationChangedListener = null;
//        aMap.clear();
//        mapView.onDestroy();
//    }
//
////    @Override
////    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
////        inflater.inflate(R.menu.ordinary_map_menu, menu);
////        MenuItem searchItem = menu.findItem(R.id.action_search);
////        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
////            @Override
////            public boolean onMenuItemActionExpand(MenuItem item) {
////                Log.e(TAG, "query==");
////                if (pointListWindow != null && pointListWindow.isShowing()) {
////                    pointListWindow.dismiss();
////                }
////                if (pointInfoWindow != null && pointInfoWindow.isShowing()) {
////                    pointInfoWindow.dismiss();
////                }
////                return true;
////            }
////
////            @Override
////            public boolean onMenuItemActionCollapse(MenuItem item) {
////                return true;
////            }
////        });
////        SearchView searchView = (SearchView) searchItem.getActionView();
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                Log.e(TAG, "query==" + query);
////                return true;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String newText) {
////                Log.e(TAG, "newText==" + newText);
////                return true;
////            }
////        });
////        displayDataMenu = menu.findItem(R.id.display_data);
////        if (displayDataMenu != null && PanoramicAgricultureApplication.isLogined() && PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ADMIN) {
////            displayDataMenu.setVisible(true);
////        }
////        super.onCreateOptionsMenu(menu, inflater);
////    }
//
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        switch (item.getItemId()) {
////            case R.id.display_data:
////                Intent intent = new Intent(mActivity, OrdinaryDataActivity.class);
////                intent.putExtra("province", province);
////                intent.putExtra("city", city);
////                startActivity(intent);
////                break;
////        }
////        return super.onOptionsItemSelected(item);
////    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//        refreshScreenMarker();
//        if (PanoramicAgricultureApplication.isLogined()) {
//            Log.i(TAG, "isLogined");
//            if (slidingMenufragment.drawerNickname != null) {
//                Log.i(TAG, "setText");
//                slidingMenufragment. drawerNickname.setText(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getNickName());
//                if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead() != null) {
//                    if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead().startsWith("content")) {
//                        slidingMenufragment. drawerUserIcon.setImageURI(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
//                    } else {
//                        slidingMenufragment. drawerUserIcon.setImageURI(Constants.SERVER_ADDRESS + PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
//                    }
//                }
//                if (Constants.ROLE_FARMER == PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole()) {
//                    slidingMenufragment.  mImgUserRole.setImageResource(R.mipmap.ic_navigation_farmer);
//                } else if (Constants.ROLE_PURCHASER == PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole()) {
//                    slidingMenufragment.mImgUserRole.setImageResource(R.mipmap.ic_navigation_buyer);
//                }
//            }
//        } else {
//            UserEntityDao userEntityDao = daoSession.getUserEntityDao();
//            List<UserEntity> userEntities = userEntityDao.loadAll();
//            Log.i(TAG, "user.." + userEntities.size());
//            if (userEntities.size() != 0) {
//                PanoramicAgricultureApplication.LOCAL_LOGINED_USER = userEntities.get(userEntities.size() - 1);
//                if ( slidingMenufragment.drawerNickname != null) {
//                    Log.i(TAG, PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId() + "..user..nickname..." + PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getNickName());
//                    slidingMenufragment.drawerNickname.setText(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getNickName());
//                    if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead() != null) {
//                        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead().startsWith("content")) {
//                            slidingMenufragment.drawerUserIcon.setImageURI(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
//                        } else {
//                            slidingMenufragment.drawerUserIcon.setImageURI(Constants.SERVER_ADDRESS + PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
//                        }
//                    }
//                } else {
//                    slidingMenufragment.drawerNickname.setText(getString(R.string.not_logged));
//                    //drawerUserIcon.setImageResource(R.mipmap.user_icon);
//                    slidingMenufragment. drawerUserIcon.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.ic_person_info)).build());
//                }
//            }
//        }
//
//        if (displaydata != null && PanoramicAgricultureApplication.isLogined() && PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ADMIN) {
//            displaydata.setVisibility(View.VISIBLE);
//        }
//        slidingMenufragment.initMenuByUserEntitiy(PanoramicAgricultureApplication.LOCAL_LOGINED_USER);
//    }
//
//    public void  refreshScreenMarker() {
//        List pointList = new ArrayList();
//        for (PurchasingPoint purchasingPoint : daoSession.getPurchasingPointDao().loadAll()) {
//            pointList.add(purchasingPoint);
//            addMarker(new LatLng(purchasingPoint.getLatitude(), purchasingPoint.getLongitude()), null, null, 2, purchasingPoint.getPurchasingPointId());
//        }
//        for (FarmEntity farmEntity : daoSession.getFarmEntityDao().loadAll()) {
//            pointList.add(farmEntity);
//            addMarker(new LatLng(farmEntity.getLatitude(), farmEntity.getLongitude()), null, null, 1, farmEntity.getFarmId());
//        }
//        for (DCEntity dcEntity : daoSession.getDCEntityDao().loadAll()) {
//            pointList.add(dcEntity);
//            addMarker(new LatLng(dcEntity.getLatitude(), dcEntity.getLongitude()), null, null, 3, dcEntity.getId());
//        }
//        addPointListData(pointList);
//    }
//
//    public void addPointListData(List pointList) {
//        pointListAdapter.clear();
//        pointListAdapter.addItems(pointList);
//    }
//
//    public void addMarker(LatLng latLng, String title, String snippet, int pointType, long pointId) {
//        Log.i(TAG, "addMarker" + title);
////        marker.setTitle(title);
////        marker.setSnippet(snippet);
//        Map<String, Object> object = new HashMap<>();
//        object.put("pointType", pointType);
//        object.put("pointId", pointId);
//        for (Marker marker1 : aMap.getMapScreenMarkers()) {
//            if (object.equals(marker1.getObject())) {
////                if (pointType == 1) {
////                    if (PanoramicAgricultureApplication.isLogined() && daoSession.getFarmEntityDao().load(pointId).getUserId() == PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId()) {
////                        marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
////                                .decodeResource(getResources(), R.mipmap.farm_marker_self)));
////                    } else {
////                        marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
////                                .decodeResource(getResources(), R.mipmap.farm_marker)));
////                    }
////                } else if (pointType == 2) {
////                    if (PanoramicAgricultureApplication.isLogined() && daoSession.getPurchasingPointDao().load(pointId).getUserId() == PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId()) {
////                        marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
////                                .decodeResource(getResources(), R.mipmap.purchasing_point_marker_self)));
////                    } else {
////                        marker1.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
////                                .decodeResource(getResources(), R.mipmap.purchasing_point_marker)));
////                    }
////                }
//                return;
//            }
//        }
//        Marker marker = mapHelperManager.addMarker(aMap, latLng);
//        marker.setObject(object);
//        if (pointType == 1) {
//            marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(), R.mipmap.farm_marker)));
//        } else if (pointType == 2) {
//            marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(), R.mipmap.purchasing_point_marker)));
//        } else if (pointType == 3) {
//            marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(), R.mipmap.dc_point_marker)));
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        LogUtils.e("jw","data=="+v.getId());
//        switch (v.getId()) {
//            case R.id.iv_yaw:
//                if (pointInfoWindow.isShowing()) {
//                    pointInfoWindow.dismiss();
//                }
//                if (pointListWindow.isShowing()) {
//                    pointListWindow.dismiss();
//                }
//                mActivity.showSlidingMenu();
//                break;
//            case R.id.display_data:
//                Intent intent1 = new Intent(mActivity, OrdinaryDataActivity.class);
//                intent1.putExtra("province", province);
//                intent1.putExtra("city", city);
//                startActivity(intent1);
//                break;
//            case R.id.ordinary_supply_and_deman_tv:
//                Intent mOrdinaryDataActivityIntent = new Intent(mActivity, OrdinaryDataActivity.class);
//                mOrdinaryDataActivityIntent.putExtra("province", province);
//                mOrdinaryDataActivityIntent.putExtra("city", city);
//                startActivity(mOrdinaryDataActivityIntent);
//                break;
//            case R.id.ordinary_supply_and_deman_view:
//                mOrdinaryDataActivityIntent = new Intent(mActivity, OrdinaryDataActivity.class);
//                mOrdinaryDataActivityIntent.putExtra("province", province);
//                mOrdinaryDataActivityIntent.putExtra("city", city);
//                startActivity(mOrdinaryDataActivityIntent);
//                break;
//            case R.id.toolbar_title_layout:
//                if (cityPicker != null && cityPicker.isShow())
//                    return;
//
//                if (pointListWindow.isShowing()) {
//                    pointListWindow.dismiss();
//                }
//                cityPicker = new CityPicker.Builder(mActivity).textSize(20)
//                        .title("选择地区")
//                        .titleTextColor("#000000")
//                        .backgroundPop(0xa0000000)
//                        .confirTextColor("#000000")
//                        .cancelTextColor("#000000")
//                        .provinceCyclic(true)
//                        .cityCyclic(false)
//                        .districtCyclic(false)
//                        .visibleItemsCount(7)
//                        .itemPadding(10)
//                        .build();
//                cityPicker.show();
//                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
//                    @Override
//                    public void onSelected(String... citySelected) {
//
//                        JsonObject jsonObject = new JsonObject();
//                        if (Integer.parseInt(citySelected[3]) == 0) {
//                            jsonObject.addProperty("regionType", 0);
//                            jsonObject.addProperty("regionName", citySelected[0]);
//                            toolbarTitle.setText(citySelected[0]);
//                            mapHelperManager.districtSearch(mActivity, citySelected[0], GoudeMapFragment.this);
//                        } else if (Integer.parseInt(citySelected[3]) == 1) {
//                            toolbarTitle.setText(citySelected[1]);
//                            jsonObject.addProperty("regionType", 1);
//                            jsonObject.addProperty("regionName", citySelected[1]);
//                            mapHelperManager.districtSearch(mActivity, citySelected[1], GoudeMapFragment.this);
//                        } else {
//                            toolbarTitle.setText(citySelected[2]);
//                            jsonObject.addProperty("regionType", 2);
//                            jsonObject.addProperty("regionName", citySelected[2]);
//                            mapHelperManager.districtSearch(mActivity, citySelected[2], GoudeMapFragment.this);
//                        }
//                        Log.i(TAG, citySelected.length + " 选择结果：\n省：" + citySelected[0] + "\n市：" + citySelected[1] + "\n区："
//                                + citySelected[2] + "\n邮编：" + citySelected[3]);
//                        aMap.clear();
//                        jsonObject.addProperty("userRole", 0);
//                        getPresenter().getAllPoint("getPlaceInfo", jsonObject);
//                        showProgress();
//                    }
//                });
//                break;
//            case R.id.drawer_user_icon:
//            case R.id.drawer_user_layout:
//                if (PanoramicAgricultureApplication.isLogined()) {
//                    startActivity(new Intent(mActivity, PersonalInfoActivity.class));
//                } else {
//                    startActivity(new Intent(mActivity, LoginActivity.class));
//                }
//                break;
////            case R.id.navigation_settings_fl:
////                startActivity(new Intent(mActivity, SettingsActivity.class));
////                break;
//            case R.id.point_popup_layout:
//                Intent intent = new Intent();
//                Map object = (Map) currentMarker.getObject();
//                if ((int) object.get("pointType") == 1) {
//                    intent.setClass(mActivity, FarmActivity.class);
//
//                } else if ((int) object.get("pointType") == 2) {
//                    intent.setClass(mActivity, PointMainActivity.class);
//                } else if ((int) object.get("pointType") == 3) {
//                    intent.setClass(mActivity, DcActivity.class);
//                }
//                intent.putExtra("pointId", (long) object.get("pointId"));
//                startActivity(intent);
//                break;
//            default:
//
//                break;
//        }
//    }
//
//    @Override
//    public View getInfoWindow(final Marker marker) {
//        Log.i(TAG, "getInfoWindow.." + marker.getId());
//        if (marker.getObject() instanceof Map && "地址".equalsIgnoreCase(marker.getTitle())) {
//            final Map<String, String> data = (Map<String, String>) marker.getObject();
//            View view = LayoutInflater.from(mActivity).inflate(R.layout.newfarm_infowindow_layout, null);
//            TextView citText = (TextView) view.findViewById(R.id.tv_farm_city);
//            final EditText addressEdit = (EditText) view.findViewById(R.id.et_farm_address);
//            Button button = (Button) view.findViewById(R.id.btn_submit);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, NewFarmActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("latitude", String.valueOf(marker.getPosition().latitude));
//                    bundle.putString("longitude", String.valueOf(marker.getPosition().longitude));
//                    bundle.putString("province", data.get("province"));
//                    bundle.putString("city", data.get("city"));
//                    bundle.putString("district", data.get("district"));
//                    bundle.putString("address", data.get("province") + data.get("city") + data.get("district") + addressEdit.getText().toString());
//                    bundle.putString("street", data.get("street"));
//                    bundle.putString("streetNum", data.get("streetNum"));
//                    intent.putExtras(bundle);
//                    marker.setVisible(false);
//                    marker.destroy();
//                    marker.remove();
//                    newFarmMarker = null;
//                    startActivity(intent);
//                }
//            });
//            citText.setText(data.get("city") + data.get("district"));
//            addressEdit.setText(data.get("address").substring((data.get("province") + data.get("city") + data.get("district")).length()));
//            addressEdit.setSelection(addressEdit.getText().length());
//            addressEdit.requestFocus();
//            return view;
//        }
//        return null;
//    }
//
//    @Override
//    public View getInfoContents(Marker marker) {
//        return null;
//    }
//
//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        if (marker.getObject() instanceof Map && "地址".equalsIgnoreCase(marker.getTitle())) {
//
//        } else {
//            Intent intent = new Intent();
//            Map object = (Map) marker.getObject();
//            if ((int) object.get("pointType") == 1) {
//                intent.setClass(mActivity, FarmActivity.class);
//
//            } else if ((int) object.get("pointType") == 2) {
//                intent.setClass(mActivity, PointMainActivity.class);
//            }
//            intent.putExtra("pointId", (long) object.get("pointId"));
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    public void onMapClick(LatLng latLng) {
//        Log.i(TAG, "onMapClick.." +pointListWindow.isShowing());
//        if (pointListWindow.isShowing()) {
////           mActivity. getSupportActionBar().hide();
//            rl_title.setVisibility(View.GONE);
//            mToolbar.setVisibility(View.GONE);
//            toolbarTitleLayout.setVisibility(View.GONE);
//            pointListWindow.dismiss();
//            onResume();
//        } else {
//            if (pointInfoWindow.isShowing()) {
//                pointInfoWindow.dismiss();
//                return;
//            }
//            if (pointListAdapter.getDataSize() > 0) {
//                pointListWindow.showAtLocation(popupListLayout, Gravity.BOTTOM, 0, 0);
////                mActivity. getSupportActionBar().show();
//                rl_title.setVisibility(View.VISIBLE);
//                toolbarTitleLayout.setVisibility(View.VISIBLE);
//                mSupplyAndDemandView.setVisibility(View.VISIBLE);
//                mToolbar.setVisibility(View.VISIBLE);
//            } else {
////                if ( mActivity.getSupportActionBar().isShowing()) {
////                    mActivity.getSupportActionBar().hide();
////                } else {
////                    mActivity.getSupportActionBar().show();
////                }
//            }
//
//        }
//        if (currentMarker != null && currentMarker.isInfoWindowShown()) {
//            currentMarker.hideInfoWindow();
//        }
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//
//    }
//
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//        Log.i(TAG, "position:" + marker.getPosition() + ",isInfoWindowShown:" + marker.isInfoWindowShown() + "," + marker.getId());
//        currentMarker = marker;
//        if (marker.isInfoWindowShown()) {
//            marker.hideInfoWindow();//此处隐藏infoWindow不能生效，后续更进
//            Log.i(TAG, "hide");
//        } else {
//            Log.i(TAG, "show");
//            if (marker.isDraggable()) {
//                showProgress();
//                mapHelperManager.reverseGeocode(mActivity, marker.getPosition(), new AMapLocationListener() {
//                    @Override
//                    public void onLocationChanged(AMapLocation aMapLocation) {
//                        Log.i(TAG, "onLocationChanged.." + aMapLocation);
//                        Map<String, String> data = new HashMap<String, String>();
//                        data.put("province", aMapLocation.getProvince());
//                        data.put("city", aMapLocation.getCity());
//                        data.put("district", aMapLocation.getDistrict());
//                        data.put("address", aMapLocation.getAddress());
//                        data.put("street", aMapLocation.getStreet());
//                        data.put("streetNum", aMapLocation.getStreetNum());
//                        marker.setObject(data);
//                        marker.setTitle("地址");
//                        marker.showInfoWindow();
//                        dismissProgress();
//                    }
//                });
//            } else {
//                if (pointListWindow.isShowing()) {
//                    pointListWindow.dismiss();
//                }
//                Log.i(TAG, "1..." + marker.getObject());
//                if (marker.getObject() instanceof Map) {
//                    Map object = (Map) marker.getObject();
//                    RoundingParams roundingParams = new RoundingParams();
//                    roundingParams.setCornersRadii(20, 20, 0, 0);
//                    GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mActivity.getResources());
//                    GenericDraweeHierarchy hierarchy = builder.build();
//                    hierarchy.setRoundingParams(roundingParams);
//
//                    Log.i(TAG, "22..." + object.get("pointType"));
//                    if ((int) object.get("pointType") == 1) {
//                        FarmEntityDao farmEntityDao = daoSession.getFarmEntityDao();
//                        FarmEntity farmEntity = farmEntityDao.load((Long) object.get("pointId"));
//                        hierarchy.setPlaceholderImage(R.mipmap.morentu);
//                        mapHelperManager.getCityWeather(mActivity, farmEntity.getCity(), this);
//                        if (farmEntity.getFarmId() % 3 == 2) {
//                            pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + farmEntity.getSlide1());
//                            pointLevelImage.setImageResource(R.mipmap.porousvip3);
//                        } else if (farmEntity.getFarmId() % 3 == 1) {
//                            pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + farmEntity.getSlide2());
//                            pointLevelImage.setImageResource(R.mipmap.greenvip2);
//                        } else {
//                            pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + farmEntity.getSlide3());
//                        }
//                        pointAreaText.setText(farmEntity.getTotalArea().toString() + "亩");
//                        pointIntroductionLayout.setVisibility(View.VISIBLE);
//                        pointDescText.setText(farmEntity.getFarmName() + ":位于" + farmEntity.getAddress() + "。" + farmEntity.getFarmInfo());
//                        pointInfoWindow.showAtLocation(pointInfoLayout, Gravity.BOTTOM, 0, 0);
//                    } else if ((int) object.get("pointType") == 2) {
//                        hierarchy.setPlaceholderImage(R.mipmap.bitmap);
//                        PurchasingPointDao purchasingPointDao = daoSession.getPurchasingPointDao();
//                        PurchasingPoint purchasingPoint = purchasingPointDao.load((Long) object.get("pointId"));
//                        if (purchasingPoint.getPurchasingPointId() % 3 == 2) {
//                            pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + purchasingPoint.getSlide1());
//                            pointLevelImage.setImageResource(R.mipmap.porousvip3);
//                        } else if (purchasingPoint.getPurchasingPointId() % 3 == 1) {
//                            pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + purchasingPoint.getSlide2());
//                            pointLevelImage.setImageResource(R.mipmap.greenvip2);
//                        } else {
//                            pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + purchasingPoint.getSlide3());
//                        }
//                        pointIntroductionLayout.setVisibility(View.GONE);
//                        pointDescText.setText(purchasingPoint.getPurchasingPointName() + "：位于" + purchasingPoint.getAddress() + "。" + purchasingPoint.getPurchasingPointInfo());
//                        pointInfoWindow.showAtLocation(pointInfoLayout, Gravity.BOTTOM, 0, 0);
//                        mapHelperManager.getCityWeather(mActivity, purchasingPoint.getCity(), this);
//                    } else if ((int) object.get("pointType") == 3) {
//                        Log.i(TAG, "3333");
//                        hierarchy.setPlaceholderImage(R.mipmap.morentu);
//                        DCEntityDao dcEntityDao = daoSession.getDCEntityDao();
//                        DCEntity dcEntity = dcEntityDao.load((Long) object.get("pointId"));
//                        pointAreaText.setText(dcEntity.getTotalArea().toString() + "亩");
//                        pointMainPictrue.setImageURI(Constants.SERVER_ADDRESS + dcEntity.getSlide2());
//                        pointLevelImage.setImageResource(R.mipmap.greenvip2);
//                        pointIntroductionLayout.setVisibility(View.VISIBLE);
//                        pointDescText.setText(dcEntity.getDcName() + ":位于" + dcEntity.getAddress() + "。" + dcEntity.getDcInfo());
//                        pointInfoWindow.showAtLocation(pointInfoLayout, Gravity.BOTTOM, 0, 0);
//                        mapHelperManager.getCityWeather(mActivity, dcEntity.getCity(), this);
//                    }
//                    pointMainPictrue.setHierarchy(hierarchy);
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//        if (marker.isInfoWindowShown()) {
//            marker.hideInfoWindow();
//        }
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//
//    }
//
//    @Override
//    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
//        Log.e(TAG, "activate");
//        locationChangedListener = onLocationChangedListener;
//        if (aMapLocationClient == null) {
//            aMapLocationClient = new AMapLocationClient(mActivity);
//            //设置定位监听
//            aMapLocationClient.setLocationListener(this);
//        }
//        if (aMapLocationClientOption == null) {
//            aMapLocationClientOption = new AMapLocationClientOption();
//            //设置为高精度定位模式
//            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//            aMapLocationClientOption.setOnceLocation(true);
//            //设置定位参数
//            aMapLocationClient.setLocationOption(aMapLocationClientOption);
//        }
//        aMapLocationClient.startLocation();
//    }
//
//    @Override
//    public void deactivate() {
//        Log.e(TAG, "deactivate");
//        locationChangedListener = null;
//        if (aMapLocationClient != null) {
//            aMapLocationClient.stopLocation();
//            aMapLocationClient.onDestroy();
//        }
//        aMapLocationClient = null;
//    }
//    public void changeCameraPosition(LatLng latLng, float zoom, float tilt, float bearing, boolean move) {
//        CameraUpdate cameraUpdate = null;
//        try {
//            cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                    latLng, zoom, tilt, bearing
//            ));
//        } catch (NullPointerException e) {
//
//        }
//        if (move) {
//            aMap.moveCamera(cameraUpdate);
//        } else {
//            aMap.animateCamera(cameraUpdate);
//        }
//    }
//
//    @Override
//    public void onLocationChanged(AMapLocation aMapLocation) {
//        if (locationChangedListener != null && aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                locationChangedListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
//                changeCameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 12, 0, 0, true);
//                province = aMapLocation.getProvince();
//                city = aMapLocation.getCity();
//                Log.i("sysout", "onLocationChanged");
//                currentLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                pointListAdapter.notifyDataSetChanged();
//                toolbarTitle.setText(aMapLocation.getCity());
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("regionType", 1);
//                jsonObject.addProperty("regionName", aMapLocation.getCity());
//                jsonObject.addProperty("userRole", 0);
//                getPresenter().getAllPoint("getPlaceInfo", jsonObject);
//
//                JsonObject mAllSupplyAndDemandJsonObject = new JsonObject();
//                mAllSupplyAndDemandJsonObject.addProperty("typeArea", 1);
//                mAllSupplyAndDemandJsonObject.addProperty("date", CommonUtils.mLineDateFormat.format(new Date()));
//                mAllSupplyAndDemandJsonObject.addProperty("areaName", aMapLocation.getCity());
//                mAllSupplyAndDemandJsonObject.addProperty("version", 1);
//                getPresenter().getTotalPurchaseNeedsAndFarmFieldHarvest("getTotalPurchaseNeedsAndFarmFieldHarvest", mAllSupplyAndDemandJsonObject);
//            } else {
//                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
//                Log.e(TAG, errText);
//            }
//        }
//    }
//
//    @Override
//    public void onDistrictSearched(DistrictResult districtResult) {
//        if (districtResult.getAMapException().getErrorCode() == 1000 && districtResult.getDistrict().size() > 0) {
//            DistrictItem districtItem = districtResult.getDistrict().get(0);
//            LatLng latLng = new LatLng(districtItem.getCenter().getLatitude(), districtItem.getCenter().getLongitude());
//            Log.i(TAG, districtItem.getLevel() + " onDistrictSearched..." + districtItem.getCenter());
//            this.dismissProgress();
//            if ("province".equalsIgnoreCase(districtItem.getLevel())) {
//                changeCameraPosition(latLng, 9, 0, 0, true);
//            } else if ("city".equalsIgnoreCase(districtItem.getLevel())) {
//                changeCameraPosition(latLng, 11, 0, 0, true);
//            } else if ("district".equalsIgnoreCase(districtItem.getLevel())) {
//                changeCameraPosition(latLng, 14, 0, 0, true);
//            }
//        }
//    }
//
//    @Override
//    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
//        LocalWeatherLive weatherlive;
//        if (rCode == 1000) {
//            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
//                weatherlive = weatherLiveResult.getLiveResult();
//                temperatureText.setText(weatherlive.getTemperature() + "℃");
//                pointInfoWindow.notify();
//                Log.i(TAG, weatherlive.getReportTime() + "发布" + ",天气：" + weatherlive.getWeather() + ",温度：" + weatherlive.getTemperature());
//            } else {
//                Log.i(TAG, "weatherLiveResult.." + weatherLiveResult);
//            }
//        } else {
//            Log.i(TAG, "rcode=" + rCode);
//        }
//    }
//
//    @Override
//    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
//
//    }
//}
