package com.yt98.manager.android_builder.local;

import org.intellij.lang.annotations.Flow;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import io.reactivex.Flowable;

/**
 * Base Data Access Object for Room
 * @param <E> Entity
 */
public interface BaseDao<E> {

    @RawQuery
    Flowable<List<E>> getAllContents(String query);

    @RawQuery
    Flowable<E> getContentById(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Flowable<Void> insertContent(E content);

    @Delete
    Flowable<Void> deleteContent(E content);
}
