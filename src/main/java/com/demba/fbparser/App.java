package com.demba.fbparser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {

        Document doc;
        try {
            File input = new File("messages.htm");
            doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

            // get all links
            Elements threads = doc.select("div.thread");

            System.out.println("Threads count: " + threads.size());

            int i=0;
            for (Element th : threads) {
                System.out.println("-------------------------");
                Elements mes = th.select("div.message");

                String threadString = th.toString();
                String people = threadString.substring(22, threadString.indexOf("<div class=\"message\">") - 2);

                String[] peopleArray = people.split(", ");

                for(String person : peopleArray){
                    String personURL = "https://www.facebook.com/profile.php?id=" + person.substring(0, person.indexOf("@"));
                    try {
                        Connection connect = Jsoup.connect(personURL);
                        System.out.println(connect.get().select("span#fb-timeline-cover-name").text());
                    } catch (Exception e) {
                        System.out.println(personURL);
                    }
                }
                System.out.println("");

                System.out.println("Messages count: " + mes.size());
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}