package com.example.englishrussiandict.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.englishrussiandict.R;

public class WebMenuActivity extends AppCompatActivity {
    private WebView mwebView;
    private TextView mTextViewwebmenu;
    private ImageView mImageViewBackwebmenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mwebView = findViewById(R.id.webview);
        mTextViewwebmenu = findViewById(R.id.txt_webmenu);
        mImageViewBackwebmenu = findViewById(R.id.imv_backwebmenu);
        mImageViewBackwebmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        String filename = intent.getStringExtra("NAMEFILE");
        String titlefilename = intent.getStringExtra("TITLENAMEFILE");
        mTextViewwebmenu.setText(titlefilename);
        String url = "file:///android_asset/" + titlefilename + "/" + filename + ".html";
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.setWebViewClient(new WebViewClient());
        mwebView.loadUrl(url);
    }
}
