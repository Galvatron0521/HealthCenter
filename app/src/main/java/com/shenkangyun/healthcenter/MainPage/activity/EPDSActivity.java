package com.shenkangyun.healthcenter.MainPage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.healthcenter.BaseFolder.Base;
import com.shenkangyun.healthcenter.BeanFolder.EPDSBean;
import com.shenkangyun.healthcenter.MainPage.Adapter.EPDSAdapter;
import com.shenkangyun.healthcenter.MainPage.Adapter.QuestionnaireAdapter;
import com.shenkangyun.healthcenter.R;
import com.shenkangyun.healthcenter.UtilFolder.GsonCallBack;
import com.shenkangyun.healthcenter.UtilFolder.RecyclerViewDivider;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class EPDSActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.EPDSRecycler)
    RecyclerView EPDSRecycler;

    private EPDSAdapter epdsAdapter;
    private LinearLayoutManager linearLayoutManager;

    private String moduleCode;
    private String moduleName;
    private String moduleUrl;

    private String months;
    private List<String> monthList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epds);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.home_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("爱丁堡产后抑郁量表");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initNetRequest();
    }

    private void initView() {
        Intent intent = getIntent();
        moduleCode = intent.getStringExtra("ModuleCode");
        moduleName = intent.getStringExtra("ModuleName");
        moduleUrl = intent.getStringExtra("ModuleUrl");

        epdsAdapter = new EPDSAdapter();
        linearLayoutManager = new LinearLayoutManager(EPDSActivity.this);
        EPDSRecycler.setLayoutManager(linearLayoutManager);
        EPDSRecycler.setAdapter(epdsAdapter);
    }

    private void initNetRequest() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "patientsFieldNew")
                .addParams("moduleCode", moduleCode)
                .addParams("moduleName", moduleName)
                .addParams("moduleUrl", moduleUrl)
                .addParams("patientID", String.valueOf(Base.getUserID()))
                .addParams("appType", "1")
                .addParams("diseasesid", "")
                .addParams("data", new patientsFieldNew("1", Base.getMD5Str(), Base.getTimeSpan()).toJson())
                .build()
                .execute(new GsonCallBack<EPDSBean>() {
                    @Override
                    public void onSuccess(EPDSBean response) throws JSONException {
                        List<EPDSBean.DataBean.MonthRecordMapBean.AllMonthBean> allMonth = response.getData().getMonthRecordMap().getAllMonth();
                        for (int i = 0; i < allMonth.size(); i++) {
                            months = allMonth.get(i).getMonths();
                            monthList.add(months);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void jsonToJavaObjectByNative(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject monthRecordMap = data.getJSONObject("monthRecordMap");
            JSONArray allMonth = monthRecordMap.getJSONArray("allMonth");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    static class patientsFieldNew {
        private String appKey;
        private String mobileType;
        private String timeSpan;

        public patientsFieldNew(String mobileType, String appKey, String timeSpan) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}
