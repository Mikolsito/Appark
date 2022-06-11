package com.example.appark.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.R;

import java.util.ArrayList;
import java.util.List;

public class HistorialUbisFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistorialViewModel viewModel;
    private String TAG = "HistorialUbisFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        setLiveDataObservers();
        return view;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(getActivity()).get(HistorialViewModel.class);
        Log.d(TAG, "SetAdapter");
        final Observer<ArrayList<Location>> observer = new Observer<ArrayList<Location>>() {
            @Override
            public void onChanged(ArrayList<Location> e) {
                Log.d(TAG, "onChanged");
                HistorialAdapter adapter = new HistorialAdapter(e);
                recyclerView.swapAdapter(adapter, false);
                Log.d(TAG, "Adapter Swapped");
                adapter.notifyDataSetChanged();
                Log.d(TAG, "notifyDataSetChanged");
            }
        };
        viewModel.getEstacionaments().observe(getViewLifecycleOwner(), observer);
    }
}