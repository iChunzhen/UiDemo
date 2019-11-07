package com.ichunzhen.zs.animator;

import com.ichunzhen.ui.animator.TimeInterpolator;

public class LineInterpolator implements  TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input;
    }
}
