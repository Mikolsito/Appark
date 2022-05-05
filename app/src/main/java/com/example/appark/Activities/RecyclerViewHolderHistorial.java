package com.example.appark.Activities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appark.R;

public class RecyclerViewHolderHistorial extends RecyclerView.ViewHolder {

    private TextView view;
    public RecyclerViewHolderHistorial(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.nom_ubicacio);
    }

    public TextView getView(){
        return view;
    }
}