package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements  ItarefaDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    // getWritableDatabase() escreve os itens
    // getReadableDatabase(); ler os itens

    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();

    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        /* salvar e colocado em uma estrutura try catch por causa do banco de dados
        caso erro cach retornarar um log, caso de certo no try od dados só serao preenchidos
        caso os item não seja nulo, ContentValues serve para passar os valores da tabela que são
        utilizados
         */

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNometarefa());

       try {

           escrever.insert(DbHelper.TABELAS_TAREFAS, null,cv);

       }catch (Exception e){
           Log.e("INFO","Erro ao salvar tarefa" + e.getMessage());
           return false;
       }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNometarefa());

    // foi passado 4 argumentos no atualizar Nome da tabela, ContentValues, whereClause no caso o id,
    // e args que e um array com o parametro tarefa pra fazer atualização.

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.update(DbHelper.TABELAS_TAREFAS, cv, "id=?", args );
            Log.e("INFO","Tarefa Atualizada com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        // mesmo metodo acima porem sem o uso do contentValues

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.delete(DbHelper.TABELAS_TAREFAS, "id=?", args );
            Log.e("INFO","Tarefa Removida com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao Remover tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        // criar a lista
        List<Tarefa> tarefas = new ArrayList<>();

        // recuperar as tarefas do banco de dados
        String sql = "SELECT * FROM " + DbHelper.TABELAS_TAREFAS + " ;";
        Cursor c = ler.rawQuery(sql, null);

        // para percorrer o cursor
        while ( c.moveToNext() ){

            Tarefa tarefa = new Tarefa();

            // itens recuperados do banco de dados
            Long id = c.getLong( c.getColumnIndex("id") );
            String nomeTarefa = c.getString( c.getColumnIndex("nome") );

            tarefa.setId( id );
            tarefa.setNometarefa( nomeTarefa );

            //recuperando a tarefa
            tarefas.add( tarefa );

        }

        return tarefas;
    }
}
