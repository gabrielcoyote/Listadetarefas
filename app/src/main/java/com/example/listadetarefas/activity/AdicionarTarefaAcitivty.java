package com.example.listadetarefas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaAcitivty extends AppCompatActivity {

    private TextInputEditText textView;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa_acitivty);

        textView = findViewById(R.id.texttarefa);

        //Recperar tarefa caso haja edição

        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        // Configurar caixa de texto

        if(tarefaAtual != null){
        textView.setText(tarefaAtual.getNometarefa());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar o menu; isso adiciona itens à barra de ação, se houver.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Lidar com cliques do item da barra de ação aqui.
        // lida automaticamente com cliques no botão
        // conforme você especifica uma atividade ela responde aqui


        switch (item.getItemId()){

            case R.id.Salvar:

                // tanto edição como salvar usam a mesma logica
                // finish(); fecha a tarefa assim que termina
                // if faz uma verificação se o item e nulo
                //  Toast.makeText lança uma mensagem se der certo a ação e outra caso não
                // utilizando do bolean do  ItarefaDAO

                TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );

                if ( tarefaAtual != null ){//edicao

                    String nomeTarefa = textView.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNometarefa( nomeTarefa );
                        tarefa.setId( tarefaAtual.getId() );

                        //atualizar no banco de dados
                        if ( tarefaDAO.atualizar( tarefa ) ){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao atualizar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {//salvar

                    String nomeTarefa = textView.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNometarefa( nomeTarefa );

                        if ( tarefaDAO.salvar( tarefa ) ){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }

                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}