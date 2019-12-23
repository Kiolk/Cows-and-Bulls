package com.github.kiolk.cowsandbulls.ui.adapters;

import android.os.Parcelable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    protected List<T> data = new ArrayList<>();

    public void setData(List<T> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return data;
    }
}
