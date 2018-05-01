package com.epereyra.didipay.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.epereyra.didipay.model.Item;
import com.epereyra.didipay.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mItemRepository;
    private LiveData<List<Item>> mAllItems;

    public ItemViewModel (Application application) {
        super(application);
        mItemRepository = new ItemRepository(application);
        mAllItems = mItemRepository.getAllItems();
    }

    public LiveData<List<Item>> getAllItems(){ return mAllItems; }

    public void insert(Item item) { mItemRepository.insert(item); }

    public void update(Item item) { mItemRepository.update(item); }

    public void delete(Item item) { mItemRepository.delete(item); }
}
