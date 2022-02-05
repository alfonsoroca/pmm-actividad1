package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ControladorDB controladorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controladorDB = new ControladorDB(this);
        // Listener del botón de login
        findViewById(R.id.loginButton).setOnClickListener(this);
        // Listener del botón crear usuario
        findViewById(R.id.createUserButton).setOnClickListener(this);
        // Ocultar ActionBar
        getSupportActionBar().hide();
    }

    // Listener de los botones login / crear usuario
    @Override
    public void onClick(View view) {

        View v = view;
        // Almacenamos el contenido de las cajas
        TextInputEditText usuario = findViewById(R.id.userBox);
        TextInputEditText pass = findViewById(R.id.passBox);
        // Pasamos a String el contenido de las cajas
        String usuarioString = usuario.getText().toString();
        String passString = pass.getText().toString();

        // Pasamos a MainActivity
        Intent intentLogin = new Intent(this, MainActivity.class);

        // Con el if controlamos el origen de la pulsación
        if (v == findViewById(R.id.loginButton)) {

            // Lógica del botón de login
            if (controladorDB.loginUsuario(usuarioString, passString)) {

                Toast toast = Toast.makeText(this, "Usuario / Contraseña correcto", Toast.LENGTH_LONG);
                toast.show();
                intentLogin.putExtra("usuario", usuarioString);
                startActivity(intentLogin);

            } else {
                Toast toast = Toast.makeText(this, "Usuario / Contraseña erróneo", Toast.LENGTH_LONG);
                toast.show();
            }

            // Lógica del botón crear usuario
        } else {

            // Creamos el usuario y su contraseña en la base de datos
            if (controladorDB.crearUsuario(usuarioString, passString)) {

                Toast toast = Toast.makeText(this, "Usuario creado", Toast.LENGTH_LONG);
                toast.show();

                intentLogin.putExtra("usuario", usuarioString);
                startActivity(intentLogin);

            // Si ya existe el usuario lo informamos
            } else {
                Toast toast = Toast.makeText(this, "Ya existe ese usuario", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}