package project.webscrapping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Learning how to scrap
 *
 */
public class TestScrapperJSoup 
{
    public static void main( String[] args )
    {
        String url = "https://cointelegraph.com/tags/blockchain";

        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements articles = document.select(".post-card-inline");     // cssquery can query element, classes, IDs,... using css select 
        
            for (Element ar : articles){
                String title = ar.select(".post-card-inline__title").text();
                System.out.println(title);
            }
        
        } catch (IOException e) {
            e.printStackTrace();    // show exactly where the error came from
        }

    }
}
