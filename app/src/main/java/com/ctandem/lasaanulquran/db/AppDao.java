package com.ctandem.lasaanulquran.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ctandem.lasaanulquran.models.ContentModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AppDao {

    @Query("select * from content")
    List<ContentModel> getAllContents();

    @Query("select * from content where contentId=:id")
    ContentModel getContent(long id);

    @Insert(onConflict = REPLACE)
    void insertContent(ContentModel model);

    @Delete
    void delete(ContentModel model);

    @Query("delete from content")
    void deleteAll();

}
