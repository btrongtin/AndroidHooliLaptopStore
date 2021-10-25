package com.example.hoolilaptopstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.activity.ChiTietSanPhamActivity;
import com.example.hoolilaptopstore.activity.LaptopTheoThuongHieuActivity;
import com.example.hoolilaptopstore.activity.MainActivity;
import com.example.hoolilaptopstore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamHolder>{
    Context context;
    ArrayList<SanPham> arrayListSanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayListSanPham) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
    }

    @NonNull
    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham_moinhat, null);
        SanPhamHolder sanPhamHolder = new SanPhamHolder(v);

        return sanPhamHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHolder holder, int position) {
        SanPham sanPham = arrayListSanPham.get(position);
        holder.txtTenSP.setMaxLines(2);
        holder.txtTenSP.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtTenSP.setText(sanPham.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText(decimalFormat.format(sanPham.getGia()) + "Đ");
        Picasso.with(context).load(sanPham.getHinhAnh())
                .into(holder.imgSanPham);

    }

    @Override
    public int getItemCount() {
        return arrayListSanPham.size();
    }


    public class SanPhamHolder extends RecyclerView.ViewHolder {
        public ImageView imgSanPham;
        public TextView txtTenSP, txtGiaSP;



        public SanPhamHolder(View itemView) {
            super(itemView);
            this.imgSanPham = itemView.findViewById(R.id.imgSPMoiNhat);
            this.txtTenSP = itemView.findViewById(R.id.txtTenSPMoiNhat);
            this.txtGiaSP = itemView.findViewById(R.id.txtGiaSPMoiNhat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("Laptop", arrayListSanPham.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }); //set sự kiên onClick cho View
        }



    }
}
