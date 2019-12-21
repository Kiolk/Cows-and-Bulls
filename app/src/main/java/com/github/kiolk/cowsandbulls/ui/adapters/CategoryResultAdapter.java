package com.github.kiolk.cowsandbulls.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.result.Result;
import com.github.kiolk.cowsandbulls.utils.StringUtils;

public class CategoryResultAdapter extends BaseEmptyPlaceHolderAdapter<Result>{

    @NonNull
    @Override
    public BaseViewHolder<Result> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_result, parent, false);
            return new EmptyCategoryViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new CategoryResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<Result> holder, int position) {
        if(getItemViewType(position) == EMPTY_TYPE){
            holder.onViewBound(null);
        }else{
            holder.onViewBound(data.get(position));
        }
    }

    public class CategoryResultViewHolder extends BaseViewHolder<Result>{

        private TextView mPosition;
        private TextView mMoves;
        private TextView mTime;
        private TextView mUserName;
        private ImageView mShare;
        private LinearLayout mLayout;

        public CategoryResultViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosition = itemView.findViewById(R.id.tv_result_position);
            mMoves = itemView.findViewById(R.id.tv_result_moves);
            mTime = itemView.findViewById(R.id.tv_result_time);
            mUserName = itemView.findViewById(R.id.tv_result_user_name);
            mShare = itemView.findViewById(R.id.iv_result_share_icon);
            mLayout = itemView.findViewById(R.id.ll_result_info);
        }

        @Override
        public void onViewBound(Result item) {
            if(item == null){
                return;
            }

            if(item.isOwn()){
                mShare.setVisibility(View.VISIBLE);
            }else{
                mShare.setVisibility(View.INVISIBLE);
            }

            if(getAdapterPosition() < 3){
                mLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.bg_blue_round));
            }else{
                mLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.bg_round));
            }

            mPosition.setText(String.valueOf(getAdapterPosition() + 1));
            mMoves.setText(String.valueOf(item.getMoves()));
            mTime.setText(StringUtils.getTime(item.getTime()));
            mUserName.setText(item.getUserName());
        }
    }

    public class EmptyCategoryViewHolder extends BaseViewHolder<Result>{

        public EmptyCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onViewBound(Result item) { }
    }
}
