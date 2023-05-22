package br.edu.ifsp.arq.minhastarefas.model.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifsp.arq.minhastarefas.model.entities.Tag;
import br.edu.ifsp.arq.minhastarefas.model.entities.Tarefa;

public class TarefaDaoSingleton implements ITarefaDao {
    private static TarefaDaoSingleton instance = null;
    private List<Tarefa> tarefaList;

    private TarefaDaoSingleton() {
        tarefaList = new ArrayList<Tarefa>();
        tarefaList.add(new Tarefa("Prova Redes","Materia: Camada de redes", "23/5/2023", 2));
        tarefaList.add(new Tarefa("Projeto DMO","Primeira fase feita","22/5/2023", 1));
        tarefaList.add(new Tarefa("Stop Motion IHC2", "Adicionar efeitos sonoros","24/5/2023", 3));
        tarefaList.add(new Tarefa("Site Web","Alterar css", "26/5/2023", 1));

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
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Tarefa tarefa) {
        return tarefaList.remove(tarefa);
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


    @Override
    public List<Tarefa> findAll() {
        return tarefaList;
    }
}
