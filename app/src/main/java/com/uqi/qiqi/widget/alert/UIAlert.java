package com.uqi.qiqi.widget.alert;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.uqi.qiqi.R;

/**
 * Created by Shuxin on 2016/7/30.
 */
public class UIAlert {
    private Activity mContext;
    private FrameLayout viewRoot;
    private View parent;
    private Window xWindow;
    private LayoutInflater xInflater;
    private FrameLayout alertRoot;
    private OnItemClickListener xItemClickListener;
    private OnAlertDismissListener xDismissListener;
    private AlertItem titleAlertItem;
    private AlertItem cancelAlertItem;
    private AlertItem okAlertItem;
    private AlertItem msgAlertItem;
    public UIAlert(Context pContext,AlertItem title,AlertItem msg,AlertItem cancel,AlertItem ok,OnItemClickListener pItemClickListener,OnAlertDismissListener pDismissListener){
        if(!(pContext instanceof Activity)){
            throw new RuntimeException("The context must is a Activity");
        }
        this.mContext = (Activity) pContext;

        this.titleAlertItem = title;
        this.msgAlertItem = msg;
        this.cancelAlertItem  = cancel;
        this.okAlertItem = ok;

        this.xItemClickListener = pItemClickListener;
        this.xDismissListener = pDismissListener;
        xInflater = mContext.getLayoutInflater();
        xWindow = mContext.getWindow();
        viewRoot = (FrameLayout)xWindow.getDecorView().findViewById(android.R.id.content);
        initRootView();
        initAlert();
    }
    private void initRootView(){
         parent = xInflater.inflate(R.layout.layout_ui_alert_root,viewRoot,false);
        alertRoot = (FrameLayout) parent.findViewById(R.id.alert_content);

        FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.CENTER;
        lParams.setMargins(60,0,60,0);
        alertRoot.setLayoutParams(lParams);
        viewRoot.addView(parent);
    }
    private void initAlert(){
        View view = xInflater.inflate(R.layout.layout_ui_alert,null,false);
        TextView titleTv = (TextView)view.findViewById(R.id.title);
        titleTv.setText(titleAlertItem.getContent());
        titleTv.setTextColor(titleAlertItem.getColor());
        if(titleAlertItem.isBold())
            titleTv.setTypeface(Typeface.DEFAULT_BOLD);
        TextView msgTv = (TextView)view.findViewById(R.id.message);
        msgTv.setText(msgAlertItem.getContent());
        msgTv.setTextColor(msgAlertItem.getColor());
        if(msgAlertItem.isBold())
            msgTv.setTypeface(Typeface.DEFAULT_BOLD);
        TextView cancelTv = (TextView)view.findViewById(R.id.cancel);
        cancelTv.setText(cancelAlertItem.getContent());
        cancelTv.setTextColor(cancelAlertItem.getColor());
        if(cancelAlertItem.isBold())
            cancelTv.setTypeface(Typeface.DEFAULT_BOLD);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                dismiss();
                if(xItemClickListener!=null)
                    xItemClickListener.onItemClick(pView,-1);

            }

        });
        TextView okTv = (TextView)view.findViewById(R.id.ok);
        okTv.setText(okAlertItem.getContent());
        okTv.setTextColor(okAlertItem.getColor());
        if(okAlertItem.isBold())
            okTv.setTypeface(Typeface.DEFAULT_BOLD);
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                dismiss();
                if(xItemClickListener!=null){
                    xItemClickListener.onItemClick(pView,0);
                }
            }
        });
        alertRoot.addView(view);
    }
    private void dismiss(){
        if(viewRoot.findViewById(R.id.alert_root)!=null) {
            viewRoot.removeView(parent);
            if(xItemClickListener!=null){
                xDismissListener.onAlertDismiss();
            }
        }
    }
    private boolean isShowning(){
        if(viewRoot!=null)
            return  viewRoot.findViewById(R.id.alert_root)!=null;
        else
            return false;
    }
}
