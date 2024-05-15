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


    public String getArticlesListUrl() {
        return articlesListUrl;
    }


    public String getFilePath() {
        return filePath;
    }


    public void setArticlesListUrl(String articlesListUrl) {
        this.articlesListUrl = articlesListUrl;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

   

}
