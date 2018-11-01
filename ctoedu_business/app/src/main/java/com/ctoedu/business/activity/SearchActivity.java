package com.ctoedu.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.activity.base.BaseActivity;
import com.ctoedu.business.adapter.SearchAdapter;
import com.ctoedu.business.db.database.DBDataHelper;
import com.ctoedu.business.db.database.DBHelper;
import com.ctoedu.business.module.BaseModel;
import com.ctoedu.business.module.search.ProductModel;
import com.ctoedu.business.util.Util;

import java.util.ArrayList;

/**
 * 产品搜索页面
 */
public class SearchActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
    private static final int MAX_HISTORY_RECORD = 10;
    /**
     * 公共UI
     */
    private EditText inputEditText;
    private TextView cancelView;
    /**
     * 历史记录相关UI
     */
    private TextView clearHistoryView;
    private LinearLayout historyLayout;
    private ListView historyListView;
    private SearchAdapter historyAdapter;
    /**
     * 既没有历史纪绿，也没有开始搜索，空间面
     */
    private LinearLayout emptyLayout;
    /**
     * 正在搜索界面
     */
    private LinearLayout searchingLayout;
    private ListView searchingListView;
    private SearchAdapter searchingAdapter;
    private LinearLayout searchingEmptyLayout;
    private TextView searchNoView;
    /**
     * data
     */
    private ArrayList<BaseModel> historyListData;
    private ArrayList<BaseModel> searchingListData;
    private ProductModel fundModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        initView();

        Util.hasError();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        changeStatusBarColor(R.color.white);

        inputEditText = (EditText) findViewById(R.id.fund_search_view);
        cancelView = (TextView) findViewById(R.id.cancel_view);
        historyLayout = (LinearLayout) findViewById(R.id.fund_history_layout);
        clearHistoryView = (TextView) historyLayout
                .findViewById(R.id.delect_histroy_view);
        historyListView = (ListView) historyLayout
                .findViewById(R.id.history_list_view);
        historyListView.setOnItemClickListener(this);
        // 空间面View
        emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        // 正在搜索View
        searchingLayout = (LinearLayout) findViewById(R.id.fund_search_layout);
        searchingListView = (ListView) searchingLayout
                .findViewById(R.id.fund_list_view);
        searchingEmptyLayout = (LinearLayout) searchingLayout
                .findViewById(R.id.fund_search_empty_layout);
        searchNoView = (TextView) searchingEmptyLayout
                .findViewById(R.id.seach_no_fund_info);
        searchingListView.setEmptyView(searchingEmptyLayout);
        searchingListView.setOnItemClickListener(this);
        cancelView.setOnClickListener(this);
        clearHistoryView.setOnClickListener(this);
        inputEditText.addTextChangedListener(watcher);

        decideWhichMode();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String selections = inputEditText.getText().toString();
            if (selections != null && !selections.equals("")) {
                entrySearchMode();
                /**
                 * 重数据库中模糊查询匹赔基金
                 */
                searchingListData = DBDataHelper
                        .getInstance()
                        .select(DBHelper.FUND_LIST_TABLE,
                                "fdcode like ? or abbrev like ? or spell like ?",
                                new String[]{"%" + selections + "%",
                                        "%" + selections + "%",
                                        "%" + selections + "%"}, null,
                                ProductModel.class);

                if (searchingAdapter == null) {
                    searchingAdapter = new SearchAdapter(
                            SearchActivity.this, searchingListData);
                    searchingListView.setAdapter(searchingAdapter);
                } else {
                    searchingAdapter.updateData(searchingListData);
                }
                /**
                 * 滚动到第零项
                 */
                searchingListView.smoothScrollToPosition(0);
                searchNoView.setText(Html.fromHtml(getNoFundInfo(selections)));
            } else {
                decideWhichMode();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_view:
                finish();
                break;
            case R.id.delect_histroy_view:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        switch (parent.getId()) {
            case R.id.history_list_view:
                fundModel = (ProductModel) historyAdapter.getItem(position);
                intent.putExtra(CourseDetailActivity.COURSE_ID, fundModel.fdcode);
                startActivity(intent);
                break;
            case R.id.fund_list_view:
                fundModel = (ProductModel) searchingAdapter
                        .getItem(position);
                insertHistoryTable();
                intent.putExtra(CourseDetailActivity.COURSE_ID, fundModel.fdcode);
                startActivity(intent);
                break;
        }
    }

    /**
     * decide to which mode
     */
    private void decideWhichMode() {
        if (getHistoryData() == 0) {
            entryEmptyMode();
        } else {
            entryHistoryMode();
        }
    }

    private void insertHistoryTable() {
        if (DBDataHelper
                .getInstance()
                .select(DBHelper.FUND_BROWSE_TABLE, "fdcode = ?",
                        new String[]{fundModel.fdcode}, null,
                        ProductModel.class).size() == 0) {
            if (getHistoryData() < MAX_HISTORY_RECORD) {
                fundModel.time = String.valueOf(System.currentTimeMillis());
                DBDataHelper.getInstance().insert(DBHelper.FUND_BROWSE_TABLE,
                        fundModel);
            } else {
                DBDataHelper.getInstance().delete(
                        DBHelper.FUND_BROWSE_TABLE,
                        "id = (select min(id) from "
                                + DBHelper.FUND_BROWSE_TABLE + ")", null);
                fundModel.time = String.valueOf(System.currentTimeMillis());
                DBDataHelper.getInstance().insert(DBHelper.FUND_BROWSE_TABLE,
                        fundModel);
            }
        } else {
            fundModel.time = String.valueOf(System.currentTimeMillis());
            DBDataHelper.getInstance().update(DBHelper.FUND_BROWSE_TABLE,
                    "fdcode = ?", new String[]{fundModel.fdcode}, fundModel);
        }
    }

    /**
     * 进入历史记录模式
     */
    private void entryHistoryMode() {
        emptyLayout.setVisibility(View.GONE);
        searchingLayout.setVisibility(View.GONE);
        historyLayout.setVisibility(View.VISIBLE);

        historyListData = DBDataHelper.getInstance().select(
                DBHelper.FUND_BROWSE_TABLE, null, null,
                DBHelper.TIME + DBHelper.DESC, ProductModel.class);
        if (historyAdapter == null) {
            historyAdapter = new SearchAdapter(this, historyListData);
            historyListView.setAdapter(historyAdapter);
        } else {
            historyAdapter.updateData(historyListData);
        }
    }

    /**
     * 进入空模式
     */
    private void entryEmptyMode() {
        DBDataHelper.getInstance().delete(DBHelper.FUND_BROWSE_TABLE, null,
                null);
        searchingLayout.setVisibility(View.GONE);
        historyLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 进入搜索模式
     */
    private void entrySearchMode() {
        historyLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
        searchingLayout.setVisibility(View.VISIBLE);
    }

    private int getHistoryData() {
        return DBDataHelper
                .getInstance()
                .select(DBHelper.FUND_BROWSE_TABLE, null, null, null,
                        ProductModel.class).size();
    }

    private String getNoFundInfo(String info) {
        String sourceInfo = "<font color= #666666>"
                + getString(R.string.search_no_title) + "</font>"
                + "<font color= #ff3b3b>" + info + "</font>"
                + "<font color= #666666>" + getString(R.string.search_no_end)
                + "</font>";
        return sourceInfo;
    }
}