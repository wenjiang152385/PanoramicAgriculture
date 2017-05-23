package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;

/**
 * Created by Administrator on 2017/3/16.
 */
public class FieldMarkerView extends FrameLayout {

    private TextView markerIndex;
    private ImageView markerIcon;

    public FieldMarkerView(Context context) {
        super(context);
    }

    public FieldMarkerView(Context context, int index, int iconRes) {
        this(context);
        initView(index, iconRes);
    }

    private void initView(int index, int iconRes) {
        View view = View.inflate(getContext(), R.layout.view_field_marker, null);
        markerIndex = (TextView) view.findViewById(R.id.field_marker_index);
        markerIcon = (ImageView) view.findViewById(R.id.field_marker_icon);
        addView(view);
        markerIndex.setText(String.valueOf(index));
        markerIcon.setImageResource(iconRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
