package com.example.hoolilaptopstore.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.adapter.LaptopTheoThuongHieuAdapter;
import com.example.hoolilaptopstore.model.SanPham;
import com.example.hoolilaptopstore.model.ThuongHieu;
import com.example.hoolilaptopstore.util.CheckConnection;
import com.example.hoolilaptopstore.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopTheoThuongHieuActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewSP;
    LaptopTheoThuongHieuAdapter laptopTheoThuongHieuAdapter;
    ArrayList<SanPham> sanPhamArrayList;
    //thương hiệu nhận từ main activity
    ThuongHieu thuongHieuNhan = null;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_theo_thuong_hieu);

        anhXa();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getThuongHieuNhan();
            ActionToolbar();
            getData(page);
            actionChooseLaptop();
        }
        else{
            CheckConnection.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionChooseLaptop() {
        listViewSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LaptopTheoThuongHieuActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("Laptop", sanPhamArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void getData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanLaptopTheoThuongHieu + page;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenSP = "";
                int giaSP = 0;
                String hinhAnhSP = "";
                String moTaSP = "";
                int idThuongHieu = 0;
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0; i< jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenSP = jsonObject.getString("Ten");
                            giaSP = jsonObject.getInt("Gia");
                            hinhAnhSP = jsonObject.getString("HinhAnh");
                            moTaSP = jsonObject.getString("MoTa");
                            idThuongHieu = jsonObject.getInt("idThuongHieu");

                            sanPhamArrayList.add(new SanPham(id, tenSP, giaSP, hinhAnhSP, moTaSP, idThuongHieu));
                            laptopTheoThuongHieuAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                param.put("idThuongHieu", String.valueOf(thuongHieuNhan.getId()));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getThuongHieuNhan() {
        thuongHieuNhan = (ThuongHieu) getIntent().getSerializableExtra("ThuongHieu");
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbarLaptopTheoThuongHieu);
        listViewSP = findViewById(R.id.listViewLaptopTheoThuongHieu);
        sanPhamArrayList = new ArrayList<>();
        laptopTheoThuongHieuAdapter = new LaptopTheoThuongHieuAdapter(getApplicationContext(), sanPhamArrayList);
        listViewSP.setAdapter(laptopTheoThuongHieuAdapter);
    }
}