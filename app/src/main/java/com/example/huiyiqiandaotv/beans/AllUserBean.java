package com.example.huiyiqiandaotv.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class AllUserBean {


    /**
     * code : 0
     * data : [{"avatar":"","birthday":null,"come_from":"","company_id":51,"department":"","description":"","email":"","end_time":0,"entry_date":null,"gender":0,"id":15281,"interviewee":"","interviewee_pinyin":"","job_number":"","name":"和","password_reseted":false,"phone":"","photo_ids":[12310],"photos":[{"company_id":51,"id":12310,"quality":0.893032,"subject_id":15281,"url":"/static/upload/photo/2017-12-12/34dd98afdce1a43f322b2d21289907d2f115cb0d.jpg","version":3}],"pinyin":"he","purpose":0,"remark":"","start_time":0,"subject_type":0,"title":"","visit_notify":false},{"avatar":"","birthday":null,"come_from":"","company_id":51,"department":"","description":"","email":"","end_time":1538111700,"entry_date":null,"gender":0,"id":15274,"interviewee":"","interviewee_pinyin":"","job_number":"0","name":"朱","password_reseted":false,"phone":"","photo_ids":[12297],"photos":[{"company_id":51,"id":12297,"quality":0.97101,"subject_id":15274,"url":"/static/upload/photo/2017-12-12/8d5c404866d06be4d9de34638590bd5f5b3b100e.jpg","version":3}],"pinyin":"zhu","purpose":0,"remark":"","start_time":1512364500,"subject_type":1,"title":"","visit_notify":false},{"avatar":"","birthday":828806400,"come_from":"","company_id":51,"department":"临时部门","description":"","email":"","end_time":0,"entry_date":null,"gender":2,"id":15254,"interviewee":"大黄","interviewee_pinyin":"daihuang","job_number":"","name":"黄夏文            ","password_reseted":false,"phone":"","photo_ids":[12249],"photos":[{"company_id":51,"id":12249,"quality":0.766283,"subject_id":15254,"url":"/static/upload/photo/2017-12-08/0c44d2dc8a9adf6c6137f656b53891c2e0077b07.jpg","version":3}],"pinyin":"huangxiawen            ","purpose":0,"remark":"访客","start_time":0,"subject_type":0,"title":"","visit_notify":false},{"avatar":"","birthday":null,"come_from":"","company_id":51,"department":"学生","description":"","email":"","end_time":0,"entry_date":null,"gender":0,"id":15137,"interviewee":"","interviewee_pinyin":"","job_number":"","name":"小胖子","password_reseted":false,"phone":"","photo_ids":[11964],"photos":[{"company_id":51,"id":11964,"quality":0.927979,"subject_id":15137,"url":"/static/upload/photo/2017-12-04/d0e4e2a961ac61fb215688d72f62277c5ff68341.jpg","version":3}],"pinyin":"xiaopangzi","purpose":0,"remark":"员工","start_time":0,"subject_type":0,"title":"","visit_notify":false}]
     * page : {}
     */

    private int code;
    private PageBean page;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
    }

    public static class DataBean {
        /**
         * avatar :
         * birthday : null
         * come_from :
         * company_id : 51
         * department :
         * description :
         * email :
         * end_time : 0
         * entry_date : null
         * gender : 0
         * id : 15281
         * interviewee :
         * interviewee_pinyin :
         * job_number :
         * name : 和
         * password_reseted : false
         * phone :
         * photo_ids : [12310]
         * photos : [{"company_id":51,"id":12310,"quality":0.893032,"subject_id":15281,"url":"/static/upload/photo/2017-12-12/34dd98afdce1a43f322b2d21289907d2f115cb0d.jpg","version":3}]
         * pinyin : he
         * purpose : 0
         * remark :
         * start_time : 0
         * subject_type : 0
         * title :
         * visit_notify : false
         */

        private String avatar;
        private Object birthday;
        private String come_from;
        private int company_id;
        private String department;
        private String description;
        private String email;
        private int end_time;
        private Object entry_date;
        private int gender;
        private int id;
        private String interviewee;
        private String interviewee_pinyin;
        private String job_number;
        private String name;
        private boolean password_reseted;
        private String phone;
        private String pinyin;
        private int purpose;
        private String remark;
        private int start_time;
        private int subject_type;
        private String title;
        private boolean visit_notify;
        private List<Integer> photo_ids;
        private List<PhotosBean> photos;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getCome_from() {
            return come_from;
        }

        public void setCome_from(String come_from) {
            this.come_from = come_from;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public Object getEntry_date() {
            return entry_date;
        }

        public void setEntry_date(Object entry_date) {
            this.entry_date = entry_date;
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

        public String getInterviewee() {
            return interviewee;
        }

        public void setInterviewee(String interviewee) {
            this.interviewee = interviewee;
        }

        public String getInterviewee_pinyin() {
            return interviewee_pinyin;
        }

        public void setInterviewee_pinyin(String interviewee_pinyin) {
            this.interviewee_pinyin = interviewee_pinyin;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPassword_reseted() {
            return password_reseted;
        }

        public void setPassword_reseted(boolean password_reseted) {
            this.password_reseted = password_reseted;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public int getPurpose() {
            return purpose;
        }

        public void setPurpose(int purpose) {
            this.purpose = purpose;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
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

        public boolean isVisit_notify() {
            return visit_notify;
        }

        public void setVisit_notify(boolean visit_notify) {
            this.visit_notify = visit_notify;
        }

        public List<Integer> getPhoto_ids() {
            return photo_ids;
        }

        public void setPhoto_ids(List<Integer> photo_ids) {
            this.photo_ids = photo_ids;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class PhotosBean {
            /**
             * company_id : 51
             * id : 12310
             * quality : 0.893032
             * subject_id : 15281
             * url : /static/upload/photo/2017-12-12/34dd98afdce1a43f322b2d21289907d2f115cb0d.jpg
             * version : 3
             */

            private int company_id;
            private int id;
            private double quality;
            private int subject_id;
            private String url;
            private int version;

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getQuality() {
                return quality;
            }

            public void setQuality(double quality) {
                this.quality = quality;
            }

            public int getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(int subject_id) {
                this.subject_id = subject_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }
        }
    }
}
