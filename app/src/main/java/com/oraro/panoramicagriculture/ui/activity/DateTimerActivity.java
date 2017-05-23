package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.oraro.panoramicagriculture.R;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/21 0021.
 *
 * @author
 */
public class DateTimerActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private String transitionName;
    private Button button;
    private Date parse;
    private String time2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        datePicker = (DatePicker) findViewById(R.id.date_timer);
        button = (Button) findViewById(R.id.button1);
        final Calendar calendar = Calendar.getInstance();
        int year1 = calendar.get(Calendar.YEAR);
        final int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year1, month1, day1, new DatePicker.OnDateChangedListener() {


            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String month = (monthOfYear < 9)?("0" +(monthOfYear + 1)):("" + (monthOfYear + 1));
                String day = (dayOfMonth < 10)?("0" + dayOfMonth):""+dayOfMonth;

                time2 = year + "-" + month + "-" + day;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    parse = simpleDateFormat.parse(time2);
                    parse.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (null != time2) {
                    intent.putExtra("timer2", time2);

                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }


}
