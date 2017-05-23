package com.oraro.panoramicagriculture.ui.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.data.dao.CropsDao;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.VFCrops;
import com.oraro.panoramicagriculture.presenter.CropsPresenter;
import com.oraro.panoramicagriculture.ui.activity.CropsDialogActivity;
import com.oraro.panoramicagriculture.ui.listener.LeftItemListener;
import com.oraro.panoramicagriculture.ui.view.MyGridView;
import com.oraro.panoramicagriculture.ui.view.anim.AddCartAnimation;
import com.oraro.panoramicagriculture.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/13 0013.
 *
 * @author
 */
public class CropsFragment extends BaseFragment<CropsPresenter> {
    private static DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
    private ArrayList<VFCrops> cropsList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView_left;
    private CropsDao cropsDao;
    private MyLeftAdapter myleftAdapter;
    private LeftItemListener leftItemListener;
    public final static String CROPNAME = "cropname";
    private int[] imageview1 = new int[]{R.mipmap.doulei};
    //    private int[] imageview2 = new int[]{ R.mipmap.douleitubiao, R.mipmap.shucaishuiguo};
//    private String[] strings1 = new String[]{"蔬菜作物", "水果作物"};
//    private String[] strings2 = new String[]{"蔬菜作物是人类生活中必不可少的一部分", "水果类作物是人类补充维生素的重要部分"};
    private ImageView imageView;
    private RelativeLayout relative_parent;
    private MyGridViewAdapter myGridViewAdapter;

    public void setRightData(int currentClickPosition, List<VFCrops> cropsList) {
        Log.e("jw", "setright..." + cropsList.size());

        myleftAdapter.getMap().put(currentClickPosition, cropsList);
        myleftAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public CropsPresenter createPresenter() {
        return new CropsPresenter();
    }

    @Override
    public BaseFragment getUi() {
        return this;
    }

    @Override
    public void initView() {
        relative_parent = (RelativeLayout) view.findViewById(R.id.relative_parent);
        recyclerView_left = (RecyclerView) view.findViewById(R.id.list_left);
        imageView = (ImageView) view.findViewById(R.id.iv_save);
        myleftAdapter = new MyLeftAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_left.setLayoutManager(linearLayoutManager);
        recyclerView_left.setAdapter(myleftAdapter);
        myGridViewAdapter = new MyGridViewAdapter();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type1", "粮食作物");
        showProgress();
        getPresenter().requestCropsData(0, "getVFcrops", jsonObject);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jww", "mmmmmmm");
                Intent intent=new Intent();
                Bundle bundle = new Bundle();
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<Integer> ids = new ArrayList<>();

                for (int i = 0; i < cropsList.size(); i++) {
                    String name = cropsList.get(i).getByname();
                    int cropid = new Long((cropsList.get(i).getVfid())).intValue();
                    names.add(name);
                    ids.add(cropid);

                }
                bundle.putStringArrayList("names", names);
                bundle.putIntegerArrayList("ids", ids);
                intent.putExtras(bundle);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(getActivity(), CropsDialogActivity.class);
//                ArrayList<String> names = new ArrayList<String>();
//                ArrayList<Integer> ids = new ArrayList<Integer>();
//                for (int i = 0; i < cropsList.size(); i++) {
//                    String name = cropsList.get(i).getVfname();
//                    int cropid = new Long(cropsList.get(i).getVfid()).intValue();
//                    names.add(name);
//                    ids.add(cropid);
//                }
//                bundle.putStringArrayList("name", names);
//                bundle.putIntegerArrayList("id", ids);
//                intent.putExtras(bundle);
//                getActivity().startActivityForResult(intent, 1);

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_crops_first, null);
        return view;
    }

    class MyGridViewAdapter extends BaseAdapter {

        public List<VFCrops> gridlist = new ArrayList<>();

        public void setGridviewData(List<VFCrops> list) {
            Log.i("sysout", "setGridviewData.." + list.size());
            gridlist = list;
            Log.i("sysout", "setGridviewData.." + gridlist.size());
            notifyDataSetChanged();
            Log.i("sysout", "setGridviewData.." + gridlist.size());

        }

