package com.example.appark.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View configView = inflater.inflate(R.layout.fragment_configuracio, container, false);

        cancelaBtn = configView.findViewById(R.id.btnCancela);
        guardaBtn = configView.findViewById(R.id.btnGuardar);
        antigaContrasenya = configView.findViewById(R.id.editOldPassword);
        novaContrasenya = configView.findViewById(R.id.editNewPassword);
        anticCorreu = configView.findViewById(R.id.editOldEmail);
        nouCorreu = configView.findViewById(R.id.editNewEmail);

        cancelaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Falta implementar esto con FragmentManager", Toast.LENGTH_SHORT).show();
            }
        });

        guardaBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MainActivity.currentUser.updateUser(antigaContrasenya.getText().toString(), novaContrasenya.getText().toString(), anticCorreu.getText().toString(), nouCorreu.getText().toString())){
                            Toast.makeText(view.getContext(), "S'han actualitzat correctament les dades", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(view.getContext(), "La contrasenya o el mail no corresponen amb els actuals", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return configView;
    }


}