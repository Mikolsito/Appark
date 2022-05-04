package com.example.appark.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appark.R;

public class LoginActivity extends Activity {
    Button entra, registre;
    EditText mail, contrasenya;

    public LoginActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entra = (Button) findViewById(R.id.btn_login);
        registre = (Button) findViewById(R.id.btn_goToRegister);
        mail = (EditText) findViewById(R.id.et_email);
        contrasenya = (EditText) findViewById(R.id.et_password);

        //TODO crear una classe DataBaseHelper per a gestionar la base de dades
        //databaseHelper = new DatabaseHelper(this);

        entra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mail.getText().toString();
                String password = contrasenya.getText().toString();

                if(true){ // if (databaseHelper.CheckLogin(username, password)) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent processar_main = new Intent(view.getContext(), MainActivity.class);
                    startActivityForResult(processar_main, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "Correu o contrasenya invalids", Toast.LENGTH_SHORT).show();
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