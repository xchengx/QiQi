package com.uqi.qiqi.widget.alert;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by Shuxin on 2016/7/30.
 */
public class AlertItem {
    private String content;
    private int color = Color.rgb(0x22,0x22,0x22);
    private boolean isBold = false;

    public AlertItem(String pContent) {
        this(pContent,Color.rgb(0x22,0x22,0x22));
    }

    public AlertItem(String pContent, int pColor) {
        content = pContent;
        color = pColor;
    }
    public AlertItem(String pContent, boolean pIsBold) {
        content = pContent;
        isBold = pIsBold;
    }


    public AlertItem(String pContent, int pColor, boolean pIsBold) {
        content = pContent;
        color = pColor;
        isBold = pIsBold;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean pBold) {
        isBold = pBold;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String pContent) {
        content = pContent;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int pColor) {
        color = pColor;
    }


}
