package br.edu.ifsp.arq.minhastarefas.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.arq.minhastarefas.model.dao.TarefaDaoSingleton;
import br.edu.ifsp.arq.minhastarefas.model.dao.ITarefaDao;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;
import br.edu.ifsp.arq.minhastarefas.mvp.MainMVP;
import br.edu.ifsp.arq.minhastarefas.utils.Constantes;
import br.edu.ifsp.arq.minhastarefas.view.TarefaActivity;
import br.edu.ifsp.arq.minhastarefas.view.adapter.ItemPocketRecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter {

    private MainMVP.View view;
    private ITarefaDao dao;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        dao = TarefaDaoSingleton.getInstance();
    }

    @Override
    public void deatach() {
        view = null;
    }

    @Override
    public void openDetails() {
        Intent intent = new Intent(view.getContext(), TarefaActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openDetails(Tarefa tarefa) {
        Intent intent = new Intent(view.getContext(), TarefaActivity.class);
        intent.putExtra(Constantes.ATTR_TITLE, tarefa.getNomeTarefa());
        view.getContext().startActivity(intent);
    }

    @Override
    public void populateList(RecyclerView recyclerView) {
        ItemPocketRecyclerAdapter adapter = new
                ItemPocketRecyclerAdapter(view.getContext(), dao.findAll(), this);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void favoriteTarefa(Tarefa tarefa) {
        tarefa.setPrioridade(tarefa.getPrioridade());
        dao.update(tarefa.getNomeTarefa(), tarefa);
    }

    @Override
    public void deleteTask(Tarefa tarefa) {
        dao.delete(tarefa);
    }

}
