package com.epereyra.didipay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_ITEM_NAME = "NEW_ITEM_NAME";
    public static final String EXTRA_NEW_ITEM_DETAIL = "NEW_ITEM_DETAIL";
    public static final String EXTRA_NEW_ITEM_LAST_MONTH_PAID = "NEW_ITEM_LAST_MONTH_PAID";
    public static final String EXTRA_NEW_ITEM_TYPE = "NEW_ITEM_TYPE";

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
    }

    public void saveItem(View view){
        Intent replyIntent = new Intent();

        String name = mEditItemName.getText().toString();
        String details = mEditItemDetail.getText().toString();
        int lastMonthPaid = mSpinnerLastMonth.getSelectedItemPosition();
        int type = mSpinnerType.getSelectedItemPosition();

        replyIntent.putExtra(EXTRA_NEW_ITEM_NAME, name);
        replyIntent.putExtra(EXTRA_NEW_ITEM_DETAIL, details);
        replyIntent.putExtra(EXTRA_NEW_ITEM_LAST_MONTH_PAID, lastMonthPaid);
        replyIntent.putExtra(EXTRA_NEW_ITEM_TYPE, type);

        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void cancelSave(View view){
        Intent replyIntent = new Intent();

        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}
