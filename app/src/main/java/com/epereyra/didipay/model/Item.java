package com.epereyra.didipay.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "detail")
    private String detail;

    @ColumnInfo(name = "is_paid")
    private boolean isPaid;

    @ColumnInfo(name = "last_month_paid")
    private int lastMonthPaid;

    @ColumnInfo(name = "type")
    private int category;

    public Item(String name, String detail, boolean isPaid, int lastMonthPaid, int category) {
        this.name = name;
        this.detail = detail;
        this.isPaid = isPaid;
        this.lastMonthPaid = lastMonthPaid;
        this.setCategory(category);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getLastMonthPaid() {
        return lastMonthPaid;
    }

    public void setLastMonthPaid(int lastMonthPaid) {
        this.lastMonthPaid = lastMonthPaid;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCurrentMonthPaid() {
        return (lastMonthPaid == Calendar.getInstance().get(Calendar.MONTH) && isPaid);
    }

    public void setLastPaidPreviousMonth() {
        if (lastMonthPaid == 1) {
            lastMonthPaid = 12;
        } else {
            lastMonthPaid = lastMonthPaid - 1;
        }
    }
}
