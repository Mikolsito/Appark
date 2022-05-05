package com.example.appark.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.User;
import com.example.appark.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ConfiguracioFragment extends Fragment {
    private AppCompatButton cancelaBtn;
    private AppCompatButton guardaBtn;
    private EditText antigaContrasenya;
    private EditText novaContrasenya;
    private EditText anticCorreu;
    private EditText nouCorreu;

    private ConfiguracioViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View configView = inflater.inflate(R.layout.fragment_configuracio, container, false);
        viewModel = new ViewModelProvider(this).get(ConfiguracioViewModel.class);

        cancelaBtn = configView.findViewById(R.id.btnCancela);
        guardaBtn = configView.findViewById(R.id.btnGuardar);
        antigaContrasenya = configView.findViewById(R.id.editOldPassword);
        novaContrasenya = configView.findViewById(R.id.editNewPassword);
        anticCorreu = configView.findViewById(R.id.editOldEmail);
        nouCorreu = configView.findViewById(R.id.editNewEmail);

        guardaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewModel.updateUser(antigaContrasenya.getText().toString(), novaContrasenya.getText().toString(), anticCorreu.getText().toString(), nouCorreu.getText().toString());
                //TODO: salta la excepcion public boolean performClick() {
                //        throw new RuntimeException("Stub!");
                //    }
            }
        });
        cancelaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return configView;
    }

}