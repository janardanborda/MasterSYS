package com.code.master;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.code.generalcode.TestToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TestToast().showToast(this, "APP NAME");
    }
}