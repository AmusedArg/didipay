package com.epereyra.didipay.util;

import android.content.res.Resources;

import com.epereyra.didipay.R;
import com.epereyra.didipay.model.ItemCategory;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<ItemCategory> getCategories(Resources resources){
        List<ItemCategory> categoriesList = new ArrayList<>();
        categoriesList.add(new ItemCategory(0, resources.getString(R.string.category_item_credit_card)));
        categoriesList.add(new ItemCategory(2, resources.getString(R.string.category_item_club)));
        categoriesList.add(new ItemCategory(5, resources.getString(R.string.category_item_electric)));
        categoriesList.add(new ItemCategory(4, resources.getString(R.string.category_item_gym)));
        categoriesList.add(new ItemCategory(6, resources.getString(R.string.category_item_loan)));
        categoriesList.add(new ItemCategory(1, resources.getString(R.string.category_item_phone)));
        categoriesList.add(new ItemCategory(3, resources.getString(R.string.category_item_other)));
        return categoriesList;
    }
}
