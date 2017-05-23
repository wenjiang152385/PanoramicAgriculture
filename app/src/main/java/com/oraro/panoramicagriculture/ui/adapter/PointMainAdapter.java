package com.oraro.panoramicagriculture.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.ui.activity.PointMainActivity;
import com.oraro.panoramicagriculture.utils.LogUtils;

/**
 * Created by admin on 2017/4/10.
 */
public abstract class PointMainAdapter extends RecyclerView.Adapter{
    //当前正在请求今天的需求信息
    public static final int TYPE_LOAD_NEEDS_TODAY = 1;
    //当前正在请求昨天的需求信息
    public static final int TYPE_LOAD_NEEDS_YESTERDAY = 2;
    //当前正在请求前天的需求信息
    public static final int TYPE_LOAD_NEEDS_BEFORE_YESTERDAY = 3;
    //当前正在请求需求更多的信息
    public static final int TYPE_LOAD_NEEDS_MORE = 7;
    //当前正在请求销售更多信息
    public static final int TYPE_LOAD_SALE_MORE = 8;
    //当前正在请求今天的销售信息
    public static final int TYPE_LOAD_SALE_TODAY = 4;
    //当前正在请求昨天的销售信息
    public static final int TYPE_LOAD_SALE_YESTERDAY = 5;
    //当前正在请求前天的销售信息
    public static final int TYPE_LOAD_SALE_BEFORE_YESTERDAY = 6;

    //一页加载的条数
    public static final int PAGE_COUNT = 10;

    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;

    private static int ONE_ROW_COUNT = 1;
    private static int TWO_ROW_COUNT = 1;
    private static int THREE_ROW_COUNT = 1;
    private static int FOOT_ROW_COUNT = 1;
    public static int SUM_TOP_ROW_COUNT = ONE_ROW_COUNT + TWO_ROW_COUNT + THREE_ROW_COUNT;

    private static int TYPE_FOOTER_ROW = -999;

    protected PointMainActivity context;
    protected LayoutInflater mInflate;

    protected final int PositionType0 = -1;
    protected final int PositionType1 = -2;
    protected final int PositionType2 = -3;


    public HolderData holderData;

    protected PTHolder0 ptHolder0;
    protected PTHolder1 ptHolder1;
    protected PTHolderSwitcher ptHolder2;
    protected PointMainAdapterImpl mainAdapter;
    protected FooterViewHolder ptFooterHolder;

    private int mCurrentState = STATE_LOADING;
    private int mCurrentPage = 0;

    private int mCurrentLoadType = 0;

    protected abstract  PTHolderSwitcher onCreateSwitchHolder(ViewGroup parent);
    protected abstract void CreateMainAdapter(PTHolderSwitcher holder);
    protected HolderData getHolderData() {
        return holderData;
    }

    protected void setHolderData(HolderData holderData) {
        this.holderData = holderData;
    }

    public PTHolderSwitcher.TypeGlobal getPTHolderSwitcherType(){
        if(null == ptHolder2){
            return PTHolderSwitcher.TypeGlobal.purchase_page1;
        }else{
            return ptHolder2.getType();
        }
    }

    public void setState(int state){
        mCurrentState = state;
        notifyDataSetChanged();
    }

    public void setCurrentPage(int page){
        mCurrentPage = page;
    }

    public int getCurrentPage(){
        return mCurrentPage;
    }

    public void setCurrentLoadType(int type){
        mCurrentLoadType = type;
    }

    public int getCurrentLoadType(){
        return mCurrentLoadType;
    }

