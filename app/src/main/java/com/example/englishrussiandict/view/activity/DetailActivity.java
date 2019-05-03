package com.example.englishrussiandict.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.englishrussiandict.R;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private TextView mTextView_top, mTextView_mid, mTextView_bottom;
    private ImageView mImageView_backdetail, mImageViewspeech;
    private TextToSpeech mTextToSpeech;
    private String mWord;
    private String mDefitinition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTextView_top = findViewById(R.id.txttop);
        mTextView_mid = findViewById(R.id.txtmid);
        mTextView_bottom = findViewById(R.id.txtbottom);
        mImageView_backdetail = findViewById(R.id.imv_backdetail);
        mImageViewspeech = findViewById(R.id.imv_speech);
        Intent intent = getIntent();
        mWord = intent.getStringExtra("WORD");
         mDefitinition = intent.getStringExtra("DEFINITION");

        mTextView_top.setText(mWord);
        mTextView_mid.setText(mWord);
        mTextView_bottom.setText(mDefitinition);
        mImageView_backdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int result = mTextToSpeech.setLanguage(Locale.ENGLISH);
                    mImageViewspeech.setEnabled(true);
                }
            }
        });

        mImageViewspeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }
    public void speak()
    {

        mTextToSpeech.speak(mWord,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        if(mTextToSpeech !=null)
        {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();

    }
}
