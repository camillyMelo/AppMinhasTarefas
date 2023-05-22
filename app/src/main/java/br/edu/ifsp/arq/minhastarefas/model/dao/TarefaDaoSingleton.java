package br.edu.ifsp.arq.minhastarefas.model.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifsp.arq.minhastarefas.model.entities.Tag;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;
import br.edu.ifsp.arq.minhastarefas.utils.Constantes;

public class TarefaDaoSingleton implements ITarefaDao {
    private static TarefaDaoSingleton instance = null;
    private List<Tarefa> tarefaList;
    private Context context;
    private TarefaDaoSingleton() {
        tarefaList = new ArrayList<Tarefa>();
        tarefaList.add(new Tarefa("Prova Redes","Materia: Camada de redes", "23/5/2023", 1));
        tarefaList.add(new Tarefa("Projeto DMO","Primeira fase feita","22/5/2023", 1));
        tarefaList.add(new Tarefa("Stop Motion IHC2", "Adicionar efeitos sonoros","24/5/2023", 2));
        tarefaList.add(new Tarefa("Site Web","Alterar css", "26/5/2023", 3));

    }

    public static TarefaDaoSingleton getInstance(){
        if(instance == null)
            instance = new TarefaDaoSingleton();
        return instance;
    }

    @Override
    public void create(Tarefa tarefa) {
        if(tarefa != null){
            tarefaList.add(tarefa);
            Collections.sort(tarefaList, comparatorTarefa);
            adicionarTarefaBanco();
            pegarTarefaBanco();
        }
    }

    @Override
    public boolean update(String oldTitle, Tarefa tarefa) {
        Tarefa inDataset;
        inDataset = tarefaList.stream()
                .filter(tarefa1 -> tarefa1.getNomeTarefa().equals(oldTitle))
                .findAny()
                .orElse(null);
        if(inDataset != null){
            inDataset.setNomeTarefa(tarefa.getNomeTarefa());
            inDataset.setPrioridade(tarefa.getPrioridade());
            inDataset.getTags().clear();
            inDataset.getTags().addAll(tarefa.getTags());
            Collections.sort(tarefaList, comparatorTarefa);
            adicionarTarefaBanco();
            pegarTarefaBanco();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Tarefa tarefa) {
        tarefaList.remove(tarefa);
        adicionarTarefaBanco();
        pegarTarefaBanco();
        return true;
    }

    @Override
    public Tarefa findByTitle(String title) {
        return tarefaList.stream()
                .filter(tarefa -> tarefa.getNomeTarefa().equals(title))
                .findFirst()
                .orElse(null);
    }
    Comparator<Tarefa> comparatorTarefa = Comparator.comparing(Tarefa::getPrioridade)
            .thenComparing(Tarefa::getData) //mudar para Date
            .thenComparing(Tarefa::getNomeTarefa);


    @Override
    public List<Tarefa> findByTag(Tag tag) {
        List<Tarefa> selection = new ArrayList<>();
        for(Tarefa a : tarefaList){
            for(Tag t : a.getTags()){
                if(t.getTagName().equals(tag.getTagName())){
                    selection.add(a);
                }
            }
        }
        return selection;
    }

    public void setContext(Context context){
        this.context = context;
    }
    @Override
    public List<Tarefa> findAll() { pegarTarefaBanco();
        return tarefaList;
    }

    private void adicionarTarefaBanco(){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        JSONObject jsonObject;
        JSONArray jsonArray;

        jsonArray = new JSONArray();
        for(Tarefa t : tarefaList){
            jsonObject = new JSONObject();
            try{
                jsonObject.put(Constantes.ATTR_TITLE, t.getNomeTarefa());
                jsonObject.put(Constantes.ATTR_DESCRIPTION, t.getDescricao());
                jsonObject.put(Constantes.ATTR_DATE, t.getData());
                jsonObject.put(Constantes.ATTR_PRIORITY, t.getPrioridade());
                jsonArray.put(jsonObject);
            }catch (JSONException e){
                Log.e("Erro", e.getMessage());
            }
        }
        preferences = context.getSharedPreferences(Constantes.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(Constantes.TABLE_NAME, jsonArray.toString());
        editor.commit();

    }
    private void pegarTarefaBanco(){
        SharedPreferences preferences;
        String json;
        Tarefa tarefa;
        JSONObject jsonObject;
        JSONArray jsonArray;

        preferences = context.getSharedPreferences(Constantes.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        json = preferences.getString(Constantes.TABLE_NAME, "");

        if(!json.isEmpty()){
            tarefaList.clear();
            try{
                jsonArray = new JSONArray(json);
                for(int i=0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    tarefa = new Tarefa(jsonObject.getString(Constantes.ATTR_TITLE), jsonObject.getString(Constantes.ATTR_DESCRIPTION), jsonObject.getString(Constantes.ATTR_DATE), jsonObject.getInt(Constantes.ATTR_PRIORITY));
                    tarefaList.add(tarefa);
                }
            }catch (JSONException e){
                Log.e("TarefaDAOJson", e.getMessage());
            }
        }else{
            Log.v("TarefaDAOJson", "Sem dados");
        }
    }
}
