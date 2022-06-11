package com.example.appark.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceEstacionament;
import com.example.appark.R;

import java.util.ArrayList;
import java.util.Random;

public class HistorialAdapter extends RecyclerView.Adapter<RecyclerViewHolderHistorial> {
    private ArrayList<Location> historial;
    private int i;

    public HistorialAdapter(ArrayList<Location> e) {
        historial = e;

        //Location u = new Location("Universitat de Barcelona", 41.38723792822906, 2.164683452233139, 23, 9, "Eixample");
        //historial.add(new Estacionament(u));
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
        holder.getView().setText(String.valueOf(historial.get(position).getNom()));
        holder.getShareButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + historial.get(holder.getAdapterPosition()).
                        getUbicacio().getLatitude() + "," + historial.get(holder.getAdapterPosition()
                        ).getUbicacio().getLongitude() + historial.get(holder.getAdapterPosition()
                ).getUbicacio().getNom());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                view.getContext().startActivity(mapIntent);
                */

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Ubicació Aparcada";
                String assumpte = historial.get(holder.getAdapterPosition()).getNom()
                        + ", " + "www.google.com/maps/@" + historial.get(holder.getAdapterPosition()).
                        getLatitude() + "," + historial.get(holder.getAdapterPosition()).
                        getLongitude() + ",15z";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                intent.putExtra(Intent.EXTRA_TEXT, assumpte);
                view.getContext().startActivity(Intent.createChooser(intent, "Share using"));

            }
        });
    }



    @Override
    public int getItemCount() {
        if(historial != null) {
            return historial.size();
        }
        return 0;
    }
}