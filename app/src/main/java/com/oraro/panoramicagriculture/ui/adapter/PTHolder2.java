package com.oraro.panoramicagriculture.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.listener.DateSetListener;
import com.oraro.panoramicagriculture.ui.view.MyButtonView;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.UIManager;

import java.util.Calendar;

/**
 * Created by admin on 2017/4/7.
 */
public class PTHolder2 extends PTHolderSwitcher implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private MyButtonView id_btn_need;
    private MyButtonView id_btn_sale;

    private RadioGroup id_radioGroup;
    private RadioButton id_today;
    private RadioButton id_yesterday;
    private RadioButton id_threedaysago;
    private RadioButton id_more;
    public PTHolder2(Activity activity, PointMainAdapter pointMainAdapter, View itemView) {
        super(activity, pointMainAdapter,itemView);
        setType(TypeGlobal.purchase_page1);
        Calendar cal = Calendar.getInstance();
        setDate(sf.format(cal.getTime()));
        id_btn_need = (MyButtonView) itemView.findViewById(R.id.id_btn_need);
        id_btn_need.SetText("需求");
        id_btn_need.setOnClickListener(this);
        id_btn_sale = (MyButtonView) itemView.findViewById(R.id.id_btn_sale);
        id_btn_sale.SetText("销售");
        id_btn_sale.setOnClickListener(this);
        id_radioGroup = (RadioGroup)itemView.findViewById(R.id.id_radioGroup);
        id_radioGroup.setOnCheckedChangeListener(this);
        id_today = (RadioButton) itemView.findViewById(R.id.id_today);
        id_yesterday = (RadioButton) itemView.findViewById(R.id.id_yesterday);
        id_threedaysago = (RadioButton) itemView.findViewById(R.id.id_threedaysago);

        id_more = (RadioButton) itemView.findViewById(R.id.id_more);
//        id_more.setOnClickListener(this);

        id_btn_need.setHot(true);
        id_btn_sale.setHot(false);
    }

    @Override
    public int switchHolder() {
        LogUtils.e("PTHolder2 switchHolder  ");
        pointMainAdapter.CreateMainAdapter(this);
        return R.layout.item_purchase_point_detail;
    }

    private void onHot(MyButtonView btnHot){
        id_btn_need.setHot(false);
        id_btn_sale.setHot(false);
        btnHot.setHot(true);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String date;
        Calendar cal = Calendar.getInstance();
        switch (checkedId) {
            case R.id.id_today:
                setDateType(DateTypeGlobal.TODAY);
                date = sf.format(cal.getTime());
                setDate(date);
                break;
            case R.id.id_yesterday:
                setDateType(DateTypeGlobal.YESTERDAY);
                cal.add(cal.DATE, -1);
                date = sf.format(cal.getTime());
                setDate(date);
                break;
            case R.id.id_threedaysago:
                setDateType(DateTypeGlobal.BEFORE_YESTERDAY);
                cal.add(cal.DATE, -2);
                date = sf.format(cal.getTime());
                setDate(date);
                break;
            case R.id.id_more:
                LogUtils.e("PTHolder2 more click");
                setDateType(DateTypeGlobal.MORE);
                UIManager.loadDatePicker(activity, new DateSetListener() {
                    @Override
                    public void dataChoosed(String date) {
                        setDate(date);
                        switchHolder();
                    }
                });
        }
        switchHolder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_need:
                setType(TypeGlobal.purchase_page1);
                onHot(id_btn_need);
                switchHolder();
                break;
            case R.id.id_btn_sale:
                setType(TypeGlobal.purchase_page2);
                onHot(id_btn_sale);
                switchHolder();
                break;
//            case R.id.id_more:
//                LogUtils.e("PTHolder2 more click");
//                setDateType(DateTypeGlobal.MORE);
//                id_today.setChecked(false);
//                id_yesterday.setChecked(false);
//                id_threedaysago.setChecked(false);
//                UIManager.loadDatePicker(activity, new DateSetListener() {
//                    @Override
//                    public void dataChoosed(String date) {
//                        setDate(date);
//                        switchHolder();
//                    }
//                });
////                Intent intent = new Intent(activity, MPChartActivity.class);
////                intent.putExtra("pointId", holderData.pointId);
////                if (type == TypeGlobal.purchase_page1){
////                    intent.putExtra("type", Constants.CHART_ROLE_POINT_REQUIRE);
////                }else if (type == TypeGlobal.purchase_page2){
////                    intent.putExtra("type", Constants.CHART_ROLE_POINT_SALE);
////                }
////                activity.startActivityForResult(intent, 100);
//                break;
        }
    }
}
