package com.uqi.qiqi.widget.alert;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by Shuxin on 2016/7/30.
 */
public class TitleAlertItem extends AlertItem {
    private int resid=-1;
    public enum Align{
        left,top,right,botton;
    }
    private Align align = Align.left;

    public TitleAlertItem(String pContent, int pResid, Align pAlign) {
        super(pContent);
        resid = pResid;
        align = pAlign;
    }

    public TitleAlertItem(String pContent, int pColor, int pResid, Align pAlign) {
        super(pContent, pColor);
        resid = pResid;
        align = pAlign;
    }

    public TitleAlertItem(String pContent, boolean pIsBold, int pResid, Align pAlign) {
        super(pContent, pIsBold);
        resid = pResid;
        align = pAlign;
    }

    public TitleAlertItem(String pContent, int pColor, boolean pIsBold, int pResid, Align pAlign) {
        super(pContent, pColor, pIsBold);
        resid = pResid;
        align = pAlign;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int pResid) {
        resid = pResid;
    }
    public Align getAlign() {
        return align;
    }

    public void setAlign(Align pAlign) {
        align = pAlign;
    }
    public static void drawDrawable(Context context,TextView view, TitleAlertItem ai){
        TitleAlertItem.Align align = ai.getAlign();
        if(ai.getResid()==-1){
            return;
        }
        Drawable drawable = ContextCompat.getDrawable(context,ai.getResid());
        drawable.setBounds(0, 0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawablePadding(10);
        if(align.equals(Align.left)){
            view.setCompoundDrawables(drawable,null,null,null);
        }else if(align.equals(Align.top)){
            view.setCompoundDrawables(null,drawable,null,null);
        }else if(align.equals(Align.right)){
            view.setCompoundDrawables(null,null,drawable,null);
        }else if(align.equals(Align.botton)){
            view.setCompoundDrawables(null,null,null,drawable);
        }
    }
}
