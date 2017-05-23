package com.oraro.panoramicagriculture.presenter;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.ItemView;
import com.oraro.panoramicagriculture.data.entity.PurchaseNeeds;
import com.oraro.panoramicagriculture.data.entity.PurchaseNeedsChart;
import com.oraro.panoramicagriculture.data.entity.RegionSaleInfo;
import com.oraro.panoramicagriculture.data.entity.VFCrops;
import com.oraro.panoramicagriculture.http.HttpManager;
import com.oraro.panoramicagriculture.ui.fragment.DetailChartFragment;
import com.oraro.panoramicagriculture.ui.fragment.DetailFragment;
import com.oraro.panoramicagriculture.ui.view.chart.MyPieChart;
import com.oraro.panoramicagriculture.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class DetailChartPresenter extends Presenter<DetailChartFragment> {
    public final static boolean DEBUG = false;

    @Override
    public void onUiReady(DetailChartFragment ui) {
        super.onUiReady(ui);
//        Log.e("wjq","text = " + ui.mDataEnd.getText().toString());
        if (DEBUG) {
            setTestPieChartData(4, 100);
        } else {
//            setPieChartData(ui.mDataEnd.getText().toString(), ui.mType);
            if (getUi().mType == Constants.CHART_ROLE_PURCHASER) {
                setPieChartDataInCity(ui.mDataEnd.getText().toString());
            } else if (getUi().mType == Constants.CHART_ROLE_REQUIRE) {
                setPieChartDataNeeds(ui.mDataEnd.getText().toString());
            } else if (getUi().mType == Constants.CHART_ROLE_FARMER) {
                Log.e("wjq","ui.mDataEnd.getText().toString() = " + ui.mDataEnd.getText().toString());
                setPieChartDataFarm(ui.mDataEnd.getText().toString());
            } else if (getUi().mType == Constants.CHART_ROLE_POINT_SALE) {
                setPieChartDataById(ui.mDataEnd.getText().toString(), 32);
            }
        }
    }


    public void setFarmLineChartData(String StartDate, String EndDate, long vfcropsId) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("vfcropsId", vfcropsId);
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("startDate", StartDate);
        jsonObject.addProperty("endDate", EndDate);

        HttpManager.getInstance().test("getChartByHarvestAndAreaAndStartDateAndEndDateAndVFcropsId", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wjq", e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o.toString());
                float largestNum = 0;
                float smallestNum = 0;
                ArrayList<Entry> values1 = new ArrayList<Entry>();
                ArrayList<Entry> values2 = new ArrayList<Entry>();
                List<com.oraro.panoramicagriculture.data.entity.ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                for (int i = 0; i < data.size(); i++) {
                    float largerNum = 0;
                    float smallerNum = 0;
                    LinkedTreeMap entity = data.get(i);
                    String dateText = (String) entity.get("expectHarvestTime");
                    float expectHarvestNum = ((Double) entity.get("expectHarvestNum")).floatValue();
                    float actualHarvestNum = ((Double) entity.get("actualHarvestNum")).floatValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");

                    Entry lineEntry1 = new Entry(i, expectHarvestNum);
                    Entry lineEntry2 = new Entry(i, actualHarvestNum);
                    values1.add(lineEntry1);
                    values2.add(lineEntry2);
                    ItemView itemView1 = new ItemView();
                    itemView1.setName(label);
                    if (getUi().mType == Constants.ROLE_FARMER) {
                        itemView1.setNumber("预期收获: " + (int) expectHarvestNum + "\n" + "实际收获: " + (int) actualHarvestNum);
                    } else if (getUi().mType == Constants.ROLE_PURCHASER) {
                        itemView1.setNumber("销售: " + (int) expectHarvestNum);
                    } else if (getUi().mType == Constants.CHART_ROLE_REQUIRE) {
                        itemView1.setNumber("需求: " + (int) expectHarvestNum);
                    }
                    itemView1.setDate(dateText);
//                    itemViews.add(itemView1);


                    if (i == 0) {
                        smallestNum = smallerNum;
                        String city = (String) entity.get("city");
                        getUi().setChartTitle(city + label);
                    }
                    if (i == data.size() - 1) {
                    }

                    largerNum = expectHarvestNum >= actualHarvestNum ? expectHarvestNum : actualHarvestNum;
                    smallerNum = actualHarvestNum >= actualHarvestNum ? actualHarvestNum : actualHarvestNum;

                    largestNum = largestNum >= largerNum ? largestNum : largerNum;
                    smallestNum = smallestNum >= smallerNum ? smallerNum : smallestNum;

                }
                getUi().mChartItemAdapter.addItems(itemViews);
            }
        });
    }

    public void setSaleLineChartDataInCity(String StartDate, String EndDate, long vfcropsId) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("vfcropsId", vfcropsId);
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("startDate", StartDate);
        jsonObject.addProperty("endDate", EndDate);

        HttpManager.getInstance().test("getChartSalesDataByAreaAndStartDateAndEndDateAndVFcropsId", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o.toString());
                ArrayList<Entry> values1 = new ArrayList<Entry>();
                ArrayList<Entry> values2 = new ArrayList<Entry>();
                List<com.oraro.panoramicagriculture.data.entity.ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                float largestNum = 0;
                float smallestNum = 0;
                for (int i = 0; i < data.size(); i++) {

                    LinkedTreeMap entity = data.get(i);
                    String dateText = (String) entity.get("date");
                    float saleNumTotal = ((Double) entity.get("saleNumTotal")).floatValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    int day = Integer.parseInt(dateText.split("-")[2]);
                    int month = Integer.parseInt(dateText.split("-")[1]);
                    Entry lineEntry1 = new Entry(i, saleNumTotal);
                    values1.add(lineEntry1);
                    ItemView itemView1 = new ItemView();
                    itemView1.setName(label);
                    if (getUi().mType == Constants.ROLE_FARMER) {
                        itemView1.setNumber("收获: " + (int) saleNumTotal);
                    } else if (getUi().mType == Constants.ROLE_PURCHASER) {
                        itemView1.setNumber("销售: " + (int) saleNumTotal);
                    } else if (getUi().mType == Constants.CHART_ROLE_REQUIRE) {
                        itemView1.setNumber("需求: " + (int) saleNumTotal);
                    }
                    itemView1.setDate(dateText);
//                    itemViews.add(itemView1);


                    if (i == 0) {
                        smallestNum = saleNumTotal;
                        String city = (String) entity.get("city");
                        getUi().setChartTitle(city + label + month + "月份");
                    }

                    largestNum = largestNum > saleNumTotal ? largestNum : saleNumTotal;

                    smallestNum = smallestNum < saleNumTotal ? smallestNum : saleNumTotal;
                    if (i == data.size() - 1) {
                    }

                }
                getUi().mChartItemAdapter.addItems(itemViews);
            }
        });
    }

    public void setLineChartDataInId(String StartDate, String EndDate, long vfCropsId) {
        getUi().mChartItemAdapter.clear();

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("purchasingPointId", 32);
        jsonObject.addProperty("vfcropsId", vfCropsId);
        jsonObject.addProperty("startDate", StartDate);
        jsonObject.addProperty("endDate", EndDate);


        HttpManager.getInstance().test("getChartByStartDateAndEndDateAndVFcropsId", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", "i = " + o.toString());
                float largestNum = 0;
                float smallestNum = 0;
                ArrayList<Entry> values1 = new ArrayList<Entry>();
                ArrayList<Entry> values2 = new ArrayList<Entry>();
                List<com.oraro.panoramicagriculture.data.entity.ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                for (int i = 0; i < data.size(); i++) {
                    float largerNum = 0;
                    float smallerNum = 0;
                    LinkedTreeMap entity = data.get(i);
                    String dateText = (String) entity.get("date");
                    String city = (String) entity.get("city");
                    float needsNum = ((Double) entity.get("needsNum")).floatValue();
                    float actualNum = ((Double) entity.get("actualNum")).floatValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    int day = Integer.parseInt(dateText.split("-")[2]);
                    int month = Integer.parseInt(dateText.split("-")[1]);
                    Entry lineEntry1 = new Entry(i, needsNum);
                    Entry lineEntry2 = new Entry(i, actualNum);
                    values1.add(lineEntry1);
                    values2.add(lineEntry2);
                    ItemView itemView1 = new ItemView();
                    itemView1.setName(label);
                    itemView1.setNumber("预计需求: " + needsNum + "\n" + "实际需求: " + actualNum);
                    itemView1.setDate(dateText);
//                    itemViews.add(itemView1);


                    if (i == 0) {
                        smallestNum = smallerNum;
                        getUi().setChartTitle(city + label);
                    }
                    if (i == data.size() - 1) {
                    }

                    largerNum = actualNum >= needsNum ? actualNum : needsNum;
                    smallerNum = actualNum >= needsNum ? needsNum : actualNum;

                    largestNum = largestNum >= largerNum ? largestNum : largerNum;
                    smallestNum = smallestNum >= smallerNum ? smallerNum : smallestNum;

                }
                getUi().mChartItemAdapter.addItems(itemViews);

            }
        });
    }

    public void setLineChartDataInCity(String StartDate, String EndDate, long vfcropsId) {
        getUi().mChartItemAdapter.clear();

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("vfcropsId", vfcropsId);
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("startDate", StartDate);
        jsonObject.addProperty("endDate", EndDate);

        HttpManager.getInstance().test("getChartPurchaseNeedsByAreaAndStartDateAndEndDateAndVFcropsId", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wjq", e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o.toString());
                float largestNum = 0;
                float smallestNum = 0;
                ArrayList<Entry> values1 = new ArrayList<Entry>();
                ArrayList<Entry> values2 = new ArrayList<Entry>();
                List<com.oraro.panoramicagriculture.data.entity.ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                for (int i = 0; i < data.size(); i++) {

                    LinkedTreeMap entity = data.get(i);
                    String dateText = (String) entity.get("date");
                    String city = (String) entity.get("city");
                    float totalPurchaseNeeds = ((Double) entity.get("totalPurchaseNeeds")).floatValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    int day = Integer.parseInt(dateText.split("-")[2]);
                    int month = Integer.parseInt(dateText.split("-")[1]);
                    Entry lineEntry1 = new Entry(i, totalPurchaseNeeds);
                    values1.add(lineEntry1);
                    ItemView itemView1 = new ItemView();
                    itemView1.setName(label);
                    if (getUi().mType == Constants.ROLE_FARMER) {
                        itemView1.setNumber("收获: " + (int) totalPurchaseNeeds);
                    } else if (getUi().mType == Constants.ROLE_PURCHASER) {
                        itemView1.setNumber("销售: " + (int) totalPurchaseNeeds);
                    } else if (getUi().mType == Constants.CHART_ROLE_REQUIRE) {
                        itemView1.setNumber("需求: " + (int) totalPurchaseNeeds);
                    }
                    itemView1.setDate(dateText);
//                    itemViews.add(itemView1);

                    if (i == 0) {
                        smallestNum = totalPurchaseNeeds;
                        getUi().setChartTitle(city + label);
                    }
                    largestNum = largestNum > totalPurchaseNeeds ? largestNum : totalPurchaseNeeds;
                    smallestNum = smallestNum < totalPurchaseNeeds ? smallestNum : totalPurchaseNeeds;


                }
                getUi().mChartItemAdapter.addItems(itemViews);
            }
        });
    }


    public void setLineChartData(String startDate, String endDate, final int type) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("purchasingPointId", 1);
        jsonObject.addProperty("vfcropsId", 1);
        jsonObject.addProperty("startDate", /*startDate*/"2017-03-12");
        jsonObject.addProperty("endDate", /*endDate*/"2017-03-15");
        jsonObject.addProperty("type", type);
        HttpManager.getInstance().test("getChartByStartDateAndEndDateAndVFcropsId", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wjq", e + "");
            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o + "");
                getUi().mChartItemAdapter.clear();
                float largestNum = 0;
                float smallestNum = 0;
                float startDate = 0;
                float endDate = 0;
                ArrayList<Entry> values1 = new ArrayList<Entry>();
                ArrayList<Entry> values2 = new ArrayList<Entry>();
                List<com.oraro.panoramicagriculture.data.entity.ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                for (int i = 0; i < data.size(); i++) {
                    float largerNum = 0;
                    float smallerNum = 0;
                    LinkedTreeMap entity = data.get(i);
                    String dateText = (String) entity.get("date");
                    int date = Integer.parseInt(dateText.split("-")[2]);
                    float needsNum = ((Double) entity.get("needsNum")).floatValue();
                    float actualNum = ((Double) entity.get("actualNum")).floatValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    ItemView itemView1 = new ItemView();
                    itemView1.setName(label);
                    if (getUi().mType == Constants.ROLE_FARMER) {
                        itemView1.setNumber("预计收获: " + needsNum + "\n" + "实际收获: " + actualNum);
                    } else if (getUi().mType == Constants.ROLE_PURCHASER) {
                        itemView1.setNumber("预计销售: " + needsNum + "\n" + "实际销售: " + actualNum);
                    }
                    itemView1.setDate(dateText);
//                    itemViews.add(itemView1);


                    Entry lineEntry1 = new Entry(date, needsNum);
                    Entry lineEntry2 = new Entry(date, actualNum);
                    values1.add(lineEntry1);
                    values2.add(lineEntry2);

                    largerNum = actualNum >= needsNum ? actualNum : needsNum;
                    smallerNum = actualNum >= needsNum ? needsNum : actualNum;
                    if (i == 0) {
                        smallestNum = smallerNum;
                        startDate = date;
                    }
                    if (i == data.size() - 1) {
                        endDate = date;
                    }
                    largestNum = largestNum >= largerNum ? largestNum : largerNum;
                    smallestNum = smallestNum >= smallerNum ? smallerNum : smallestNum;

                }

                getUi().mChartItemAdapter.addItems(itemViews);

            }
        });

    }

    public void setTestLineChartData(int count, float range, String label) {
        getUi().mChartItemAdapter.clear();
        ArrayList<Entry> values1 = new ArrayList<Entry>();
        ArrayList<Entry> values2 = new ArrayList<Entry>();
        List<com.oraro.panoramicagriculture.data.entity.ItemView> itemViews = new ArrayList<>();
        for (int i = 10; i < count + 11; i++) {

            float needsNum = (float) (Math.random() * range) + 3;
            values1.add(new Entry(i, needsNum));

            float actualNum = (float) (Math.random() * range) + 3;
            values2.add(new Entry(i, actualNum));

            ItemView itemView1 = new ItemView();
            itemView1.setName(label);
            if (getUi().mType == Constants.ROLE_FARMER) {
                itemView1.setNumber("预计收获: " + (int) needsNum + "\n" + "实际收获: " + (int) actualNum);
            } else if (getUi().mType == Constants.ROLE_PURCHASER) {
                itemView1.setNumber("预计销售: " + (int) needsNum + "\n" + "实际销售: " + (int) actualNum);
            }
            itemView1.setDate("2017 -04 -" + i);
//            itemViews.add(itemView1);

        }

        getUi().mChartItemAdapter.addItems(itemViews);
    }


    public void setPieChartDataFarm(String date) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("date", date);

        HttpManager.getInstance().test("getChartByHarvestAndAreaAndDate", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o.toString());
                List<PieEntry> pieEntries = new ArrayList<>();
                List<ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                double num = 0;
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    if (i == 0) {
                        String cityName = (String) entity.get("city");
                        getUi().setChartTitle(cityName);
                    }
                    double expectHarvestNum = (Double) entity.get("expectHarvestNum");
                    num += expectHarvestNum;
                }
                Log.e("dy", "num = " + num);
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    double expectHarvestNum = (Double) entity.get("expectHarvestNum");
                    long vfCropsId = ((Double) entity.get("vfcropsId")).longValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    String dateText = (String) entity.get("expectHarvestTime");
                    PieEntry pieEntry = new PieEntry((float) (expectHarvestNum / num), label);
                    pieEntries.add(pieEntry);

                    ItemView itemView = new ItemView();
                    itemView.setName(label);
                    itemView.setmId(vfCropsId);
                    itemView.setNumber(expectHarvestNum + "");
                    itemView.setDate(dateText + "");
                    itemViews.add(itemView);
                }

                if (pieEntries.size() > 0 && num > 0) {
                    getUi().getPieChart().setPieData(pieEntries);
                    getUi().mChartItemAdapter.addItems(itemViews);
                }
            }
        });
    }

    public void setPieChartDataNeeds(final String date) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("type", 1);//1是城市2是区县
        jsonObject.addProperty("typeTime", 1);
        jsonObject.addProperty("typeArea", 1);
        jsonObject.addProperty("date", date);

        HttpManager.getInstance().test("getPurchaseNeeds", jsonObject, new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o.toString());
                List<PieEntry> pieEntries = new ArrayList<>();
                List<ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                double num = 0;
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    if (i == 0) {
                        String cityName = (String) entity.get("city");
                        getUi().setChartTitle(cityName);
                    }
                    double saleNumTotal = (Double) entity.get("totalPurchaseNeeds");
                    num += saleNumTotal;
                }

                Log.e("dy", "num = " + num);
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    double saleNumTotal = (Double) entity.get("totalPurchaseNeeds");
                    long vfCropsId = ((Double) entity.get("vfcropsId")).longValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    PieEntry pieEntry = new PieEntry((float) (saleNumTotal / num), label);
                    pieEntries.add(pieEntry);

                    ItemView itemView = new ItemView();
                    itemView.setName(label);
                    itemView.setmId(vfCropsId);
                    itemView.setNumber(saleNumTotal + "");
                    itemView.setDate(date);
                    itemViews.add(itemView);
                }

                if (pieEntries.size() > 0) {
                    getUi().getPieChart().setPieData(pieEntries);
                    getUi().mChartItemAdapter.addItems(itemViews);
                }


            }
        });
    }

    public void setPieChartDataById(final String date, long id) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        float startDate = 0;
        float endDate = 0;
        jsonObject.addProperty("purchasingPointId", 32);
        jsonObject.addProperty("date", date);

        HttpManager.getInstance().test("getPurchaseNeedsByDate", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", "0 = " + o.toString());
                List<PieEntry> pieEntries = new ArrayList<>();
                List<ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                double num = 0;
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    if (i == 0) {
                        String district = (String) entity.get("district");
                        getUi().setChartTitle(district);
                    }
                    double needsNum = (Double) entity.get("needsNum");
                    num += needsNum;
                }
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    double needsNum = (Double) entity.get("needsNum");
                    long vfCropsId = ((Double) entity.get("vfcropsId")).longValue();
                    Log.e("dy", "PIEvfCropsId = " + vfCropsId);
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    PieEntry pieEntry = new PieEntry((float) (needsNum / num), label);
                    pieEntries.add(pieEntry);

                    ItemView itemView = new ItemView();
                    itemView.setmId(vfCropsId);
                    itemView.setName(label);
                    itemView.setNumber((int) (needsNum) + "");
                    itemView.setDate(date);
                    itemViews.add(itemView);
                }

                if (pieEntries.size() > 0) {
                    getUi().getPieChart().setPieData(pieEntries);
                    getUi().mChartItemAdapter.addItems(itemViews);
                }

            }
        });

    }

    public void setPieChartDataInCity(final String date) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("areaName", "南京市");
        jsonObject.addProperty("type", 1);//1是城市2是区县
        jsonObject.addProperty("typeArea", 1);//1是城市2是区县
        jsonObject.addProperty("typeTime", "1");
        jsonObject.addProperty("date", date);
        HttpManager.getInstance().test("getSalesData", jsonObject, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wjq", e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e("wjq", o.toString());
                List<PieEntry> pieEntries = new ArrayList<>();
                List<ItemView> itemViews = new ArrayList<>();
                List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                double num = 0;
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    if (i == 0) {
                        String cityName = (String) entity.get("city");
                        getUi().setChartTitle(cityName);
                    }
                    double saleNumTotal = (Double) entity.get("saleNumTotal");
                    num += saleNumTotal;
                }
                for (int i = 0; i < data.size(); i++) {
                    LinkedTreeMap entity = data.get(i);
                    double saleNumTotal = (Double) entity.get("saleNumTotal");
                    long vfCropsId = ((Double) entity.get("vfcropsId")).longValue();
                    String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                    PieEntry pieEntry = new PieEntry((float) (saleNumTotal / num), label);
                    pieEntries.add(pieEntry);

                    ItemView itemView = new ItemView();
                    itemView.setmId(vfCropsId);
                    itemView.setName(label);
                    itemView.setNumber(saleNumTotal + "");
                    itemView.setDate(date);
                    itemViews.add(itemView);
                }

                if (pieEntries.size() > 0) {
                    getUi().getPieChart().setPieData(pieEntries);
                    getUi().mChartItemAdapter.addItems(itemViews);
                }

            }
        });
    }

    public void setPieChartData(String dateText, final int type) {
        getUi().mChartItemAdapter.clear();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(type == Constants.ROLE_FARMER ? "farmId" : "purchasingPointId", type == Constants.ROLE_FARMER ? 41 : 1);
        jsonObject.addProperty("startDate", dateText);
        jsonObject.addProperty("type", type);

        HttpManager.getInstance().test("getChartByDate", jsonObject, new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("wjq", "e = " + e);

                    }

                    @Override
                    public void onNext(Object o) {
                        getUi().mChartItemAdapter.clear();
                        Log.e("wjq", o.toString());
                        List<PieEntry> pieEntries = new ArrayList<>();
                        List<ItemView> itemViews = new ArrayList<>();
                        List<LinkedTreeMap> data = (List<LinkedTreeMap>) ((LinkedTreeMap) o).get("data");
                        for (int i = 0; i < data.size(); i++) {
                            LinkedTreeMap entity = data.get(i);
                            if (Constants.ROLE_PURCHASER == type) {
                                Double totalNum = (Double) entity.get("sumTotalExpectNeedsNum");
                                Double value = (Double) entity.get("sumNeedsNum");
                                String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");

                                PieEntry pieEntry = new PieEntry((float) (value / totalNum), label);
                                pieEntries.add(pieEntry);
                            } else if (Constants.ROLE_FARMER == type) {
                                Double totalNum = (Double) entity.get("sumTotalExpectHarvestNum");
                                Double value = (Double) entity.get("sumHarvestNum");
                                String label = (String) ((LinkedTreeMap) entity.get("vfcrops")).get("vfname");
                                String date = (String) entity.get("expectHarvestTime");
                                PieEntry pieEntry = new PieEntry((float) (value / totalNum), label);
                                ItemView itemView = new ItemView();
                                itemView.setName(label);
                                itemView.setNumber(value + "");
                                itemView.setDate(date);
                                itemViews.add(itemView);

                                pieEntries.add(pieEntry);
                            }
                        }
                        if (pieEntries.size() > 0) {
                            getUi().getPieChart().setPieData(pieEntries);
                            getUi().mChartItemAdapter.addItems(itemViews);
                        }
                    }
                }

        );
    }

    public void setTestPieChartData(int count, float range) {
        getUi().mChartItemAdapter.clear();
        List<ItemView> itemViews = new ArrayList<>();
        String[] mParties = {"土豆", "番茄", "萝卜", "茄子", "韭菜"};

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float mult = (float) (range * Math.random() + 1);
            pieEntries.add(new PieEntry((mult / range), mParties[i % mParties.length]));
            ItemView itemView = new ItemView();
            itemView.setName(mParties[i % mParties.length]);
            if (getUi().mType == Constants.ROLE_FARMER) {
                itemView.setNumber("收获: " + (int) mult);
            } else if (getUi().mType == Constants.ROLE_PURCHASER) {
                itemView.setNumber("销售: " + (int) mult);
            }
            itemView.setDate("");
            itemViews.add(itemView);
        }
        getUi().getPieChart().setPieData(pieEntries);
        getUi().mChartItemAdapter.addItems(itemViews);
    }


    public class ItemView {
        String name;
        String date;
        String number;
        int mType;
        long mId;

        public long getmId() {
            return mId;
        }

        public void setmId(long mId) {
            this.mId = mId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getmType() {
            return mType;
        }

        public void setmType(int mType) {
            this.mType = mType;
        }
    }
}
