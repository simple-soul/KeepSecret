package com.example.simple_soul.keepsecret.custom;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by simple_soul on 2017/3/27.
 */

public class MyScrollView extends LinearLayout
{
    private Scroller mScroller;
    private int winWidth;
    private int winHeight;
    private int mLastX;
    private int mStart;
    private int mEnd;
    private long mStartTime;
    private long mLastTime;
    private VelocityTracker tracker;
    private int mMaxVelocity;
    private int xVelocity;
    private int mMinVelocity;

    public MyScrollView(Context context)
    {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mScroller = new Scroller(context);

        //获取屏幕宽高
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        winWidth = dm.widthPixels;
        winHeight = dm.heightPixels;
        Log.i("main", "winHeight"+winHeight);

        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int) event.getX();

        acquireVelocityTracker(event);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mStart = getScrollX();
                mStartTime = SystemClock.uptimeMillis();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                }
                Log.i("main", "getScrollX()=" + getScrollX());
                int dx = mLastX - x;
                if (getScrollX() < -20 && dx < 0)
                {
                    dx = 0;
                }
                if (getScrollY() > getWidth() - winWidth + 20 && dx > 0)
                {
                    dx = 0;
                }
                scrollBy(dx, 0);
                mLastX = x;
                break;

            case MotionEvent.ACTION_UP:

                tracker.computeCurrentVelocity(300, mMaxVelocity);
                xVelocity = (int) tracker.getXVelocity();

                if (Math.abs(xVelocity) > mMinVelocity)
                {
                    mScroller.fling(getScrollX(), 0, -xVelocity, 0, 0, 0, getWidth() - winWidth, 0);
                }

                //回弹动画
                if (getScrollY() < 0)
                {
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                    Log.i("main", "执行了");
                }
                if (getScrollY() > getHeight() - winHeight)
                {
                    mScroller.startScroll(0, getScrollY(), 0, -(getScrollY() - getHeight() + winHeight));
                }

                break;
        }
        invalidate();
        releaseVelocityTracker();
        return true;
    }

    //向VelocityTracker添加MotionEvent
    private void acquireVelocityTracker(final MotionEvent event) {
        if(null == tracker) {
            tracker = VelocityTracker.obtain();
        }
        tracker.addMovement(event);
    }

     //释放VelocityTracker
    private void releaseVelocityTracker() {
        if(null != tracker) {
            tracker.clear();
            tracker.recycle();
            tracker = null;
        }
    }

    @Override
    public void computeScroll()
    {
        super.computeScroll();
        if (mScroller.computeScrollOffset())
        {
            scrollTo(mScroller.getCurrX(), 0);
            invalidate();
        }
    }


}
