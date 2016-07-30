package com.uqi.qiqi.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.uqi.qiqi.R;

/**
 * Created by Shuxin on 2016/7/29.
 */
public class CornerFlagView extends View {
    public enum Orientation{
        RIGHT(0),LEFT(1);
        private int value;
        private Orientation(int value){
            this.value = value;
        }
    }
    private Context xContext;
    /**
     * View 的宽高
     **/
    private int width, height;

    /**
     * 需要绘制的文本
     */
    private String textContent = "";
    /**
     * 文本的字体大小
     */
    private float textSize = 15;
    /**
     * 背景的颜色
     */
    private int backGroundColor = Color.RED;
    /**
     * 文本颜色
     **/
    private int textColor = Color.WHITE;
    /**
     * 是否占满全角
     */
    private boolean isFullCorner = false;
    /**
     * 文本方向，只有向左倾斜和向右倾斜
     **/
    private int orientation = Orientation.RIGHT.value;

    /**
     * 处理文本的画笔
     */
    private Paint textPaint;
    /**
     * 处理背景的画笔
     */
    private Paint backGroundPaint;

    public void setTextContent(String pTextContent) {
        this.textContent = pTextContent;
        invalidate();
    }

    public void setFullCorner(boolean pFullCorner) {
        isFullCorner = pFullCorner;
        invalidate();
    }

    public void setOrientation(Orientation pOrientation) {
        orientation = pOrientation.value;
        invalidate();
    }

    public void setBackGroundColor(int pBackGroundColor) {
        this.backGroundColor = pBackGroundColor;
        invalidate();
    }

    public void setTextColor(int pTextColor) {
        this.textColor = pTextColor;
        invalidate();
    }

    public void setTextSize(float pTextSize) {
        Resources r;
        if(xContext==null){
            r = Resources.getSystem();
        }else {
            r = xContext.getResources();
        }
        this.textSize = pTextSize*r.getDisplayMetrics().scaledDensity;
        invalidate();
    }
    public CornerFlagView(Context context) {
        super(context);
        this.xContext = context;
        intPaint();
    }

    public CornerFlagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.xContext = context;
        initAttr(attrs);
        intPaint();
    }

    public CornerFlagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.xContext = context;
        initAttr(attrs);
        intPaint();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray ta = xContext.obtainStyledAttributes(attrs, R.styleable.CornerFlagView);
        textSize = ta.getDimensionPixelSize(R.styleable.CornerFlagView_cfv_textSize, 15);
        textContent = ta.getString(R.styleable.CornerFlagView_cfv_textContent);
        textColor = ta.getColor(R.styleable.CornerFlagView_cfv_textColor, Color.BLACK);
        backGroundColor = ta.getColor(R.styleable.CornerFlagView_cfv_backgroundColor, Color.RED);
        isFullCorner = ta.getBoolean(R.styleable.CornerFlagView_cfv_fullCorner, false);
        orientation = ta.getInt(R.styleable.CornerFlagView_cfv_orientation, 0);
        ta.recycle();
    }

    private void intPaint() {
        backGroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backGroundPaint.setColor(backGroundColor);
        backGroundPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Paint pTextPaint = new Paint();
        pTextPaint.setTextAlign(Paint.Align.CENTER);
        pTextPaint.setTextSize(textSize);
        Rect mrect = new Rect();
        pTextPaint.getTextBounds(textContent, 0, textContent.length(), mrect);

        int specmode = MeasureSpec.getMode(widthMeasureSpec);
        if(specmode == MeasureSpec.AT_MOST){
            Resources r;
            if(xContext==null){
                r = Resources.getSystem();
            }else {
                r = xContext.getResources();
            }
            int padding =  (int)(8*r.getDisplayMetrics().density+0.5f);
            int contentWidth = mrect.width()+ padding;
            int contentHeight = mrect.height() + padding;
            int width = (int)Math.sqrt(contentWidth*contentWidth+contentHeight*contentHeight);
            setMeasuredDimension(width,width);
        }else {
            setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        backGroundPaint.setColor(backGroundColor);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        if (textContent == null) textContent = "";
        Path p = new Path();
        if (orientation == Orientation.RIGHT.value) {
            p.moveTo(0, 0);
            if (!isFullCorner) {
                p.lineTo(width / 2, 0);
                p.lineTo(width, height / 2);
            } else {
                p.lineTo(width, 0);
            }
            p.lineTo(width, height);
            p.close();

        } else {
            p.moveTo(width, 0);
            if (!isFullCorner) {
                p.lineTo(width / 2, 0);
                p.lineTo(0, height / 2);
            } else {
                p.lineTo(0, 0);
            }
            p.lineTo(0, height);
            p.close();
        }
        canvas.drawPath(p, backGroundPaint);
        Rect mrect = new Rect();
        textPaint.getTextBounds(textContent, 0, textContent.length(), mrect);
        if (orientation == Orientation.RIGHT.value) {
            canvas.rotate(45, 5 * width / 8, 3 * width / 8);
            canvas.drawText(textContent, 5 * width / 8, 3 * width /8 + mrect.height() / 3, textPaint);
        } else {
            canvas.rotate(-45, 3 * width / 8, 3 * width / 8);
            canvas.drawText(textContent, 3 * width / 8, 3 * width /8 + mrect.height() / 3, textPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = w;
    }
}
