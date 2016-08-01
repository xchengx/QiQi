package com.uqi.qiqi.widget.alert;

import android.view.View;

/**
 * Created by Shuxin on 2016/7/30.
 */
public interface OnItemClickListener {
    /**position 返回-1时为点击取消按钮*/
    public void onItemClick(View pView, int position);
}
