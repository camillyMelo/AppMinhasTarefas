package br.edu.ifsp.arq.minhastarefas.mvp;

import android.content.Context;
import android.os.Bundle;

public interface TarefaDetailsMVP {

    interface View{
        void updateUI(String nomeTarefa, String descricao, String data, int prioridade);

        Bundle getBundle();

        void showToast(String message);

        void close();
        Context getContext();
    }

    interface Presenter{
        void deatach();

        void verifyUpdate();

        void saveTarefa(String nomeTarefa, String descricao, String data, int prioridade);
    }
}
