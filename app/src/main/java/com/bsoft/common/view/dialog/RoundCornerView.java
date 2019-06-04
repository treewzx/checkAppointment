package com.bsoft.common.view.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


public class RoundCornerView extends FrameLayout {
    private float rx = 0f;
    private float ry = 0f;
    private View mContentView;
    public RoundCornerView(Context context) {
        super(context);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setRadius(float radius){
        setRadius(radius,radius);
    }

    public void setRadius(float rx, float ry) {
       this.rx= rx;
       this.ry= rx;
       setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),rx,ry,Path.Direction.CCW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
