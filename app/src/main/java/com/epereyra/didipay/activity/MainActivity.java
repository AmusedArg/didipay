package com.epereyra.didipay.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.epereyra.didipay.R;
import com.epereyra.didipay.adapter.ItemListAdapter;
import com.epereyra.didipay.model.Item;
import com.epereyra.didipay.viewmodel.ItemViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ItemViewModel mItemViewModel;

    private static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Calendar c = Calendar.getInstance();
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = c.get(Calendar.DAY_OF_MONTH);

        toolbar.setTitle(day + " " + month.substring(0,1).toUpperCase() + month.substring(1) + " " + c.get(Calendar.YEAR));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerview_items);
        final ItemListAdapter adapter = new ItemListAdapter(this, new ItemListAdapter.OnClickListener() {

            @Override
            public void onIsPaidClick(Item item) {
                item.updateLastMonthPaid();
                mItemViewModel.update(item);
            }

            @Override
            public void onLongItemClick(Item item) {
                showDeleteItemDialog(item);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable final List<Item> items) {
                // Update the cached copy of the words in the adapter.
                adapter.setItems(items);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String itemName = data.getStringExtra(NewItemActivity.EXTRA_NEW_ITEM_NAME);
            String detail = data.getStringExtra(NewItemActivity.EXTRA_NEW_ITEM_DETAIL);
            int lastMonthPaid = data.getIntExtra(NewItemActivity.EXTRA_NEW_ITEM_LAST_MONTH_PAID, 0);
            int type = data.getIntExtra(NewItemActivity.EXTRA_NEW_ITEM_TYPE, 0);
            boolean isPaid = lastMonthPaid == Calendar.getInstance().get(Calendar.MONTH);

            Item i = new Item(itemName, detail, isPaid, lastMonthPaid, type);

            mItemViewModel.insert(i);
        }
    }

    private void showDeleteItemDialog(final Item item) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(getLocalizedString(R.string.delete_item_dialog_description))
                .setTitle(getLocalizedString(R.string.delete_item_dialog_title));

        builder.setPositiveButton(getLocalizedString(R.string.delete_item_dialog_btn_delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mItemViewModel.delete(item);
            }
        });
        builder.setNegativeButton(getLocalizedString(R.string.delete_item_dialog_btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getLocalizedString(int resId){
        return getResources().getString(resId);
    }
}
