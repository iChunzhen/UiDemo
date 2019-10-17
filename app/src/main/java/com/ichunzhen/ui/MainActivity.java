package com.ichunzhen.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ichunzhen.ui.paint.gradient.GradientLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GradientLayout(this));
    }
}
