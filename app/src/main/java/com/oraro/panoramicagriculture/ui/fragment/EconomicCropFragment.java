package com.oraro.panoramicagriculture.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.presenter.Presenter;

/**
 * Created by Administrator on 2017/2/15 0015.
 *
 * @author
 */
public class EconomicCropFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_crop_second,null);

        return view;
    }
}
