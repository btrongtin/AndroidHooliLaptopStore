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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnRegister;
    int POSITION_CODE = -1;
    Account loginAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        actionRegister();
        actionLogin();
    }

    private void actionLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUsername.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                    Ultilities.ShowToast_short(getApplicationContext(), "Mời bạn nhập tài khoản và mật khẩu!");
                    return;
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String duongDan = Server.duongDanLogin;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String hoTen, tenDangNhap, diaChi, soDienThoai = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            id = jsonObject.getInt("id");
                            hoTen = jsonObject.getString("HoTen");
                            tenDangNhap = jsonObject.getString("TenDangNhap");
                            diaChi = jsonObject.getString("DiaChi");
                            soDienThoai = jsonObject.getString("SoDienThoai");

                            loginAccount = new Account(id, hoTen, tenDangNhap, diaChi, soDienThoai);

                            Intent intent;
                            if(POSITION_CODE == 113){
                                intent = new Intent(getApplicationContext(), ThongTinDonHangActivity.class);
                            }
                            else{
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                            }
                            intent.putExtra("loginAccount", loginAccount);
                            startActivity(intent);

                        } catch (JSONException e) {
                            //Khi Jsonobject không parse được => sai tên dn or mk
                            Ultilities.ShowToast_short(getApplicationContext(),"Sai tên đăng nhập hoặc mật khẩu!");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Khi server bị lỗi
                        Ultilities.ShowToast_short(getApplicationContext(), error.toString());
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("username", edtUsername.getText().toString());
                        param.put("password", edtPassword.getText().toString());
                        return param;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });
    }

    private void actionRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        edtUsername = findViewById(R.id.editTextDNUsername);
        edtPassword = findViewById(R.id.editTextDNPassword);
        btnLogin = findViewById(R.id.btnDNLogin);
        btnRegister = findViewById(R.id.btnDNRegister);
        POSITION_CODE = getIntent().getIntExtra("POSITION_CODE", -1);
    }


}