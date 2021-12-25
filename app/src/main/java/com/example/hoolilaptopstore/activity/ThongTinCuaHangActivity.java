package com.example.hoolilaptopstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.activity.MainActivity;

public class ThongTinCuaHangActivity extends AppCompatActivity {
    Button btnTTCHTroVeTrangChinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_cua_hang);

        btnTTCHTroVeTrangChinh = findViewById(R.id.btnTTCHTroVeTrangChinh);
        btnTTCHTroVeTrangChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }


}