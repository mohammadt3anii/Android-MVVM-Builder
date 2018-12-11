package com.yt98.manager.apps.localActivity;

import com.yt98.manager.android_builder.local.BaseDao;
import com.yt98.manager.android_builder.local.BaseDaoRepository;
import com.yt98.manager.android_builder.local.DaoManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule<E> {

    private BaseDao<E> dao;

    public DaoModule(BaseDao<E> dao){
        this.dao = dao;
    }

    @Provides
    DaoManager<E> manager(){
        return new DaoManager<>(dao);
    }

    @Provides
    BaseDaoRepository<E> provideDao(DaoManager<E> manager){
        return new BaseDaoRepository<>(manager);
    }

}
