package com.uqi.qiqi;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uqi.qiqi.widget.CornerFlagView;

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
    }
}
