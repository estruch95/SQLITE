package com.example.estruch18.repaso5a;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnInsertarPersona, btnInsertarCoche, btnConsultarPersona, btnConsultarCoche;
    private DBAdapter dbAdapter;
    private ListView listaPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declaracionViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void declaracionViews(){
        dbAdapter = new DBAdapter(this);
        btnInsertarPersona = (Button)findViewById(R.id.btnInsertarPersona);
        btnInsertarCoche = (Button)findViewById(R.id.btnInsertarCoche);
        listaPersonas = (ListView)findViewById(R.id.lv_Personas);
        btnConsultarPersona = (Button)findViewById(R.id.btnConsultarPersonas);
        btnConsultarCoche = (Button)findViewById(R.id.btnConsultarCoches);
    }

    public void accionBtnInsertarPersona(View v){
        dbAdapter.open();
        dbAdapter.insertarPersona("Ivan", "Estruch", "Santa Cecília nº12", 695391923);
    }

    public void accionBtnInsertarCoche(View v){
        dbAdapter.open();
        dbAdapter.insertarCoche("443543-CZY", "Citroen", "Xsara", 156000, 1);
    }

    public void accionBtnConsultarPersonas(View v){
        dbAdapter.open();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dbAdapter.cargarPersonas());
        listaPersonas.setAdapter(adaptador);
    }

    public void accionBtnConsultarCoches(View v){
        dbAdapter.open();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dbAdapter.cargarCoches());
        listaPersonas.setAdapter(adaptador);
    }

}
