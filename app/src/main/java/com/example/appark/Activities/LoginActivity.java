package com.example.appark.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appark.Activities.src.User;
import com.example.appark.R;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {
    private Button entra, registre;
    private EditText correu, contrasenya;
    private LoginActivityViewModel viewModel;

    Context context;

    public LoginActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setLiveDataObservers(); //Inicializa los observers de esta Activity

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
                }
                viewModel.getRegisteredUserDB(mail);

                /*if (info) { //TODO mailExists()) { ???? viewModel.insertUserDB(mail, password) { ????
                    Toast.makeText(getApplicationContext(), "Login coreecte", Toast.LENGTH_SHORT).show();
                    //Intent processar_main = new Intent(view.getContext(), MainActivity.class);
                    //startActivityForResult(processar_main, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "Correu o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
                }*/
            }
            // TODO: nos salta una excepcion al acabar el onClick pero todas las llamadasfuncionan correctamente
        });

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent processar_reg = new Intent(view.getContext(), RegisterActivity.class);
                startActivityForResult(processar_reg, 0);
            }
        });
    }

    public void setLiveDataObservers() {
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        contrasenya = (EditText) findViewById(R.id.et_password);

        final Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(User us) {
                if(us.getPwd().equals(contrasenya.getText().toString())){
                    MainActivity.currentUser = us;
                    Intent processar_main = new Intent(context, MainActivity.class);
                    startActivityForResult(processar_main, 0);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Login incorrecte", Toast.LENGTH_SHORT).show();
                }
            }
        };

        viewModel.getUser().observe(this, observer);
    }
}