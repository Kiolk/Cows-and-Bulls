package com.github.kiolk.cowsandbulls.ui.adapters;

public abstract class BaseEmptyPlaceHolderAdapter<T> extends BaseAdapter<T> {

    protected static final int NORMAL_TYPE = 1;
    protected static final int EMPTY_TYPE = 0;

    @Override
    public int getItemCount() {
        if(data.size() == 0){
            return 1;
        }

        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(data.size() == 0){
            return EMPTY_TYPE;
        }

        return NORMAL_TYPE;
    }
}
