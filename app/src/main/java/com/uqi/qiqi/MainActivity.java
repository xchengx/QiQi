package com.uqi.qiqi;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uqi.qiqi.widget.CornerFlagView;
import com.uqi.qiqi.widget.alert.AlertItem;
import com.uqi.qiqi.widget.alert.OnAlertDismissListener;
import com.uqi.qiqi.widget.alert.OnItemClickListener;
import com.uqi.qiqi.widget.alert.UIAlert;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        View decorView = getWindow().getDecorView();
//        FrameLayout contentParent = (FrameLayout) decorView.findViewById(android.R.id.content);
//
//        TextView x = new TextView(this);
//        x.setText("这是一个TextView\n通过DecorView将其绘制到了Activity上层");
//        x.setGravity(Gravity.CENTER);
//        x.setBackgroundColor(Color.RED);
//        x.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
//        contentParent.addView(x);
        new UIAlert(this, new AlertItem("新标题"), new AlertItem("新内容", Color.RED), new AlertItem("取消"), new AlertItem("非常确定",Color.BLUE,true), new OnItemClickListener() {
            @Override
            public void onItemClick(View pView, int position) {
                Log.e("--->", "------------>" + position);
            }
        }, new OnAlertDismissListener() {
            @Override
            public void onAlertDismiss() {
                Log.e("--->", "------------>dismiss");
            }
        });
    }
}
