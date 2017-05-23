package com.oraro.panoramicagriculture.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.VFCrops;
import com.oraro.panoramicagriculture.presenter.ColzaPresenter;
import com.oraro.panoramicagriculture.ui.view.SideLetterBar;
import com.oraro.panoramicagriculture.utils.LogUtils;
import com.oraro.panoramicagriculture.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 *
 * @author
 */
public class ColzaListActivity extends BaseActivity<ColzaPresenter> {

    private MyColzaAdapter myColzaAdapter;
    private SideLetterBar sideLetterBar;
    private TextView overlay;
    private ListView listView;
    private EditText search;
    private ImageView clear_search;
    private LinearLayout linearLayout;
    private ImageView imageView_search;
    private List<VFCrops> list = new ArrayList<>();
    private ListView listview_search;
    private MysearchAdapter mysearchAdapter;
    private static DaoSession daoSession = PanoramicAgricultureApplication.getInstances().getDaoSession();
    @Override
    public ColzaPresenter createPresenter() {
        return new ColzaPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_colza_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("菜品种类");
        listView = (ListView) findViewById(R.id.listview_all_city);
        overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        sideLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        search = (EditText) findViewById(R.id.et_search);
        clear_search = (ImageView) findViewById(R.id.iv_search_clear);
        linearLayout = (LinearLayout) findViewById(R.id.empty_view);
//        imageView_search = (ImageView) findViewById(R.id.iv_search_clear);
        listview_search = (ListView) findViewById(R.id.listview_search_result);
        myColzaAdapter = new MyColzaAdapter();
        listView.setAdapter(myColzaAdapter);
        mysearchAdapter = new MysearchAdapter();
        listview_search.setAdapter(mysearchAdapter);
        if (daoSession.getVFCropsDao().loadAll() != null &&daoSession.getVFCropsDao().loadAll().size()>0){
            setlist(daoSession.getVFCropsDao().loadAll());
        }else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type1", "粮食作物");
            getPresenter().requestCropsData("getVFcrops", jsonObject);
        }

        sideLetterBar.setOverlay(overlay);
        clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
                clear_search.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                listview_search.setVisibility(View.GONE);
            }
        });
        sideLetterBar.setOnLetterChangeListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                myColzaAdapter.setindex();
                int position = myColzaAdapter.getLetterPosition(letter);
                listView.setSelection(position);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString().trim();

                List<VFCrops> searchList = new ArrayList<VFCrops>();
                for (int i = 0; i <list.size() ; i++) {
            LogUtils.e("jww","..="+list.get(i).getByname()+"==="+list.get(i).getPinyin()+"==="+string);

                    if ( list.get(i).getByname().contains(string)){
                   LogUtils.e("jj","list=="+list.get(i));
                        searchList.add(list.get(i));

                    }
                    else if (list.get(i).getPinyin().contains(string.toUpperCase())) {
                        searchList.add(list.get(i));

                    }

                }
                if (searchList.size()>0&&!string.isEmpty()) {
                    clear_search.setVisibility(View.VISIBLE);
                    listview_search.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }

                mysearchAdapter.changeData(searchList);

            }
        });
    }

    public void setlist(List<VFCrops> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPinyin(PinyinUtils.converterToFirstSpell(list.get(i).getByname()));
            LogUtils.e("jww","setlist=="+PinyinUtils.converterToFirstSpell(list.get(i).getByname()));

        }

        Collections.sort(list, new Comparator<VFCrops>() {
            @Override
            public int compare(VFCrops lhs, VFCrops rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
        this.list = list;
        myColzaAdapter.setColzalist(list);
        myColzaAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                break;
            case R.id.action_save:

                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MysearchAdapter extends BaseAdapter {
        private List<VFCrops> mysearchlist = new ArrayList<>();

        public void changeData(List<VFCrops> list) {
            LogUtils.e("jww","list=="+list.size());
            if (mysearchlist == null) {
                mysearchlist = list;
            } else {
                mysearchlist.clear();
                mysearchlist.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mysearchlist==null?0:mysearchlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ResultViewHolder holder;
            if(convertView==null){
                convertView= LayoutInflater.from(ColzaListActivity.this).inflate(R.layout.item_search_result_listview,parent,false);
                holder=new ResultViewHolder();
                holder.name= (TextView) convertView.findViewById(R.id.tv_item_result_listview_name);
                convertView.setTag(holder);
            }else{
                holder= (ResultViewHolder) convertView.getTag();
            }
            holder.name.setText(mysearchlist.get(position).getByname());
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("names", mysearchlist.get(position).getByname());
                    intent.putExtra("ids", mysearchlist.get(position).getVfid());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return convertView;
        }

    }
     class ResultViewHolder{
        TextView name;
         LinearLayout ll_cropname;
    }

    class MyColzaAdapter extends BaseAdapter {
        public List<VFCrops> colzalist = new ArrayList<>();
        private HashMap<String, Integer> letterIndexes = new HashMap<>();

        public void setindex() {
            int size = colzalist.size();
            String[] sections = new String[size];
            for (int i = 0; i < size; i++) {
                //当前城市拼音的首字母
                String currentLetter = PinyinUtils.getFirstLetter(colzalist.get(i).getPinyin());
                //上个首字母，如果不存在设为“”
                String previousLetter = i >= 1 ? PinyinUtils.getFirstLetter(colzalist.get(i - 1).getPinyin()) : "";
                if (!TextUtils.equals(currentLetter, previousLetter)) {
                    letterIndexes.put(currentLetter, i);
                    sections[i] = currentLetter;
                }
            }
        }


        public void setColzalist(List<VFCrops> list) {
            colzalist = list;
            notifyDataSetChanged();

        }

        public int getLetterPosition(String letter) {
            Integer integer = letterIndexes.get(letter);
            return integer == null ? -1 : integer;
        }

        @Override
        public int getCount() {
            return colzalist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ColzaListActivity.this).inflate(R.layout.item_city_listview, parent, false);
                holder = new MyViewHolder();
                holder.letter = (TextView) convertView.findViewById(R.id.tv_item_city_listview_letter);
                holder.name = (TextView) convertView.findViewById(R.id.tv_item_city_listview_name);
                holder.mVFName = (TextView) convertView.findViewById(R.id.tv_item_vf_name);
                holder.simpleDraweeView= (SimpleDraweeView) convertView.findViewById(R.id.sdv_sale_info);
                holder.ll_cropname= (LinearLayout) convertView.findViewById(R.id.ll_cropname);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }
            final String name = colzalist.get(position).getByname();
            holder.simpleDraweeView.setImageURI(Constants.SERVER_ADDRESS+colzalist.get(position).getPath1());
            holder.name.setText(name);
            holder.mVFName.setText(colzalist.get(position).getVfname());
            LogUtils.e("jww","colzalist.get(position).getPinyin()=="+colzalist.get(position).getPinyin());
            String currentLetter = PinyinUtils.getFirstLetter(colzalist.get(position).getPinyin());
            String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(colzalist.get(position - 1).getPinyin()) : "";
            if (!TextUtils.equals(previousLetter, currentLetter)) {
                holder.letter.setText(currentLetter);
                holder.letter.setVisibility(View.VISIBLE);
            } else {
                holder.letter.setVisibility(View.GONE);
            }
            holder.ll_cropname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("names", colzalist.get(position).getByname());
                    intent.putExtra("ids", colzalist.get(position).getVfid());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            return convertView;
        }
    }

    class MyViewHolder {
        TextView letter;
        TextView name;
        TextView mVFName;
        SimpleDraweeView simpleDraweeView;
        LinearLayout ll_cropname;
    }
}
