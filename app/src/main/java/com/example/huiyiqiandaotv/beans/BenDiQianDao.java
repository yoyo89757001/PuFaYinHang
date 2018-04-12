package com.example.huiyiqiandaotv.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/12.
 */
@Entity
public class BenDiQianDao {
    @Id
    @NotNull
    private Long id;
    private String name;
    private String phone;
    private String photo_ids;
    @Generated(hash = 550609702)
    public BenDiQianDao(@NotNull Long id, String name, String phone,
            String photo_ids) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.photo_ids = photo_ids;
    }
    @Generated(hash = 1092204061)
    public BenDiQianDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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

    


}
