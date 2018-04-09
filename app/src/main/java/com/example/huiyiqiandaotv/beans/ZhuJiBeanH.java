package com.example.huiyiqiandaotv.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/8.
 */

@Entity
public class ZhuJiBeanH {


    /**
     * accountId : 10000038
     * createTime : 1523189324608
     * dtoResult : 0
     * hostUrl : http://192.168.2.50
     * id : 10000102
     * machineCode : 460010132637533
     * machineName : 广告机1
     * modifyTime : 1523189324608
     * pageNum : 0
     * pageSize : 0
     * pwd : 123456
     * sid : 0
     * status : 1
     * username : test@megvii.com
     */

    private int accountId;
    private long createTime;
    private int dtoResult;
    private String hostUrl;
    @Id
    @NotNull
    private Long id;
    private String machineCode;
    private String machineName;
    private long modifyTime;
    private int pageNum;
    private int pageSize;
    private String pwd;
    private int status;
    private String username;
    @Generated(hash = 1497634750)
    public ZhuJiBeanH(int accountId, long createTime, int dtoResult, String hostUrl,
            @NotNull Long id, String machineCode, String machineName,
            long modifyTime, int pageNum, int pageSize, String pwd, int status,
            String username) {
        this.accountId = accountId;
        this.createTime = createTime;
        this.dtoResult = dtoResult;
        this.hostUrl = hostUrl;
        this.id = id;
        this.machineCode = machineCode;
        this.machineName = machineName;
        this.modifyTime = modifyTime;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pwd = pwd;
        this.status = status;
        this.username = username;
    }
    @Generated(hash = 59330943)
    public ZhuJiBeanH() {
    }
    public int getAccountId() {
        return this.accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public int getDtoResult() {
        return this.dtoResult;
    }
    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }
    public String getHostUrl() {
        return this.hostUrl;
    }
    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMachineCode() {
        return this.machineCode;
    }
    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
    public String getMachineName() {
        return this.machineName;
    }
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
    public long getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
    public int getPageNum() {
        return this.pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return this.pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


}
