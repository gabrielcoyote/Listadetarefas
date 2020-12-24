package com.example.listadetarefas.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

// estender o banco de dados

public class DbHelper extends SQLiteOpenHelper {

    //indica a versão do app
    //indica o nome do banco de dados
    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELAS_TAREFAS = "tarefas";


    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS  " + TABELAS_TAREFAS + "(id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, nome TEXT NOT NULL);";

        try {
         db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao  criar tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela" + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "ALTER TABLE " + TABELAS_TAREFAS + " ADD COLUMN status VARCHAR(2) ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao atualizar app");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao ataulizar app" + e.getMessage());
        }

    }
   
}
