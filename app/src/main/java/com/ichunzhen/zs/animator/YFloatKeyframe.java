package com.ichunzhen.zs.animator;
//关键帧 保存着 某一时刻的具体状态    初始化的时候
public class YFloatKeyframe {
    float mFraction;
    Class mValueType;
    float mValue;
    public YFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }

    public float getFraction() {
        return mFraction;
    }
}
