package com.example.hoolilaptopstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.SearchActivity;
import com.example.hoolilaptopstore.adapter.NavExpandableListViewAdapter;
import com.example.hoolilaptopstore.adapter.SanPhamAdapter;
import com.example.hoolilaptopstore.model.Account;
import com.example.hoolilaptopstore.model.GioHang;
import com.example.hoolilaptopstore.model.NavItem;
import com.example.hoolilaptopstore.model.SanPham;
import com.example.hoolilaptopstore.model.ThuongHieu;
import com.example.hoolilaptopstore.util.CheckConnection;
import com.example.hoolilaptopstore.util.Server;
import com.example.hoolilaptopstore.util.Ultilities;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    EditText edtSearch;
    ImageView imgSearch;

    //Expandable List View
    TextView textViewNavHeader;
    View headerView;
    public static Account loginAccount;
    List<NavItem> headerList;
    HashMap<NavItem, List<ThuongHieu>> listDataChild;
    ExpandableListView navExpandableListView;
    NavExpandableListViewAdapter navExpandableListAdapter;
    List<ThuongHieu> listThuongHieu;
    int id = 0;
    String tenThuongHieu = "";
    String hinhAnhThuongHieu = "";
    public static ArrayList<GioHang> gioHangArrayList;

    //SP Moi Nhat
    ArrayList<SanPham> sanPhamArrayList;
    SanPhamAdapter sanPhamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            getDataNav();
            handleNavClick();
            getDataSPMoiNhat();
            handleSearch();

        }
        else{
            Ultilities.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại đường truyền.");
            finish();
        }


    }

    private void handleSearch() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = edtSearch.getText().toString().trim();
                if(searchQuery.isEmpty() ) {
                    Ultilities.ShowToast_short(getApplicationContext(), "Mời bạn nhập thông tin cần tìm!");
                    return;
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("searchInput", searchQuery);
                    startActivity(intent);
                }
            }
        });
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

    private void handleNavClick() {
        //kiểm tra kết nối
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            navExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                    switch (groupPosition){
                        case 0:
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            //đóng nav
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case 1:
                            //title = đăng nhập => chuyển sang màn hình đăng nhập
                            if(headerList.get(groupPosition).getTitle().equals("Đăng nhập")){
                                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent1);
                            }
                            //title = đăng xuất => set loginAccount = null, set title = đăng nhập
                            else{
                                loginAccount = null;
                                textViewNavHeader.setVisibility(View.INVISIBLE);
                                headerList.get(groupPosition).setTitle("Đăng nhập");
                            }
                            //đóng nav
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        case 3:
                            Intent intent2 = new Intent(MainActivity.this, ThongTinCuaHangActivity.class);
                            startActivity(intent2);
                            //đóng nav
                            drawerLayout.closeDrawer(GravityCompat.START);
                            break;

                    }
                    return false;
                }
            });

            navExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                    Intent intent = new Intent(MainActivity.this, LaptopTheoThuongHieuActivity.class);
                    intent.putExtra("ThuongHieu", listThuongHieu.get(childPosition));
                    startActivity(intent);
                    //đóng nav
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            });
        }
        else{
            Ultilities.ShowToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối!");
        }

    }

    private void getDataSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    int id = 0;
                    String tenSP = "";
                    int giaSP = 0;
                    String hinhAnh = "";
                    String moTa = "";
                    int idThuongHieu = 0;
                    int soLuong = 0;

                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenSP = jsonObject.getString("tensp");
                            giaSP = jsonObject.getInt("giasp");
                            hinhAnh = jsonObject.getString("hinhanhsp");
                            moTa = jsonObject.getString("motasp");
                            idThuongHieu = jsonObject.getInt("idThuongHieu");
                            soLuong = jsonObject.getInt("soLuong");

                            sanPhamArrayList.add(new SanPham(id, tenSP, giaSP, hinhAnh, moTa, idThuongHieu, soLuong));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }


    private void getDataNav() {

        headerList = new ArrayList<>();
        listDataChild = new HashMap<NavItem, List<ThuongHieu>>();

        headerList.add( new NavItem("Trang Chính", R.drawable.ic_home));

        if(loginAccount != null){
            textViewNavHeader.setText("Xin chào: "+loginAccount.getHoTen());
            headerList.add( new NavItem("Đăng xuất", R.drawable.ic_login));
        }
        else{
            headerList.add( new NavItem("Đăng nhập", R.drawable.ic_login));
        }

        headerList.add( new NavItem("Laptop theo thương hiệu", R.drawable.ic_category));
        headerList.add( new NavItem("Thông tin cửa hàng", R.drawable.ic_about));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanThuongHieu, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenThuongHieu = jsonObject.getString("ten");
                            hinhAnhThuongHieu = jsonObject.getString("hinhanh");

                            listThuongHieu.add(new ThuongHieu(id, tenThuongHieu, hinhAnhThuongHieu));
                            navExpandableListAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Ultilities.ShowToast_short(getApplicationContext(), error.toString());
            }
        });




        requestQueue.add(jsonArrayRequest);

        listDataChild.put(headerList.get(2), listThuongHieu);

        navExpandableListAdapter = new NavExpandableListViewAdapter(this, listDataChild, headerList);
        navExpandableListView.setAdapter(navExpandableListAdapter);
    }

    private void actionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/sis-apple-690-300-max.png");
        mangQuangCao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/lttt-690x300_19_.png");
        mangQuangCao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/sis-apple-690-300-max.png");
        mangQuangCao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/mb-redmi-10-690-300-max.png");

        for (int i=0; i<mangQuangCao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar_manhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        headerView = navigationView.getHeaderView(0);
        textViewNavHeader =  headerView.findViewById(R.id.textViewNavHeader);
        drawerLayout = findViewById(R.id.drawerlayout);
        navExpandableListView = findViewById(R.id.elv_manHinhChinh);
        listThuongHieu = new ArrayList<ThuongHieu>();
        sanPhamArrayList = new ArrayList<>();
        edtSearch = findViewById(R.id.edtSearch);
        imgSearch = findViewById(R.id.imgSearch);


        if(loginAccount==null)
            loginAccount = (Account) getIntent().getSerializableExtra("loginAccount");

        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), sanPhamArrayList);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        //2 cot
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewManHinhChinh.setAdapter(sanPhamAdapter);

        if(gioHangArrayList==null)
            gioHangArrayList = new ArrayList<>();
    }
}