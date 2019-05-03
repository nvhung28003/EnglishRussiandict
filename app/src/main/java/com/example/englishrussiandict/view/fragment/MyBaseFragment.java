package com.example.englishrussiandict.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.englishrussiandict.sqlitedb.MyDatabaseHelper;

public class MyBaseFragment extends Fragment {

    MyDatabaseHelper dbBase;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbBase = new MyDatabaseHelper(getContext());
    }
}
