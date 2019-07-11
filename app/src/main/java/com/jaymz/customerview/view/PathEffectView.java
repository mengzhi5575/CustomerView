package com.jaymz.customerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jaymz.customerview.R;

/**
 * @author jaymz
 * Created by 2019/07/10
 */
public class PathEffectView extends View {
    private static final String TAG = "DottedLineView";

    private Paint mPaint;
    private Path mPath;
    private Context mContext;
    private PathEffect mEffect;
    private float phase;
    private int mStrokeColor;   //描边颜色
    private int mStrokeWidth;   //描边宽度
    private int mStartPosition; //起始坐标
    private int mEndPosition;   //截至坐标
    private boolean mIsAnim;    //是否有动画
    private int mAdvance;       //点间距
    private int mDashIntervalsOn;//虚线中实线间距
    private int mDashIntervalsOff;//虚线中实线宽度
    private int mCornerRadius;  //折线转角处圆滑角度
    private int mSlideDirection;//动画运动方向
    private int mOrientation;   //线条方向，水平或者垂直
    private int mStyle;         //图形风格，支持实线，折线，虚线，圆点

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DottedLineView);
        mStrokeColor = typedArray.getColor(R.styleable.DottedLineView_strokeColor, Color.GRAY);
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_strokeWidth, 1);
        mStartPosition = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_startPosition, 0);
        mEndPosition = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_endPosition, 0);
        mIsAnim = typedArray.getBoolean(R.styleable.DottedLineView_isAnim, false);
        mAdvance = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_dotAdvance, 10);
        mDashIntervalsOn = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_dashIntervalsOn, 10);
        mDashIntervalsOff = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_dashIntervalsOff, 10);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.DottedLineView_cornerRadius, 10);
        mSlideDirection = typedArray.getInt(R.styleable.DottedLineView_slideDirection, 0);
        mOrientation = typedArray.getInt(R.styleable.DottedLineView_orientation, 0);
        mStyle = typedArray.getInt(R.styleable.DottedLineView_style, 0x001);
        typedArray.recycle();

        Log.d(TAG, "mStrokeColor = " + mStrokeColor);
        Log.d(TAG, "mStrokeWidth = " + mStrokeWidth);
        Log.d(TAG, "mStartPosition = " + mStartPosition);
        Log.d(TAG, "mEndPosition = " + mEndPosition);
        Log.d(TAG, "mIsAnim = " + mIsAnim);
        Log.d(TAG, "mAdvance = " + mAdvance);
        Log.d(TAG, "mStyle = " + mStyle);
        Log.d(TAG, "mCornerRadius = " + mCornerRadius);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(20, 20);
        if (mIsAnim) {
            if (mSlideDirection == 0) {
                phase++;
            } else {
                phase--;
            }
        }
        if (mStyle == 0x001) {
            mEffect = new CornerPathEffect(mCornerRadius);
        } else if (mStyle == 0x002) {
            mEffect = new DiscretePathEffect(2, 10);
        } else if (mStyle == 0x003) {
            mEffect = new DashPathEffect(new float[]{mDashIntervalsOff, mDashIntervalsOn}, phase);
        } else if (mStyle == 0x004) {
            Path path = new Path();
            path.addCircle(0, 0, 3, Path.Direction.CW);
            mEffect = new PathDashPathEffect(path, mAdvance, phase, PathDashPathEffect.Style.TRANSLATE);
        }
        mPath.reset();
        if (mOrientation == 1) {
            int centerX = getWidth() / 2;
            mPath.moveTo(centerX, mStartPosition);
            mPath.lineTo(centerX, getHeight() - mEndPosition);
        } else {
            int centerY = getHeight() / 2;
            mPath.moveTo(mStartPosition, centerY);
            mPath.lineTo(getWidth() - mEndPosition, centerY);
        }
        mPaint.setPathEffect(mEffect);
        canvas.drawPath(mPath, mPaint);
        if (mIsAnim) {
            invalidate();
        }
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mStrokeColor);
//        mPaint.setPathEffect(new CornerPathEffect(20)); //沿途倒角设置圆滑
//        mPaint.setPathEffect(new DiscretePathEffect(20,10));   //离散路径效果，第一个参数越小，杂点密度越高，第二个参数越大，杂点越突出
//        mPaint.setPathEffect(new DashPathEffect(new float[]{20,10},1)); //虚线效果，float数组，第一个参数代表实线长度，第二个代表虚线长度，后续没有数据则重复这两个数值，第二个参数，动态改变会产生动态效果
//        mPaint.setPathEffect(new PathDashPathEffect(path,12,3, PathDashPathEffect.Style.ROTATE));
//        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(path, 12, 1, PathDashPathEffect.Style.TRANSLATE);
//        CornerPathEffect cornerPathEffect = new CornerPathEffect(20);
//        ComposePathEffect composePathEffect = new ComposePathEffect(pathDashPathEffect,cornerPathEffect); //先把路径二变现，再复合路径一效果
//        SumPathEffect sumPathEffect = new SumPathEffect(pathDashPathEffect,cornerPathEffect); //两种路径效果加一起作为路径
//        mPaint.setPathEffect(new SumPathEffect(pathDashPathEffect,cornerPathEffect));
//        mPaint.setPathEffect(new ComposePathEffect(pathDashPathEffect, cornerPathEffect));
    }

}
