package br.edu.ifsp.arq.minhastarefas.model.dao;

import java.util.List;

import br.edu.ifsp.arq.minhastarefas.model.entities.Tag;


public interface ITagDao {

    void create(Tag tag);

    boolean delete(Tag tag);

    Tag find(String tagName);

    List<Tag> findAll();
}
