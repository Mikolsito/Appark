package com.example.appark.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appark.R;

import java.util.Random;

public class HistorialAdapter extends RecyclerView.Adapter<RecyclerViewHolderHistorial> {
    private Random random;

    public HistorialAdapter(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_ubicacio;
    }

    @NonNull
    @Override
    public RecyclerViewHolderHistorial onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolderHistorial(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderHistorial holder, int position) {
        holder.getView().setText(String.valueOf(random.nextInt()));
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}