package br.edu.ifsp.arq.minhastarefas.model.dao;

import android.content.Context;

import java.util.List;

import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tag;

public interface ITarefaDao {
    void create(Tarefa tarefa);

    boolean update(String oldTitle, Tarefa tarefa);

    boolean delete(Tarefa tarefa);

    Tarefa findByTitle(String title);

    List<Tarefa> findByTag(Tag tag);

    List<Tarefa> findAll();

    void setContext (Context context);
}
