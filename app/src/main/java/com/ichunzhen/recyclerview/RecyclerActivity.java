package com.ichunzhen.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichunzhen.ui.R;

public class RecyclerActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerActivity";
    private Rclv rclv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        rclv = findViewById(R.id.rclv);
        rclv.setAdapter(new Rclv.Adapter() {
            @Override
            public View onCreateViewHodler(int position, View convertView, ViewGroup parent) {
                convertView=  RecyclerActivity.this.getLayoutInflater().inflate( R.layout.item_table,parent,false);
                TextView textView= (TextView) convertView.findViewById(R.id.text1);
                textView.setText("itemT: "+position);
                Log.i(TAG, "onCreateViewHodler: " + convertView.hashCode());
                return convertView;
            }

            @Override
            public View onBinderViewHodler(int position, View convertView, ViewGroup parent) {
                TextView textView= (TextView) convertView.findViewById(R.id.text1);
                textView.setText("itemL: "+position);
                Log.i(TAG, "onBinderViewHodler: " + convertView.hashCode());
                return convertView;
            }

            @Override
            public int getItemViewType(int row) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public int getHeight(int index) {
                return 100;
            }
        });
    }
}
