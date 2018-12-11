package com.yt98.manager.android_builder.local;

import androidx.room.RoomDatabase;


public abstract class BaseRoomDatabase<E, Dao extends BaseDao<E>> extends RoomDatabase {

    protected abstract Dao getDao();

}
