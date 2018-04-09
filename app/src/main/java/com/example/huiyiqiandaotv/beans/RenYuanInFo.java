package com.example.huiyiqiandaotv.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/9.
 */

@Entity
public class RenYuanInFo {
    
    /**
     * accountId : 10000038
     * cardNumber : 10010073
     * createTime : 1523269126000
     * department : 0
     * dtoResult : 0
     * gender : 0
     * id : 10403428
     * jobStatus : 0
     * modifyTime : 1523269126000
     * name : 小军
     * pageNum : 0
     * pageSize : 0
     * phone : 1555555555
     * photo_ids :
     * remark :
     * sid : 0
     * status : 1
     * subject_type : 0
     * title : 11
     */

    private int accountId;
    private String cardNumber;
    private String department;
    private int gender;
    @Id
    @NotNull
    private Long id;
    private int jobStatus;
    private long modifyTime;
    private String name;
    private String phone;
    private String photo_ids;
    private String remark;
    private int sid;
    private int status;
    private int subject_type;
    private String title;
    @Generated(hash = 91804554)
    public RenYuanInFo(int accountId, String cardNumber, String department,
            int gender, @NotNull Long id, int jobStatus, long modifyTime,
            String name, String phone, String photo_ids, String remark, int sid,
            int status, int subject_type, String title) {
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.department = department;
        this.gender = gender;
        this.id = id;
        this.jobStatus = jobStatus;
        this.modifyTime = modifyTime;
        this.name = name;
        this.phone = phone;
        this.photo_ids = photo_ids;
        this.remark = remark;
        this.sid = sid;
        this.status = status;
        this.subject_type = subject_type;
        this.title = title;
    }
    @Generated(hash = 1243708610)
    public RenYuanInFo() {
    }
    public int getAccountId() {
        return this.accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getCardNumber() {
        return this.cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public int getGender() {
        return this.gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getJobStatus() {
        return this.jobStatus;
    }
    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }
    public long getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhoto_ids() {
        return this.photo_ids;
    }
    public void setPhoto_ids(String photo_ids) {
        this.photo_ids = photo_ids;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getSid() {
        return this.sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getSubject_type() {
        return this.subject_type;
    }
    public void setSubject_type(int subject_type) {
        this.subject_type = subject_type;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


}
