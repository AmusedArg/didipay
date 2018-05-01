package com.epereyra.didipay.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.epereyra.didipay.dao.ItemDao;
import com.epereyra.didipay.model.Item;

@Database(entities = {Item.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();

    private static AppDatabase sInstance;

    /**
     * Gets the singleton instance of AppDatabase.
     *
     * @param context The context.
     * @return The singleton instance of AppDatabase.
     */
    public static AppDatabase getDatabase(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Item")
                            .build();
                }
            }
        }
        return sInstance;
    }
}
