package com.example.appark.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;

    Context context;

    public LoginActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this.getApplicationContext();
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("logged", false)) {
            String n = sharedPreferences.getString("name", "dummyCurrentUser");
            String u = sharedPreferences.getString("user", "dummy@gmail.com");
            String p = sharedPreferences.getString("pwd", "dummyPwd");
            MainActivity.currentUser.setUser(n,u,p);
            Intent processar_main = new Intent(context, MainActivity.class);
            startActivityForResult(processar_main, 0);
        }

        setLiveDataObservers(); //Inicializa los observers de esta Activity
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        entra = (Button) findViewById(R.id.btn_login);
        registre = (Button) findViewById(R.id.btn_goToRegister);
        correu = (EditText) findViewById(R.id.et_email);
        contrasenya = (EditText) findViewById(R.id.et_password);
        progressBar = (ProgressBar) findViewById(R.id.circular_progressbar);

        InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);

        progressBar.setVisibility(View.GONE);

        entra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                for (int i = 0; i < 100000; i++); //delay per a mostrar la progressbar
                String mail = correu.getText().toString();
                String password = contrasenya.getText().toString();
                if (mail.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Correu i contrasenya requerits", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else /*TODO if mailExists())*/ {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); //treiem el teclat
                    viewModel.getRegisteredUserDB(mail);
                }
            }
        });

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent processar_reg = new Intent(view.getContext(), RegisterActivity.class);
                startActivityForResult(processar_reg, 0);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void setLiveDataObservers() {
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        contrasenya = (EditText) findViewById(R.id.et_password);

        final Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(User us) {

                if(us == null){
                    Toast.makeText(getApplicationContext(), "Correu o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if(us.getPwd().equals(contrasenya.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Login correcte", Toast.LENGTH_SHORT).show();
                    MainActivity.currentUser = us;

                    //Guardem el usuari a shared preferences per a que no calgui tornar a loguejar-se després
                    SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("logged", true);
                    editor.putString("name", us.getName());
                    editor.putString("user", us.getMail());
                    editor.putString("pwd", us.getPwd());
                    editor.apply();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Login correcte", Toast.LENGTH_SHORT).show();
                    Intent processar_main = new Intent(context, MainActivity.class);
                    startActivityForResult(processar_main, 0);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Correu o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

        };
        viewModel.getUser().observe(this, observer);
    }
}