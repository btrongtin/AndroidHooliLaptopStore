package com.example.hoolilaptopstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.model.GioHang;
import com.example.hoolilaptopstore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView txtTen, txtGia, txtMoTa;
    Button btnThemGioHang;
    SanPham sanPham; //sản phẩm nhận từ intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        anhXa();
        actionToolbar();
        getData();
        actionThemGioHang();
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

    private void actionThemGioHang() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nếu mảng giỏ hàng đã có phần tử
                if(MainActivity.gioHangArrayList.size() > 0){
                    boolean exist = false;
                    for(int i=0; i<MainActivity.gioHangArrayList.size(); i++){
                        //nếu sp mới thêm vào đã có trong mảng => cập nhật lại số lượng
                        if(MainActivity.gioHangArrayList.get(i).getIdSP() == sanPham.getId()){
                            MainActivity.gioHangArrayList.get(i).setSoLuongSP(MainActivity.gioHangArrayList.get(i).getSoLuongSP()+1);
                            MainActivity.gioHangArrayList.get(i).setGiaSP((long) sanPham.getGia() * MainActivity.gioHangArrayList.get(i).getSoLuongSP());
                            exist = true;
                        }


                    }
                    //lặp hết giỏ hàng mà exist=false => sp mới thêm vào chưa có trong mảng => thêm sp mới
                    if(!exist){
                        MainActivity.gioHangArrayList.add(new GioHang(sanPham.getId(), sanPham.getTen(),
                                sanPham.getGia(), sanPham.getHinhAnh(), 1));
                    }
                }
                //nếu mảng giỏ hàng chưa có phần tử
                else{
                    MainActivity.gioHangArrayList.add(new GioHang(sanPham.getId(), sanPham.getTen(),
                            sanPham.getGia(), sanPham.getHinhAnh(), 1));
                }
            }
        });
    }

    private void getData() {
        String tenChiTiet = "";
        int giaChiTiet = 0;
        String moTa = "";
        String hinhAnhChiTiet = "";

        sanPham = (SanPham) getIntent().getSerializableExtra("Laptop");
        tenChiTiet = sanPham.getTen();
        giaChiTiet = sanPham.getGia();
        moTa = sanPham.getMoTa();
        hinhAnhChiTiet = sanPham.getHinhAnh();

        txtTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText(decimalFormat.format(giaChiTiet));
        txtMoTa.setText(moTa);
        Picasso.with(getApplicationContext()).load(hinhAnhChiTiet).into(imgChiTiet);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbarChiTiet = findViewById(R.id.toolbarChiTietSanPham);
        imgChiTiet = findViewById(R.id.imageViewLaptopChiTiet);
        txtTen = findViewById(R.id.textViewTenLaptopChiTiet);
        txtGia = findViewById(R.id.textViewGiaLaptopChiTiet);
        txtMoTa = findViewById(R.id.textViewMoTaLaptopChiTiet);
        btnThemGioHang = findViewById(R.id.btnThemGioHangChiTiet);
    }
}