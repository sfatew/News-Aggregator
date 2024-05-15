package project.datacollecting.scraper;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

public abstract class Scraper {
    protected String articlesListUrl;
    protected String filePath;

    public Scraper(String articlesListUrl, String filePath) {
        this.articlesListUrl = articlesListUrl;
        this.filePath = filePath;
    }


    public abstract void scrapeArticlesList();

    protected abstract void scrapeArticle(WebDriver newBrowser, String link, JSONObject content);

   

}
