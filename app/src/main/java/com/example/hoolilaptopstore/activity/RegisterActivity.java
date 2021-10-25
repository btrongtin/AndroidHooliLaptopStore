package com.example.hoolilaptopstore.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.util.Server;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    EditText edtHoTen, edtUsername, edtPassword, edtDiaChi, edtSoDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhXa();
        actionBtnRegister();
    }

    private void actionBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String duongDan = Server.duongDanRegister;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("HoTen", edtHoTen.getText().toString());
                        param.put("TenDangNhap", edtUsername.getText().toString());
                        param.put("MatKhau", edtPassword.getText().toString());
                        param.put("DiaChi", edtDiaChi.getText().toString());
                        param.put("SoDienThoai", edtSoDienThoai.getText().toString());
                        return param;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });
    }

    private void anhXa() {
        btnRegister = findViewById(R.id.btnDKRegister);
        edtHoTen = findViewById(R.id.editTextDKName);
        edtUsername = findViewById(R.id.editTextDKUsername);
        edtPassword = findViewById(R.id.editTextDKPassword);
        edtDiaChi = findViewById(R.id.editTextDKAddress);
        edtSoDienThoai = findViewById(R.id.editTextDKSDT);
    }
}