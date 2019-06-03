package com.shenkangyun.healthcenter.MainPage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.healthcenter.BeanFolder.QuestionnaireBean;
import com.shenkangyun.healthcenter.R;

/**
 * Created by Administrator on 2018/10/17.
 */

public class QuestionnaireAdapter extends BaseQuickAdapter<QuestionnaireBean.DataBean.QuestionListBean, BaseViewHolder> {
    public QuestionnaireAdapter() {
        super(R.layout.item_questionnaire, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionnaireBean.DataBean.QuestionListBean item) {
        helper.setText(R.id.moduleName, item.getModuleName());
    }
}