        public List<VFCrops> getGridviewData() {
            return gridlist;
        }

        @Override
        public int getCount() {
            LogUtils.e("jw", "gridlist.size()==" + gridlist.size() + "-----" + this);
            return gridlist.size();
        }

        @Override
        public Object getItem(int position) {
            return gridlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            MyGridViewHolder myGridViewHolder = null;
            Log.i("sysout", "getView.." + position);
            if (convertView == null) {
                myGridViewHolder = new MyGridViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.gridview_button, null);
                myGridViewHolder.textView = (TextView) convertView.findViewById(R.id.button_1);
                myGridViewHolder.ll_bt = (LinearLayout) convertView.findViewById(R.id.ll_bt);
                convertView.setTag(myGridViewHolder);
            } else {
                myGridViewHolder = (MyGridViewHolder) convertView.getTag();

            }
            final VFCrops crops = gridlist.get(position);
            myGridViewHolder.textView.setText(crops.getByname());
            final MyGridViewHolder finalMyGridViewHolder = myGridViewHolder;
            final MyGridViewHolder finalMyGridViewHolder1 = myGridViewHolder;
            final MyGridViewHolder finalMyGridViewHolder3 = myGridViewHolder;
            myGridViewHolder.ll_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("jww", "textView.==" + position);
                    // Log.e("jww","ischeck=="+ischeck);
                    if (finalMyGridViewHolder3.ischeck) {

                        cropsList.add(crops);
                        finalMyGridViewHolder.ll_bt.setBackgroundResource(R.drawable.kuang_huang);

                        finalMyGridViewHolder1.ll_bt.setDrawingCacheEnabled(true);
                        Bitmap bitmap = finalMyGridViewHolder1.ll_bt.getDrawingCache();

                        AddCartAnimation.AddToCart(finalMyGridViewHolder1.ll_bt, bitmap, imageView, getActivity(), relative_parent, 1);

                        finalMyGridViewHolder3.ischeck = false;
                    } else {
                        cropsList.remove(crops);
                        finalMyGridViewHolder.ll_bt.setBackgroundResource(R.drawable.kuang1);
                        finalMyGridViewHolder3.ischeck = true;
                    }
                }
            });
            return convertView;
        }
    }

    class MyGridViewHolder {
        boolean ischeck = true;
        TextView textView;
        LinearLayout ll_bt;
    }


    class MyLeftAdapter extends RecyclerView.Adapter<MyLeftAdapter.MyLeftViewHolder> {

        private View inflate;
        private JsonObject jsonObject;

        private Map<Integer, List<VFCrops>> mMap = new HashMap<>();

        public MyLeftAdapter() {

        }

        public Map<Integer, List<VFCrops>> getMap() {
            return mMap;
        }

        public void setOnItemListener(LeftItemListener onItemListener) {
            leftItemListener = onItemListener;
        }

        @Override
        public MyLeftAdapter.MyLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            inflate = LayoutInflater.from(getActivity()).inflate(R.layout.left_adapter_item, parent, false);
            MyLeftViewHolder myViewHolder = new MyLeftViewHolder(inflate);


            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyLeftViewHolder holder, final int position) {
            holder.imageView1.setImageResource(imageview1[position]);
            holder.imageView_1.setImageResource(imageview1[position]);
            holder.gridView.setAdapter(myGridViewAdapter);
            if (mMap.get(position) != null) {
                myGridViewAdapter.setGridviewData(mMap.get(position));

            }
//

        }

        @Override
        public int getItemCount() {
            return imageview1.length;
        }

        public class MyLeftViewHolder extends RecyclerView.ViewHolder {
            private boolean isFrontFace = true;//是否是正面
            private final ImageView imageView1;
            private final ImageView imageView2;
            private final GridView gridView;
            private final ImageView imageView_1;

            public MyLeftViewHolder(View itemView) {
                super(itemView);
                imageView1 = (ImageView) itemView.findViewById(R.id.imageView_1);
                imageView2 = (ImageView) itemView.findViewById(R.id.iv_1);
                imageView_1 = (ImageView) itemView.findViewById(R.id.imageView_2);
                gridView = (GridView) itemView.findViewById(R.id.gv);



            }
        }
    }

}
