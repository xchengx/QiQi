package com.uqi.qiqi.widget.alert;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.uqi.qiqi.R;

import java.util.List;

/**
 * Created by Shuxin on 2016/7/30.
 */
public class UIAlertView implements KeyEvent.Callback, Window.Callback {

    private Activity mContext;
    private FrameLayout viewRoot;
    private View parent;
    private Window xWindow;
    private LayoutInflater xInflater;
    private FrameLayout alertRoot;
    private OnItemClickListener xItemClickListener;
    private OnDismissListener xDismissListener;
    private OnShowListener xOnShowListener;
    private boolean mCancelable = true;
    private AlertItem titleAlertItem;
    private AlertItem cancelAlertItem;
    private AlertItem okAlertItem;
    private AlertItem msgAlertItem;
    private ButtonOrientation orientation = ButtonOrientation.HORIZONTAL;
    public enum ButtonOrientation{
        VERTICAL,HORIZONTAL
    }
    public UIAlertView(Context pContext) {
        if (!(pContext instanceof Activity)) {
            throw new RuntimeException("The context must is a Activity");
        }
        this.mContext = (Activity) pContext;
        xInflater = mContext.getLayoutInflater();
        xWindow = mContext.getWindow();
        //当dialog初始化的时候要占用activity 对keyevent的处理，否则无法监听返回事件
        xWindow.setCallback(this);
        viewRoot = (FrameLayout) xWindow.getDecorView().findViewById(android.R.id.content);
        initRootView();
    }

