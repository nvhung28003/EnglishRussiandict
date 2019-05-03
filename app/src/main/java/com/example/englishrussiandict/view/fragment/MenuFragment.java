package com.example.englishrussiandict.view.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.englishrussiandict.R;
import com.example.englishrussiandict.adapter.MenuAdapter;
import com.example.englishrussiandict.view.activity.WebMenuActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuFragment extends Fragment implements MenuAdapter.Onitemclicklistener{
    private View rootview;
    private RecyclerView mRcvMenu;
    private List<String> listname = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private String mNameFile;
    private WebView mwebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_history, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        mNameFile = b.getString("FILENAME");
        Log.d("SSS", mNameFile);
        listname = getFilenameinAssets();
        mRcvMenu = rootview.findViewById(R.id.rcvhistory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MenuFragment.super.getContext());
        mRcvMenu.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(MenuFragment.super.getContext(), getFilenameinAssets());
        mRcvMenu.setAdapter(menuAdapter);
        menuAdapter.setOnitemclicklistener(this);

    }

    public List<String> getFilenameinAssets() {
        final AssetManager assetManager = getActivity().getAssets();
        List<String> fileList = new ArrayList<>();
        try {
            // for assets folder add empty string
            String[] filelist = assetManager.list(mNameFile);
            for (int i = 0; i < filelist.length; i++) {
                fileList.add(filelist[i].substring(0, filelist[i].length() - 5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    @Override
    public void OnitemClicked(String namefile) {
        Intent intent = new Intent(MenuFragment.super.getContext(), WebMenuActivity.class);
        intent.putExtra("NAMEFILE",namefile);
        intent.putExtra("TITLENAMEFILE",mNameFile);
        startActivity(intent);
    }
}
