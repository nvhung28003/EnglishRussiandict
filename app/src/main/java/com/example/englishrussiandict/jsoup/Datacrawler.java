package com.example.englishrussiandict.jsoup;

import android.preference.PreferenceManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;

public class Datacrawler {
    String mTranslate = "";

    public void crawledata(final OnresultCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    mTranslate = excute();
                    callBack.onSuccess(mTranslate);
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onFailure(e);
                }


            }
        }).start();
    }

    public String excute() {
        String url = "https://translate.google.com/?hl=vi#view=home&op=translate&sl=en&tl=ru&text=Today%20is%20a%20bad%20day";
        String m = "a";
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements items = document.select("div.result-shield-container tlid-copy-target");
        for (Element item : items) {
                Element a = item.selectFirst("span.tlid-translation translation");
                Element gt = a.selectFirst("span");
            Node node1 = gt.nextSibling();
            String gtt = node1.toString();
            Log.d("HHHH", gtt);
        }


        //
        Log.d("HHHH", "excute: ");
        return m;
    }

    public interface OnresultCallBack {
        void onSuccess(String translte);

        void onFailure(Throwable throwable);
    }
}
