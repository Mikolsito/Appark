package com.example.appark.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appark.R;

public class RegisterActivity extends Activity {
    Button registrarse, tornar;
    EditText nom, mail, contrasenya;

    public RegisterActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registrarse = (Button) findViewById(R.id.btn_register);
        tornar = (Button) findViewById(R.id.btn_tornarLogin);
        nom = (EditText) findViewById(R.id.et_name);
        mail = (EditText) findViewById(R.id.et_email_r);
        contrasenya = (EditText) findViewById(R.id.et_password_r);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Comprovar, afegir a base de dades, tornar a pantalla login i toast de confirmar
                Intent processar_main = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(processar_main, 0);
            }
        });

        tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO tornar a pantalla login
                Intent processar_log = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(processar_log, 0);
            }
        });
    }
}
