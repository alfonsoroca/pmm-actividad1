package com.example.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ControladorDB extends SQLiteOpenHelper {

    public ControladorDB(Context context) {
        super(context, "com.example.todolist.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación de tablas (USUARIOS)
        db.execSQL("CREATE TABLE USUARIOS (NOMBRE TEXT PRIMARY KEY, PASS TEXT NOT NULL);");
        // Creación de tablas (TAREAS)
        db.execSQL("CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL, USUARIO TEXT, FOREIGN KEY (USUARIO) REFERENCES USUARIOS(NOMBRE));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Inserción registros
    public void addTarea(String tarea, String usuario) {

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", tarea);
        registro.put("USUARIO", usuario);

        // Abrir la bd
        SQLiteDatabase db = this.getWritableDatabase();

        // Insercción de registro
        db.insert("TAREAS", null, registro);
        /*Alternativa a lo anterior:
            db.execSQL("INSERT INTO TAREAS VALUES (null, '+ tarea +');");*/

        // Cerrar la bd
        db.close();
    }

    // Obtener las tareas de la base de datos de un determinado usuario
    public String[] obtenerTareas(String usuario) {
        // Abrir la bd
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta.- Genera un conjunto de registros = Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE USUARIO=?", new String[]{usuario});

        int regs = cursor.getCount(); // Guardamos el número de registros

        if (regs == 0) {
            // Cerrar la bd
            db.close();
            return null;
        } else {
            String[] tareas = new String[regs];
            cursor.moveToFirst(); // Nos movemos al primer registro
            for (int i = 0; i < regs; i++) {
                tareas[i] = cursor.getString(1); // Obtenemos el campo 1
                cursor.moveToNext();
            }
            db.close();
            return tareas;
        }
    }

    // Obtener el número de registros de la base de datos de un determinado usuario
    public int numeroRegistros(String usuario) {
        // Abrir la bd
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta.- Genera un conjunto de registros = Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE USUARIO=?", new String[]{usuario});
        return cursor.getCount();

    }

    // Dar de baja tareas
    public void borrarTarea(String tarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS", "NOMBRE=?", new String[]{tarea});
        db.close();
    }

    // Método para controlar el acceso a la aplicación
    public boolean loginUsuario(String usuario, String password) {

        if (this.validarUsuario(usuario)) {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT PASS FROM USUARIOS WHERE NOMBRE =?", new String[]{usuario});
            cursor.moveToFirst();
            if (password.equals(cursor.getString(0))) {
                // Cerrar la bd
                db.close();
                return true;
            } else {
                // Cerrar la bd
                db.close();
                return false;
            }

        } else {
            return false;
        }
    }

    // Método que crea un usuario con su contraseña
    public boolean crearUsuario(String usuario, String password) {

        // Si no existe el usuario en la base de datos creamos usuario y contraseña
        if (!this.validarUsuario(usuario)) {

            ContentValues registro = new ContentValues();
            registro.put("NOMBRE", usuario);
            registro.put("PASS", password);

            // Abrir la bd
            SQLiteDatabase db = this.getWritableDatabase();

            // Insercción de registro
            db.insert("USUARIOS", null, registro);

            // Cerrar la bd
            db.close();

            return true;

        } else {
            return false;
        }
    }

    // Método que comprueba si existe un usuario en la base de datos
    public boolean validarUsuario(String usuario) {

        // Abrir la bd
        SQLiteDatabase db = this.getReadableDatabase();
        // Ejecutar consulta
        Cursor cursor = db.rawQuery("SELECT PASS FROM USUARIOS WHERE NOMBRE =?", new String[]{usuario});
        // Lógica de la existencia del usuario
        if (cursor.getCount() > 0) {
            // Cerrar base de datos
            db.close();
            return true;
        } else {
            // Cerrar base de datos
            db.close();
            return false;
        }
    }
}