package com.ichunzhen.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ichunzhen.ui.path.PathMeasureView;
import com.ichunzhen.ui.pingxing.ParallaxContainer;
import com.ichunzhen.zs.animator.LineInterpolator;
import com.ichunzhen.zs.animator.YObjectAnimator;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathMeasureView(this));

        setContentView(R.layout.activity_splash);
        ParallaxContainer container = findViewById(R.id.parallax_container);
        container.setUp(new int[]{
                R.layout.px_view_intro_1,
                R.layout.px_view_intro_2,
                R.layout.px_view_intro_3,
                R.layout.px_view_intro_4,
                R.layout.px_view_intro_5,
                R.layout.px_view_login
        });
        //设置动画
        ImageView iv_man = (ImageView) findViewById(R.id.iv_man);
        iv_man.setBackgroundResource(R.drawable.px_man_run);
        container.setIv_man(iv_man);
    }

}
