package com.epereyra.didipay.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.epereyra.didipay.R;
import com.epereyra.didipay.model.Item;

import java.text.DateFormatSymbols;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{

    public interface OnClickListener {
        void onIsPaidClick(Item item);
        void onLongItemClick(Item item);
        void onItemClick(Item item);
    }

    private final LayoutInflater mInflater;
    private List<Item> mItems; // Cached copy of Items
    private final OnClickListener listener;
    private String lastMonthPaidText;

    public ItemListAdapter(Context context, OnClickListener listener) {
        mInflater = LayoutInflater.from(context);
        lastMonthPaidText = context.getResources().getString(R.string.item_last_month_paid_text);
        this.listener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemNameTextView;
        private final TextView itemDetailsTextView;
        private final TextView itemLastMonthPaidTextView;
        private final CheckBox itemIsPaidCheckbox;
        private final ImageView itemIconImage;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.name);
            itemDetailsTextView = itemView.findViewById(R.id.details);
            itemLastMonthPaidTextView = itemView.findViewById(R.id.txt_paid_at_month);
            itemIsPaidCheckbox = itemView.findViewById(R.id.paid_chk);
            itemIconImage = itemView.findViewById(R.id.icon);
            itemView.setLongClickable(true);
        }

        private void bind(final Item item, final OnClickListener listener) {
            itemIsPaidCheckbox.setOnClickListener(v -> listener.onIsPaidClick(item));
            itemView.setOnLongClickListener(v -> {
                listener.onLongItemClick(item);
                return false;
            });
            itemView.setOnClickListener(v -> {
                listener.onItemClick(item);
            });
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            holder.itemNameTextView.setText(current.getName());
            holder.itemDetailsTextView.setText(current.getDetail());
            String month = new DateFormatSymbols().getMonths()[current.getLastMonthPaid()];
            holder.itemLastMonthPaidTextView.setText(lastMonthPaidText + " " + month.substring(0,1).toUpperCase() + month.substring(1));
            holder.itemIsPaidCheckbox.setChecked(current.isCurrentMonthPaid());
            holder.itemIconImage.setImageResource(getIcon(current.getCategory()));
        } else {
            // Covers the case of data not being ready yet.
            holder.itemNameTextView.setText(" ");
            holder.itemDetailsTextView.setText(" ");
            holder.itemIconImage.setImageResource(getIcon(-1));
        }
        holder.bind(mItems.get(position), listener);
    }

    public void setItems(List<Item> Items){
        mItems = Items;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    private int getIcon(int type){
        switch (type) {
            case 0: return R.drawable.ic_credit_card_black_24dp;
            case 1: return R.drawable.ic_call_black_24dp;
            case 2: return R.drawable.ic_golf_course_black_24dp;
            case 3: return R.drawable.ic_bookmark_black_24dp;
            case 4: return R.drawable.ic_fitness_center_black_24dp;
            case 5: return R.drawable.ic_lightbulb_outline_black_24dp;
            case 6: return R.drawable.ic_account_balance_black_24dp;
            default: return R.drawable.ic_bookmark_black_24dp;
        }
    }
}
