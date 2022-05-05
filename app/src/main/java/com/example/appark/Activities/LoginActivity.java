package com.example.appark.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.LoginActivityViewModel;
import com.example.appark.Activities.src.RegisterActivityViewModel;
import com.example.appark.R;

public class LoginActivity extends AppCompatActivity {
    private Button entra, registre;
    private EditText correu, contrasenya;
    LoginActivityViewModel viewModel;

    public LoginActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        entra = (Button) findViewById(R.id.btn_login);
        registre = (Button) findViewById(R.id.btn_goToRegister);
        correu = (EditText) findViewById(R.id.et_email);
        contrasenya = (EditText) findViewById(R.id.et_password);

        entra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = correu.getText().toString();
                String password = contrasenya.getText().toString();
                if (mail.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Correu i contrasenya requerits", Toast.LENGTH_SHORT).show();
                } else if(true) { //TODO mailExists()) {
                    Intent processar_main = new Intent(view.getContext(), MainActivity.class);
                    startActivityForResult(processar_main, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "Correu o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent processar_reg = new Intent(view.getContext(), RegisterActivity.class);
                startActivityForResult(processar_reg, 0);
            }
        });

    }
}