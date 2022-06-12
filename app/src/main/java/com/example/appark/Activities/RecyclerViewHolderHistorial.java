package com.example.appark.Activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appark.R;

public class RecyclerViewHolderHistorial extends RecyclerView.ViewHolder {

    private TextView view;
    private ImageView share;

    public RecyclerViewHolderHistorial(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.nom_ubicacio);
        share = itemView.findViewById(R.id.share_button);
    }

    public TextView getView(){
        return view;
    }

    public ImageView getShareButton(){
        return share;
    }
}