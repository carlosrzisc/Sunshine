package com.owlbyte.sunshine.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

/**
 * Created by carlos on 6/9/15.
 */
public class MyView extends View {
    private Paint drawPaint;
    private Path path;
    private float direction;
    private String windSpeedDir;
    // setup initial color
    private final int paintColor = Color.GRAY;

    public MyView(Context context) {
        super(context);
        setupPaint();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupPaint();
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(7);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec){
        int desiredWidth = 100;
        int desiredHeight = 100;

        int hSpecMode = MeasureSpec.getMode(hMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(hMeasureSpec);
        int myHeight = hSpecSize;

        if(hSpecMode == MeasureSpec.EXACTLY) {
            myHeight = hSpecSize;
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            myHeight = Math.min(desiredHeight, hSpecSize);
        }

        int wSpecMode = MeasureSpec.getMode(wMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(wMeasureSpec);
        int myWidth = wSpecSize;

        if(wSpecMode == MeasureSpec.EXACTLY) {
            myWidth = wSpecSize;
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            myWidth = Math.min(desiredWidth, wSpecSize);
        }
        setMeasuredDimension(myWidth, myHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int insideRectangleOffset = 6;
        path = new Path();
        path.moveTo(getWidth() * .5f - insideRectangleOffset, getHeight() - insideRectangleOffset);
        path.lineTo(getWidth() * .5f - insideRectangleOffset, insideRectangleOffset);
        path.lineTo(getWidth() * .75f - insideRectangleOffset, getHeight() * .3f - insideRectangleOffset);
        path.moveTo(getWidth() * .5f - insideRectangleOffset, insideRectangleOffset);
        path.lineTo(getWidth() * .25f - insideRectangleOffset, getHeight() * .3f - insideRectangleOffset);
        path.close();

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        canvas.translate(x, y);
        canvas.rotate(direction);
        canvas.translate(-x, -y);
        canvas.save();
        canvas.drawPath(path, drawPaint);
        canvas.restore();
    }

    public void setWindDirection(String direction) {
        if (direction.equalsIgnoreCase(Utility.NORTH)){
            this.direction = 0f;
            windSpeedDir = getContext().getString(R.string.wind_dir_north);
        } else if (direction.equalsIgnoreCase(Utility.NORTH_EAST)){
            this.direction = 45f;
            windSpeedDir = getContext().getString(R.string.wind_dir_north_east);
        } else if (direction.equalsIgnoreCase(Utility.NORTH_WEST)){
            this.direction = 315f;
            windSpeedDir = getContext().getString(R.string.wind_dir_north_west);
        } else if (direction.equalsIgnoreCase(Utility.EAST)){
            this.direction = 90f;
            windSpeedDir = getContext().getString(R.string.wind_dir_east);
        } else if (direction.equalsIgnoreCase(Utility.WEST)){
            this.direction = 270f;
            windSpeedDir = getContext().getString(R.string.wind_dir_west);
        } else if (direction.equalsIgnoreCase(Utility.SOUTH)){
            this.direction = 180f;
            windSpeedDir = getContext().getString(R.string.wind_dir_south);
        } else if (direction.equalsIgnoreCase(Utility.SOUTH_EAST)){
            this.direction = 135f;
            windSpeedDir = getContext().getString(R.string.wind_dir_south_east);
        } else if (direction.equalsIgnoreCase(Utility.SOUTH_WEST)){
            this.direction = 225f;
            windSpeedDir = getContext().getString(R.string.wind_dir_south_west);
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.getText().add(windSpeedDir);
        return true;
    }
}
