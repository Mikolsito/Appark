package com.example.appark.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.appark.R;

public class RegisterActivity extends AppCompatActivity {
    Button registrarse, tornar;
    EditText nom, mail, contrasenya, contrasenya2;
    RegisterActivityViewModel viewModel;
    CheckBox termes;

    public RegisterActivity() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewModel = new ViewModelProvider(this).get(RegisterActivityViewModel.class);
        registrarse = (Button) findViewById(R.id.btn_register);
        tornar = (Button) findViewById(R.id.btn_tornarLogin);
        nom = (EditText) findViewById(R.id.et_name);
        mail = (EditText) findViewById(R.id.et_email_r);
        contrasenya = (EditText) findViewById(R.id.et_password_r);
        contrasenya2 = (EditText) findViewById(R.id.et_password2_r);

        //TODO crear DataBaseHelper per a gestionar la base de dades
        //databaseHelper = new DatabaseHelper(this);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nom.getText().toString();
                String correu = mail.getText().toString();
                String password = contrasenya.getText().toString();
                String confirm_password = contrasenya2.getText().toString();

                if(name.equals("") || correu.equals("") || password.equals("") || confirm_password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Nom, correu i contrasenya requerits", Toast.LENGTH_SHORT).show();
                } else {
                    if(password.equals(confirm_password)) {
                        if (isMail(correu)){ // TODO && !mailExists()) {
                            if (isPasswordSegur(password)) {
                                if (true /*termes.isChecked()*/) {
                                    viewModel.createUserDB(name, correu, password);
                                    Toast.makeText(getApplicationContext(), "Registrat amb èxit", Toast.LENGTH_SHORT).show();
                                    nom.setText("");
                                    mail.setText("");
                                    contrasenya.setText("");
                                    contrasenya2.setText("");
                                    Intent processar_main = new Intent(view.getContext(), MainActivity.class);
                                    startActivityForResult(processar_main, 0);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Has d'acceptar els termes i polítiques", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"Contrasenya - mínim 8 caràcters, 1 majúscula, 1 minuscula, 1 numero", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Correu no vàlid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Les contrasenyes no són iguals", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent processar_log = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(processar_log, 0);
            }
        });
    }

    //TODO afegir aquests dos metodes a la classe de la database, dins de CheckUsername

    /* Classe per a comprovar si un mail és valid (minim una majuscula,
     * format correcte, etc)
     * @param correu
     * */
    public boolean isMail(String correu) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correu);
        return matcher.find();
    }

    /* Classe per a comprovar si una contrasenya és valida (format correcte, etc)
     * @param password
     * */
    public boolean isPasswordSegur(String password) {
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

}
