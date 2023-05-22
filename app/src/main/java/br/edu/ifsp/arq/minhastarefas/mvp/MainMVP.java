package br.edu.ifsp.arq.minhastarefas.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;

public interface MainMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void deatach();

        void openDetails();

        void openDetails(Tarefa tarefa);

        void populateList(RecyclerView recyclerView);

        void favoriteTarefa(Tarefa tarefa);

        void deleteTask(Tarefa tarefa);

    }
}
