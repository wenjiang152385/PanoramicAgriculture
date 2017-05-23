package com.oraro.panoramicagriculture.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.R;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.entity.PushMessageJson;
import com.oraro.panoramicagriculture.presenter.MessageListPresenter;
import com.oraro.panoramicagriculture.ui.adapter.BaseListAdapter;
import com.oraro.panoramicagriculture.ui.adapter.MessageListAdapter;
import com.oraro.panoramicagriculture.utils.CommonUtils;

import java.util.List;

public class MessageListActivity extends BaseActivity<MessageListPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseListAdapter.OnItemClickListener {

    private RecyclerView messageListView;
    private SwipeRefreshLayout refreshLayout;
    private MessageListAdapter messageListAdapter;

    @Override
    public MessageListPresenter createPresenter() {
        return new MessageListPresenter();
    }

    @Override
    public BaseActivity getUi() {
        return this;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("消息列表");
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_list_swipe_refresh);
        messageListView = (RecyclerView) findViewById(R.id.message_list_view);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnabled(false);
        messageListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messageListAdapter = new MessageListAdapter(this, BaseListAdapter.NEITHER);
        messageListView.setAdapter(messageListAdapter);
        messageListView.addItemDecoration(new PinnedSectionDecoration(this, new DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return messageListAdapter.getItem(position).getSendTime().getYear();
            }

            @Override
            public String getGroupFirstLine(int position) {
                return String.valueOf(messageListAdapter.getItem(position).getSendTime().getYear() + 1900);
            }
        }));

        messageListAdapter.setOnItemClickListener(this);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("receiveUserId", PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId());
        getPresenter().getAllMessage("getUserMessageList", jsonObject);
    }

    public void updateUI(List<PushMessageJson> pushMessageJsonList) {
        messageListAdapter.addItems(pushMessageJsonList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(int position, long id, View view) {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
//        Intent intent = new Intent(MessageListActivity.this, MessageActivity.class);
//        intent.putExtra("gson", gson.toJson(messageListAdapter.getItem(position)));
//        startActivity(intent);
    }


    public class PinnedSectionDecoration extends RecyclerView.ItemDecoration {
        private static final String TAG = "PinnedSectionDecoration";

        private DecorationCallback callback;
        private TextPaint textPaint;
        private int topGap;
        private Paint.FontMetrics fontMetrics;
        /*绘制圆周的画笔*/
        private Paint backCirclePaint;
        private Paint viewCenterCirclePaint;

        private Paint viewLeftLinePaint;
        private Paint paint;

        private TextPaint yearTextPaint;
        private TextPaint titleTextPaint;

        private int marginLeft;
        private int paddingLeft;


        public PinnedSectionDecoration(Context context, DecorationCallback decorationCallback) {
            Resources res = context.getResources();
            this.callback = decorationCallback;

            textPaint = new TextPaint();
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(30);
            textPaint.setColor(Color.GREEN);
            textPaint.getFontMetrics(fontMetrics);
            textPaint.setStrokeWidth(3);
            textPaint.setTextAlign(Paint.Align.CENTER);
            fontMetrics = new Paint.FontMetrics();
            topGap = 100;
            marginLeft = CommonUtils.dip2px(context, 100);
            paddingLeft = CommonUtils.dip2px(context,12);

            yearTextPaint = new TextPaint();
            yearTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
            yearTextPaint.setAntiAlias(true);
            yearTextPaint.setTextSize(20);
            yearTextPaint.setColor(Color.LTGRAY);
            yearTextPaint.getFontMetrics(fontMetrics);
            yearTextPaint.setStrokeWidth(2);
            yearTextPaint.setTextAlign(Paint.Align.CENTER);

            titleTextPaint = new TextPaint();
            titleTextPaint.setTypeface(Typeface.DEFAULT);
            titleTextPaint.setAntiAlias(true);
            titleTextPaint.setTextSize(40);
            titleTextPaint.setColor(Color.LTGRAY);
            titleTextPaint.getFontMetrics(fontMetrics);
            titleTextPaint.setStrokeWidth(2);
            titleTextPaint.setTextAlign(Paint.Align.LEFT);

            paint = new Paint();
            paint.setColor(res.getColor(R.color.white));

            backCirclePaint = new Paint();
            backCirclePaint.setStyle(Paint.Style.STROKE);
            backCirclePaint.setAntiAlias(true);
            backCirclePaint.setColor(Color.LTGRAY);
            backCirclePaint.setStrokeWidth(10);

            viewCenterCirclePaint = new Paint();
            viewCenterCirclePaint.setStyle(Paint.Style.STROKE);
            viewCenterCirclePaint.setAntiAlias(true);
            viewCenterCirclePaint.setColor(Color.GREEN);
            viewCenterCirclePaint.setStrokeWidth(3);

            viewLeftLinePaint = new Paint();
            viewLeftLinePaint.setColor(Color.LTGRAY);
            viewLeftLinePaint.setStrokeWidth(2);

        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            Log.i(TAG, "getItemOffsets.." + state.willRunPredictiveAnimations());
            int pos = parent.getChildAdapterPosition(view);
            long groupId = callback.getGroupId(pos);
            if (groupId < 0) return;
            if (pos == 0 || isFirstInGroup(pos)) {
                outRect.top = topGap;
            } else {
                outRect.top = 0;
            }
        }


        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int itemCount = state.getItemCount();
            int childCount = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

            Log.i(TAG, "onDrawOver...itemCount=" + itemCount + ",childCount=" + childCount + ",left=" + left + ",right=" + right);
            long preGroupId, groupId = -1;
            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(view);
                int viewCircleRadius = 20;


                if (view.getTop() >= topGap) {
                    //itemview 中间绿色圆圈
                    c.drawArc(new RectF(view.getLeft() + marginLeft + topGap / 2 - viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2 - viewCircleRadius,
                            view.getLeft() + marginLeft + topGap / 2 + viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2 + viewCircleRadius), 0, 360, false, viewCenterCirclePaint);
                    //itemview 绿色圆圈左侧灰线
                    c.drawLine(view.getLeft() + marginLeft + topGap / 4 - viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2,
                            view.getLeft() + marginLeft + topGap / 2 - viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2, viewLeftLinePaint);
                    //itemview 绿色圆圈上面灰线
                    c.drawLine(view.getLeft() + marginLeft + topGap / 2, view.getTop(),
                            view.getLeft() + marginLeft + topGap / 2, view.getTop() + (view.getBottom() - view.getTop()) / 2 - viewCircleRadius, backCirclePaint);
                    //itemview 绿色圆圈下面灰线
                    c.drawLine(view.getLeft() + marginLeft + topGap / 2, view.getTop() + (view.getBottom() - view.getTop()) / 2 + viewCircleRadius,
                            view.getLeft() + marginLeft + topGap / 2, view.getBottom(), backCirclePaint);
                } else {
                    Log.i(TAG, "position=" + position + ",top=" + view.getTop() + ",bottom=" + view.getBottom());
                    if (view.getTop() + (view.getBottom() - view.getTop()) / 2 - viewCircleRadius >= topGap) {
                        //itemview 绿色圆圈上面灰线
                        c.drawLine(view.getLeft() + marginLeft + topGap / 2, topGap,
                                view.getLeft() + marginLeft + topGap / 2, view.getTop() + (view.getBottom() - view.getTop()) / 2 - viewCircleRadius, backCirclePaint);
                    }

                    //itemview 中间绿色圆圈
                    c.drawArc(new RectF(view.getLeft() + marginLeft + topGap / 2 - viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2 - viewCircleRadius,
                            view.getLeft() + marginLeft + topGap / 2 + viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2 + viewCircleRadius), 0, 360, false, viewCenterCirclePaint);
                    if (view.getTop() + (view.getBottom() - view.getTop()) / 2 > topGap) {
                        //itemview 绿色圆圈左侧灰线
                        c.drawLine(view.getLeft() + marginLeft + topGap / 4 - viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2,
                                view.getLeft() + marginLeft + topGap / 2 - viewCircleRadius, view.getTop() + (view.getBottom() - view.getTop()) / 2, viewLeftLinePaint);
                    }

                    if (view.getTop() + (view.getBottom() - view.getTop()) / 2 + viewCircleRadius <= topGap) {
                        //itemview 绿色圆圈下面灰线
                        c.drawLine(view.getLeft() + marginLeft + topGap / 2, topGap,
                                view.getLeft() + marginLeft + topGap / 2, view.getBottom(), backCirclePaint);
                    } else {
                        //itemview 绿色圆圈下面灰线
                        c.drawLine(view.getLeft() + marginLeft + topGap / 2, view.getTop() + (view.getBottom() - view.getTop()) / 2 + viewCircleRadius,
                                view.getLeft() + marginLeft + topGap / 2, view.getBottom(), backCirclePaint);
                    }
                }


                preGroupId = groupId;
                groupId = callback.getGroupId(position);
                if (groupId < 0 || groupId == preGroupId) {
                    continue;
                }

                String textLine = callback.getGroupFirstLine(position);
                if (TextUtils.isEmpty(textLine)) continue;

                int viewBottom = view.getBottom();
                float textY = Math.max(topGap, view.getTop());
                if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                    long nextGroupId = callback.getGroupId(position + 1);
                    if (nextGroupId != groupId && viewBottom < textY) {//组内最后一个view进入了header
                        textY = viewBottom;
                    }
                }
                c.drawRect(left, textY - topGap, right, textY, paint);
                //headview 灰色圆圈
                c.drawArc(new RectF(left + marginLeft, textY - topGap,
                        left + marginLeft + topGap, textY), 0, 360, false, backCirclePaint);
                c.drawText(textLine, left + marginLeft + topGap / 2, textY - topGap / 2, textPaint);
                c.drawText("YEAR", left + marginLeft + topGap / 2, textY - topGap / 4, yearTextPaint);
                c.drawText("消息列表",  paddingLeft, textY - topGap / 4, titleTextPaint);
            }
        }

        private boolean isFirstInGroup(int pos) {
            if (pos == 0) {
                return true;
            } else {
                long prevGroupId = callback.getGroupId(pos - 1);
                long groupId = callback.getGroupId(pos);
                return prevGroupId != groupId;
            }
        }
    }

    public interface DecorationCallback {

        long getGroupId(int position);

        String getGroupFirstLine(int position);
    }

}
