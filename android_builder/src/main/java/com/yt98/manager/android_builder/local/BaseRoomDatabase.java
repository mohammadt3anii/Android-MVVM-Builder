package com.yt98.manager.android_builder.local;

import com.yt98.manager.android_builder.base.ClassInfo;

import androidx.room.RoomDatabase;

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseRoomDatabase<E, Dao extends BaseDao<E>> extends RoomDatabase {

    protected abstract Dao getDao();

}
