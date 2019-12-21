package com.github.kiolk.cowsandbulls.ui.screens.result.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.PeriodType;
import com.github.kiolk.cowsandbulls.data.models.result.Result;
import com.github.kiolk.cowsandbulls.domain.ResultListener;
import com.github.kiolk.cowsandbulls.domain.use_cases.GetBestResultUseCase;
import com.github.kiolk.cowsandbulls.ui.adapters.CategoryResultAdapter;

import java.util.List;

public abstract class BaseCategoryResultFragment extends Fragment {

    private RecyclerView rvResult;
    private CategoryResultAdapter adapter;


    abstract PeriodType getType();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.layout_category_result, container, false);
       rvResult = view.findViewById(R.id.rw_category_result);
       rvResult.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
       adapter = new CategoryResultAdapter();
       rvResult.setAdapter(adapter);
       onLoadData();
       return view;
    }

    public void onRefresh(){
        onLoadData();
    }

    private void onLoadData(){
        GetBestResultUseCase useCase = new GetBestResultUseCase(new GetBestResultUseCase.Params(getType()), new ResultListener<List<Result>>() {
            @Override
            public void onResult(List<Result> result) {

                adapter.setData(result);
            }

            @Override
            public void onFailer(Exception ex) {
                Toast.makeText(getContext(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        useCase.start();
    }
}
