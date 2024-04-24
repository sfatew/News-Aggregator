package project.webscrapping;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestWebCrawling {

    private static void  crawl(int level, String url, ArrayList<String> visited){
        if (level <= 3){
            Document doc = request(url, visited);

            if(doc != null){
                // System.out.println(doc);
                for(Element link : doc.select("a[href]")){
                    String next_link = link.absUrl("href");
                    if(visited.contains(next_link) == false){
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> v){
        try{
            Connection con = Jsoup.connect(url);
            Document doc = con.userAgent("Mozilla").get();

            if (con.response().statusCode() == 200){
                System.out.println("Link: " + url);
                System.out.println(doc.title());
                v.add(url);

                return doc;
            }
            return null;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String url = "https://cointelegraph.com/tags/blockchain";
        crawl(1, url, new ArrayList<String>());
    }
}
