package com.code.master;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.generalcode.TestToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TestToast().showToast(this, "APP NAME");

        findViewById(R.id.toastShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestToast().showToast(MainActivity.this, "APP NAME");
            }
        });
    }
}