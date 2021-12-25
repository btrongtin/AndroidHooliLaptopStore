package com.example.hoolilaptopstore.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.hoolilaptopstore.model.Account;
import com.example.hoolilaptopstore.util.CheckConnection;
import com.example.hoolilaptopstore.util.Server;
import com.example.hoolilaptopstore.util.Ultilities;

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


                String hoTen, tenDangNhap, matKhau, diaChi, soDienThoai;

                hoTen = edtHoTen.getText().toString();
                tenDangNhap = edtUsername.getText().toString();
                matKhau = edtPassword.getText().toString();
                diaChi = edtDiaChi.getText().toString();
                soDienThoai = edtSoDienThoai.getText().toString();

                //kiểm tra input
                if(hoTen.length()>0 && tenDangNhap.length()>0 && matKhau.length()>0 &&diaChi.length()>0 &&soDienThoai.length()>0){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int id = 0;
                            if(!response.equals("error")){ //đăng ký thành công => echo id vừa tạo, dk thất bại => echo "error"
                                id = Integer.parseInt(response);
                                Account loginAccount = new Account(id, hoTen, tenDangNhap, diaChi, soDienThoai);

                                Ultilities.ShowToast_short(getApplicationContext(), "Chúc mừng bạn đã đăng ký thành công");

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("loginAccount", loginAccount);
                                startActivity(intent);
                            }
                            else{
                                Ultilities.ShowToast_short(getApplicationContext(), "Lỗi: "+response);
                            }

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
                            param.put("HoTen", hoTen);
                            param.put("TenDangNhap", tenDangNhap);
                            param.put("MatKhau", matKhau);
                            param.put("DiaChi", diaChi);
                            param.put("SoDienThoai", soDienThoai);
                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
                else{
                    Ultilities.ShowToast_short(getApplicationContext(), "Mời bạn nhập đầy đủ thông tin!");
                }


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