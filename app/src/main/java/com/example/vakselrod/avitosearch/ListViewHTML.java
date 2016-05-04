package com.example.vakselrod.avitosearch;

import java.util.List;

public class ListViewHTML {
    private static String html;

    public static String createHTML(List<Advertisement> resultAdList) {
        html = "\n\n<table>";
        for (Advertisement ad : resultAdList) {
            html += "\n\n<tr><td style=\"border-bottom: 1px solid black;\">";
            html += "\n\n<p><a style=\"color: #434C94; font-size: 16px; line-height: 20px; margin: 0 0 5px;\" href=\"" + ad.url + "\">" + ad.header + "</a></p>";
            html += "\n\n<p style=\"font-size: 16px; line-height: 20px; margin: 0 0 7px;\">" + ad.price + "</p>";
            html += "\n\n<p style=\"font-size: 13px; height: 18px; margin-top: 2px;\">Время: " + ad.date + "</p>";
            html += "\n\n</td></tr>";
        }
        html += "\n\n</table>";

        return html;
    }

    public static String createOfflineHTML(List<Advertisement> resultAdList) {
        html = "\n\n<table><tr><td><p style=\"font-size: 16px; line-height: 20px; margin: 0 0 7px;\">" +
                "Данные могут быть не актуальны. Список будет обновлен после подключения к сети.</p></td></tr>";

        for (Advertisement ad : resultAdList) {
            html += "\n\n<tr><td style=\"border-bottom: 1px solid black;\">";
            html += "\n\n<p><a style=\"color: #434C94; font-size: 16px; line-height: 20px; margin: 0 0 5px;\" href=\"" + ad.url + "\">" + ad.header + "</a></p>";
            html += "\n\n<p style=\"font-size: 16px; line-height: 20px; margin: 0 0 7px;\">" + ad.price + "</p>";
            html += "\n\n<p style=\"font-size: 13px; height: 18px; margin-top: 2px;\">Время: " + ad.date + "</p>";
            html += "\n\n</td></tr>";
        }
        html += "\n\n</table>";

        return html;
    }

    public static String createOfflineErrorHTML() {
        html = "\n\n<table><tr><td><p style=\"font-size: 16px; line-height: 20px; margin: 0 0 7px;\">" +
                "Список будет загружен после подключения к сети.</p></td></tr>";
        html += "\n\n</table>";

        return html;
    }

    public static String createErrorHTML() {
        html = "\n\n<table><tr><td><p style=\"font-size: 16px; line-height: 20px; margin: 0 0 7px;\">" +
                "Список будет загружен после подключения к сети.</p></td></tr>";
        html += "\n\n</table>";

        return html;
    }
}
