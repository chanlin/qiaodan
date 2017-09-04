package com.jordan.project.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.jordan.project.entity.LineEntity;
import com.jordan.project.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MAChart extends GridChart {
    /**
     * 显示数据
     */
    private List<LineEntity> lineData;

    /**
     * 点数
     */
    private int maxPointNum;

    /**
     * 最低价格
     */
    private int minValue;

    /**
     * 最高价格
     */
    private int maxValue;

    ArrayList<HashMap<String, Object>> listItem;

    public MAChart(Context context) {
        super(context);
    }

    public MAChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MAChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制平面
        if (null != this.lineData) {
            drawLines(canvas);
        }
    }

    protected void drawLines(Canvas canvas) {
        // 点线距离
        float lineLength = ((super.getWidth() - super.getAxisMarginLeft() - this.getMaxPointNum()) / this.getMaxPointNum()) - 1;
//		LogUtils.showLog("jumpdata","super.getWidth()："+super.getWidth());
//		LogUtils.showLog("jumpdata","super.getAxisMarginLeft()："+super.getAxisMarginLeft());
//		LogUtils.showLog("jumpdata","this.getMaxPointNum()："+this.getMaxPointNum());
//		LogUtils.showLog("jumpdata","super.getWidth() - super.getAxisMarginLeft()-this.getMaxPointNum()："
//				+(super.getWidth() - super.getAxisMarginLeft()-this.getMaxPointNum()));
//		LogUtils.showLog("jumpdata","lineLength："+lineLength);
        // 起始位置
        float startX;

        //逐条输�?MA线
        for (int i = 0; i < lineData.size(); i++) {
            LineEntity line = (LineEntity) lineData.get(i);
            if (line.isDisplay()) {
                Paint mPaint = new Paint();
                mPaint.setColor(line.getLineColor());
                mPaint.setAntiAlias(true);
                List<Float> lineData = line.getLineData();
                //输�?�?��线
                startX = 0;
//                LogUtils.showLog("jumpdata","super.getAxisMarginLeft()："+super.getAxisMarginLeft());
//                LogUtils.showLog("jumpdata","lineLength/ 2f："+lineLength/ 2f);
//                LogUtils.showLog("jumpdata","startX："+startX);
                //定义起始点
                PointF ptFirst = null;
                if (lineData != null) {
                    for (int j = 0; j < lineData.size(); j++) {
                        float value = lineData.get(j).floatValue() * 100;
                        LogUtils.showLog("jumpdata", "value:" + value);
                        //获取终点Y坐�?
                        float valueY = (float) ((1f - (value - this.getMinValue())
                                / (this.getMaxValue() - this.getMinValue()))
                                * (super.getHeight() - super.getAxisMarginBottom()));

                        LogUtils.showLog("jumpdata", "(value - this.getMinValue())："
                                + (value - this.getMinValue()));
                        LogUtils.showLog("jumpdata", "this.getMaxValue() - this.getMinValue()："
                                + (this.getMaxValue() - this.getMinValue()));
                        LogUtils.showLog("jumpdata", "(1f-(value - this.getMinValue())/(this.getMaxValue() - this.getMinValue()))："
                                + (1f - (value - this.getMinValue())
                                / (this.getMaxValue() - this.getMinValue())));
                        LogUtils.showLog("jumpdata", "(super.getHeight() - super.getAxisMarginBottom())："
                                + (super.getHeight() - super.getAxisMarginBottom()));
                        LogUtils.showLog("jumpdata", "startX：" + startX);
                        LogUtils.showLog("jumpdata", "valueY：" + valueY);
                        //绘制线条
                        if (j > 0) {
                            canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY, mPaint);
//							mPaint.setColor(Color.RED);//设置画笔颜色  
//							mPaint.setStrokeWidth((float)20.0);//线宽  
//							canvas.drawPoint(startX,valueY,mPaint);
                            LogUtils.showLog("jumpdata", "ptFirst.x：" + ptFirst.x);
                            LogUtils.showLog("jumpdata", "ptFirst.y：" + ptFirst.y);
                        }
                        canvas.drawCircle(startX, valueY, 5, mPaint);
                        //重置起始点
                        ptFirst = new PointF(startX, valueY);
                        //X位移
                        startX = startX + 1 + lineLength;
                        LogUtils.showLog("jumpdata", "startX位移动后：" + startX);
                    }
                }
            }
        }
    }


    public List<LineEntity> getLineData() {
        return lineData;
    }

    public void setLineData(List<LineEntity> lineData) {
        this.lineData = lineData;
    }

    public int getMaxPointNum() {
        return maxPointNum;
    }

    public void setMaxPointNum(int maxPointNum) {
        this.maxPointNum = maxPointNum;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
