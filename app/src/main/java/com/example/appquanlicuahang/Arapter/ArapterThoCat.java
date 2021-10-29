package com.example.appquanlicuahang.Arapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appquanlicuahang.CallBack.SetClickSpinner;
import com.example.appquanlicuahang.Model.ThoCatToc;
import com.example.appquanlicuahang.R;

import java.util.ArrayList;
import java.util.List;

public class ArapterThoCat extends ArrayAdapter {
    Context mContext ;

    ArrayList<ThoCatToc> thoCatTocList ;

    public ArapterThoCat(@NonNull Context context,  @NonNull ArrayList objects) {
        super(context, 0, objects);
        this.mContext = context ;
        this.thoCatTocList = objects ;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    private  View initView(int pos ,View convertView , ViewGroup parent ){
        if(convertView ==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_row_spiner,parent ,false);
        };
//        TextView txtSpinerTho = convertView.findViewById(R.id.txtSpinnerTho);
        ThoCatToc thoCatToc = (ThoCatToc) getItem(pos);
        if (thoCatToc!=null){
//            txtSpinerTho.setText(thoCatToc.getTenTho());
        }
           return convertView ;
    }
}
