package com.oraro.panoramicagriculture.ui.activity;

import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.RegisterPresenter;

/**
 * Created by Administrator on 2017/2/13.
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener {
    private Button showPasswordButton = null;
    private EditText passwordEdit = null;
    private EditText usernameEdit = null;
    private EditText nicknameEdit = null;
    private EditText phoneNumberEdit = null;
    private EditText identityNumberEdit = null;

    private Button registerButton = null;

    private FrameLayout mFlTitleBarLeft;

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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

        setContentView(R.layout.activity_register);
        mFlTitleBarLeft = (FrameLayout) findViewById(R.id.fl_title_bar_left);
        showPasswordButton = (Button) findViewById(R.id.show_password_button);
        registerButton = (Button) findViewById(R.id.btn_submit);
        mFlTitleBarLeft.setOnClickListener(this);
        showPasswordButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        usernameEdit = (EditText) findViewById(R.id.et_username);
        passwordEdit = (EditText) findViewById(R.id.et_password);
        nicknameEdit = (EditText) findViewById(R.id.et_nickname);
        phoneNumberEdit = (EditText) findViewById(R.id.et_phoneNum);
        identityNumberEdit = (EditText) findViewById(R.id.et_identityNumber);
        passwordEdit.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Log.i("sysout", "beforeTextChanged");
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.i("sysout", "onTextChanged");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().length() > 0) {
                            showPasswordButton.setVisibility(View.VISIBLE);
                        } else {
                            showPasswordButton.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
            case R.id.show_password_button:
                if (passwordEdit.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    passwordEdit.setSelection(passwordEdit.getText().length());
                    showPasswordButton.setBackgroundResource(R.mipmap.not_show_password);
                } else {
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEdit.setSelection(passwordEdit.getText().length());
                    showPasswordButton.setBackgroundResource(R.mipmap.show_password);
                }
                break;
            case R.id.btn_submit:
                if (usernameEdit == null || passwordEdit == null || nicknameEdit == null || phoneNumberEdit == null || identityNumberEdit == null) {
                    break;
                }
                String userName = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String nickName = nicknameEdit.getText().toString();
                String phoneNumber = phoneNumberEdit.getText().toString();
                String identityNumber = identityNumberEdit.getText().toString();
                int userRole = 1;
                switch (((RadioGroup) findViewById(R.id.radioGroupRole)).getCheckedRadioButtonId()) {
                    case R.id.radioFarmer:
                        userRole = 1;
                        break;
                    case R.id.radioBuyer:
                        userRole = 2;
                        break;
                    case R.id.radioDC:
                        userRole = 3;
                        break;
                }
                int sex = ((RadioButton) findViewById(R.id.radioMale)).isChecked() ? 1 : 0;

                if (userName.isEmpty() || password.isEmpty() || nickName.isEmpty() || phoneNumber.isEmpty() || identityNumber.isEmpty()) {
                    break;
                }
                JsonObject params = new JsonObject();
                params.addProperty("userName", userName);
                params.addProperty("password", password);
                params.addProperty("nickName", nickName);
                params.addProperty("phoneNumber", phoneNumber);
                params.addProperty("identityNumber", identityNumber);
                params.addProperty("userRole", userRole);
                params.addProperty("sex", sex);
                getPresenter().test("userAdd", params);

                break;
        }
    }
}
