package com.github.kiolk.cowsandbulls.ui.adapters;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.Move;
import com.github.kiolk.cowsandbulls.ui.views.StartSpannable;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends Adapter<GameAdapter.ViewHolder> {

    private List<Move> moves;
    private final int VIEW_TYPE_EMPTY = 0;
    private final int VIEW_TYPE_NORMAL = 1;
    private boolean isStartPressed = false;
    private StartSpannable.StartSpannableOnclickListener onclickListener;

    public GameAdapter(StartSpannable.StartSpannableOnclickListener onclickListener) {
        this.onclickListener = onclickListener;
        moves = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (moves.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tempView;
        if (viewType == VIEW_TYPE_EMPTY && !isStartPressed) {
            tempView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_rules_item, parent, false);
            return new ViewHolder(tempView);

        } else {
            tempView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
            return new ViewHolder(tempView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (moves.size() == 0) {
            holder.onBindRules(onclickListener);
        } else {
            holder.onBindView(moves.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (moves.size() == 0 && !isStartPressed) {
            return 1;
        }
        return moves.size();
    }

    public void addNextMove(Move next) {
        moves.add(next);
        notifyDataSetChanged();
    }

    public void setStartPressed() {
        isStartPressed = true;
    }

    public void onClear() {
        moves.clear();
        notifyDataSetChanged();
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mMoveNumber;
        private TextView mCombination;
        private TextView mCowsCount;
        private TextView mBullsCount;
        private TextView mRules;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoveNumber = itemView.findViewById(R.id.tv_move_number);
            mCombination = itemView.findViewById(R.id.tv_moveCombination);
            mCowsCount = itemView.findViewById(R.id.tv_cows_count);
            mBullsCount = itemView.findViewById(R.id.tv_bulls_count);
            mRules = itemView.findViewById(R.id.rv_rules_item);
        }

        private void onBindView(Move move, int position) {
            mMoveNumber.setText(String.valueOf(position + 1));
            mCombination.setText(move.getValue());
            mCowsCount.setText(String.valueOf(move.getAmountOfCows()));
            mBullsCount.setText(String.valueOf(move.getAmountOfBulls()));
        }

        private void onBindRules(StartSpannable.StartSpannableOnclickListener onclickListener) {
            Resources resources = itemView.getContext().getResources();
            String text = resources.getString(R.string.rules);
            Spannable span;
            String howToPlayText = resources.getString(R.string.how_to_play_part);
            if (!onclickListener.gameIsStarted()) {

                String bottomText = resources.getString(R.string.bottom_rules_start);
                String coloredStartText = resources.getString(R.string.start_part);
                text = text + bottomText;

                span = new SpannableString(text);
                int length = text.length();

                StartSpannable clickableSpan = new StartSpannable(onclickListener,
                        itemView.getContext().getResources().getColor(R.color.middleBlue));

                span.setSpan(clickableSpan, length - coloredStartText.length() - 2, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                span = new SpannableString(text);
            }
            
            final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
            span.setSpan(bss, 0, howToPlayText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mRules.setText(span);
            mRules.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}