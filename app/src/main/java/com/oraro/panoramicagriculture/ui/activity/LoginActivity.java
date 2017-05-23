package com.oraro.panoramicagriculture.ui.activity;


import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.igexin.sdk.PushManager;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.LoginPresenter;

/**
 * Created by Administrator on 2017/2/10.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener {
    private Button loginButton = null;
    private Button cancelLoginButton = null;
    private EditText usernameEdit = null;
    private EditText passwordEdit = null;
    private TextInputLayout usernameLayout = null;
    private TextInputLayout passwordLayout = null;

    private FrameLayout mFlTitleBarLeft;
    private FrameLayout mFlTitleBarRight;
    private TextView mTvTitleBar;
    private TextView mTvTitleBarRight;

    private TextView mTvNoRegister;

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
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
        setContentView(R.layout.activity_login);

        mFlTitleBarLeft = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        mFlTitleBarRight = (FrameLayout) findViewById(R.id.fl_title_bar_right);
        mTvTitleBar = (TextView) findViewById(R.id.tv_title_bar);
        mTvTitleBarRight = (TextView) findViewById(R.id.tv_title_bar_right);
        mTvNoRegister = (TextView) findViewById(R.id.tv_no_register);
        mFlTitleBarRight.setVisibility(View.VISIBLE);
        mTvTitleBarRight.setVisibility(View.VISIBLE);

        loginButton = (Button) findViewById(R.id.btn_submit);
        cancelLoginButton = (Button) findViewById(R.id.btn_cancel_login);
        usernameEdit = (EditText) findViewById(R.id.et_username);
        passwordEdit = (EditText) findViewById(R.id.et_password);
        usernameLayout = (TextInputLayout) findViewById(R.id.til_username);
        passwordLayout = (TextInputLayout) findViewById(R.id.til_password);

        mFlTitleBarLeft.setOnClickListener(this);
        mFlTitleBarRight.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        cancelLoginButton.setOnClickListener(this);
        mTvNoRegister.setOnClickListener(this);

        mTvTitleBar.setText(R.string.Login);
        mTvTitleBarRight.setText(R.string.register);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.register_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_bar_left:
                finish();
                break;
            case R.id.fl_title_bar_right:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_submit:
                if (usernameEdit == null || passwordEdit == null) {
                    break;
                }
                String userName = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                Log.i("sysout", userName + "," + password);
                if (userName.isEmpty() || password.isEmpty()) {
                    passwordLayout.setError("用户名或密码不能为空");
                    break;
                }
                JsonObject params = new JsonObject();
                params.addProperty("userName", userName);
                params.addProperty("password", password);
                params.addProperty("clientId", PushManager.getInstance().getClientid(LoginActivity.this));
                getPresenter().test("userLogin", params);

                loginButton.setBackgroundResource(R.drawable.bg_login_btn_pressed);
                cancelLoginButton.setBackgroundResource(R.drawable.bg_login_btn_normal);

                break;
            case R.id.btn_cancel_login:
                loginButton.setBackgroundResource(R.drawable.bg_login_btn_normal);
                cancelLoginButton.setBackgroundResource(R.drawable.bg_login_btn_pressed);
                finish();
                break;
            case R.id.tv_no_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }
}
