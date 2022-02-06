package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.db.ControladorDB;

public class MainActivity extends AppCompatActivity {

    ControladorDB controladorDB;
    private ArrayAdapter<String> mdapter;
    ListView listViewTareas;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorDB = new ControladorDB(this);
        usuario = getIntent().getStringExtra("usuario");
        listViewTareas = (ListView) findViewById(R.id.list_todo);
        actualizarUI();
    }

    // Carga del menu en AppBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        EditText cajaDialogo = new EditText(this);
        Toast toast = Toast.makeText(this, "Tarea creada", Toast.LENGTH_LONG);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Nueva tarea")
                .setMessage("Intoduce la nueva tarea")
                .setView(cajaDialogo)
                .setPositiveButton("Añadir tarea", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        controladorDB.addTarea(cajaDialogo.getText().toString(), usuario);
                        actualizarUI();
                        toast.show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();

        return super.onOptionsItemSelected(item);
    }

    // Actualizar ViewList
    private void actualizarUI() {
        if (controladorDB.numeroRegistros(usuario) == 0) {
            listViewTareas.setAdapter(null);
        } else {
            mdapter = new ArrayAdapter<>(this, R.layout.item_todo, R.id.task_title, controladorDB.obtenerTareas(usuario));
            listViewTareas.setAdapter(mdapter);
        }
    }

    // Borrar tareas
    public void borrarTarea(View view) {
        // Obtención del parent
        View parent = (View) view.getParent();
        // Obtención el objeto
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        // Obtención el texto
        String tarea = tareaTextView.getText().toString();
        // Llamada al método para borrar tarea
        controladorDB.borrarTarea(tarea);
        // Mensaje de información al usuario
        Toast toast = Toast.makeText(this, "Tarea finalizada", Toast.LENGTH_LONG);
        toast.show();
        actualizarUI();
    }

    // Actualizar tarea
    public void updateTarea(View view){

        // Obtención del parent
        View parent = (View) view.getParent();
        // Obtención el objeto
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        // Obtención el texto
        String oldTask = tareaTextView.getText().toString();
        EditText cajaDialogo = new EditText(this);
        Toast toast = Toast.makeText(this, "Tarea modificada", Toast.LENGTH_LONG);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Modificar tarea")
                .setMessage("Modifica la tarea")
                .setView(cajaDialogo)
                .setPositiveButton("Modificar tarea", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        controladorDB.updateTarea(cajaDialogo.getText().toString(), usuario, oldTask);
                        actualizarUI();
                        toast.show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }
}