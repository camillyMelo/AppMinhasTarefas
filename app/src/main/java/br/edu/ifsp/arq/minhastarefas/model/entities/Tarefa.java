package br.edu.ifsp.arq.minhastarefas.model.entities;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tarefa implements Comparable<Tarefa>{
        private String nomeTarefa;
        private int prioridade;
        private String descricao;
        private String data;
        private List<Tag> tags;

        private void init(){
                tags = new ArrayList<>();
        }

        public Tarefa(String nomeTarefa, String descricao,String data, int prioridade){
                this.nomeTarefa = nomeTarefa;
                this.data = data;
                this.setDescricao(descricao);
                this.prioridade = prioridade;
                init();
        }

        public Tarefa(String nomeTarefa, String description, String data, int prioridade, boolean important){
                this.nomeTarefa = nomeTarefa;
                this.data = data;
                this.setDescricao(getDescricao());
                this.prioridade = prioridade;
                init();
        }

        public String getNomeTarefa() {
                return nomeTarefa;
        }

        public void setNomeTarefa(String nomeTarefa) {
                this.nomeTarefa = nomeTarefa;
        }

        public int getPrioridade() {
                return prioridade;
        }

        public void setPrioridade(int prioridade) {
                this.prioridade = prioridade;
        }

        public String getData() {
                return data;
        }

        public void setData(String data) {
                this.data = data;
        }

        public String getDescricao() {
                return descricao;
        }

        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }
        public void addTag(Tag tag){
                this.tags.add(tag);
        }

        public boolean removeTag(Tag tag){
                return this.tags.remove(tag);
        }

        public List<Tag> getTags() {
                return tags;
        }


        @NonNull
        @Override
        public String toString() {
                return "Nome tarefa: " + nomeTarefa;
        }

        @Override
        public int compareTo(Tarefa t) {
                return Comparator.comparing(Tarefa::getPrioridade).reversed().thenComparing(Tarefa::getNomeTarefa).compare(this, t);

        }


}
