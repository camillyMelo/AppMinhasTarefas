package br.edu.ifsp.arq.minhastarefas.presenter;

import android.os.Bundle;

import br.edu.ifsp.arq.minhastarefas.model.dao.TarefaDaoSingleton;
import br.edu.ifsp.arq.minhastarefas.model.dao.ITarefaDao;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;
import br.edu.ifsp.arq.minhastarefas.mvp.TarefaDetailsMVP;
import br.edu.ifsp.arq.minhastarefas.utils.Constantes;

public class TarefaDetailsPresenter implements TarefaDetailsMVP.Presenter {

    private TarefaDetailsMVP.View view;
    private Tarefa tarefa;
    private ITarefaDao dao;

    public TarefaDetailsPresenter(TarefaDetailsMVP.View view) {
        this.view = view;
        tarefa = null;
        dao = TarefaDaoSingleton.getInstance();
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate() {
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constantes.ATTR_TITLE);
            tarefa = dao.findByTitle(title);
            view.updateUI(tarefa.getNomeTarefa(), tarefa.getDescricao(), tarefa.getData(), tarefa.getPrioridade());
        }
    }

    @Override
    public void saveTarefa(String nomeTarefa, String descricao, String data, int prioridade) {

        if(tarefa == null){
            //New article
            tarefa = new Tarefa(nomeTarefa, descricao, data, prioridade);
            dao.create(tarefa);
            view.showToast("Nova tarefa adicionada com sucesso.");
            view.close();
        }else{
            //Update a existent article
            String oldTitle = tarefa.getNomeTarefa();
            Tarefa newTarefa= new Tarefa(nomeTarefa,descricao, data, prioridade);
            if(dao.update(oldTitle, newTarefa)){
                view.showToast("Tarefa atualizada com sucesso.");
                view.close();
            }else{
                view.showToast("Erro ao atualizar a tarefa.");
            }
        }
    }
}
