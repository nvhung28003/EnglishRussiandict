package com.example.englishrussiandict.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishrussiandict.MyConstants;
import com.example.englishrussiandict.R;
import com.example.englishrussiandict.sqlitedb.Database_favorites;
import com.example.englishrussiandict.sqlitedb.Database_history;
import com.example.englishrussiandict.sqlitedb.MyDatabaseHelper;
import com.example.englishrussiandict.view.fragment.FavoritesFragment;
import com.example.englishrussiandict.view.fragment.HistoryFragment;
import com.example.englishrussiandict.view.fragment.MenuFragment;
import com.example.englishrussiandict.view.fragment.SearchFragment;
import com.example.englishrussiandict.view.fragment.TranslatorFragment;
import com.example.englishrussiandict.view.fragment.WebFragment;
import com.example.englishrussiandict.view.service.FloatingWindow;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private LinearLayout mlinear_bottomSearch, mlinear_bottomhistory,
            mlinear_bottomFavorites, mlinear_bottomTranlator, mlinear_bottomWeb, mlinear_bottom;
    private TextView mTextViewSearch, mTextViewHistory, mTextViewFavorites,
            mTextViewTranslator, mTextViewWeb;
    private ImageView mImageViewSearch, mImageViewHistory, mImageViewFavorites,
            mImageViewTranslator, mImageViewWeb;
    private NavigationView mnavigationView;
    private SearchFragment msearchFragment;
    private HistoryFragment mhistoryFragment;
    private FavoritesFragment mfavoritesFragment;
    private TranslatorFragment mtranslatorFragment;
    private WebFragment mwebFragment;
    private MenuFragment mmenuFragment;
    private Toolbar mToolbar;
    public static MyDatabaseHelper databasehelper;
    public static Database_history database_history;
    public static Database_favorites database_favorites;
    public static final String FILENAME = "FILENAME";
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initlizecomponet();
        Toolbar toolbar = findViewById(R.id.toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mnavigationView.setNavigationItemSelectedListener(this);
        database_favorites = new Database_favorites(this);
        database_history = new Database_history(this);
        databasehelper = new MyDatabaseHelper(this);
        databasehelper.createDataBase();
        if (msearchFragment == null) {
            msearchFragment = new SearchFragment();
            mImageViewSearch.setColorFilter(ContextCompat.getColor(this, R.color.orange));
            mTextViewSearch.setTextColor(ContextCompat.getColor(this, R.color.orange));
            getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, msearchFragment).commit();

        }
        mlinear_bottomSearch.setOnClickListener(this);
        mlinear_bottomhistory.setOnClickListener(this);
        mlinear_bottomFavorites.setOnClickListener(this);
        mlinear_bottomWeb.setOnClickListener(this);
        mlinear_bottomTranlator.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        }

    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                if (msearchFragment == null) {
                    msearchFragment = new SearchFragment();
                }
                Log.d("AAA", String.valueOf(item.getItemId()));
                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                white();
                mImageViewSearch.setColorFilter(ContextCompat.getColor(this, R.color.orange));
                mTextViewSearch.setTextColor(ContextCompat.getColor(this, R.color.orange));
                mToolbar.setTitle(getString(R.string.title_toolbar_home));

                mlinear_bottom.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, msearchFragment).commit();
                break;
            case R.id.nav_Nouns:

                mmenuFragment = new MenuFragment();

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mToolbar.setTitle("Noun");
                mlinear_bottom.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b = new Bundle();
                b.putString(FILENAME, "noun");
                mmenuFragment.setArguments(b);
                //mLinear_Menu.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_Article:
                mmenuFragment = new MenuFragment();

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);

                mToolbar.setTitle("Article");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b1 = new Bundle();
                b1.putString(FILENAME, "article");
                mmenuFragment.setArguments(b1);
                break;
            case R.id.nav_Pronoun:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Pronoun");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b2 = new Bundle();
                b2.putString(FILENAME, "pronoun");
                mmenuFragment.setArguments(b2);
                break;
            case R.id.nav_Numberal:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Numberal");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b3 = new Bundle();
                b3.putString(FILENAME, "numberal");
                mmenuFragment.setArguments(b3);
                break;
            case R.id.nav_Adjective:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Adjective");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b4 = new Bundle();
                b4.putString(FILENAME, "adjective");
                mmenuFragment.setArguments(b4);
                break;
            case R.id.nav_Adverb:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Adverb");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b5 = new Bundle();
                b5.putString(FILENAME, "adverb");
                mmenuFragment.setArguments(b5);
                break;
            case R.id.nav_Verb:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Verb");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b6 = new Bundle();
                b6.putString(FILENAME, "verb");
                mmenuFragment.setArguments(b6);
                break;
            case R.id.nav_Preposition:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Preposition");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b7 = new Bundle();
                b7.putString(FILENAME, "preposition");
                mmenuFragment.setArguments(b7);
                break;
            case R.id.nav_Conjunction:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Conjunction");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b8 = new Bundle();
                b8.putString(FILENAME, "conjunction");
                mmenuFragment.setArguments(b8);
                break;
            case R.id.nav_Particles:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Particles");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b9 = new Bundle();
                b9.putString(FILENAME, "particles");
                mmenuFragment.setArguments(b9);
                break;
            case R.id.nav_Interjection:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("Interjection");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b10 = new Bundle();
                b10.putString(FILENAME, "interjection");
                mmenuFragment.setArguments(b10);
                break;
            case R.id.PhraseBook1:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("PhraseBook 1");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b11 = new Bundle();
                b11.putString(FILENAME, "book1");
                mmenuFragment.setArguments(b11);
                break;
            case R.id.PhraseBook2:

                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                mmenuFragment = new MenuFragment();
                mToolbar.setTitle("PhraseBook 2");
                mlinear_bottom.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mmenuFragment).commit();
                Bundle b12 = new Bundle();
                b12.putString(FILENAME, "book2");
                mmenuFragment.setArguments(b12);
                break;
            case R.id.nav_fastkey:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        startService(new Intent(MainActivity.this, FloatingWindow.class));
                    } else {
                        startForegroundService(new Intent(MainActivity.this, FloatingWindow.class));
                    }

                } else if (Settings.canDrawOverlays(this)) {
                    startService(new Intent(MainActivity.this, FloatingWindow.class));
                } else {
                    askPermission();
                    Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_share:


                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.hdpsolution.englishrussiandict&fbclid=IwAR0q1HNe7Hj3kGdsj1zwL2rjrtcic58PCF2jF7r85qiy5sFnO8SRUElSXzE");
                startActivity(Intent.createChooser(sharingIntent, "Share English Russian App"));
                break;
            case R.id.nav_feedback:


                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                Intent feedback = new Intent(Intent.ACTION_SEND);
                feedback.setType("message/html");
                feedback.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@hdpsolutions.com"});
                feedback.putExtra(Intent.EXTRA_SUBJECT, "Feedback for app " + "" + "English Russian Dictionary");
                feedback.putExtra(android.content.Intent.EXTRA_TEXT, "Do you like this app - English Russian Dictionary? please provide us feedback for this app." + "\n" + "Thank you");
                startActivity(Intent.createChooser(feedback, "Hoàn tất tác vụ đang sử dụng"));
                break;
            case R.id.nav_rate:


                mnavigationView.getMenu().findItem(item.getItemId()).setCheckable(true);
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                startActivity(i);
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initlizecomponet() {
        mlinear_bottomSearch = findViewById(R.id.linear_bottomsearch);
        mlinear_bottomhistory = findViewById(R.id.linear_bottomhistory);
        mlinear_bottomFavorites = findViewById(R.id.linear_bottomfavorites);
        mlinear_bottomTranlator = findViewById(R.id.linear_bottomTranslator);
        mlinear_bottomWeb = findViewById(R.id.linear_bottomweb);
        mlinear_bottom = findViewById(R.id.linear_bottom);
        mToolbar = findViewById(R.id.toolbar);

        mTextViewSearch = findViewById(R.id.txt_search);
        mTextViewHistory = findViewById(R.id.txt_history);
        mTextViewFavorites = findViewById(R.id.txt_favorites);
        mTextViewTranslator = findViewById(R.id.txt_translator);
        mTextViewWeb = findViewById(R.id.txt_web);

        mImageViewSearch = findViewById(R.id.imv_search);
        mImageViewHistory = findViewById(R.id.imv_history);
        mImageViewFavorites = findViewById(R.id.imv_favorites);
        mImageViewTranslator = findViewById(R.id.imv_translator);
        mImageViewWeb = findViewById(R.id.imv_web);

        mnavigationView = findViewById(R.id.nav_view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_bottomsearch:
                if (msearchFragment == null) {
                    msearchFragment = new SearchFragment();
                }
                white();

                mImageViewSearch.setColorFilter(ContextCompat.getColor(this, R.color.orange));
                mTextViewSearch.setTextColor(ContextCompat.getColor(this, R.color.orange));
                mTextViewSearch.setTextSize(MyConstants.COMMON_TEXT_SIZE);

                mlinear_bottom.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, msearchFragment).commit();
                break;
            case R.id.linear_bottomhistory:
                if (mhistoryFragment == null) {
                    mhistoryFragment = new HistoryFragment();
                }
                white();
                mImageViewHistory.setColorFilter(ContextCompat.getColor(this, R.color.orange));
                mTextViewHistory.setTextColor(ContextCompat.getColor(this, R.color.orange));
                mTextViewHistory.setTextSize(MyConstants.COMMON_TEXT_SIZE);
                mlinear_bottom.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mhistoryFragment).commit();
                break;
            case R.id.linear_bottomfavorites:
                if (mfavoritesFragment == null) {
                    mfavoritesFragment = new FavoritesFragment();
                }
                white();
                mImageViewFavorites.setColorFilter(ContextCompat.getColor(this, R.color.orange));
                mTextViewFavorites.setTextColor(ContextCompat.getColor(this, R.color.orange));
                mTextViewFavorites.setTextSize(MyConstants.COMMON_TEXT_SIZE);
                mlinear_bottom.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mfavoritesFragment).commit();
                break;
            case R.id.linear_bottomweb:
                if (mwebFragment == null) {
                    mwebFragment = new WebFragment();
                }
                white();
                mImageViewWeb.setColorFilter(ContextCompat.getColor(this, R.color.orange));
                mTextViewWeb.setTextColor(ContextCompat.getColor(this, R.color.orange));
                mTextViewWeb.setTextSize(MyConstants.COMMON_TEXT_SIZE);
                mlinear_bottom.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mwebFragment).commit();
                break;
            case R.id.linear_bottomTranslator:
                if (mtranslatorFragment == null) {
                    mtranslatorFragment = new TranslatorFragment();
                }
                white();
                mTextViewTranslator.setTextSize(MyConstants.COMMON_TEXT_SIZE);
                mImageViewTranslator.setColorFilter(ContextCompat.getColor(this, R.color.orange));
                mTextViewTranslator.setTextColor(ContextCompat.getColor(this, R.color.orange));
                mlinear_bottom.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_main, mtranslatorFragment).commit();
                break;
            default:
                break;
        }
    }

    public void white() {

        mImageViewSearch.setColorFilter(ContextCompat.getColor(this, R.color.white));
        mImageViewHistory.setColorFilter(ContextCompat.getColor(this, R.color.white));
        mImageViewFavorites.setColorFilter(ContextCompat.getColor(this, R.color.white));
        mImageViewTranslator.setColorFilter(ContextCompat.getColor(this, R.color.white));
        mImageViewWeb.setColorFilter(ContextCompat.getColor(this, R.color.white));

        mTextViewSearch.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTextViewHistory.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTextViewFavorites.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTextViewTranslator.setTextColor(ContextCompat.getColor(this, R.color.white));
        mTextViewWeb.setTextColor(ContextCompat.getColor(this, R.color.white));

        mTextViewSearch.setTextSize(13);
        mTextViewHistory.setTextSize(13);
        mTextViewFavorites.setTextSize(13);
        mTextViewTranslator.setTextSize(13);
        mTextViewWeb.setTextSize(13);

    }

}
