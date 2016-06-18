package com.dev.gott.sqlitetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DataBaseManager(this);

        String Ruta1 = "Mi ruta 1";
        double[][] puntosRuta1 = {{-33.415888, -70.716666},{-33.4777777, -70.7117111},{-33.400001, -70.7052432}};
        String Ruta2 = "Mi ruta 2";
        double[][] puntosRuta2 = {{-33.437303, -70.635218},{-33.437589, -70.636548},{-33.440391, -70.635303},{-33.439764, -70.632771},{-33.442686, -70.632112},{-33.443914, -70.635205}};

        String Ruta3 = "Mi ruta 3";
        double[][] puntosRuta3 = {{-33.425648,-70.674832},{-33.898989,-70.1321323},{-33.123123,-70.111111},{-33.313213,-70.898989},{-33.222222,-70.8895893}};

        /*manager.ingresarRuta(Ruta1,puntosRuta1);
        manager.ingresarRuta(Ruta2,puntosRuta2);
        manager.ingresarRuta(Ruta3,puntosRuta3);
        */
        double[][] puntosActual = manager.obtenerRuta(Ruta1);




    }
}
