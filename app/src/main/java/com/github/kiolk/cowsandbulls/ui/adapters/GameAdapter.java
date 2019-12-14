package com.github.kiolk.cowsandbulls.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.Move;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends Adapter<GameAdapter.ViewHolder> {

    private List<Move> moves;

    public GameAdapter() {
        moves = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindView(moves.get(position), position);
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public void addNextMove(Move next){
        moves.add(next);
        notifyDataSetChanged();
    }

    public void onClear(){
        moves.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mMoveNumber;
        TextView mCombination;
        TextView mCowsCount;
        TextView mBullsCount;

//        Add views
//        ImageView mCowIcon;
//        ImageView mBullIcon;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMoveNumber = itemView.findViewById(R.id.tv_move_number);
            mCombination = itemView.findViewById(R.id.tv_moveCombination);
            mCowsCount = itemView.findViewById(R.id.tv_cows_count);
            mBullsCount = itemView.findViewById(R.id.tv_bulls_count);
        }

        private void onBindView(Move move, int position) {
            mMoveNumber.setText(String.valueOf(position + 1));
            mCombination.setText(move.getValue());
            mCowsCount.setText(String.valueOf(move.getAmountOfCows()));
            mBullsCount.setText(String.valueOf(move.getAmountOfBulls()));
        }

    }
}
