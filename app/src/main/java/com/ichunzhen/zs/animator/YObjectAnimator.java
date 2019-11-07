package com.ichunzhen.zs.animator;

import android.util.Log;
import android.view.View;

import com.ichunzhen.ui.animator.TimeInterpolator;

import java.lang.ref.WeakReference;
import java.util.logging.Logger;

/**
 * @Author yuancz
 * @Date 2019/11/5-14:34
 * @Email ichunzhen6@gmail.com
 */
public class YObjectAnimator implements VSYNCManager.AnimationFrameCallback {
    private static final String TAG = "YObjectAnimator";
    long mStartTime = -1;
    private long mDuration = 0;
    private WeakReference<View> target;//执行动画的view
    private float index = 0;
    private TimeInterpolator interpolator;
    YFloatPropertyValuesHolder myFloatPropertyValuesHolder;

    public YObjectAnimator(View view, String propertyName, float... values) {
        target = new WeakReference<>(view);
        myFloatPropertyValuesHolder = new YFloatPropertyValuesHolder(view, propertyName, values);
    }

    public static YObjectAnimator ofFloat(View view, String propertyName, float... values) {
        YObjectAnimator anim = new YObjectAnimator(view, propertyName, values);
        return anim;
    }

    public void start() {
        myFloatPropertyValuesHolder.setupSetter(target);
        mStartTime = System.currentTimeMillis();
        VSYNCManager.getInstance().add(this);
    }

    @Override
    public boolean doAnimationFrame(long currentTime) {
        float total= mDuration / 16;
//        执行百分比
        float fraction = (index++) / total;
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
            Log.i("qqq",fraction+"");
        }
        if (index >=total) {
            index = 0;
        }
        myFloatPropertyValuesHolder.setAnimatedValue(target.get(),fraction);
        return false;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }
}
