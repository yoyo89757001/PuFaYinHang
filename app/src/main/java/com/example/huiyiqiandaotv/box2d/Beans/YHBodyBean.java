package com.example.huiyiqiandaotv.box2d.Beans;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Administrator on 2018/3/23.
 */

public class YHBodyBean {
   private long id;
   private float runTime;
   private String name;
   private Image image;  //头像
   private Image sprite; //背景
    private float wdith;
    private float hight;
    private String type;
    private float bg_X;
    private float bg_Y;
    private float touxiang_X;
    private float touxiang_Y;
    private float wz_X;
    private float wz_Y;

    public float getBg_X() {
        return bg_X;
    }

    public void setBg_X(float bg_X) {
        this.bg_X = bg_X;
    }

    public float getBg_Y() {
        return bg_Y;
    }

    public void setBg_Y(float bg_Y) {
        this.bg_Y = bg_Y;
    }

    public float getTouxiang_X() {
        return touxiang_X;
    }

    public void setTouxiang_X(float touxiang_X) {
        this.touxiang_X = touxiang_X;
    }

    public float getTouxiang_Y() {
        return touxiang_Y;
    }

    public void setTouxiang_Y(float touxiang_Y) {
        this.touxiang_Y = touxiang_Y;
    }

    public float getWz_X() {
        return wz_X;
    }

    public void setWz_X(float wz_X) {
        this.wz_X = wz_X;
    }

    public float getWz_Y() {
        return wz_Y;
    }

    public void setWz_Y(float wz_Y) {
        this.wz_Y = wz_Y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getWdith() {
        return wdith;
    }

    public void setWdith(float wdith) {
        this.wdith = wdith;
    }

    public float getHight() {
        return hight;
    }

    public void setHight(float hight) {
        this.hight = hight;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getRunTime() {
        return runTime;
    }

    public void setRunTime(float runTime) {
        this.runTime = runTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
