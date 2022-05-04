package com.example.appark.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        entra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent processar_main = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(processar_main, 0);
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