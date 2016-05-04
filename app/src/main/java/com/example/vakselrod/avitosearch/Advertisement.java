package com.example.vakselrod.avitosearch;

import android.util.Log;

import org.jsoup.nodes.Element;

public class Advertisement {
    public String url;
    public String header;
    public String date;
    public int price;
    public String ownerName;
    public String phone;
    public String location;
    public String description;
    public long adNumber;

    Advertisement(Element lot) {
        header = lot.select("div.description > h3 > a").text();
        url = "https://www.avito.ru" + lot.select("div.description > h3 > a").attr("href");

        String stringPrice = (lot.select("div.description > div.about").text().replaceAll("[^0-9]", ""));
        price = Integer.parseInt(stringPrice.length() > 0 ? stringPrice : "0");

        String tempDate = lot.select("div.description > div.data > div").text();
        if (tempDate.contains("Сегодня")) {
            date = tempDate.substring(8);
        } else {
            date = "Объявление устарело.";
        }
    }

    Advertisement() {}

    public static void printObject(Advertisement ad) {
        Log.i("LOG_TAG", ad.header + " " + ad.date + " " + ad.price + " " + ad.url);
    }
}
