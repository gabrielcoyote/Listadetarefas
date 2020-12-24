package com.example.listadetarefas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listadetarefas.R;
import com.example.listadetarefas.adapter.TarefaAdapter;
import com.example.listadetarefas.helper.RecyclerItemClickListener;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listatarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.minha_lista);

        //Adicionar o evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            // quando o clique acontece uma vez e rapido utilizado o onItemClick
            @Override
            public void onItemClick(View view, int position) {

                // Recuperar tarefa para edição

                Tarefa tarefaselecionada = listatarefas.get(position);

                // Envia tarefa para tela adicionar tarefa
                Intent intent = new Intent(MainActivity.this, AdicionarTarefaAcitivty.class);
                intent.putExtra("tarefaSelecionada", tarefaselecionada );
                startActivity( intent);

            }
            // quando o clique fica um tempo precionado e utilizado onLongItemClick
            @Override
            public void onLongItemClick(View view, int position) {

                tarefaSelecionada = listatarefas.get(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                // Configura o titulo da mensagem

                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir a tarefa:" + tarefaSelecionada.getNometarefa() + "?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                        if (tarefaDAO.deletar(tarefaSelecionada)){
                            carregarlistatarefas();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao excluir tarefa!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao Excluir tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.setNegativeButton("Não", null);
                // Exibir dialog

                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }
        ));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaAcitivty.class);
                startActivity(intent);
            }
        });
    }

    public void carregarlistatarefas(){

        TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );
        listatarefas = tarefaDAO.listar();


        //listar tarefas de modo estatico

       /* Tarefa tarefa = new Tarefa();
        tarefa.setNometarefa("Ir ao mercado");
        listatarefas.add(tarefa);

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setNometarefa("Ir ao Shopping");
        listatarefas.add(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNometarefa("Ir ao Restaurante");
        listatarefas.add(tarefa2); */


        tarefaAdapter = new TarefaAdapter(listatarefas);

        // setHasFixedSizecomo true quando a alteração do conteúdo do adaptador não altera
        // sua altura ou largura.
        // addItemDecoration Este é o método construtor para meu RecyclerView personalizado:

        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);

    }
        // metodo do ciclo de vida de uma activity

    @Override
    protected void onStart() {
        carregarlistatarefas();
        super.onStart();
    }


}