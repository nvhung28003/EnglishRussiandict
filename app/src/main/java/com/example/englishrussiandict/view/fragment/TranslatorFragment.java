package com.example.englishrussiandict.view.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishrussiandict.R;
import com.example.englishrussiandict.jsoup.Datacrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TranslatorFragment extends Fragment implements View.OnClickListener {
    private View rootview;
    private ImageView mImageViewCountryleft;
    private ImageView mImageViewCountryright;
    private ImageView mImageViewSwapcountry;
    private TextView mTextViewcountryleft;
    private TextView mTextViewcountryright;
    private TextView mTextViewTranslate;
    private EditText mEditTextKeywordTranslate;
    private ImageView mImageViewDeleteTranslate;
    private ImageView mImageViewSpeechTranslate;
    private boolean checkcountry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_translator, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageViewCountryleft = rootview.findViewById(R.id.imv_countryleft);
        mTextViewcountryleft = rootview.findViewById(R.id.txt_countryleft);
        mImageViewSwapcountry = rootview.findViewById(R.id.imv_swapcountry);
        mImageViewCountryright = rootview.findViewById(R.id.imv_countryright);
        mTextViewcountryright = rootview.findViewById(R.id.txt_countryright);
        mEditTextKeywordTranslate = rootview.findViewById(R.id.edt_keywordtranslate);
        mImageViewDeleteTranslate = rootview.findViewById(R.id.imv_deletetranslate);
        mTextViewTranslate = rootview.findViewById(R.id.txt_translate);
        mImageViewSpeechTranslate = rootview.findViewById(R.id.imv_speechtranslate);
        mImageViewSwapcountry.setOnClickListener(this);
        mImageViewDeleteTranslate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_swapcountry:

                if (checkcountry == true) {
                    mImageViewCountryleft.setImageResource(R.drawable.icon_russian);
                    mTextViewcountryleft.setText("Russian");
                    mImageViewCountryright.setImageResource(R.drawable.ic_england);
                    mTextViewcountryright.setText("English");
                    checkcountry = false;
                } else {
                    mImageViewCountryleft.setImageResource(R.drawable.ic_england);
                    mTextViewcountryleft.setText("English");
                    mImageViewCountryright.setImageResource(R.drawable.icon_russian);
                    mTextViewcountryright.setText("Russian");
                    checkcountry = true;

                }
                break;
            case R.id.edt_keywordtranslate:
                mEditTextKeywordTranslate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        return false;
                    }
                });


            case R.id.imv_deletetranslate:
                mEditTextKeywordTranslate.setText("");
                break;
            default:
                break;
        }
    }
}
