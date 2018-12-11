package com.yt98.manager.android_builder.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DaoManager<E> {

    private BaseDao<E> dao;

    @Inject
    public DaoManager(BaseDao<E> dao) {
        this.dao = dao;
    }

    public Flowable<List<E>> getAllContents(String query) {
        return dao.getAllContents(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<E> getContentById(String query) {
        return dao.getContentById(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Void> insertContent(E content) {
            return dao.insertContent(content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Void> deleteContent(E content) {
            return dao.deleteContent(content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }
}
