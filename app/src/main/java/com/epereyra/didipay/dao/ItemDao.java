package com.epereyra.didipay.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.epereyra.didipay.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAll();

    @Insert
    void insertAll(Item... items);

    @Insert
    long insert(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item WHERE id = :id")
    void deleteById(long id);

    @Update
    void update(Item item);
}
