package com.example.estruch18.repaso5a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by estruch18 on 2/2/16.
 */
public class DBAdapter {
    //DATOS DE BASE DE DATOS SQLITE
    private static final String NOMBRE_BD = "Vehiculos.db";
    private static final int VERSION_BD = 1;

    //ATRIBUTOS
    private final Context context;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new MyDBHelper(context, NOMBRE_BD, null, VERSION_BD);
    }

    //PD: RECORDAR MUY IMPORTANTE: ABRIR INSTANCIA DE BASE DE DATOS!
    public void open(){

        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            db = dbHelper.getReadableDatabase();
        }

    }

    private static class MyDBHelper extends SQLiteOpenHelper{

        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //CREACIÓN DE TABLAS
            db.execSQL("CREATE TABLE Persona(" +
                    "Id             integer         primary key autoincrement," +
                    "Nombre         varchar(255)    not null," +
                    "Apellido       varchar(255)    not null," +
                    "Direccion      varchar(255)    not null," +
                    "Telefono       integer(9)      not null );");

            db.execSQL("CREATE TABLE Coche(" +
                    "Matricula      varchar(255)    primary key," +
                    "Fabricante     varchar(255)    not null," +
                    "Modelo         varchar(255)    not null," +
                    "Kilometros     integer(3)      not null," +
                    "Id_usuario     integer         not null, " +
                    "foreign key (Id_usuario) references Persona(Id));");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Persona;");
            db.execSQL("DROP TABLE IF EXISTS Coche;");

        }
    }

    //INSERTS
    public void insertarPersona(String n, String a, String d, int t){
        //ASIGNACIÓN DE VALORES A UN OBJETO ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", n);
        contentValues.put("apellido", a);
        contentValues.put("direccion", d);
        contentValues.put("telefono", t);

        db.insert("Persona", null, contentValues);

        //COMPROBACIÓN DE INSERCIÓN
        Toast.makeText(context, "Se ha insertado la persona correctamente", Toast.LENGTH_SHORT).show();
    }

    public void insertarCoche(String m, String f, String mo, int k, int id_u){
        ContentValues contentValues = new ContentValues();
        contentValues.put("matricula", m);
        contentValues.put("fabricante", f);
        contentValues.put("modelo", mo);
        contentValues.put("kilometros", k);
        contentValues.put("id_usuario", id_u);

        db.insert("Coche", null, contentValues);

        //COMPROBACIÓN DE INSERCIÓN
        Toast.makeText(context, "Se ha insertado el coche correctamente", Toast.LENGTH_SHORT).show();
    }

    //SELECTS
    public ArrayList<String> cargarPersonas(){
        ArrayList<String> personas = new ArrayList<String>();
        Cursor cursorPersonas = db.query("Persona", null, null, null, null, null, null);

        if(cursorPersonas != null && cursorPersonas.moveToFirst()){
            do {
                personas.add(cursorPersonas.getString(1));
            }while(cursorPersonas.moveToNext());
        }
        else{
            Toast.makeText(context, "No existen datos en el cursor", Toast.LENGTH_SHORT).show();
        }

        return  personas;
    }

    public ArrayList<String> cargarCoches(){
        ArrayList<String> coches =  new ArrayList<String>();
        Cursor cursorCoches = db.query("Coche", null, null, null, null, null, null);

        if(cursorCoches != null && cursorCoches.moveToFirst()){
            do {
                coches.add(cursorCoches.getString(1));
            }while(cursorCoches.moveToNext());
        }
        else{
            Toast.makeText(context, "No existen datos en el cursor", Toast.LENGTH_SHORT).show();
        }

        return coches;
    }

    public String cargarPersona(int id_persona){
        String nombre = null;
        Cursor cursorPersona = db.rawQuery("SELECT * FROM Persona WHERE Id = " + id_persona + ";", null, null);

        if(cursorPersona != null && cursorPersona.moveToFirst()){
            do {
                nombre = cursorPersona.getString(1);
            }while (cursorPersona.moveToNext());
        }
        else{
            Toast.makeText(context, "No existe ninguna persona con el ID especificado", Toast.LENGTH_SHORT).show();
        }

        return nombre;
    }

    public String cargarCoche(String matricula_coche){
        String fabricante = null;
        Cursor cursorCoche = db.rawQuery("SELECT * FROM Coche WHERE Matricula = '"+matricula_coche+"';", null, null);

        if(cursorCoche != null && cursorCoche.moveToFirst()){
            do {
                fabricante = cursorCoche.getString(1);
            }while (cursorCoche.moveToNext());
        }
        else{
            Toast.makeText(context, "No existe ningun coche con la matrícula especificada", Toast.LENGTH_SHORT).show();
        }

        return fabricante;
    }

    //DELETE
    public void eliminarPersona(int id_persona){
        db.execSQL("DELETE FROM Persona WHERE Id = "+id_persona+";");
        //COMPROBACIÓN DE BORRADO
        Toast.makeText(context, "Se ha eliminado la persona correctamente", Toast.LENGTH_SHORT).show();
    }

    public void eliminarCoche(String matricula_coche){
        db.execSQL("DELETE FROM Coche WHERE Matricula = '" + matricula_coche + "';");
    }

    public void eliminarTodasPersonas(){
        db.execSQL("DELETE FROM Persona;");
        //COMPROBACIÓN DE BORRADO
        Toast.makeText(context, "Se han eliminado todas las personas", Toast.LENGTH_SHORT).show();
    }

    public void eliminarTodosCoches(){
        db.execSQL("DELETE FROM Coche;");
        //COMPROBACIÓN DE BORRADO
        Toast.makeText(context, "Se han eliminado todos los coches", Toast.LENGTH_SHORT).show();
    }

    //UPDATES
    public void modificarPersona(String n, String a, String d, int t, int id_persona){
        db.execSQL("UPDATE Persona SET Nombre = '" + n + "', Apellido = '" + a + "', Direccion = '" + d + "', Telefono = " + t + " WHERE Id = " + id_persona + ";");
    }

    public void modificarCoche(String nuevaMatricula, String f, String mo, int k, String matricula_coche){
        db.execSQL("UPDATE Coche SET Matricula = '"+nuevaMatricula+"', Fabricante = '"+f+"', Modelo = '"+mo+"', Kilometros = "+k+" WHERE Matricula = '"+matricula_coche+"';");
    }

    //ELIMINAR BASE DE DATOS SQLITE
    public void eliminarBD(){
        context.deleteDatabase("Vehiculos.db;");
    }
}
