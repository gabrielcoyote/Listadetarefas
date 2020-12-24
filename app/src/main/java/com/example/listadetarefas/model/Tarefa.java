package com.example.listadetarefas.model;

import java.io.Serializable;

public class Tarefa implements Serializable {

    // id e necessario para saber qual item vai sofrer alteração.

    private Long id;
    private String nometarefa;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNometarefa() {
        return nometarefa;
    }

    public void setNometarefa(String nometarefa) {
        this.nometarefa = nometarefa;
    }

}
