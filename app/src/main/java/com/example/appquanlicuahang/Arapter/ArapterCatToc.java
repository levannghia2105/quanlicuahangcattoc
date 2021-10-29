package com.example.appquanlicuahang.Arapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appquanlicuahang.Model.Khach;
import com.example.appquanlicuahang.R;

import java.util.ArrayList;

public class ArapterCatToc extends RecyclerView.Adapter<ArapterCatToc.Hoder> {
    @NonNull
    ArrayList<Bitmap> khachArrayList ;
    Context mContext ;

    public ArapterCatToc(@NonNull ArrayList<Bitmap> khachArrayList, Context mContext) {
        this.khachArrayList = khachArrayList;
        this.mContext = mContext;
    }

    @Override

    public Hoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(mContext).inflate(R.layout.custom_row_cattoc,parent,false);
          Hoder hoder = new Hoder(view);
            return hoder ;
    }
    @Override
    public void onBindViewHolder(@NonNull Hoder holder, int position) {
      Bitmap bm = khachArrayList.get(position);
       holder.imgKhachHang.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        if (khachArrayList == null) {
            return 0;
        } else
            return khachArrayList.size();
    }

    public class Hoder extends RecyclerView.ViewHolder {
        ImageView imgKhachHang ;
        public Hoder(@NonNull View itemView) {
            super(itemView);
            imgKhachHang = itemView.findViewById(R.id.imgKhachHang);
        }
    }
}
