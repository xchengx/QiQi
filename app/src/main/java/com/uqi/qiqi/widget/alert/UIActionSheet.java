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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uqi.qiqi.R;

import java.util.List;

/**
 * Created by Shuxin on 2016/7/31.
 */
public class UIActionSheet implements KeyEvent.Callback, Window.Callback {

    private Activity mContext;
    private FrameLayout viewRoot;
    private View parent;
    private Window xWindow;
    private LayoutInflater xInflater;
    private FrameLayout alertRoot;
    private OnItemClickListener xItemClickListener;
    private OnDismissListener xDismissListener;
    private OnShowListener xOnShowListener;
    private View.OnKeyListener mOnKeyListener;
    private boolean mCancelable = true;
    private boolean mCancelableOnTouchSide = false;
    private TitleAlertItem titleAlertItem;
    private AlertItem cancelAlertItem;
    private List<AlertItem> actionItems;
    private AlertItem msgAlertItem;
    private View layoutParent;
    public UIActionSheet(Context pContext) {
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
    public UIActionSheet setTitle( TitleAlertItem pTitle){
        this.titleAlertItem = pTitle;
        return this;
    }
    public UIActionSheet setMessage( AlertItem pMessage){
        this.msgAlertItem = pMessage;
        return this;
    }
    public UIActionSheet setCancel( AlertItem pCancel){
        this.cancelAlertItem = pCancel;
        return this;
    }
    public UIActionSheet setActions( List<AlertItem> pActions) {
        this.actionItems = pActions;
        return this;
    }
    public UIActionSheet setOnDismissListener(OnDismissListener pListener){
        this.xDismissListener = pListener;
        return this;
    }
    public UIActionSheet setOnItemClickListener(OnItemClickListener pOnItemClickListener){
        this.xItemClickListener = pOnItemClickListener;
        return this;
    }
    public UIActionSheet setOnShowListener(OnShowListener pOnShowListener){
        this.xOnShowListener = pOnShowListener;
        return this;
    }
    public UIActionSheet build(){
        initSheet();
        return this;
    }
    private void initRootView() {
        parent = xInflater.inflate(R.layout.layout_ui_alert_root, viewRoot, false);
        alertRoot = (FrameLayout) parent.findViewById(R.id.alert_content);
        View v = parent.findViewById(R.id.alert_root);
        FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.BOTTOM;
        lParams.setMargins(20, 0, 20, 20);
        alertRoot.setLayoutParams(lParams);
    }
    private void initSheet() {
        layoutParent = xInflater.inflate(R.layout.layout_ui_action_sheet, null, false);
        layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                //占住焦点
            }
        });
        TextView titleTv = (TextView) layoutParent.findViewById(R.id.title);
        if (titleAlertItem != null) {
            titleTv.setText(titleAlertItem.getContent());
            titleTv.setTextColor(titleAlertItem.getColor());
            TitleAlertItem.drawDrawable(mContext,titleTv,titleAlertItem);
            if (titleAlertItem.isBold())
                titleTv.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            titleTv.setVisibility(View.GONE);
        }

        TextView msgTv = (TextView) layoutParent.findViewById(R.id.message);
        if (msgAlertItem != null) {
            msgTv.setText(msgAlertItem.getContent());
            msgTv.setTextColor(msgAlertItem.getColor());
            if (msgAlertItem.isBold())
                msgTv.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            msgTv.setVisibility(View.GONE);
        }
        TextView cancelTv = (TextView) layoutParent.findViewById(R.id.cancel);
        if(cancelAlertItem == null)
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
        ListView lv = (ListView)layoutParent.findViewById(R.id.actions);
        lv.setAdapter(new ActionAdapter(mContext,actionItems));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pAdapterView, View pView, int pI, long pL) {
                dismiss();
                if (xItemClickListener != null)
                    xItemClickListener.onItemClick(pView, pI);
            }
        });
        Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.push_bottom_in);
        layoutParent.startAnimation(anim);
        alertRoot.addView(layoutParent);
    }

    public UIActionSheet show() {
        if (viewRoot != null && !isShowing()) {
            viewRoot.addView(parent);
            if(xOnShowListener!=null)xOnShowListener.onShow();
        }
        return this;
    }

    private void dismiss() {
        if (viewRoot!=null && viewRoot.findViewById(R.id.alert_root) != null) {
            Animation anim = AnimationUtils.loadAnimation(mContext,R.anim.push_bottom_out);
            if(layoutParent!=null)layoutParent.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation pAnimation) {

                }

                @Override
                public void onAnimationEnd(Animation pAnimation) {
                   if(parent!=null) ((ViewGroup)parent).removeView(layoutParent);
                    if(viewRoot!=null)viewRoot.removeView(parent);
                    if (xItemClickListener != null) {
                        xDismissListener.onAlertDismiss();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation pAnimation) {

                }
            });

            //将焦点回交给activity
            if(xWindow!=null)xWindow.setCallback(mContext);
        }
    }

    private boolean isShowing() {
        if (viewRoot != null)
            return viewRoot.findViewById(R.id.alert_root) != null;
        else
            return false;
    }

    public UIActionSheet setCancelable(boolean flag) {
        mCancelable = flag;
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
