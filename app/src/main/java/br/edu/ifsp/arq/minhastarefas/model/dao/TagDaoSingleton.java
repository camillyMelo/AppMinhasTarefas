package br.edu.ifsp.arq.minhastarefas.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.minhastarefas.model.entities.Tag;


public class TagDaoSingleton implements ITagDao{
    private static TagDaoSingleton instance;
    private List<Tag> dataset;

    private TagDaoSingleton(){
        dataset = new ArrayList<>();
    }

    public static TagDaoSingleton getInstance(){
        if(instance == null)
            instance = new TagDaoSingleton();
        return instance;
    }

    @Override
    public void create(Tag tag) {
        if(tag != null){
            if(find(tag.getTagName()) == null){
                dataset.add(tag);
            }
        }
    }

    @Override
    public boolean delete(Tag tag) {
        return dataset.remove(tag);
    }

    @Override
    public Tag find(String tagName) {
        return dataset.stream()
                .filter(tag -> tag.getTagName().equals(tagName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Tag> findAll() {
        return dataset;
    }
}
