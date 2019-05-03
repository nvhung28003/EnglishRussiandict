package com.example.englishrussiandict.view.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishrussiandict.R;
import com.example.englishrussiandict.entity.Dictionary;
import com.example.englishrussiandict.sqlitedb.Databasehelper;
import com.example.englishrussiandict.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FloatingWindow extends Service implements View.OnClickListener {
    private WindowManager mWindowManager;
    private View mFloatingView;
    private View collapsedView;
    private View expandedView;
    private ImageView mImageViewCountryleft;
    private ImageView mImageViewCountryright;
    private ImageView mImageViewSwapcountry;
    private TextView mTextViewcountryleft;
    private TextView mTextViewcountryright;
    private TextView mTextViewFastkey;
    private EditText mEditTextKeywordFastkey;
    private ImageView mImageViewSpeechFastKey;
    private ImageView mImageViewDelete;
    private boolean checkcountry;
    private Databasehelper databasehelper;
    private List<Dictionary> dictionaryList = new ArrayList<>();
    private TextToSpeech mTextToSpeech;

    public FloatingWindow() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget_layout, null);
        initlizacomponet();
        databasehelper = new Databasehelper(this);
        databasehelper.createDataBase();
        if (dictionaryList.isEmpty()) {
            dictionaryList = databasehelper.getalldictionary();
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        expandedView = mFloatingView.findViewById(R.id.expanded_container);

        ImageView closeButtonCollapsed = (ImageView) mFloatingView.findViewById(R.id.imv_closeFloatingSmall);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the service and remove the from from the window
                stopSelf();
            }
        });
        ImageView closeButton = (ImageView) mFloatingView.findViewById(R.id.imv_closeFloatingBig);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });

        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });

        mImageViewSwapcountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });

        mEditTextKeywordFastkey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                for (int i = 0; i < dictionaryList.size(); i++) {
                    if ((dictionaryList.get(i).getWord().toLowerCase()).startsWith(mEditTextKeywordFastkey.getText().toString().toLowerCase()) == true) {
                         mTextViewFastkey.setText(dictionaryList.get(i).getDefinition());
                        break;
                    } else {
                        mTextViewFastkey.setText("");
                    }
                }
                return false;
            }
        });
        mImageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextKeywordFastkey.setText("");
            }
        });
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int result = mTextToSpeech.setLanguage(Locale.ENGLISH);
                    mImageViewSpeechFastKey.setEnabled(true);
                }
            }
        });
        mImageViewSpeechFastKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageViewSpeechFastKey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTextToSpeech.speak(mEditTextKeywordFastkey.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                    }
                });
            }
        });
    }

    private void initlizacomponet() {
        mImageViewCountryleft = mFloatingView.findViewById(R.id.imv_countryleftFastkey);
        mTextViewcountryleft = mFloatingView.findViewById(R.id.txt_countryleftFastkey);
        mImageViewSwapcountry = mFloatingView.findViewById(R.id.imv_swapcountryFastkey);
        mImageViewCountryright = mFloatingView.findViewById(R.id.imv_countryrightFastkey);
        mTextViewcountryright = mFloatingView.findViewById(R.id.txt_countryrightFastkey);
        mEditTextKeywordFastkey = mFloatingView.findViewById(R.id.edt_keywordFastkey);
        mImageViewDelete = mFloatingView.findViewById(R.id.imv_deleteFastkey);
        mTextViewFastkey = mFloatingView.findViewById(R.id.txt_Fastkey);
        mImageViewSpeechFastKey = mFloatingView.findViewById(R.id.imv_speechFastkey);

    }

    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
