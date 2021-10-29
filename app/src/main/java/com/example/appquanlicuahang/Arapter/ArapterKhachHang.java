package com.example.appquanlicuahang.Arapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appquanlicuahang.CallBack.SetClickListCustomer;
import com.example.appquanlicuahang.Model.KhachHang;
import com.example.appquanlicuahang.R;

import java.util.ArrayList;

public class ArapterKhachHang extends RecyclerView.Adapter<ArapterKhachHang.Holer> {
    Context mContext;
    ArrayList<KhachHang> khachHangArrayList;
    SetClickListCustomer setClickListCustomer ;


    public void setSetClickListCustomer(SetClickListCustomer setClickListCustomer) {
        this.setClickListCustomer = setClickListCustomer;
    }

    public ArapterKhachHang(Context mContext, ArrayList<KhachHang> khachHangArrayList) {
        this.mContext = mContext;
        this.khachHangArrayList = khachHangArrayList;
    }

    @NonNull
    @Override

    public ArapterKhachHang.Holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_row_khachhang,parent,false);
        Holer holer = new Holer(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(@NonNull ArapterKhachHang.Holer holder, int position) {
        KhachHang mKhachHang = khachHangArrayList.get(position);
        holder.khachHang = mKhachHang ;
        holder.txtSoLanCat.setText(mKhachHang.getSoLancat()+"");
        holder.txtTen.setText(mKhachHang.getTenKhachHang());
        holder.txtSdt.setText(mKhachHang.getSdt());
//        holder.txtSoLanCat.setText(mKhachHang.);
        holder.txtNgaySinh.setText(mKhachHang.getNgaySinh());
        if (mKhachHang.getGioiTinh() == 1) {
            holder.txtGioiTinh.setText("Nam");
        }else {
            holder.txtGioiTinh.setText("Nữ");
        }
    }

    @Override
    public int getItemCount() {
        if (khachHangArrayList != null) {
            return khachHangArrayList.size();
        } else

            return 0;
    }

    public class Holer extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTen, txtGioiTinh, txtNgaySinh, txtSdt, txtSoLanCat;
        KhachHang khachHang ;

        public Holer(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtGioiTinh = itemView.findViewById(R.id.txtGioiTinh);
            txtNgaySinh = itemView.findViewById(R.id.txtNgaySinh);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            txtSoLanCat = itemView.findViewById(R.id.txtSoLanCat);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            setClickListCustomer.ClickListCustomer(khachHang);
        }


    }
    public void thayDoiList(ArrayList<KhachHang> arrKhachHang ){
       khachHangArrayList = arrKhachHang ;
        notifyDataSetChanged();

    }

}
