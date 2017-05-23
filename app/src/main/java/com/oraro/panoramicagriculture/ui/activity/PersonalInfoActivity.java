package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.oraro.panoramicagriculture.presenter.PersonalInfoPresenter;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/2/17.
 */
public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter> implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private RelativeLayout headLayout;
    private SimpleDraweeView mPersonInfoBgImg;
    private SimpleDraweeView userIcon;
    private String userIconPath = null;
    private DaoSession daoSession;

    private TextView userNameTitle;
    private ImageView userSex;
    private TextView userNameText;
    private TextView userIdentityText;
    private TextView userPhoneNumText;
    private TextView mTvUserLevel;

    private EditText userNameEdit;
    private EditText userIdentityEdit;
    private EditText userPhoneNumEdit;
    private Button submitButton;
    private MenuItem editItem;

    private FrameLayout mFlTitleBarLeft;
    private FrameLayout mFlTitleBarRight;
    private TextView mTvTitleBar;
    private ImageView mImgTitleBarRight;

    @Override
    public PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_personal_info);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
        setTitle(null);

        mFlTitleBarLeft = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        mFlTitleBarRight = (FrameLayout) findViewById(R.id.fl_title_bar_right);
        mTvTitleBar = (TextView) findViewById(R.id.tv_title_bar);
        mImgTitleBarRight = (ImageView) findViewById(R.id.ic_title_bar_right);
        mFlTitleBarRight.setVisibility(View.VISIBLE);
        mImgTitleBarRight.setVisibility(View.VISIBLE);

//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        headLayout = (RelativeLayout) findViewById(R.id.head_layout);
        mPersonInfoBgImg = (SimpleDraweeView) findViewById(R.id.person_info_bg_img);
        userIcon = (SimpleDraweeView) findViewById(R.id.user_icon);
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead() == null || PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead().startsWith("content")) {
            userIcon.setImageURI(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
        } else {
            userIcon.setImageURI(Constants.SERVER_ADDRESS + PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
        }
        userIcon.setOnClickListener(this);

        userNameTitle = (TextView) findViewById(R.id.user_name_title);
        userSex = (ImageView) findViewById(R.id.user_sex);
        userNameText = (TextView) findViewById(R.id.tv_user_name);
        userIdentityText = (TextView) findViewById(R.id.tv_user_identity);
        userPhoneNumText = (TextView) findViewById(R.id.tv_user_phone_num);
        mTvUserLevel = (TextView) findViewById(R.id.tv_user_level);
        userNameEdit = (EditText) findViewById(R.id.et_user_name);
        userIdentityEdit = (EditText) findViewById(R.id.et_user_identity);
        userPhoneNumEdit = (EditText) findViewById(R.id.et_user_phone_num);
        submitButton = (Button) findViewById(R.id.btn_submit);

        submitButton.setOnClickListener(this);
        submitButton.setClickable(false);

        mPersonInfoBgImg.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.morentu)).build());

        mFlTitleBarLeft.setOnClickListener(this);
        mFlTitleBarRight.setOnClickListener(this);
        mTvTitleBar.setText("");
        mImgTitleBarRight.setImageResource(R.mipmap.edit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userNameTitle.setText(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getNickName());
        userNameText.setText(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getNickName());
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getIdentityNumber() != null) {
            userIdentityText.setText(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getIdentityNumber());
        }
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getPhoneNumber() != null) {
            userPhoneNumText.setText(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getPhoneNumber());
        }
        mTvUserLevel.setText("" + PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getPoints() + "积分");
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getSex() == 0) {
            userSex.setImageResource(R.mipmap.female_icon);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_bar_left:
                finish();
                break;
            case R.id.fl_title_bar_right:
                userNameText.setVisibility(View.GONE);
                userIdentityText.setVisibility(View.GONE);
                userPhoneNumText.setVisibility(View.GONE);

                userNameEdit.setVisibility(View.VISIBLE);
                userIdentityEdit.setVisibility(View.VISIBLE);
                userPhoneNumEdit.setVisibility(View.VISIBLE);

                userNameEdit.setText(userNameText.getText());
                userNameEdit.setSelection(userNameText.getText().length());
                userIdentityEdit.setText(userIdentityText.getText());
                userPhoneNumEdit.setText(userPhoneNumText.getText());

                submitButton.setClickable(true);
                break;
            case R.id.user_icon:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_submit:
                showProgress();
                userNameTitle.setText(userNameEdit.getText());
                userNameText.setText(userNameEdit.getText());
                userIdentityText.setText(userIdentityText.getText());
                userPhoneNumText.setText(userPhoneNumText.getText());

                userNameText.setVisibility(View.VISIBLE);
                userIdentityText.setVisibility(View.VISIBLE);
                userPhoneNumText.setVisibility(View.VISIBLE);

                userNameEdit.setVisibility(View.GONE);
                userIdentityEdit.setVisibility(View.GONE);
                userPhoneNumEdit.setVisibility(View.GONE);

                submitButton.setClickable(false);
                if (editItem != null)
                    editItem.setVisible(true);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("userId", PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId());
                jsonObject.addProperty("nickName", userNameEdit.getText().toString());
                jsonObject.addProperty("phoneNumber", userPhoneNumEdit.getText().toString());
                jsonObject.addProperty("identityNumber", userIdentityEdit.getText().toString());
                getPresenter().updateUserInfo("userUpdate", jsonObject);
                PanoramicAgricultureApplication.LOCAL_LOGINED_USER.setNickName(userNameEdit.getText().toString());
                PanoramicAgricultureApplication.LOCAL_LOGINED_USER.setPhoneNumber(userPhoneNumEdit.getText().toString());
                PanoramicAgricultureApplication.LOCAL_LOGINED_USER.setIdentityNumber(userIdentityEdit.getText().toString());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personal_info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri userIconUri = data.getData();
            Log.i(TAG, "picture uri=" + userIconUri);
            Bitmap b = null;
            PanoramicAgricultureApplication.LOCAL_LOGINED_USER.setHead(userIconUri.toString());
            Cursor cursor = this.getContentResolver().query(userIconUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            userIconPath = cursor.getString(column_index);
            cursor.close();
            showProgress();
            getPresenter().updataUserIcon(userIconPath);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_user_info:
                userNameText.setVisibility(View.GONE);
                userIdentityText.setVisibility(View.GONE);
                userPhoneNumText.setVisibility(View.GONE);

                userNameEdit.setVisibility(View.VISIBLE);
                userIdentityEdit.setVisibility(View.VISIBLE);
                userPhoneNumEdit.setVisibility(View.VISIBLE);

                userNameEdit.setText(userNameText.getText());
                userNameEdit.setSelection(userNameText.getText().length());
                userIdentityEdit.setText(userIdentityText.getText());
                userPhoneNumEdit.setText(userPhoneNumText.getText());

                item.setVisible(false);
                editItem = item;
                submitButton.setClickable(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateUser2DB() {
        userIcon.setImageURI(PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getHead());
        daoSession.getUserEntityDao().update(PanoramicAgricultureApplication.LOCAL_LOGINED_USER);
    }
}
