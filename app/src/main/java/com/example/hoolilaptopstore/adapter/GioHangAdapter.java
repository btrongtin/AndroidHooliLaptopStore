package com.example.hoolilaptopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoolilaptopstore.R;
import com.example.hoolilaptopstore.activity.GioHangActivity;
import com.example.hoolilaptopstore.activity.MainActivity;
import com.example.hoolilaptopstore.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<GioHang> gioHangArrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class  ViewHolder{
        public TextView txtTenGioHang;
        public  TextView txtGiaGioHang;
        public ImageView imgGioHang;
        public  Button btnPlus;
        public  Button btnValues;
        public  Button btnMinus;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_gio_hang, null);
            viewHolder.txtTenGioHang = view.findViewById(R.id.textViewTenDongGioHang);
            viewHolder.txtGiaGioHang = view.findViewById(R.id.textViewGiaDongGioHang);
            viewHolder.imgGioHang = view.findViewById(R.id.imageViewDongGioHang);
            viewHolder.btnValues = view.findViewById(R.id.btnValueDongGioHang);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinusDongGioHang);
            viewHolder.btnPlus = view.findViewById(R.id.btnPlusDongGioHang);


            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtTenGioHang.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText("Giá : "+decimalFormat.format(gioHang.getGiaSP())+" đ");
        Picasso.with(context)
                .load(gioHang.getHinhAnhSP())
                .into(viewHolder.imgGioHang);
        viewHolder.btnValues.setText(gioHang.getSoLuongSP()+"");

        int sl= Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl>=10) {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);

        }else if(sl<=1){
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);

        }else {
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }


        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) + 1;
                int slht = MainActivity.gioHangArrayList.get(position).getSoLuongSP();//
                long giaht = MainActivity.gioHangArrayList.get(position).getGiaSP();//
                MainActivity.gioHangArrayList.get(position).setSoLuongSP(slmoinhat);//
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.gioHangArrayList.get(position).setGiaSP(giamoinhat);//
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText("Gía : " + decimalFormat.format(giamoinhat) + " đ");

                GioHangActivity.tinhTien();


                if (slmoinhat > 9) {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat=Integer.parseInt(finalViewHolder.btnValues.getText().toString())-1;
                int slht=MainActivity.gioHangArrayList.get(position).getSoLuongSP();//
                long giaht=MainActivity.gioHangArrayList.get(position).getGiaSP();//
                MainActivity.gioHangArrayList.get(position).setSoLuongSP(slmoinhat);//
                long giamoinhat=(giaht*slmoinhat)/slht;
                MainActivity.gioHangArrayList.get(position).setGiaSP(giamoinhat);//
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText("Gía : "+decimalFormat.format(giamoinhat)+" đ");
                GioHangActivity.tinhTien();


                if(slmoinhat<2){
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                }
            }
        });


        return view;
    }
}
