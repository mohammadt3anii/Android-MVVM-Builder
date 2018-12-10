package com.yt98.manager.android_builder.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class DaoManager<E> {

    private BaseDao<E> dao;

    @Inject
    public DaoManager(BaseDao<E> dao) {
        this.dao = dao;
    }

    public Flowable<List<E>> getAllContents(String query){
        return dao.getAllContents(query);
    }

    public Flowable<E> getContentById(String query) {
        return dao.getContentById(query);
    }

    public void insertContent(E content){
        if(dao != null){
            dao.insertContent(content);
        }
    }

    public void deleteContent(E content){
        if(dao != null){
            dao.deleteContent(content);
        }
    }
}