    public void onResume(){CreateMainAdapter(ptHolder2);}
    public void onPause(){
        ptHolder0.mDemoSlider.stopAutoCycle();
    }
    public PointMainAdapter(PointMainActivity context, HolderData holderData){
        this.context = context;
        setHolderData(holderData);
        mInflate = LayoutInflater.from(context);

        CreateMainAdapter(null);
    }
    @Override
    public int getItemViewType(int position) {
        LogUtils.e("yjd9 position = " + position + "getItemCount() + SUM_ROW_COUNT" + (getItemCount()));

        if (position == 0){
            return PositionType0;
        }else if (position == 1){
            return PositionType1;
        }else if (position == 2){
            return PositionType2;
        }else if(position == getItemCount() - 1){
            return TYPE_FOOTER_ROW;
        } else if(SUM_TOP_ROW_COUNT <= position && position < getItemCount() - FOOT_ROW_COUNT){
            return -99 - ptHolder2.getType().ordinal();
        }
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == PositionType0) {
            viewHolder = ptHolder0 = new PTHolder0(mInflate.inflate(R.layout.item_ptholder0, parent, false));
        } else if (viewType == PositionType1) {
            viewHolder = ptHolder1 = new PTHolder1(mInflate.inflate(R.layout.item_ptholder1, parent, false));
        } else if (viewType == PositionType2) {
            viewHolder = ptHolder2 = onCreateSwitchHolder(parent);//new PTHolder2(mInflate.inflate(R.layout.item_ptholder2, parent, false));
        } else if (viewType == TYPE_FOOTER_ROW){
            LogUtils.e("yjd9 onCreateViewHolder FOOTER_ROW");
            viewHolder = ptFooterHolder =  new FooterViewHolder(mInflate.inflate(R.layout.list_footer_layout, parent, false));
        }else{
            viewHolder =  mainAdapter.onCreateViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtils.e("yjd9 onBindViewHolder");
        int type = getItemViewType(position);
        if (type == PositionType0){
            ptHolder0.initData(holderData);
        }else if (type == PositionType1){
            ptHolder1.initData(holderData);
        } else if(type == PositionType2){
            ptHolder2.initData(holderData);
        }else if(type == TYPE_FOOTER_ROW){
            RefreshState(mCurrentState);
        }else{
            mainAdapter.onBindViewHolder(holder, position - SUM_TOP_ROW_COUNT);
        }
    }
    public void RefreshState(int mState){
        LogUtils.e("yjd9 RefreshState state = " + mState);
        if (ptFooterHolder == null)
            return;
        FooterViewHolder fvh = ptFooterHolder;
        switch (mState) {
            case STATE_INVALID_NETWORK:
                fvh.mStateText.setText(context.getResources().getText(R.string.invalid_network));
                fvh.probar.setVisibility(View.GONE);
                break;
            case STATE_LOAD_MORE:
                mainAdapter.loadData(ptHolder2);
                mCurrentState = STATE_LOADING;
            case STATE_LOADING:
                fvh.mStateText.setText(context.getResources().getText(R.string.loading));
                fvh.probar.setVisibility(View.VISIBLE);
                LogUtils.e("yjd9 FooterViewHolder STATE_LOAD_MORE");
                break;
            case STATE_NO_MORE:
                fvh.mStateText.setText(context.getResources().getText(R.string.load_no_more));
                fvh.probar.setVisibility(View.GONE);
                break;
            case STATE_REFRESHING:
                fvh.mStateText.setText(context.getResources().getText(R.string.refreshing));
                fvh.probar.setVisibility(View.GONE);
                break;
            case STATE_LOAD_ERROR:
                fvh.mStateText.setText(context.getResources().getText(R.string.load_failed));
                fvh.probar.setVisibility(View.GONE);
                break;
            case STATE_HIDE:
                fvh.itemView.setVisibility(View.GONE);
                break;
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar probar;
        public TextView mStateText;

        public FooterViewHolder(View view) {
            super(view);
            probar = (ProgressBar) view.findViewById(R.id.progressbar);
            mStateText = (TextView) view.findViewById(R.id.state_text);

            mStateText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加载出错，点击重新加载
                    if(mCurrentState == STATE_LOAD_ERROR){
                        setState(STATE_LOAD_MORE);
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        int size = SUM_TOP_ROW_COUNT + FOOT_ROW_COUNT;
        if (mainAdapter != null){
            size += mainAdapter.getItemCount();
        }
        return size;
    }
}
