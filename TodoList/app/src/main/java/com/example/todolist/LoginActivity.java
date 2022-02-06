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

        // Paso a MainActivity
        Intent intentLogin = new Intent(this, MainActivity.class);

        // Con el if controlamos el origen de la pulsación
        if (v == findViewById(R.id.loginButton)) {

            // Lógica del botón de login
            if (controladorDB.loginUsuario(usuarioString, passString)) {
                // Mensaje de login correcto
                Toast toast = Toast.makeText(this, "Usuario / Contraseña correcto", Toast.LENGTH_LONG);
                toast.show();
                // Cambio de activity y paso de información
                intentLogin.putExtra("usuario", usuarioString);
                startActivity(intentLogin);

            } else {
                // Mensaje de error en el login
                Toast toast = Toast.makeText(this, "Usuario / Contraseña erróneo", Toast.LENGTH_LONG);
                toast.show();
            }

            // Lógica del botón crear usuario
        } else {

            if (!usuarioString.isEmpty() && !passString.isEmpty()) {

                // Validación para la creación en la base de datos de usuario / contraseña
                if (controladorDB.crearUsuario(usuarioString, passString)) {
                    // Mensaje de confirmación de creación de usuario / contraseña
                    Toast toast = Toast.makeText(this, "Usuario creado", Toast.LENGTH_LONG);
                    toast.show();
                    // Cambio de activity y paso de información
                    intentLogin.putExtra("usuario", usuarioString);
                    startActivity(intentLogin);

                    // Aviso de existencia en la base de datos del usuario
                } else {
                    usuario.setError("Usuario ya existe");
                    usuario.requestFocus();
                }

            } else if (usuarioString.isEmpty()){
                usuario.setError("Usuario vacío");

            } else if (passString.isEmpty()){
                pass.setError("Password vacía");
                pass.requestFocus();
            }
        }
    }
}