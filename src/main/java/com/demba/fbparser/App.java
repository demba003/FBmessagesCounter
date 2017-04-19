package com.demba.fbparser;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    private static String token = "";

    public static void main(String[] args) {

        try {
           token = Files.readAllLines(Paths.get("tokenfile")).get(0);
        } catch (NoSuchFileException e) {
            System.out.println("Get your Facebook Graph API token: https://developers.facebook.com/tools/explorer/");
            System.out.println("And paste it here:");
            Scanner scanner = new Scanner(System.in);
            token = scanner.nextLine();
            List<String> lines = Arrays.asList(token);
            try {
                Files.write(Paths.get("tokenfile"), lines, Charset.forName("UTF-8"));
            } catch (Exception ex) {
                System.out.println(ex);
                System.exit(0);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(0);
        }

        try {
            PropertiesConfiguration friends = new PropertiesConfiguration("friends");
            Document doc;

            try {
                File input = new File("messages.htm");
                doc = Jsoup.parse(input, "UTF-8");

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

                        String personId = person.substring(0, person.indexOf("@"));
                        String name;

                        try {
                            if (null == friends.getProperty(personId)) {
                                JSONObject json = readJsonFromUrl("https://graph.facebook.com/v2.9/" + personId + "?fields=name&access_token=" + token);
                                name = json.get("name").toString();
                                System.out.println(name);
                                friends.setProperty(personId, name);
                                friends.save();
                            } else {
                                System.out.println(friends.getProperty(personId));
                            }

                        } catch (Exception e) {
                            String personURL = "https://www.facebook.com/profile.php?id=" + person.substring(0, person.indexOf("@"));
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
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}