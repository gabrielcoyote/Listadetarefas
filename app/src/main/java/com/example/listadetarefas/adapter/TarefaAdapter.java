package com.example.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;

import java.util.List;

// Viewholder e adapter juntos

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.myViewHolder> {

    private List<Tarefa> listatarefas;

    public TarefaAdapter(List<Tarefa> lista) {
        this.listatarefas = lista;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // aqui e estanciado a celula utilizada com o nome lista_tarefa_adapter
        // itemlista foi criado e estanciado no myViewHolder

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa_adapter, parent, false);

        return new  myViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Tarefa tarefa = listatarefas.get(position);
        holder.tarefa.setText(tarefa.getNometarefa());

    }

    @Override
    public int getItemCount() {
        return this.listatarefas.size();
    }

    public class  myViewHolder extends RecyclerView.ViewHolder{

        TextView tarefa;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);

            tarefa = itemView.findViewById(R.id.textTarefa);
        }
    }

}
