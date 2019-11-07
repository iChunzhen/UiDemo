package com.ichunzhen.zs.animator;

import android.view.View;

import com.ichunzhen.ui.animator.MyKeyframeSet;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author yuancz
 * @Date 2019/11/5-14:36
 * @Email ichunzhen6@gmail.com
 */
public class YFloatPropertyValuesHolder {
    //    属性名
    String mPropertyName;
    //float
    Class mValueType;
    YKeyframeSet mKeyframes;
    Method mSetter = null;

    public YFloatPropertyValuesHolder(View view, String propertyName, float[] values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;
        mKeyframes = YKeyframeSet.ofFloat(values);
    }

    public void setupSetter(WeakReference<View> target) {
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName="set"+ firstLetter + theRest;
        try {
            mSetter=View.class.getDeclaredMethod(methodName,float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public void setAnimatedValue(View target,float fraction){
        Object value=mKeyframes.getValues(fraction);
        try {
            if (value!=null) {
                mSetter.invoke(target,value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
