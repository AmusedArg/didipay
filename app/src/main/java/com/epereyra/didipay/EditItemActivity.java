package com.epereyra.didipay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.epereyra.didipay.model.ItemCategory;
import com.epereyra.didipay.util.Util;

import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    public static final String EXTRA_EDIT_ITEM_ID = "EXTRA_EDIT_ITEM_ID";
    public static final String EXTRA_EDIT_ITEM_NAME = "EXTRA_EDIT_ITEM_NAME";
    public static final String EXTRA_EDIT_ITEM_DETAIL = "EXTRA_EDIT_ITEM_DETAIL";
    public static final String EXTRA_EDIT_ITEM_LAST_MONTH_PAID = "EXTRA_EDIT_ITEM_LAST_MONTH_PAID";
    public static final String EXTRA_EDIT_ITEM_CATEGORY = "EXTRA_EDIT_ITEM_CATEGORY";
    public static final String EXTRA_EDIT_ITEM_IS_PAID = "EXTRA_EDIT_ITEM_IS_PAID";

    private TextInputLayout mEditItemNameLayout;
    private TextInputLayout mEditItemDetailLayout;
    private EditText mEditItemName;
    private EditText mEditItemDetail;
    private Spinner mSpinnerType;
    private boolean isPaid; // should be a checkbox
    private long itemID;
    private int lastMonthPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        Intent intent = getIntent();
        Spinner mSpinnerLastMonth = findViewById(R.id.new_item_spinner_months);
        mSpinnerLastMonth.setVisibility(View.GONE);
        findViewById(R.id.textViewUltMes).setVisibility(View.GONE);
        mEditItemName = findViewById(R.id.new_item_name);
        mEditItemDetail = findViewById(R.id.new_item_detail);
        mSpinnerType = findViewById(R.id.new_item_spinner_types);
        List<ItemCategory> categoriesList = Util.getCategories(getResources());
        ArrayAdapter<ItemCategory> categoriesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categoriesList);
        mSpinnerType.setAdapter(categoriesAdapter);
        mEditItemNameLayout = findViewById(R.id.input_layout_new_item_name);
        mEditItemDetailLayout = findViewById(R.id.input_layout_new_item_detail);

        // set values
        itemID = intent.getLongExtra(EXTRA_EDIT_ITEM_ID, -1);
        String name = intent.getStringExtra(EXTRA_EDIT_ITEM_NAME);
        String detail = intent.getStringExtra(EXTRA_EDIT_ITEM_DETAIL);
        lastMonthPaid = intent.getIntExtra(EXTRA_EDIT_ITEM_LAST_MONTH_PAID, -1);
        int category = intent.getIntExtra(EXTRA_EDIT_ITEM_CATEGORY, 0);
        isPaid = intent.getBooleanExtra(EXTRA_EDIT_ITEM_IS_PAID, false);

        mEditItemName.setText(name);
        mEditItemDetail.setText(detail);

        for(int i = 0; i < mSpinnerType.getAdapter().getCount(); i++){
            ItemCategory iCategory = (ItemCategory) mSpinnerType.getAdapter().getItem(i);
            if(iCategory.getCode() == category){
                mSpinnerType.setSelection(i);
                break;
            }
        }
    }

    public void saveItem(View view){
        Intent replyIntent = new Intent();

        String name = mEditItemName.getText().toString();
        String details = mEditItemDetail.getText().toString();
        int type = ((ItemCategory) mSpinnerType.getSelectedItem()).getCode();

        replyIntent.putExtra(EXTRA_EDIT_ITEM_ID, itemID);
        replyIntent.putExtra(EXTRA_EDIT_ITEM_NAME, name);
        replyIntent.putExtra(EXTRA_EDIT_ITEM_DETAIL, details);
        replyIntent.putExtra(EXTRA_EDIT_ITEM_LAST_MONTH_PAID, lastMonthPaid);
        replyIntent.putExtra(EXTRA_EDIT_ITEM_CATEGORY, type);
        replyIntent.putExtra(EXTRA_EDIT_ITEM_IS_PAID, isPaid);

        if(name.trim().length() == 0) { mEditItemNameLayout.setError(getResources().getString(R.string.input_required_text));}
        if(details.trim().length() == 0) { mEditItemDetailLayout.setError(getResources().getString(R.string.input_required_text));}

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
}
