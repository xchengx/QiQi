package com.uqi.qiqi;

import android.content.Intent;
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
import com.uqi.qiqi.widget.alert.TitleAlertItem;
import com.uqi.qiqi.widget.alert.UIActionList;
import com.uqi.qiqi.widget.alert.UIActionSheet;
import com.uqi.qiqi.widget.alert.UIAlertView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<AlertItem> actions = new ArrayList<AlertItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actions.add(new AlertItem("Action1",Color.RED));
        actions.add(new AlertItem("Action2",Color.BLUE));
        actions.add(new AlertItem("Action3",Color.BLACK));
        actions.add(new AlertItem("Action4",Color.GRAY));
        actions.add(new AlertItem("Action5"));
        actions.add(new AlertItem("Action6"));
        actions.add(new AlertItem("Action7"));
        actions.add(new AlertItem("Action8"));
        findViewById(R.id.showcorner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Intent mIntent = new Intent(MainActivity.this,CornerActivity.class);
                startActivity(mIntent);
            }
        });
        findViewById(R.id.showdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                new UIAlertView(MainActivity.this)
                        .setTitle(new TitleAlertItem("Ttile",R.mipmap.ic_launcher,TitleAlertItem.Align.left))
                        .setMessage(new AlertItem("这个地方是放提示的！"))
                        .setOk(new AlertItem("OK",Color.RED))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setOnShowListener(new OnShowListener() {
                            @Override
                            public void onShow() {
                                Toast.makeText(MainActivity.this,"UIAlertView 显示",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View pView, int position) {
                                Toast.makeText(MainActivity.this,"UIAlertView 点击了>"+position,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();;
            }
        });
        findViewById(R.id.showvalert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                new UIActionList(MainActivity.this)
                        .setTitle(new TitleAlertItem("Ttile",R.mipmap.ic_launcher, TitleAlertItem.Align.left))
                        .setMessage(new AlertItem("msg"))
                        .setActions(actions)
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setOnShowListener(new OnShowListener() {
                            @Override
                            public void onShow() {
                                Log.e("----------->","----->show");
                            }
                        })
                        .setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onAlertDismiss() {
                                Toast.makeText(MainActivity.this,"UIActionList Dismiss 了",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();;
            }
        });
        findViewById(R.id.showActionSheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {

                new UIActionSheet(MainActivity.this)
                        .setTitle(new TitleAlertItem("",R.mipmap.ic_launcher,TitleAlertItem.Align.left))
                        .setMessage(new AlertItem("这是一个提示语"))
                        .setActions(actions)
                        .setCancelable(true)
                        .setOnShowListener(new OnShowListener() {
                            @Override
                            public void onShow() {
                                Log.e("----------->","----->show");
                            }
                        })
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View pView, int position) {
                                Toast.makeText(MainActivity.this,"UIActionSheet 点击了>"+position,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onAlertDismiss() {
                                Log.e("----------->","----->Dismiss");
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
