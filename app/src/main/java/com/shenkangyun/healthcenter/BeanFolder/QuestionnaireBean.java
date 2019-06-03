package com.shenkangyun.healthcenter.BeanFolder;

import java.util.List;

/**
 * Created by Administrator on 2018/10/17.
 */

public class QuestionnaireBean {

    /**
     * data : {"pageCount":8,"questionList":[{"moduleID":237,"crfType":1,"moduleType":1,"moduleName":"爱丁堡产后抑郁量表(EPDS)","moduleCode":"SP020123","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:10:55"},{"moduleID":239,"crfType":1,"moduleType":1,"moduleName":"母乳喂养情况调查表(产后1/3/6月)","moduleCode":"SP020125","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-04-29 16:11:50"},{"moduleID":244,"crfType":1,"moduleType":1,"moduleName":"母乳喂养自信心量表(BSES)(产后1/3/6月)","moduleCode":"SP020130","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-04-29 16:14:19"},{"moduleID":247,"crfType":1,"moduleType":1,"moduleName":"中医体质分类与判定自测表","moduleCode":"SP020133","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:15:15"}],"totalCount":4,"currentPage":0,"patientID":39}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pageCount : 8
         * questionList : [{"moduleID":237,"crfType":1,"moduleType":1,"moduleName":"爱丁堡产后抑郁量表(EPDS)","moduleCode":"SP020123","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:10:55"},{"moduleID":239,"crfType":1,"moduleType":1,"moduleName":"母乳喂养情况调查表(产后1/3/6月)","moduleCode":"SP020125","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-04-29 16:11:50"},{"moduleID":244,"crfType":1,"moduleType":1,"moduleName":"母乳喂养自信心量表(BSES)(产后1/3/6月)","moduleCode":"SP020130","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-04-29 16:14:19"},{"moduleID":247,"crfType":1,"moduleType":1,"moduleName":"中医体质分类与判定自测表","moduleCode":"SP020133","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:15:15"}]
         * totalCount : 4
         * currentPage : 0
         * patientID : 39
         */

        private int pageCount;
        private int totalCount;
        private int currentPage;
        private int patientID;
        private List<QuestionListBean> questionList;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPatientID() {
            return patientID;
        }

        public void setPatientID(int patientID) {
            this.patientID = patientID;
        }

        public List<QuestionListBean> getQuestionList() {
            return questionList;
        }

        public void setQuestionList(List<QuestionListBean> questionList) {
            this.questionList = questionList;
        }

        public static class QuestionListBean {
            /**
             * moduleID : 237
             * crfType : 1
             * moduleType : 1
             * moduleName : 爱丁堡产后抑郁量表(EPDS)
             * moduleCode : SP020123
             * moduleUrl : /form/scaleRecordList.html
             * CreateTime : 2018-04-29 16:10:55
             */

            private int moduleID;
            private int crfType;
            private int moduleType;
            private String moduleName;
            private String moduleCode;
            private String moduleUrl;
            private String CreateTime;

            public int getModuleID() {
                return moduleID;
            }

            public void setModuleID(int moduleID) {
                this.moduleID = moduleID;
            }

            public int getCrfType() {
                return crfType;
            }

            public void setCrfType(int crfType) {
                this.crfType = crfType;
            }

            public int getModuleType() {
                return moduleType;
            }

            public void setModuleType(int moduleType) {
                this.moduleType = moduleType;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public String getModuleCode() {
                return moduleCode;
            }

            public void setModuleCode(String moduleCode) {
                this.moduleCode = moduleCode;
            }

            public String getModuleUrl() {
                return moduleUrl;
            }

            public void setModuleUrl(String moduleUrl) {
                this.moduleUrl = moduleUrl;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }
    }
}
