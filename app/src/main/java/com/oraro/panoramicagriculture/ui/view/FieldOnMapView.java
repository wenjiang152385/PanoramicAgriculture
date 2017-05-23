package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.utils.CommonUtils;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by Administrator on 2017/4/1.
 */
public class FieldOnMapView extends LinearLayout {
    private TextView fieldPosition;
    private TextView fieldTitle;
    private TextView fieldProgressText;
    private ProgressBar fieldProgress;

    public FieldOnMapView(Context context) {
        this(context, 1, "default", 0);
    }

    public FieldOnMapView(Context context, int postion, String title, int precent) {
        super(context);
        initView(postion, title, precent);
    }

    private void initView(int postion, String title, int precent) {
        View view = View.inflate(getContext(), R.layout.view_field_on_map, this);
        fieldPosition = (TextView) view.findViewById(R.id.view_field_position);
        fieldTitle = (TextView) view.findViewById(R.id.view_field_title);
        fieldProgress = (ProgressBar) view.findViewById(R.id.view_field_progress);
        fieldProgressText = (TextView) view.findViewById(R.id.tv_field_progress);

        fieldPosition.setText("NO." + postion);
        fieldTitle.setText(title);
        fieldProgressText.setText(precent + "%");
        fieldProgress.setProgress(precent);


        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.setTextSize(CommonUtils.dip2px(getContext(), 18));
        paint.getTextBounds(title, 0, title.length(), rect);
        LayoutParams lp = new LayoutParams(rect.width(), LayoutParams.WRAP_CONTENT);
        fieldTitle.setLayoutParams(lp);

        paint.getTextBounds("NO." + postion, 0, ("NO." + postion).length(), rect);
        lp = new LayoutParams(rect.width(), LayoutParams.WRAP_CONTENT);
        fieldPosition.setLayoutParams(lp);
    }
}
