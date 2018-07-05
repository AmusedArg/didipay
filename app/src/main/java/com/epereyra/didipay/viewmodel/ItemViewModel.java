package com.epereyra.didipay.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.epereyra.didipay.model.Item;
import com.epereyra.didipay.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mItemRepository;
    private final MutableLiveData<String> mFilterNameDetail = new MutableLiveData<>();
    private final LiveData<List<Item>> mAllItems =
            Transformations.switchMap(mFilterNameDetail, (filter) -> {
                if(filter == null || filter.isEmpty()){
                    return mItemRepository.getAllItems();
                }else{
                    filter = "%"+filter+"%";
                    return mItemRepository.search(filter);
                }
            });

    public ItemViewModel (Application application) {
        super(application);
        mItemRepository = new ItemRepository(application);
//        mAllItems = mItemRepository.getAllItems();
    }

    public LiveData<List<Item>> getAllItems(){ return mAllItems; }

    public void insert(Item item) { mItemRepository.insert(item); }

    public void update(Item item) { mItemRepository.update(item); }

    public void delete(Item item) { mItemRepository.delete(item); }

    public void setFilterNameDetail(String search){
        mFilterNameDetail.setValue(search);
    }
}
