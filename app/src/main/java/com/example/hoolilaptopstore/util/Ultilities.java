package com.example.hoolilaptopstore.util;

import android.content.Context;
import android.widget.Toast;

public class Ultilities {
    public static void ShowToast_short(Context context, String thongBao){
        Toast.makeText(context, thongBao, Toast.LENGTH_SHORT).show();
    }
}
