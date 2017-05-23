package com.oraro.panoramicagriculture.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;

import com.oraro.panoramicagriculture.ui.activity.LoginActivity;
import com.oraro.panoramicagriculture.ui.listener.DateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/14.
 */
public class UIManager {

    /**
     * 跳到登录页面
     *
     * @param context
     */
    public static void jump2login(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    public  static void loadDatePicker(Context context,final DateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(year - 1900,monthOfYear,dayOfMonth);
                String dateStr = sdf.format(date);
                dateSetListener.dataChoosed(dateStr);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//        DatePicker datePicker = datePickerDialog.getDatePicker();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
        datePickerDialog.show();
    }
}
