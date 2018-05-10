package com.epereyra.didipay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.epereyra.didipay.model.ItemCategory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_ITEM_NAME = "NEW_ITEM_NAME";
    public static final String EXTRA_NEW_ITEM_DETAIL = "NEW_ITEM_DETAIL";
    public static final String EXTRA_NEW_ITEM_LAST_MONTH_PAID = "NEW_ITEM_LAST_MONTH_PAID";
    public static final String EXTRA_NEW_ITEM_TYPE = "NEW_ITEM_TYPE";

    private TextInputLayout mEditItemNameLayout;
    private TextInputLayout mEditItemDetailLayout;
    private EditText mEditItemName;
    private EditText mEditItemDetail;
    private Spinner mSpinnerLastMonth;
    private Spinner mSpinnerType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSpinnerLastMonth = findViewById(R.id.new_item_spinner_months);
        mSpinnerLastMonth.setSelection(Calendar.getInstance().get(Calendar.MONTH), false);
        mEditItemName = findViewById(R.id.new_item_name);
        mEditItemDetail = findViewById(R.id.new_item_detail);
        mSpinnerType = findViewById(R.id.new_item_spinner_types);
        List<ItemCategory> categoriesList = getCategories();
        ArrayAdapter<ItemCategory> categoriesAdapter = new ArrayAdapter<ItemCategory>(this, R.layout.support_simple_spinner_dropdown_item, categoriesList);
        mSpinnerType.setAdapter(categoriesAdapter);
        mEditItemNameLayout = findViewById(R.id.input_layout_new_item_name);
        mEditItemDetailLayout = findViewById(R.id.input_layout_new_item_detail);
    }

    public void saveItem(View view){
        Intent replyIntent = new Intent();

        String name = mEditItemName.getText().toString();
        String details = mEditItemDetail.getText().toString();
        int lastMonthPaid = mSpinnerLastMonth.getSelectedItemPosition();
        int type = ((ItemCategory) mSpinnerType.getSelectedItem()).getCode();

        replyIntent.putExtra(EXTRA_NEW_ITEM_NAME, name);
        replyIntent.putExtra(EXTRA_NEW_ITEM_DETAIL, details);
        replyIntent.putExtra(EXTRA_NEW_ITEM_LAST_MONTH_PAID, lastMonthPaid);
        replyIntent.putExtra(EXTRA_NEW_ITEM_TYPE, type);

        if(name.trim().length() == 0) { mEditItemNameLayout.setError(getResources().getString(R.string.input_required_text));};
        if(details.trim().length() == 0) { mEditItemDetailLayout.setError(getResources().getString(R.string.input_required_text));};

        if(name.trim().length() == 0 || details.trim().length() == 0){
            setResult(RESULT_CANCELED, replyIntent);
        }else{
            setResult(RESULT_OK, replyIntent);
            finish();
        }

    }

    public void cancelSave(View view){
        Intent replyIntent = new Intent();

        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    public List<ItemCategory> getCategories(){
        List<ItemCategory> categoriesList = new ArrayList<>();
        categoriesList.add(new ItemCategory(0, getResources().getString(R.string.category_item_credit_card)));
        categoriesList.add(new ItemCategory(2, getResources().getString(R.string.category_item_club)));
        categoriesList.add(new ItemCategory(5, getResources().getString(R.string.category_item_electric)));
        categoriesList.add(new ItemCategory(4, getResources().getString(R.string.category_item_gym)));
        categoriesList.add(new ItemCategory(6, getResources().getString(R.string.category_item_loan)));
        categoriesList.add(new ItemCategory(1, getResources().getString(R.string.category_item_phone)));
        categoriesList.add(new ItemCategory(3, getResources().getString(R.string.category_item_other)));
        return categoriesList;
    }
}
