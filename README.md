这是一个自定义的角标的View，有如下特性<br>
        1.占满左右上角，或是只占一部分，使用 **`cfv_fullCorner`**
        2.设置文字颜色，使用 **`cfv_textColor`**
        3.设置字体大小，使用 **`cfv_textSize`**
        4.设置角域背景色，使用 **`cfv_backgroundColor`**
        5.设置占用角域是左上角还是右上角，使用 **`cfv_orientation`**
        6.设置文本， **`cfv_textContent`**
        7.支持**`wrap_content`**
        8.CornerFlagView采用正方形模式处理，在绘制时会用width填充height，使其一致

![范例](https://github.com/xchengx/QiQi/blob/master/screenshots/device-2016-07-30-204747.png)

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
