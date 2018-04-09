package com.example.huiyiqiandaotv.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/9.
 */
@Entity
public class SheBeiInFoBean {


    /**
     * accountId : 10000038
     * boxIp :
     * box_id : 0
     * camera_address : rtsp://192.168.2.126:554/user=admin_password=tlJwpbo6_channel=1_stream=0.sdp?real_stream
     * camera_position : 测试1
     * createTime : 1523246686810
     * dtoResult : 0
     * hostId : 10000102
     * id : 10100059
     * modifyTime : 1523246687000
     * modify_time : 1523246687000
     * pageNum : 0
     * pageSize : 0
     * sid : 0
     * status : 1
     * weekDate : undefined-undefined
     * weekDay :
     */
    @Id @NotNull
    private Long id;
    private String boxIp;
    private int box_id;
    private String camera_address;
    private String camera_position;
    private long createTime;
    private int dtoResult;
    private int hostId;
    private long modifyTime;
    private int pageNum;
    private int pageSize;
    private int sid;
    private int status;
    private String weekDate;
    private String weekDay;
    private int bid;
    @Generated(hash = 724913335)
    public SheBeiInFoBean(@NotNull Long id, String boxIp, int box_id, String camera_address,
            String camera_position, long createTime, int dtoResult, int hostId, long modifyTime, int pageNum,
            int pageSize, int sid, int status, String weekDate, String weekDay, int bid) {
        this.id = id;
        this.boxIp = boxIp;
        this.box_id = box_id;
        this.camera_address = camera_address;
        this.camera_position = camera_position;
        this.createTime = createTime;
        this.dtoResult = dtoResult;
        this.hostId = hostId;
        this.modifyTime = modifyTime;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sid = sid;
        this.status = status;
        this.weekDate = weekDate;
        this.weekDay = weekDay;
        this.bid = bid;
    }
    @Generated(hash = 192116694)
    public SheBeiInFoBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBoxIp() {
        return this.boxIp;
    }
    public void setBoxIp(String boxIp) {
        this.boxIp = boxIp;
    }
    public int getBox_id() {
        return this.box_id;
    }
    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }
    public String getCamera_address() {
        return this.camera_address;
    }
    public void setCamera_address(String camera_address) {
        this.camera_address = camera_address;
    }
    public String getCamera_position() {
        return this.camera_position;
    }
    public void setCamera_position(String camera_position) {
        this.camera_position = camera_position;
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
    public int getHostId() {
        return this.hostId;
    }
    public void setHostId(int hostId) {
        this.hostId = hostId;
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
    public String getWeekDate() {
        return this.weekDate;
    }
    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }
    public String getWeekDay() {
        return this.weekDay;
    }
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
    public int getBid() {
        return this.bid;
    }
    public void setBid(int bid) {
        this.bid = bid;
    }

  
}
