package com.github.kiolk.cowsandbulls.ui.screens.result.tabs;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.PeriodType;
import com.github.kiolk.cowsandbulls.data.models.result.Result;
import com.github.kiolk.cowsandbulls.domain.ResultListener;
import com.github.kiolk.cowsandbulls.domain.use_cases.GetBestResultUseCase;
import com.github.kiolk.cowsandbulls.ui.adapters.CategoryResultAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public abstract class BaseCategoryResultFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String BUNDLE_DATA = "BUNDLE_DATA";
    private RecyclerView rvResult;
    private CategoryResultAdapter adapter;
    private SwipeRefreshLayout mRefreshLayout;
    private ProgressBar mProgress;

    abstract PeriodType getType();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.layout_category_result, container, false);
       rvResult = view.findViewById(R.id.rw_category_result);
       if(getContext().getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE){
           rvResult.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
       }else{
           rvResult.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
       }
       adapter = new CategoryResultAdapter();
       rvResult.setAdapter(adapter);
       mRefreshLayout = view.findViewById(R.id.sw_result_refresh);
       mRefreshLayout.setOnRefreshListener(this);
       mProgress = view.findViewById(R.id.pb_category_result);
       if(savedInstanceState == null){
           rvResult.setVisibility(View.INVISIBLE);
           mProgress.setVisibility(View.VISIBLE);
       }
       onLoadData();
       return view;
    }

    @Override
    public void onRefresh(){
        onLoadData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_DATA, (ArrayList<? extends Parcelable>) adapter.getData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            adapter.setData(savedInstanceState.getParcelableArrayList(BUNDLE_DATA));
        }
    }

    private void onLoadData(){
        GetBestResultUseCase useCase = new GetBestResultUseCase(new GetBestResultUseCase.Params(getType()), new ResultListener<List<Result>>() {
            @Override
            public void onResult(List<Result> result) {
                rvResult.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mRefreshLayout.setRefreshing(false);
                adapter.setData(result);
            }

            @Override
            public void onFailer(Exception ex) {
                rvResult.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        useCase.start();
    }
}
