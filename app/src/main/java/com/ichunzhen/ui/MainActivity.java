package com.ichunzhen.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.ichunzhen.ui.animator.MyObjectAnimator;
import com.ichunzhen.ui.canvas.SaveRestoreView;
import com.ichunzhen.ui.canvas.SplashView;
import com.ichunzhen.ui.canvas.TransformView;
import com.ichunzhen.ui.canvas.split.SplitView;
import com.ichunzhen.ui.paint.colorfilter.ColorFilterView;
import com.ichunzhen.ui.paint.gradient.GradientLayout;
import com.ichunzhen.ui.paint.gradient.LinearGradientView;
import com.ichunzhen.ui.paint.gradient.RadialGradientView;
import com.ichunzhen.ui.paint.xfermode.XfermodeView;
import com.ichunzhen.ui.paint.xfermode.XfermodesView;
import com.ichunzhen.ui.path.DragBubbleView;
import com.ichunzhen.ui.path.PathMeasureView;
import com.ichunzhen.ui.path.PathView;
import com.ichunzhen.ui.path.bezier.BezierView;
import com.ichunzhen.zs.animator.LineInterpolator;
import com.ichunzhen.zs.animator.YObjectAnimator;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathView(this));

//        setContentView(R.layout.activity_main);
//        DragBubbleView bubbleView = findViewById(R.id.bubbleView);
//        bubbleView.

//        闪屏页
//        ViewGroup vg = (ViewGroup) getWindow().getDecorView();
//        vg.addView(LayoutInflater.from(this).inflate(R.layout.activity_splash_view, null));

//        自定义动画
//        setContentView(R.layout.activity_main);
//        Button btn=findViewById(R.id.btn);
//
//        YObjectAnimator animator=YObjectAnimator.ofFloat(btn,"scaleX",1f,2f);
//        animator.setDuration(3000);
//        animator.setInterpolator(new LineInterpolator());
//        animator.start();
// MyObjectAnimator animators=MyObjectAnimator.ofFloat(btn,"scaleX",2f);
//        animators.setDuration(3000);
//        animators.start();

//        ObjectAnimator objectAnimator = ObjectAnimator.
//                ofFloat(btn, "scaleX", 2f);
//        objectAnimator.setDuration(3000);
//        objectAnimator.start();

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }
}
