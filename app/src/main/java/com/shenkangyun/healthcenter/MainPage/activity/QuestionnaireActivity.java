package com.shenkangyun.healthcenter.MainPage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.healthcenter.BaseFolder.Base;
import com.shenkangyun.healthcenter.BeanFolder.QuestionnaireBean;
import com.shenkangyun.healthcenter.MainPage.Adapter.QuestionnaireAdapter;
import com.shenkangyun.healthcenter.R;
import com.shenkangyun.healthcenter.UtilFolder.GsonCallBack;
import com.shenkangyun.healthcenter.UtilFolder.RecyclerViewDivider;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionnaireActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.QuestionnaireRecycler)
    RecyclerView QuestionnaireRecycler;
    @BindView(R.id.easyLayout)
    SwipeRefreshLayout easyLayout;

    private QuestionnaireAdapter questionnaireAdapter;
    private List<QuestionnaireBean.DataBean.QuestionListBean> totalList = new ArrayList<>();
    private LinearLayoutManager manager;

    private int pageNo = 0;
    private int pageCount = 10;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.home_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("问卷列表");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initNetRequest();
        initRefresh();
    }

    private void initView() {
        questionnaireAdapter = new QuestionnaireAdapter();
        manager = new LinearLayoutManager(QuestionnaireActivity.this);
        QuestionnaireRecycler.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL,
                10, getResources().getColor(R.color.white)));
        QuestionnaireRecycler.setLayoutManager(manager);
        QuestionnaireRecycler.setAdapter(questionnaireAdapter);
    }

    private void initNetRequest() {
        pageNo = 0;
        pageCount = 10;
        totalList.clear();
        final List<QuestionnaireBean.DataBean.QuestionListBean> listBeans = new ArrayList<>();
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "selectQuestionNew")
                .addParams("data", new selectQuestionNew(Base.getMD5Str(), "1", Base.getTimeSpan(), "1",
                        String.valueOf(pageNo), String.valueOf(pageCount)).toJson())
                .build()
                .execute(new GsonCallBack<QuestionnaireBean>() {
                    @Override
                    public void onSuccess(QuestionnaireBean response) {
                        size = response.getData().getQuestionList().size();
                        for (int i = 0; i < response.getData().getQuestionList().size(); i++) {
                            QuestionnaireBean.DataBean.QuestionListBean listBean = new QuestionnaireBean.DataBean.QuestionListBean();
                            String moduleCode = response.getData().getQuestionList().get(i).getModuleCode();
                            String moduleName = response.getData().getQuestionList().get(i).getModuleName();
                            String moduleUrl = response.getData().getQuestionList().get(i).getModuleUrl();

                            listBean.setModuleCode(moduleCode);
                            listBean.setModuleName(moduleName);
                            listBean.setModuleUrl(moduleUrl);

                            listBeans.add(listBean);
                            totalList.add(listBean);
                        }
                        questionnaireAdapter.setNewData(listBeans);
                        if (easyLayout.isRefreshing()) {
                            easyLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        initLoadMore();
        initClick();
    }

    private void initLoadMore() {
        questionnaireAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                QuestionnaireRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<QuestionnaireBean.DataBean.QuestionListBean> listBeans = new ArrayList<>();
                        if (!(size < pageCount)) {
                            pageNo = pageNo + size;
                            OkHttpUtils.post().url(Base.URL)
                                    .addParams("act", "selectQuestionNew")
                                    .addParams("data", new selectQuestionNew(Base.getMD5Str(), "1", Base.getTimeSpan(), "1",
                                            String.valueOf(pageNo), String.valueOf(pageCount)).toJson())
                                    .build().execute(new GsonCallBack<QuestionnaireBean>() {
                                @Override
                                public void onSuccess(final QuestionnaireBean response) throws JSONException {
                                    size = response.getData().getQuestionList().size();
                                    for (int i = 0; i < response.getData().getQuestionList().size(); i++) {
                                        QuestionnaireBean.DataBean.QuestionListBean listBean = new QuestionnaireBean.DataBean.QuestionListBean();
                                        String moduleCode = response.getData().getQuestionList().get(i).getModuleCode();
                                        String moduleName = response.getData().getQuestionList().get(i).getModuleName();
                                        String moduleUrl = response.getData().getQuestionList().get(i).getModuleUrl();

                                        listBean.setModuleCode(moduleCode);
                                        listBean.setModuleName(moduleName);
                                        listBean.setModuleUrl(moduleUrl);

                                        listBeans.add(listBean);
                                        totalList.add(listBean);
                                    }
                                    questionnaireAdapter.addData(listBeans);
                                    questionnaireAdapter.loadMoreComplete();
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                        } else {
                            questionnaireAdapter.loadMoreEnd();
                        }
                    }
                }, 2000);

            }
        }, QuestionnaireRecycler);
    }

    private void initRefresh() {
        easyLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNetRequest();
            }
        });
    }

    private void initClick() {
        questionnaireAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(QuestionnaireActivity.this, EPDSActivity.class);
                intent.putExtra("ModuleCode", totalList.get(position).getModuleCode());
                intent.putExtra("ModuleName", totalList.get(position).getModuleName());
                intent.putExtra("ModuleUrl", totalList.get(position).getModuleUrl());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    static class selectQuestionNew {

        private String appKey;
        private String mobileType;
        private String timeSpan;
        private String appType;
        private String pageNo;
        private String pageCount;

        public selectQuestionNew(String appKey, String mobileType, String timeSpan, String appType, String pageNo, String pageCount) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.pageNo = pageNo;
            this.pageCount = pageCount;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}
