package com.example.hoolilaptopstore;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoolilaptopstore.activity.ChiTietSanPhamActivity;
import com.example.hoolilaptopstore.activity.GioHangActivity;
import com.example.hoolilaptopstore.activity.LaptopTheoThuongHieuActivity;
import com.example.hoolilaptopstore.adapter.LaptopTheoThuongHieuAdapter;
import com.example.hoolilaptopstore.model.SanPham;
import com.example.hoolilaptopstore.model.ThuongHieu;
import com.example.hoolilaptopstore.util.CheckConnection;
import com.example.hoolilaptopstore.util.Server;
import com.example.hoolilaptopstore.util.Ultilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewSP;
    LaptopTheoThuongHieuAdapter laptopTheoThuongHieuAdapter;
    ArrayList<SanPham> sanPhamArrayList;
    //SearchQuery nhận từ main activity
    String searchInput = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        anhXa();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getSearchInput();
            ActionToolbar();
            getDataSearch();
            actionChooseLaptop();

        }
        else{
            Ultilities.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void actionChooseLaptop() {
        listViewSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("Laptop", sanPhamArrayList.get(i));
                startActivity(intent);
            }
        });
    }


    private void getDataSearch() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanSearch;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenSP = "";
                int giaSP = 0;
                String hinhAnhSP = "";
                String moTaSP = "";
                int idThuongHieu = 0;
                int soLuong = 0;
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
                            soLuong = jsonObject.getInt("SoLuong");

                            sanPhamArrayList.add(new SanPham(id, tenSP, giaSP, hinhAnhSP, moTaSP, idThuongHieu, soLuong));
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
                param.put("searchInput", searchInput);
                return param;
            }
        };
        requestQueue.add(stringRequest);
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

    private void getSearchInput() {
        searchInput = (String) getIntent().getSerializableExtra("searchInput");
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbarLaptopTheoTimKiem);
        listViewSP = findViewById(R.id.listViewLaptopTheoTimKiem);
        sanPhamArrayList = new ArrayList<>();
        laptopTheoThuongHieuAdapter = new LaptopTheoThuongHieuAdapter(getApplicationContext(), sanPhamArrayList);
        listViewSP.setAdapter(laptopTheoThuongHieuAdapter);
    }
}