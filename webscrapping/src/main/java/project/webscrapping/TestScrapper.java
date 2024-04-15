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
public class TestScrapper 
{
    public static void main( String[] args )
    {
        String url = "https://medium.com";

        try {
            Document document = Jsoup.connect(url).get();
            Elements writing = document.select();     // cssquery can query element, classes, IDs,... using css select 
        } catch (IOException e) {
            e.printStackTrace();    // show exactly where the error came from
        }

    }
}
