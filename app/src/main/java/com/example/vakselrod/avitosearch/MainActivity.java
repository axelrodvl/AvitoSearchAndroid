package com.example.vakselrod.avitosearch;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final int NOTIFY_ID = 101;
    HistoryStorage historyStorage;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем наш класс-обёртку
        historyStorage = new HistoryStorage(this);
        // База нам нужна для записи и чтения
        db = historyStorage.getWritableDatabase();

        new AvitoFinder().execute();

        startService(
                new Intent(MainActivity.this, NotificationService.class));

        Button searchButton = (Button) findViewById(R.id.button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new AvitoFinder().execute();
            }
        });

        // закрываем соединения с базой данных
        //historyStorage.close();
        //db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AvitoFinder extends AsyncTask<Void, Void, Void> {
        ArrayList<Advertisement> resultAdList = new ArrayList<Advertisement>();
        String html;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            int priceFrom = 999;
            int priceTo = 3500;
            String request = "PS4";

            try {
                Document doc = Jsoup.connect("https://www.avito.ru/moskva/igry_pristavki_i_programmy/igry_dlya_pristavok?user=1&q=" + request).get();
                Elements lots = doc.select("#catalog > div.js-page.layout-internal.col-12.js-autosuggest__search-list-container > div.l-content > div.clearfix > div.catalog.catalog_table > div.catalog-list.clearfix > div.js-catalog_before-ads > div");
                lots.addAll(doc.select("#catalog > div.js-page.layout-internal.col-12.js-autosuggest__search-list-container > div.l-content > div.clearfix > div.catalog.catalog_table > div.catalog-list.clearfix > div.js-catalog_after-ads > div"));

                ArrayList<Advertisement> draftResult = new ArrayList<Advertisement>();

                for (Element lot : lots)
                    draftResult.add(new Advertisement(lot));

                for (Advertisement ad : draftResult) {
                    if ((ad.price > priceFrom) && (ad.price < priceTo))
                        resultAdList.add(ad);
                }

                PushNotification.show(getApplicationContext(), resultAdList.get(0).header, "Всего " + resultAdList.get(0).price + " рублей!");
                html = ListViewHTML.createHTML(resultAdList);
                HistoryStorage.saveList(db, resultAdList);


            } catch (IOException ioex) {
                Log.i("LOG_TAG", "No internet connection");
                List<Advertisement> adList = HistoryStorage.getList(db);

                if (adList.size() > 0)
                    html = ListViewHTML.createOfflineHTML(HistoryStorage.getList(db));
                else
                    html = ListViewHTML.createOfflineErrorHTML();
            } catch (Exception ex) {
                ex.printStackTrace();
                html = ListViewHTML.createErrorHTML();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            WebView wv = (WebView) findViewById(R.id.webView);
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
        }
    }
}

