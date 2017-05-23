package com.oraro.panoramicagriculture.ui.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.oraro.citypickerview.model.CityModel;
import com.oraro.citypickerview.model.DistrictModel;
import com.oraro.citypickerview.model.ProvinceModel;
import com.oraro.citypickerview.utils.XmlParserHandler;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.Presenter;
import com.oraro.panoramicagriculture.ui.activity.OrdinaryDataActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2017/4/17.
 */
public abstract class BaseDataListFragment<T extends Object, P extends Presenter> extends BaseListFragment<T, P> implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    protected ArrayAdapter<String> spinnerAdapter;
    protected Spinner regionSpinner;
    protected RadioGroup dateRadio;
    protected LinearLayout moreDataLayout;
    protected RadioButton rb_today;
    protected RadioButton rb_yesterday;
    protected RadioButton rb_before_yesterday;
    protected RadioButton rb_more;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List<String> regionList = initProvinceDatas(getActivity(), ((OrdinaryDataActivity) getActivity()).getProvince(), ((OrdinaryDataActivity) getActivity()).getCity());
        LogUtils.i("regionList.." + regionList.size());
        spinnerAdapter.addAll(regionList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_data_list, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        regionSpinner = (Spinner) view.findViewById(R.id.region_spinner);
        dateRadio = (RadioGroup) view.findViewById(R.id.rg_date);
//        moreDataLayout = (LinearLayout) view.findViewById(R.id.id_more);
        regionSpinner.setAdapter(spinnerAdapter);
        regionSpinner.setOnItemSelectedListener(this);
        dateRadio.setOnCheckedChangeListener(this);
//        moreDataLayout.setOnClickListener(this);
        rb_today = (RadioButton) view.findViewById(R.id.rb_today);
        rb_yesterday = (RadioButton) view.findViewById(R.id.rb_yesterday);
        rb_before_yesterday = (RadioButton) view.findViewById(R.id.rb_before_yesterday);
        rb_more= (RadioButton) view.findViewById(R.id.rb_more);


        setSwipeRefreshLayout(mSwipeRefreshLayout);
    }

    private List<String> initProvinceDatas(Context context, String province, String city) {
        List<String> districtListStr = new ArrayList<>();
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区

            if (provinceList != null && !provinceList.isEmpty()) {
                for (ProvinceModel provinceModel : provinceList) {
                    if (provinceModel.getName().equalsIgnoreCase(province)) {
                        List<CityModel> cityList = provinceModel.getCityList();
                        LogUtils.e("city=" + cityList.size() + "");
                        if (cityList != null && !cityList.isEmpty()) {
                            for (CityModel cityModel : cityList) {
                                if (cityModel.getName().equalsIgnoreCase(city)) {
                                    List<DistrictModel> districtList = cityModel.getDistrictList();
                                    LogUtils.e("districtList=" + districtList.size() + "");
                                    for (DistrictModel districtModel : districtList) {
                                        LogUtils.e("districtModel=" + districtModel);
                                        if ("全城".equalsIgnoreCase(districtModel.getName())) {
                                            districtListStr.add("全  城");
                                        } else {
                                            districtListStr.add(districtModel.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            LogUtils.e(e.toString() + "sdfasf");
            e.printStackTrace();
        } finally {

        }
        return districtListStr;
    }
}
