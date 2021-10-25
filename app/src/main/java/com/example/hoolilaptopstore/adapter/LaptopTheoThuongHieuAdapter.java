package com.example.hoolilaptopstore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopTheoThuongHieuAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> sanPhamArrayList;

    public LaptopTheoThuongHieuAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @Override
    public int getCount() {
        return sanPhamArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhamArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class  ViewHolder{
        public TextView txtTenSP, txtGiaSP, txtMoTaSP;
        public ImageView imgSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop_theothuonghieu, null);
            viewHolder.txtTenSP = view.findViewById(R.id.textViewTenLaptopTheoThuongHieu);
            viewHolder.txtGiaSP = view.findViewById(R.id.textViewGiaLaptopTheoThuongHieu);
            viewHolder.txtMoTaSP = view.findViewById(R.id.textViewMoTaLaptopTheoThuongHieu);
            viewHolder.imgSP = view.findViewById(R.id.imageViewLaptopTheoThuongHieu);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenSP.setText(sanPham.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSP.setText(decimalFormat.format(sanPham.getGia()) + "Đ");
        viewHolder.txtMoTaSP.setMaxLines(2);
        viewHolder.txtMoTaSP.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaSP.setText(sanPham.getMoTa());
        Picasso.with(context).load(sanPham.getHinhAnh()).into(viewHolder.imgSP);
        return view;
    }
}
