package com.example.huiyiqiandaotv.beans;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class PiLiangBean {


    /**
     * createTime : 1523332018976
     * dtoResult : 0
     * modifyTime : 1523332018976
     * objects : [{"accountId":10000038,"cardNumber":"10010073","createTime":1523331610000,"department":"0","dtoResult":0,"gender":0,"id":10403473,"jobStatus":1,"modifyTime":1523332025000,"name":"方芳芳","pageNum":0,"pageSize":0,"phone":"321321","photo_ids":"20180410/1523331598400.jpg","remark":"","sid":0,"status":1,"subject_type":0,"title":"333"},{"accountId":10000038,"cardNumber":"10010073","createTime":1523331625000,"department":"0","dtoResult":0,"gender":0,"id":10403474,"jobStatus":1,"modifyTime":1523332025000,"name":"广告歌","pageNum":0,"pageSize":0,"phone":"32131","photo_ids":"20180410/1523331614200.jpg","remark":"","sid":0,"status":1,"subject_type":0,"title":"444"},{"accountId":10000038,"cardNumber":"10010073","createTime":1523331641000,"department":"2","dtoResult":0,"gender":0,"id":10403475,"jobStatus":1,"modifyTime":1523332025000,"name":"哈哈哈","pageNum":0,"pageSize":0,"phone":"23123","photo_ids":"20180410/1523331630015.jpg","remark":"32321","sid":0,"status":1,"subject_type":0,"title":"42"},{"accountId":10000038,"cardNumber":"10010073","createTime":1523331922000,"department":"1","dtoResult":0,"gender":0,"id":10403476,"jobStatus":1,"modifyTime":1523332025000,"name":"张三","pageNum":0,"pageSize":0,"phone":"","photo_ids":"20180410/1523331885045.jpg","sid":0,"status":1,"subject_type":0}]
     * pageNum : 0
     * pageSize : 0
     * sid : 0
     */

    private long createTime;
    private int dtoResult;
    private long modifyTime;
    private int pageNum;
    private int pageSize;
    private int sid;
    private List<ObjectsBean> objects;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDtoResult() {
        return dtoResult;
    }

    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        /**
         * accountId : 10000038
         * cardNumber : 10010073
         * createTime : 1523331610000
         * department : 0
         * dtoResult : 0
         * gender : 0
         * id : 10403473
         * jobStatus : 1
         * modifyTime : 1523332025000
         * name : 方芳芳
         * pageNum : 0
         * pageSize : 0
         * phone : 321321
         * photo_ids : 20180410/1523331598400.jpg
         * remark :
         * sid : 0
         * status : 1
         * subject_type : 0
         * title : 333
         */

        private int accountId;
        private String cardNumber;
        private long createTime;
        private String department;
        private int dtoResult;
        private int gender;
        private int id;
        private int jobStatus;
        private long modifyTime;
        private String name;
        private int pageNum;
        private int pageSize;
        private String phone;
        private String photo_ids;
        private String remark;
        private int sid;
        private int status;
        private int subject_type;
        private String title;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getDtoResult() {
            return dtoResult;
        }

        public void setDtoResult(int dtoResult) {
            this.dtoResult = dtoResult;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(int jobStatus) {
            this.jobStatus = jobStatus;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoto_ids() {
            return photo_ids;
        }

        public void setPhoto_ids(String photo_ids) {
            this.photo_ids = photo_ids;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
