package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.ChartModel;
import com.oraro.panoramicagriculture.data.entity.ItemView;
import com.oraro.panoramicagriculture.data.entity.MyChartEntry;
import com.oraro.panoramicagriculture.factory.MPChartFactory;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.fragment.DetailChartFragment;
import com.oraro.panoramicagriculture.ui.view.dialog.CustomProgressDialog;
import com.oraro.panoramicagriculture.utils.CommonUtils;


import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
public class ChartPresenter extends Presenter<DetailChartFragment> {
    protected static int mRole_type;
    public List<PieEntry> pieEntries = null;
    private ArrayList<Entry> exceptLineEntries = null;
    private ArrayList<Entry> actualLineEntries = null;
    public MPChartData mpChartData;
    protected long mPointId;
    private CustomProgressDialog mCustomProgressDialog;

    @Override
    public void onUiReady(DetailChartFragment ui) {
        super.onUiReady(ui);
        mRole_type = getUi().getActivity().getIntent().getIntExtra("type", -1);
        mPointId = getUi().getActivity().getIntent().getLongExtra("pointId", -1);
        mpChartData = MPChartFactory.getInstance().switchMPChartData(mRole_type);
        requestPieChartData(getUi().mDataEnd.getText().toString(), mPointId);
    }

    public void requestPieChartData(String dataText, long pointId) {
        JsonObject jsonObject = new JsonObject();
        mpChartData.setMPDataPIEJson(dataText, pointId, jsonObject);
        setChartData(jsonObject, mpChartData, CommonUtils.CHART_TYPE.PIE);
    }

    public void requestLineChartData(long vFCropsId, String startDataText, String endDataText, long pointId) {
        JsonObject jsonObject = new JsonObject();
        mpChartData.setMPDataLineJson(vFCropsId, startDataText, endDataText, pointId, jsonObject);
        setChartData(jsonObject, mpChartData, CommonUtils.CHART_TYPE.LINE);
    }

    public MPChartData getMpChartData() {
        return mpChartData;
    }

    public void setChartData(JsonObject jsonObject, final MPChartData mpChartData, final CommonUtils.CHART_TYPE chart_type) {
        getUi().setChartTitle(mpChartData.getTitle(""));
        showOrDismissDialog(true);
        getUi().mChartItemAdapter.clear();
        String method = "";
        if (chart_type == CommonUtils.CHART_TYPE.PIE) {
            pieEntries = new ArrayList<>();
            method = mpChartData.getPIEMethodName();
            Log.e("zzz", "method = " + method);
        } else if (chart_type == CommonUtils.CHART_TYPE.LINE) {
            exceptLineEntries = new ArrayList<>();
            actualLineEntries = new ArrayList<>();
            method = mpChartData.getLINEMethodName();
        }
        HttpManager.getInstance().test(method, jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("chart", e.toString());
                showOrDismissDialog(false);
            }

            @Override
            public void onNext(Object o) {
                Log.e("chart", o.toString());
                showOrDismissDialog(false);
                List<ItemView> itemViews = new ArrayList<>();
                ChartModel chartModel = mpChartData.parserJsonData(o);
                double totalNum = chartModel.getTotalNum();
                for (int i = 0; i < chartModel.getMyExceptEntries().size(); i++) {
                    MyChartEntry myExceptEntry = chartModel.getMyExceptEntries().get(i);
                    MyChartEntry myActualEntry = chartModel.getMyActualEntries().get(i);

                    if (chart_type == CommonUtils.CHART_TYPE.PIE) {
                        pieEntries.add(getPieChartEntry(myExceptEntry, totalNum));
                    } else if (chart_type == CommonUtils.CHART_TYPE.LINE) {
                        exceptLineEntries.add(getLineEntry(i, ((Double) myExceptEntry.getNum()).floatValue()));
                        actualLineEntries.add(getLineEntry(i, ((Double) myActualEntry.getNum()).floatValue()));
                    }

                    ItemView itemView = new ItemView();
                    itemView.setName(myExceptEntry.getLabel());
                    itemView.setmId(myExceptEntry.getVfCropsId());
//                    itemView.setNumber(mpChartData.getDataText(myExceptEntry.getNum(), myActualEntry.getNum()));
                    itemView.setPie(CommonUtils.numberFormat(myExceptEntry.getNum() / totalNum * 100) + "%");
                    itemView.seteNumber(myExceptEntry.getNum() + "");
                    itemView.setrNumber(myActualEntry.getNum() + "");
                    itemView.setDate(myExceptEntry.getDateText() + "");
                    itemView.setmType(0);
                    itemViews.add(itemView);
                }

                if (chart_type == CommonUtils.CHART_TYPE.PIE) {
                    if (pieEntries.size() > 0 && totalNum > 0) {
                        getUi().setChartTitle(mpChartData.getTitle(""));
                        getUi().getPieChart().setPieData(pieEntries);
                        getUi().mChartItemAdapter.addItems(itemViews);
                    }
                } else if (chart_type == CommonUtils.CHART_TYPE.LINE) {
                    getUi().getLineChart().setLineData(exceptLineEntries, actualLineEntries,
                            mpChartData.getLegend(0), mpChartData.getLegend(1), itemViews);
                    getUi().mChartItemAdapter.addItems(itemViews);
                }


            }
        });
    }

    private PieEntry getPieChartEntry(MyChartEntry myEntry, double totalNum) {
        PieEntry pieEntry = new PieEntry((float) (myEntry.getNum() / totalNum), myEntry.getLabel());
        return pieEntry;
    }

    private Entry getLineEntry(int index, float num) {
        Entry lineEntry = new Entry(index, num);
        return lineEntry;
    }


    private void showOrDismissDialog(boolean flag) {
        if (null == mCustomProgressDialog) {
            mCustomProgressDialog = new CustomProgressDialog(getUi().getActivity());
        }
        if (flag) {
            mCustomProgressDialog.setMessage("正在加载...");
            mCustomProgressDialog.show();
        } else {
            if (null != mCustomProgressDialog)
                mCustomProgressDialog.dismiss();
        }
    }

    public int getIndexOfAdapter(Entry entry, CommonUtils.CHART_TYPE type) {
        if (type == CommonUtils.CHART_TYPE.PIE) {
            return pieEntries.indexOf(entry);
        }
        if (type == CommonUtils.CHART_TYPE.LINE) {
            int index = -1;
            index = exceptLineEntries.indexOf(entry);
            if (index == -1) {
                index = actualLineEntries.indexOf(entry);
            }
            return index;
        }

        return -1;
    }


}
