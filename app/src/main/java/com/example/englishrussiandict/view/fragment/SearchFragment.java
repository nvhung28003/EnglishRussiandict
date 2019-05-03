package com.example.englishrussiandict.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.englishrussiandict.MyConstants;
import com.example.englishrussiandict.R;
import com.example.englishrussiandict.adapter.SearchAdapter;
import com.example.englishrussiandict.entity.MyObjDictionary;
import com.example.englishrussiandict.view.activity.DetailActivity;
import com.example.englishrussiandict.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment implements SearchAdapter.OnitemClickListener {
    private View rootview;
    private EditText mEditText_keyword;
    private ImageView mImageView_iconrecord;

    private SearchAdapter searchAdapter;
    private RecyclerView rcvSearch;
    private List<MyObjDictionary> dictionaryList = new ArrayList<>();
    private List<MyObjDictionary> dictionaries = new ArrayList<>();
    public static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_search, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initlizecomponet();
        if (dictionaryList.isEmpty()) {
            dictionaryList = MainActivity.databasehelper.getalldictionary();
        }
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(SearchFragment.super.getContext());
        rcvSearch.setLayoutManager(layoutManager);
        mEditText_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mEditText_keyword.getText().toString().isEmpty()) {
                    dictionaries.clear();
                    for (int i = 0; i < dictionaryList.size(); i++) {
                        if (dictionaryList.get(i).getWord().toLowerCase().startsWith
                                (mEditText_keyword.getText().toString().toLowerCase()) == true) {
                            dictionaries.add(dictionaryList.get(i));
                        }
                    }
                    searchAdapter = new SearchAdapter(SearchFragment.super.getContext(), dictionaries);
                    rcvSearch.setAdapter(searchAdapter);
                    searchAdapter.setOnitemClickListener(SearchFragment.this);
                } else {
                    dictionaries.clear();
                    // searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mImageView_iconrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getspeechInput();
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        mEditText_keyword.setText("");
    }

    private void initlizecomponet() {
        mEditText_keyword = rootview.findViewById(R.id.edt_keyword);
        mImageView_iconrecord = rootview.findViewById(R.id.imv_iconrecord);
        rcvSearch = rootview.findViewById(R.id.rcv_Search);
    }

    private void getspeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak something");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == -1 && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEditText_keyword.setText(result.get(0));
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void OnitemClicked(MyObjDictionary dictionary) {
        Intent intent = new Intent(SearchFragment.super.getContext(), DetailActivity.class);
        intent.putExtra(MyConstants.KEY_WORD_CURRENT_SEARCH, dictionary.getWord());
        intent.putExtra("DEFINITION", dictionary.getDefinition());
        MainActivity.database_history.addhistory(dictionary);
        startActivity(intent);

    }

    @Override
    public void Onitemchoosefavorites(MyObjDictionary dictionary) {
        MainActivity.database_favorites.addfavorite(dictionary);
    }
}
