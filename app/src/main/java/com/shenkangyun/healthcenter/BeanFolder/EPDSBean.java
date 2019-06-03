package com.shenkangyun.healthcenter.BeanFolder;

import java.util.List;

/**
 * Created by Administrator on 2018/10/19.
 */

public class EPDSBean {

    /**
     * data : {"monthRecordMap":{"allMonth":[{"months":"2018-10","count":1},{"months":"2018-08","count":1}]},"title":"爱丁堡产后抑郁量表(EPDS)","diseasesid":"","moduleUrl":"/form/scaleRecordList.html","moduleName":"爱丁堡产后抑郁量表(EPDS)","moduleCode":"SP020123","patientID":39,"appType":"1"}
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
         * monthRecordMap : {"allMonth":[{"months":"2018-10","count":1},{"months":"2018-08","count":1}]}
         * title : 爱丁堡产后抑郁量表(EPDS)
         * diseasesid :
         * moduleUrl : /form/scaleRecordList.html
         * moduleName : 爱丁堡产后抑郁量表(EPDS)
         * moduleCode : SP020123
         * patientID : 39
         * appType : 1
         */

        private MonthRecordMapBean monthRecordMap;
        private String title;
        private String diseasesid;
        private String moduleUrl;
        private String moduleName;
        private String moduleCode;
        private int patientID;
        private String appType;

        public MonthRecordMapBean getMonthRecordMap() {
            return monthRecordMap;
        }

        public void setMonthRecordMap(MonthRecordMapBean monthRecordMap) {
            this.monthRecordMap = monthRecordMap;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDiseasesid() {
            return diseasesid;
        }

        public void setDiseasesid(String diseasesid) {
            this.diseasesid = diseasesid;
        }

        public String getModuleUrl() {
            return moduleUrl;
        }

        public void setModuleUrl(String moduleUrl) {
            this.moduleUrl = moduleUrl;
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

        public int getPatientID() {
            return patientID;
        }

        public void setPatientID(int patientID) {
            this.patientID = patientID;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public static class MonthRecordMapBean {
            private List<AllMonthBean> allMonth;

            public List<AllMonthBean> getAllMonth() {
                return allMonth;
            }

            public void setAllMonth(List<AllMonthBean> allMonth) {
                this.allMonth = allMonth;
            }

            public static class AllMonthBean {
                /**
                 * months : 2018-10
                 * count : 1
                 */

                private String months;
                private int count;

                public String getMonths() {
                    return months;
                }

                public void setMonths(String months) {
                    this.months = months;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }
            }
        }
    }
}
