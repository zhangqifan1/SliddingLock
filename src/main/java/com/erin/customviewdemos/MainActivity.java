package com.erin.customviewdemos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.erin.customviewdemos.view.SlideLock;

import static android.R.attr.x;

public class MainActivity extends AppCompatActivity {
private ImageView imageView;
    private SlideLock slideLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideLock = (SlideLock) findViewById(R.id.slideLock);
        imageView = (ImageView) findViewById(R.id.imageView);
        slideLock.setOnUnlockListener(new OnUnlockListener(){
            @Override
            public void setUnlock(boolean unlock) {
                if(unlock){
                    slideLock.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
