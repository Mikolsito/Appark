package com.example.appark.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appark.R;

public class TancarSessioFragment extends Fragment {
    private ProgressBar progressBar;

    public TancarSessioFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tancarsessio, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.circular_progressbar);

        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged", false);
        editor.apply();

        Intent processar_log = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(processar_log, 0);

        return view;
    }

}
