package com.jordan.project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 昕 on 2017/5/6.
 */

public class PathOfParticleGridView extends GridView {

    public PathOfParticleGridView(Context context) {
        super(context);
    }

    public PathOfParticleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathOfParticleGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO 自动生成的构造函数存根
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO 自动生成的方法存根
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
