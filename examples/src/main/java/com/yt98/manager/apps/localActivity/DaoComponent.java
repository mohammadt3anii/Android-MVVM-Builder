package com.yt98.manager.apps.localActivity;

import com.yt98.manager.android_builder.local.BaseDaoRepository;
import com.yt98.manager.android_builder.local.DaoManager;

import dagger.Component;

@Component(modules = DaoModule.class)
public interface DaoComponent<E> {

    BaseDaoRepository<E> getBaseDaoRepository();

    DaoManager<E> getManager();

}
