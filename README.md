##CornerFlagView
这是一个自定义的角标的View，有如下特性<br>
        1.占满左右上角，或是只占一部分，使用 **`cfv_fullCorner`**<br>
        2.设置文字颜色，使用 **`cfv_textColor`**<br>
        3.设置字体大小，使用 **`cfv_textSize`**<br>
        4.设置角域背景色，使用 **`cfv_backgroundColor`**<br>
        5.设置占用角域是左上角还是右上角，使用 **`cfv_orientation`**<br>
        6.设置文本， **`cfv_textContent`**<br>
        7.支持**`wrap_content`**<br>
        8.CornerFlagView采用正方形模式处理，在绘制时会用width填充height，使其一致<br>

![范例](https://github.com/xchengx/QiQi/blob/master/screenshots/device-2016-08-01-105302.png)

##使用方法

```xml
  <com.uqi.qiqi.widget.CornerFlagView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:cfv_backgroundColor="#cf0"
      app:cfv_orientation="right"
      app:cfv_textContent="Hello Android"
      app:cfv_textColor="#d12"
      app:cfv_fullCorner="false"
      app:cfv_textSize="16sp"
      />
```

##IOS风格对话框
![范例](https://github.com/xchengx/QiQi/blob/master/screenshots/demo.gif)

###UIAlerView
   ```Java
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
           .show();
```
###UIActionList
```Java
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
            .show();
```

###UIActionSheet
```Java
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
```