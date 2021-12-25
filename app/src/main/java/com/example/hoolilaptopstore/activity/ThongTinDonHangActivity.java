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
import com.example.hoolilaptopstore.model.GioHang;
import com.example.hoolilaptopstore.util.Server;
import com.example.hoolilaptopstore.util.Ultilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongTinDonHangActivity extends AppCompatActivity {
    Account loginAccount;
    EditText edtTenKH, edtSDT, edtDiaChi;
    Button btnXacNhan, btnTroVe;
    ArrayList<GioHang> gioHangArrayList = MainActivity.gioHangArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_don_hang);

        anhXa();
        if(loginAccount!=null) //đã đăng nhập => set info account lên edittext
            setDataToForm();
        else{ //chưa đăng nhập => chuyển sang activity đăng nhập
            Ultilities.ShowToast_short(getApplicationContext(), "Bạn cần đăng nhập trước khi đặt hàng");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("POSITION_CODE", 113);

            startActivity(intent);
        }

        actionDatHang();
        actionTroVe();

    }

    private void actionTroVe() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("loginAccount", loginAccount);
                startActivity(intent);
            }
        });
    }

    private void actionDatHang() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKH = edtTenKH.getText().toString();
                String sdtGiaoHang = edtSDT.getText().toString(); //SDT giao hàng có thể khác với sdt của khách hàng
                String diaChiGiaoHang = edtDiaChi.getText().toString(); //địa chỉ giao hàng có thể khác với địa chỉ
                                                                        // của khách hàng

                if(tenKH.length()>0 && sdtGiaoHang.length()>0 && diaChiGiaoHang.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String idDonHang) {
                            if(Integer.parseInt(idDonHang) > 0){ //đặt thành công => response là id đơn mới đặt
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongDanChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            MainActivity.gioHangArrayList.clear();

                                            Intent intent = new Intent(getApplicationContext(), DatHangThanhCongActivity.class);
                                            intent.putExtra("loginAccount", loginAccount);

                                            startActivity(intent);
                                        }
                                        else{
                                            Ultilities.ShowToast_short(getApplicationContext(), "Đã có lỗi xảy ra, xin thử lại sau ít phút.");
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
                                        JSONArray jsonArray = new JSONArray();
                                        int tongTienDonHang = 0;
                                        for(int i=0; i<MainActivity.gioHangArrayList.size(); i++){
                                            tongTienDonHang += gioHangArrayList.get(i).getGiaSP();
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("idSanPham", gioHangArrayList.get(i).getIdSP());
                                                jsonObject.put("soLuong", gioHangArrayList.get(i).getSoLuongSP());
                                                jsonObject.put("giaTien", gioHangArrayList.get(i).getGiaSP());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        hashMap.put("tongTienDonHang", tongTienDonHang+"");
                                        hashMap.put("idDonHang", idDonHang+"");

                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        };
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("idKhachHang", loginAccount.getId()+"");
                            hashMap.put("DiaChiGiaoHang", diaChiGiaoHang);
                            hashMap.put("SoDienThoaiGiaoHang", sdtGiaoHang);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else {
                    Ultilities.ShowToast_short(getApplicationContext(), "Mời bạn nhập đầy đủ thông tin!");
                }
            }
        });
    }

    private void setDataToForm() {
        edtTenKH.setText(loginAccount.getHoTen());
        edtSDT.setText(loginAccount.getSoDienThoai());
        edtDiaChi.setText(loginAccount.getDiaChi());
    }

    private void anhXa() {
        edtTenKH = findViewById(R.id.editTextDHTenKH);
        edtSDT = findViewById(R.id.editTextDHSdt);
        edtDiaChi = findViewById(R.id.editTextDHDiaChi);
        btnXacNhan = findViewById(R.id.btnDHXacNhan);
        btnTroVe = findViewById(R.id.btnDHTroVe);
        //loginAccount = (Account) getIntent().getSerializableExtra("loginAccount");
        if(MainActivity.loginAccount != null)
            loginAccount = MainActivity.loginAccount;
        else
            loginAccount = (Account) getIntent().getSerializableExtra("loginAccount");

    }
}