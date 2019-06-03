package com.shenkangyun.healthcenter.MainPage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.healthcenter.BeanFolder.EPDSBean;
import com.shenkangyun.healthcenter.R;

/**
 * Created by Administrator on 2018/11/6.
 */

public class EPDSAdapter extends BaseQuickAdapter<EPDSBean.DataBean.MonthRecordMapBean, BaseViewHolder> {
    public EPDSAdapter() {
        super(R.layout.item_epds, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, EPDSBean.DataBean.MonthRecordMapBean item) {

    }
}
