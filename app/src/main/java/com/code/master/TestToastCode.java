package com.code.master;

import android.content.Context;
import android.widget.Toast;

public class TestToastCode {
    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
