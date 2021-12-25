package com.example.hoolilaptopstore.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.adapter.GioHangAdapter;
import com.example.hoolilaptopstore.model.Account;
import com.example.hoolilaptopstore.util.CheckConnection;
import com.example.hoolilaptopstore.util.Ultilities;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    ListView listViewGioHang;
    TextView textViewThongBao;
    static TextView textViewTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;
    //Account loginAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        anhXa();
        actionToolbar();
        checkData();
        tinhTien();
        actionLongClickItem(); //longclick => xóa sp
        actionBtn();

    }

    private void actionBtn() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.gioHangArrayList.size()>0){
                    Intent intent = new Intent(getApplicationContext(), ThongTinDonHangActivity.class);
                    startActivity(intent);
                }
                else{
                    Ultilities.ShowToast_short(getApplicationContext(), "Giỏ hàng của bạn đang trống.");
                }
            }
        });
    }

    private void actionLongClickItem() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác Nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm này ");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.gioHangArrayList.size()<=0){
                            textViewThongBao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.gioHangArrayList.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            tinhTien();
                            if(MainActivity.gioHangArrayList.size()<=0){
                                textViewThongBao.setVisibility(View.VISIBLE);
                            }else{
                                textViewThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                tinhTien();
                            }

                        }
                    }
                });
                builder.setNegativeButton("Không ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        tinhTien();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void tinhTien() {
        long tongTien = 0;
        for (int i = 0; i<MainActivity.gioHangArrayList.size(); i++){
            tongTien += MainActivity.gioHangArrayList.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewTongTien.setText(decimalFormat.format(tongTien) + "đ");
    }

    private void checkData() {
        if(MainActivity.gioHangArrayList.size() <= 0){
            //để khi xóa dữ liệu trong mảng
            gioHangAdapter.notifyDataSetChanged();
            textViewThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        }
        else{
            gioHangAdapter.notifyDataSetChanged();
            textViewThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void anhXa() {
        listViewGioHang = findViewById(R.id.listViewGioHang);
        textViewThongBao = findViewById(R.id.textViewGHThongBao);
        textViewTongTien = findViewById(R.id.textViewGHTongTien);
        btnThanhToan = findViewById(R.id.btnGHThanhToan);
        btnTiepTucMua = findViewById(R.id.btnGHTiepTucMua);
        toolbarGioHang = findViewById(R.id.toolbarGioHang);
        gioHangAdapter = new GioHangAdapter(this, MainActivity.gioHangArrayList);
        listViewGioHang.setAdapter(gioHangAdapter);
    }
}