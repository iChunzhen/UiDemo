package com.ichunzhen.zs.animator;


import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;


import java.util.Arrays;
import java.util.List;

/**
 * @Author yuancz
 * @Date 2019/11/5-14:43
 * @Email ichunzhen6@gmail.com
 */
public class YKeyframeSet {
    //    类型估值器
    TypeEvaluator mEvaluator;
    YFloatKeyframe mFirstKeyframe;
    List<YFloatKeyframe> mKeyframes;

    public YKeyframeSet(YFloatKeyframe... mFirstKeyframe) {
        mKeyframes = Arrays.asList(mFirstKeyframe);
        this.mFirstKeyframe = mFirstKeyframe[0];
        mEvaluator = new FloatEvaluator();
    }


    /**
     * 生成关键帧
     *
     * @param values
     * @return
     */
    public static YKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;
        YFloatKeyframe keyframes[] = new YFloatKeyframe[numKeyframes];
        keyframes[0] = new YFloatKeyframe(0, values[0]);
        for (int i = 1; i < numKeyframes; i++) {
            keyframes[i] = new YFloatKeyframe((float) i / (numKeyframes - 1), values[i]);
        }
        return new YKeyframeSet(keyframes);
    }

    public Object getValues(float fraction) {
        YFloatKeyframe prevKeyframe = mFirstKeyframe;
        for (int i = 1; i < mKeyframes.size(); ++i) {
            YFloatKeyframe nextKeyframe = mKeyframes.get(i);
            if (0 < fraction && fraction < nextKeyframe.mFraction) {
                return mEvaluator.evaluate(fraction, prevKeyframe.mValue, nextKeyframe.mValue);
            }
            prevKeyframe = nextKeyframe;
        }
        return null;
    }
}
