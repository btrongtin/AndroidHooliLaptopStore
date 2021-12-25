package com.example.hoolilaptopstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.activity.MainActivity;
import com.example.hoolilaptopstore.model.Account;

public class DatHangThanhCongActivity extends AppCompatActivity {
    Button btnTroVe;
    Account loginAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_thanh_cong);

        anhXa();

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("loginAccount", loginAccount);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        btnTroVe = findViewById(R.id.btnTCTroVeTrangChinh);
        loginAccount = (Account) getIntent().getSerializableExtra("loginAccount");
    }
}