package com.dev.gott.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 07-06-2016.
 */
public class DataBaseManager {
    public static final String TABLE_RUTAS = "RUTAS";
    public static final String TABLE_PUNTOS = "PUNTOS";
    public static final String TABLE_RUTAPUNTOS = "PUNTOS_RUTAS";

    //Columnas tabla PUNTOS
    public static final String ID_PUNTO = "ID_PUNTO";
    public static final String LAT_PUNTO = "LAT_PUNTO";
    public static final String LONG_PUNTO = "LONG_PUNTO";
    public static final String CALLE_LAT = "CALLE_LAT";
    public static final String CALLE_LONG = "CALLE_LONG";

    //Columnas tabla RUTAS
    public static final String ID_RUTA = "ID_RUTA";
    public static final String NOMBRE_RUTA = "NOMBRE_RUTA";

    //Columnas tabla RUTAS_PUNTOS
    public static final String ID_PUNTORUTA = "ID_PUNTORUTA";
    public static final String ID_P = "ID_P";
    public static final String ID_R = "ID_R";
    public static final String ORDEN_PUNTORUTA = "ORDEN_PUNTORUTA";

    public static final String CREATE_RUTAS = "CREATE TABLE "+TABLE_RUTAS+" ("
            +ID_RUTA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NOMBRE_RUTA+" TEXT NOT NULL);";

    public static final String CREATE_PUNTOS = "CREATE TABLE "+TABLE_PUNTOS+" ("
            +ID_PUNTO+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +LAT_PUNTO+" FLOAT NOT NULL,"
            +LONG_PUNTO+" FLOAT NOT NULL,"
            +CALLE_LAT+" TEXT,"
            +CALLE_LONG+" TEXT);";

    public static final String CREATE_PUNTOSRUTAS = "CREATE TABLE "+TABLE_RUTAPUNTOS+" ("
            +ID_PUNTORUTA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +ID_P+" INTEGER NOT NULL,"
            +ID_R+" INTEGER NOT NULL,"
            +ORDEN_PUNTORUTA+" INTEGER NOT NULL, FOREIGN KEY("
            +ID_R+") REFERENCES "+TABLE_RUTAS+"("+ID_RUTA+"), FOREIGN KEY("
            +ID_P+") REFERENCES "+TABLE_PUNTOS+"("+ID_PUNTO+"));";





    private DBHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    public void ingresarPunto(double latitud, double longitud){
        db.execSQL("INSERT INTO "+TABLE_PUNTOS+" VALUES (null,"+latitud+","+longitud+",null,null)");
    }

    public void ingresarRuta(String nombre, double[][] puntos){
        String[] args = {nombre};
        int idRuta=0;
        db.execSQL("INSERT INTO "+TABLE_RUTAS+" VALUES (null,'"+nombre+"')");
        Cursor c1 = db.rawQuery("SELECT "+ID_RUTA+" FROM "+TABLE_RUTAS+" WHERE "+NOMBRE_RUTA+"=? ",args);
        if (c1.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            /*do {
                String id = c.getString(0);
                String nombre = c.getString(1);
            } while(c.moveToNext());
            */
            String idRutaString = c1.getString(0);
            idRuta = Integer.parseInt(idRutaString);
        }
        c1.close();
        int i=0;
        int largo = puntos.length;
        int idPuntos[] = new int[largo];

        while (i<largo){
            ingresarPunto(puntos[i][0],puntos[i][1]);
            String[] args1 = {String.valueOf(puntos[i][0]), String.valueOf(puntos[i][1])};
            Cursor c2 = db.rawQuery("SELECT "+ID_PUNTO+" FROM "+TABLE_PUNTOS+" WHERE "+LAT_PUNTO+"=? AND "+LONG_PUNTO+"=?",args1);
            if (c2.moveToFirst()) {
                String idPuntoString = c2.getString(0);
                idPuntos[i] = Integer.parseInt(idPuntoString);
            }
            db.execSQL("INSERT INTO "+TABLE_RUTAPUNTOS+" VALUES (null,"+idPuntos[i]+","+idRuta+","+i+")");
            c2.close();
            i++;
        }

    }


    public double[][] obtenerRuta(String nombre){
        String[] args = {nombre};
        String idRutaString = "";
        String latString = "";
        String longString = "";
        double[][] puntos = {{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1},{-1.1,-1.1}};
        //Encontrar ID de la ruta
        Cursor c = db.rawQuery("SELECT "+ID_RUTA+" FROM "+TABLE_RUTAS+" WHERE "+NOMBRE_RUTA+"=?",args);
        if (c.moveToFirst()) {
            idRutaString = c.getString(0);
            //int idRuta = Integer.parseInt(idRutaString);
        }

        //Encontrar id de cada punto, respecto al id de la ruta (y en orden)
        args = new String[]{idRutaString};
        String consulta = "SELECT "+ORDEN_PUNTORUTA+", "+LAT_PUNTO+", "+LONG_PUNTO
                +" FROM "+TABLE_PUNTOS+" JOIN "+TABLE_RUTAPUNTOS
                +" ON "+TABLE_PUNTOS+"."+ID_PUNTO+"="+TABLE_RUTAPUNTOS+"."+ID_P
                +" WHERE "+ID_R+" =? ORDER BY ORDEN_PUNTORUTA;";
        Cursor c2 = db.rawQuery(consulta,args);
        int i=0;
        if (c2.moveToFirst()) {
            do {
                latString = c2.getString(1);
                longString = c2.getString(2);
                puntos[i][0] = Double.parseDouble(latString);
                puntos[i][1] = Double.parseDouble(longString);
                i++;
            } while(c2.moveToNext());
        }
        return puntos;


    }



}
