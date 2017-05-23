package com.oraro.panoramicagriculture.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.entity.FarmField;
import com.oraro.panoramicagriculture.ui.activity.EditFarmActivity;
import com.oraro.panoramicagriculture.ui.activity.EditPlantActivity;
import com.oraro.panoramicagriculture.ui.activity.FarmActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.text.NumberFormat;

/**
 * Created by Administrator on 2017/4/12.
 */
public class MyLandView extends FrameLayout implements View.OnClickListener {


    private Button textView;

    private FarmField anAcreLand;

    private TextView tv_crops;
    private TextView tv_progress;
    private ProgressBar pr_2;
    private LinearLayout ll_land;
    private TextView text_farm_feild;
    private SimpleDraweeView mItemMylandSimpleDraweeView;
    private boolean ischeck = true;
    private Context mContext;

    private int mState;
    private Double plantday;
    private Double totalday;
    private Double fen;
    private int i;
    private String baifenbi;
    private NumberFormat nf;

    public final int NOTHING = 0;
    public final int SOWING = 1;
    public final int GROWING = 2;
    public final int HARVEST = 3;

    public MyLandView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MyLandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        View itemView = View.inflate(getContext(), R.layout.item_myland, this);
        textView = (Button) itemView.findViewById(R.id.id_num);
        tv_crops = (TextView) itemView.findViewById(R.id.tv_crops);
        tv_progress = (TextView) itemView.findViewById(R.id.tv_progress);
        ll_land = (LinearLayout) itemView.findViewById(R.id.ll_land);
        pr_2 = (ProgressBar) itemView.findViewById(R.id.pr_2);
        text_farm_feild = (TextView) itemView.findViewById(R.id.text_farm_feild);
        mItemMylandSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_myland_simple_drawee_view);
        mItemMylandSimpleDraweeView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.tomato)).build());
        textView.setOnClickListener(this);
    }

    public void setAnAcreLand(FarmField anAcreLand) {
        this.anAcreLand = anAcreLand;
        switch (anAcreLand.getState()) {
            case 0:
                textView.setText("未播种");
                tv_crops.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
                pr_2.setVisibility(View.GONE);
                textView.setBackgroundResource(R.drawable.transparent);
                text_farm_feild.setVisibility(View.GONE);
                // holder.simpleDraweeView.setVisibility(View.GONE);
                break;
            case 1:
                pr_2.setVisibility(View.VISIBLE);
                text_farm_feild.setText(anAcreLand.getFieldName());
                text_farm_feild.setVisibility(View.VISIBLE);
                tv_progress.setVisibility(View.VISIBLE);
                tv_crops.setVisibility(View.VISIBLE);
                tv_crops.setText(anAcreLand.getVfCrops().getByname());
                mItemMylandSimpleDraweeView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.tomato)).build());
                textView.setText("播种");
                textView.setBackgroundResource(R.drawable.transparent);
                plantday = Double.valueOf(anAcreLand.getPlantday());
                totalday = Double.valueOf(anAcreLand.getTotalday());
                fen = (plantday / totalday);
                i = (int) (fen * 100);
                if (plantday > totalday) {
                    tv_progress.setText("100%");
                    pr_2.setProgress(i);
                }

                nf = NumberFormat.getPercentInstance();
                nf.setMinimumFractionDigits(0);
                baifenbi = nf.format(fen);
                LogUtils.e("jw", "anAcreLand.getPlantday()==" + baifenbi + "--" + "....." + fen);
                if (plantday <= totalday) {
                    tv_progress.setText(baifenbi);
                    pr_2.setProgress(i);
                }

                break;
            case 2:
                textView.setText("生长");
                text_farm_feild.setText(anAcreLand.getFieldName());
                text_farm_feild.setVisibility(View.VISIBLE);
                textView.setBackgroundResource(R.drawable.transparent);
                pr_2.setVisibility(View.VISIBLE);
                tv_progress.setVisibility(View.VISIBLE);
                tv_crops.setVisibility(View.VISIBLE);
                mItemMylandSimpleDraweeView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.lettuce)).build());
                LogUtils.e("jw","anAcreLand.getVfCrops()=="+anAcreLand.getVfCrops());
                if (anAcreLand.getVfCrops()!=null){
                    tv_crops.setText(anAcreLand.getVfCrops().getByname());
                }


                Double plantday2 = Double.valueOf(anAcreLand.getPlantday());
                Double totalday2 = Double.valueOf(anAcreLand.getTotalday());
                Double fen = (plantday2 / totalday2);
                int i = (int) (fen * 100);
                if (plantday2 > totalday2) {
                    tv_progress.setText("100%");
                    pr_2.setProgress(i);
                }

                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMinimumFractionDigits(0);
                String baifenbi = nf.format(fen);
                LogUtils.e("jw", "anAcreLand.getPlantday()==" + baifenbi + "--" + "....." + fen);
                if (plantday2 <= totalday2) {
                    tv_progress.setText(baifenbi);
                    pr_2.setProgress(i);
                }

                break;
            case 3:
                textView.setText("收获");
                text_farm_feild.setText(anAcreLand.getFieldName());
                text_farm_feild.setVisibility(View.VISIBLE);
                textView.setBackgroundResource(R.drawable.green);
                pr_2.setVisibility(View.VISIBLE);
                mItemMylandSimpleDraweeView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.pepper)).build());
                tv_progress.setVisibility(View.VISIBLE);
                tv_crops.setVisibility(View.VISIBLE);
                if (anAcreLand.getVfCrops()!=null){
                    tv_crops.setText(anAcreLand.getVfCrops().getByname());
                }

                Double plantday3 = Double.valueOf(anAcreLand.getPlantday());
                Double totalday3 = Double.valueOf(anAcreLand.getTotalday());
                Double fen3 = (plantday3 / totalday3);
                LogUtils.e("jw", "plantday3--" + plantday3 + "--totalday3==" + totalday3);
                int i3 = (int) (fen3 * 100);
                if (plantday3 > totalday3) {
                    tv_progress.setText("100%");
                    pr_2.setProgress(i3);
                }

                NumberFormat nf3 = NumberFormat.getPercentInstance();
                nf3.setMinimumFractionDigits(0);
                String baifenbi3 = nf3.format(fen3);
                LogUtils.e("jw", "anAcreLand.getPlantday()==" + baifenbi3 + "--" + "....." + fen3);
                if (plantday3 <= totalday3) {
                    tv_progress.setText(baifenbi3);
                    pr_2.setProgress(i3);
                }

                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_num:
                if (anAcreLand == null) {
                    Intent intent = new Intent(mContext, EditPlantActivity.class);
                    intent.putExtra("farmId", ((FarmActivity) getContext()).getFarmId());
                    intent.putExtra("farmfield",anAcreLand.getFieldName());
                    LogUtils.e("jw","anAcreLand.getFieldName()1="+anAcreLand.getFieldName());
                    // intent.putExtra("position", getAdapterPosition());
                    mContext.startActivity(intent);
                } else {
                    LogUtils.e("jjww", "anAcreLand.getState()=" + anAcreLand.getState());
                    switch (anAcreLand.getState()) {
                        case 0:
                            Intent intent = new Intent(mContext, EditPlantActivity.class);
                            intent.putExtra("farmId", anAcreLand.getFarmId());
                            // intent.putExtra("position", getAdapterPosition());
                            intent.putExtra("anAcreLandId", anAcreLand.getFieldId());
                            intent.putExtra("farmfield",anAcreLand.getFieldName());
                            LogUtils.e("jw","anAcreLand.getFieldName()2="+anAcreLand.getFieldName());
                            textView.setBackgroundResource(R.drawable.transparent);
                            mContext.startActivity(intent);
                            break;
                        case 1:
                            if (ischeck) {
                                textView.setBackgroundResource(R.drawable.green);
                                Toast.makeText(mContext, "现在是播种期", Toast.LENGTH_SHORT).show();
                                ischeck = false;
                            } else {
                                textView.setBackgroundResource(R.drawable.transparent);
//                                        Toast.makeText(getActivity(), "现在是播种期", Toast.LENGTH_SHORT).show();
                                ischeck = true;
                            }


                            break;
                        case 2:
                            if (ischeck) {
                                textView.setBackgroundResource(R.drawable.green);
                                Toast.makeText(mContext, "现在是生长期", Toast.LENGTH_SHORT).show();
                                ischeck = false;
                            } else {
                                textView.setBackgroundResource(R.drawable.transparent);
                                ischeck = true;
                            }

                            break;
                        case 3:
                            Intent intent1 = new Intent(mContext, EditFarmActivity.class);
                            intent1.putExtra("fieldId", anAcreLand.getFieldId());
                            intent1.putExtra("farmid", anAcreLand.getFarmId());
                            intent1.putExtra("vfname",anAcreLand.getVfCrops().getByname());
                            intent1.putExtra("farmfield",anAcreLand.getFieldName());
                            intent1.putExtra("plantNum",anAcreLand.getPlantNum());
                            intent1.putExtra("expectHarvestNum",anAcreLand.getExpectHarvestNum());
                            intent1.putExtra("currentSowTime",anAcreLand.getCurrentSowTime());
                            intent1.putExtra("currentMatureTime",anAcreLand.getCurrentMatureTime());
                            mContext.startActivity(intent1);
                            break;
                    }
                }
                break;
        }
    }
}
