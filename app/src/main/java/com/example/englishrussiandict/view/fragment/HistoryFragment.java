package com.example.englishrussiandict.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishrussiandict.MyConstants;
import com.example.englishrussiandict.R;
import com.example.englishrussiandict.adapter.HistoryAdapter;
import com.example.englishrussiandict.entity.MyObjDictionary;
import com.example.englishrussiandict.view.activity.DetailActivity;
import com.example.englishrussiandict.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends MyBaseFragment implements HistoryAdapter.OnitemClickListener {
    private View rootview;
    private RecyclerView mrcvhistory;
    private List<MyObjDictionary> dictionaryArrayList = new ArrayList<>();
    private HistoryAdapter historyAdapter;

    @Nullable
    @Override//
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_history, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //dbBase.copyDatabase();

        dictionaryArrayList = MainActivity.database_history.getalldictionaryList();
        mrcvhistory = rootview.findViewById(R.id.rcvhistory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HistoryFragment.super.getContext());
        mrcvhistory.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(HistoryFragment.super.getContext(), dictionaryArrayList);
        mrcvhistory.setAdapter(historyAdapter);
        historyAdapter.setOnitemClickListener(HistoryFragment.this);
    }

    @Override
    public void OnitemClicked(MyObjDictionary dictionary) {
        Intent intent = new Intent(HistoryFragment.super.getContext(), DetailActivity.class);
        intent.putExtra(MyConstants.KEY_WORD_CURRENT_SEARCH, dictionary.getWord());
        intent.putExtra("DEFINITION", dictionary.getDefinition());
        startActivity(intent);
    }

    @Override
    public void OnitemDeleteClick(MyObjDictionary dictionary) {
        MainActivity.database_history.deletedata(dictionary.get_id());
        dictionaryArrayList.remove(dictionary);
        historyAdapter.notifyDataSetChanged();
    }
}
