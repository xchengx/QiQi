package com.uqi.qiqi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.uqi.qiqi.widget.alert.AlertItem;
import com.uqi.qiqi.widget.alert.OnDismissListener;
import com.uqi.qiqi.widget.alert.OnItemClickListener;
import com.uqi.qiqi.widget.alert.OnShowListener;
import com.uqi.qiqi.widget.alert.UIActionSheet;
import com.uqi.qiqi.widget.alert.UIAlertView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.showdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                new UIAlertView(MainActivity.this)
                        .setTitle(new AlertItem("Title"))
                        .setMessage(new AlertItem("msg"))
                        .setOk(new AlertItem("OK"))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setOnShowListener(new OnShowListener() {
                            @Override
                            public void onShow() {
                                Log.e("----------->","----->show");
                            }
                        })
                        .build()
                        .show();;
            }
        });
        findViewById(R.id.showActionSheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                List<AlertItem> actions = new ArrayList<AlertItem>();
                actions.add(new AlertItem("Action1",Color.RED));
                actions.add(new AlertItem("Action2",Color.BLUE));
                actions.add(new AlertItem("Action3",Color.BLACK));
                actions.add(new AlertItem("Action4",Color.GRAY));
                actions.add(new AlertItem("Action5"));

                new UIActionSheet(MainActivity.this)
                        .setTitle(new AlertItem("Title"))
                        .setActions(actions)
                        .setCancelable(true)
                        .setOnShowListener(new OnShowListener() {
                            @Override
                            public void onShow() {
                                Log.e("----------->","----->show");
                            }
                        })
                        .build()
                        .show();
            }
        });
//        View decorView = getWindow().getDecorView();
//        FrameLayout contentParent = (FrameLayout) decorView.findViewById(android.R.id.content);
//
//        TextView x = new TextView(this);
//        x.setText("这是一个TextView\n通过DecorView将其绘制到了Activity上层");
//        x.setGravity(Gravity.CENTER);
//        x.setBackgroundColor(Color.RED);
//        x.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
//        contentParent.addView(x);

    }
    private long exitTime = 0;

    /**
     * 捕捉返回事件按钮
     *
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
//                this.exitApp();
//            }
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
