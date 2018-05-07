package com.epereyra.didipay.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.epereyra.didipay.dao.ItemDao;
import com.epereyra.didipay.database.AppDatabase;
import com.epereyra.didipay.model.Item;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAll();
    }

    public LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public void insert (Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }
    public void update (Item item) {
        new updateAsyncTask(mItemDao).execute(item);
    }
    public void delete (Item item) { new deleteAsyncTask(mItemDao).execute(item); }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;

        updateAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Item, Void, Void>{
        private ItemDao mAsyncTaskDao;

        deleteAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