    private void initRootView() {
        parent = xInflater.inflate(R.layout.layout_ui_alert_root, viewRoot, false);
        alertRoot = (FrameLayout) parent.findViewById(R.id.alert_content);
        View v = parent.findViewById(R.id.alert_root);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if (mCancelable) {//点击对话框外消失
                    dismiss();
                }
            }
        });
        FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.CENTER;
        lParams.setMargins(60, 0, 60, 0);
        alertRoot.setLayoutParams(lParams);
    }
    public UIAlertView setTitle( AlertItem pTitle){
        this.titleAlertItem = pTitle;
        return this;
    }
    public UIAlertView setMessage( AlertItem pMessage){
        this.msgAlertItem = pMessage;
        return this;
    }
    public UIAlertView setCancel( AlertItem pCancel){
        this.cancelAlertItem = pCancel;
        return this;
    }
    public UIAlertView setOk( AlertItem pOk) {
        this.okAlertItem = pOk;
        return this;
    }
    public UIAlertView setOnDismissListener(OnDismissListener pListener){
        this.xDismissListener = pListener;
        return this;
    }
    public UIAlertView setOnItemClickListener(OnItemClickListener pOnItemClickListener){
        this.xItemClickListener = pOnItemClickListener;
        return this;
    }
    public UIAlertView setOnShowListener(OnShowListener pOnShowListener){
        this.xOnShowListener = pOnShowListener;
        return this;
    }
    public UIAlertView build(){
        initAlert();
        return this;
    }
    private void initAlert() {
        View view;
        if(orientation.equals(ButtonOrientation.HORIZONTAL))
            view = xInflater.inflate(R.layout.layout_ui_alert, null, false);
        else
            view = xInflater.inflate(R.layout.layout_ui_alert_vertical, null, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                //占住焦点
            }
        });
        TextView titleTv = (TextView) view.findViewById(R.id.title);
        if (titleAlertItem != null) {
            titleTv.setText(titleAlertItem.getContent());
            titleTv.setTextColor(titleAlertItem.getColor());
            if (titleAlertItem.isBold())
                titleTv.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            titleTv.setVisibility(View.GONE);
        }

        TextView msgTv = (TextView) view.findViewById(R.id.message);
        if (msgAlertItem != null) {
            msgTv.setText(msgAlertItem.getContent());
            msgTv.setTextColor(msgAlertItem.getColor());
            if (msgAlertItem.isBold())
                msgTv.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            msgTv.setVisibility(View.GONE);
        }
        TextView cancelTv = (TextView) view.findViewById(R.id.cancel);
        if(cancelAlertItem==null)
            cancelAlertItem = new AlertItem("取消", Color.rgb(0, 99, 219), true);
        cancelTv.setText(cancelAlertItem.getContent());
        cancelTv.setTextColor(cancelAlertItem.getColor());
        if (cancelAlertItem.isBold())
            cancelTv.setTypeface(Typeface.DEFAULT_BOLD);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                dismiss();
                if (xItemClickListener != null)
                    xItemClickListener.onItemClick(pView, -1);

            }

        });
        View spline = view.findViewById(R.id.spline);
        TextView okTv = (TextView) view.findViewById(R.id.ok);
        if (okAlertItem != null) {
            okTv.setText(okAlertItem.getContent());
            okTv.setTextColor(okAlertItem.getColor());
            if (okAlertItem.isBold())
                okTv.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            spline.setVisibility(View.GONE);
            okTv.setVisibility(View.GONE);
        }
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                dismiss();
                if (xItemClickListener != null) {
                    xItemClickListener.onItemClick(pView, 0);
                }
            }
        });
        alertRoot.addView(view);
    }

    public UIAlertView show() {
        if (viewRoot != null && !isShowing()) {
            viewRoot.addView(parent);
            if(xOnShowListener!=null)
                xOnShowListener.onShow();
        }
        return this;
    }

    private void dismiss() {
        if (viewRoot.findViewById(R.id.alert_root) != null) {
            viewRoot.removeView(parent);
            if (xItemClickListener != null) {
                xDismissListener.onAlertDismiss();
            }
            //将焦点回交给activity
            xWindow.setCallback(mContext);
        }
    }

    private boolean isShowing() {
        if (viewRoot != null)
            return viewRoot.findViewById(R.id.alert_root) != null;
        else
            return false;
    }

    public UIAlertView setCancelable(boolean flag) {
        mCancelable = flag;
        return this;
    }

    public UIAlertView setCanceledOnTouchOutside(boolean cancel) {
        if (cancel && !mCancelable) {
            mCancelable = true;
        }
        return this;
    }

    public void onBackPressed() {
        if (mCancelable) {
            dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            event.startTracking();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int pI, KeyEvent pKeyEvent) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyMultiple(int pI, int pI1, KeyEvent pKeyEvent) {
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (xWindow.superDispatchKeyEvent(event)) {
            return true;
        }
        return event.dispatch(this, xWindow.getDecorView() != null
                ? xWindow.getDecorView().getKeyDispatcherState() : null, this);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent pKeyEvent) {
        if (xWindow.superDispatchKeyShortcutEvent(pKeyEvent)) {
            return true;
        }
        return onKeyShortcut(pKeyEvent.getKeyCode(), pKeyEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent pMotionEvent) {
        if (xWindow.superDispatchTouchEvent(pMotionEvent)) {
            return true;
        }
        return onTouchEvent(pMotionEvent);
    }

    public boolean onTrackballEvent(MotionEvent event) {
        return false;
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mCancelable) {
            dismiss();
            return true;
        }

        return false;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent pMotionEvent) {
        if (xWindow.superDispatchTrackballEvent(pMotionEvent)) {
            return true;
        }
        return onTrackballEvent(pMotionEvent);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent pMotionEvent) {
        if (xWindow.superDispatchGenericMotionEvent(pMotionEvent)) {
            return true;
        }
        return onGenericMotionEvent(pMotionEvent);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent pAccessibilityEvent) {
        return false;
    }

    @Nullable
    @Override
    public View onCreatePanelView(int pI) {
        return null;
    }

    @Override
    public boolean onCreatePanelMenu(int pI, Menu pMenu) {
        return false;
    }

    @Override
    public boolean onPreparePanel(int pI, View pView, Menu pMenu) {
        return false;
    }

    @Override
    public boolean onMenuOpened(int pI, Menu pMenu) {
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int pI, MenuItem pMenuItem) {
        return false;
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams pLayoutParams) {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onWindowFocusChanged(boolean pB) {

    }

    @Override
    public void onAttachedToWindow() {

    }

    @Override
    public void onDetachedFromWindow() {

    }

    @Override
    public void onPanelClosed(int pI, Menu pMenu) {

    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    @Override
    public boolean onSearchRequested(SearchEvent pSearchEvent) {
        return false;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback pCallback) {
        return null;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback pCallback, int pI) {
        return null;
    }

    @Override
    public void onActionModeStarted(ActionMode pActionMode) {

    }

    @Override
    public void onActionModeFinished(ActionMode pActionMode) {

    }
}
