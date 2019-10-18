package com.ichunzhen.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ichunzhen.ui.paint.colorfilter.ColorFilterView;
import com.ichunzhen.ui.paint.gradient.GradientLayout;
import com.ichunzhen.ui.paint.gradient.LinearGradientView;
import com.ichunzhen.ui.paint.gradient.RadialGradientView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ColorFilterView(this));
    }
}
