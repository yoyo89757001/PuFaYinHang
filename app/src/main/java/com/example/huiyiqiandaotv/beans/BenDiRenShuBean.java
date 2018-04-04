package com.example.huiyiqiandaotv.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/26.
 */
@Entity
public class BenDiRenShuBean {
    @Id
    @NotNull
    private Long id;
    private int y1;
    private int yShen;
    private int yShi;
    private int yTeyao;
    private int n1;
    private int nShen;
    private int nShi;
    private int nTeyao;
    @Generated(hash = 556907398)
    public BenDiRenShuBean(@NotNull Long id, int y1, int yShen, int yShi,
            int yTeyao, int n1, int nShen, int nShi, int nTeyao) {
        this.id = id;
        this.y1 = y1;
        this.yShen = yShen;
        this.yShi = yShi;
        this.yTeyao = yTeyao;
        this.n1 = n1;
        this.nShen = nShen;
        this.nShi = nShi;
        this.nTeyao = nTeyao;
    }
    @Generated(hash = 1553308740)
    public BenDiRenShuBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getY1() {
        return this.y1;
    }
    public void setY1(int y1) {
        this.y1 = y1;
    }
    public int getYShen() {
        return this.yShen;
    }
    public void setYShen(int yShen) {
        this.yShen = yShen;
    }
    public int getYShi() {
        return this.yShi;
    }
    public void setYShi(int yShi) {
        this.yShi = yShi;
    }
    public int getYTeyao() {
        return this.yTeyao;
    }
    public void setYTeyao(int yTeyao) {
        this.yTeyao = yTeyao;
    }
    public int getN1() {
        return this.n1;
    }
    public void setN1(int n1) {
        this.n1 = n1;
    }
    public int getNShen() {
        return this.nShen;
    }
    public void setNShen(int nShen) {
        this.nShen = nShen;
    }
    public int getNShi() {
        return this.nShi;
    }
    public void setNShi(int nShi) {
        this.nShi = nShi;
    }
    public int getNTeyao() {
        return this.nTeyao;
    }
    public void setNTeyao(int nTeyao) {
        this.nTeyao = nTeyao;
    }


    

}
