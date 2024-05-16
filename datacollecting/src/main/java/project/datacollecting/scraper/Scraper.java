package project.datacollecting.scraper;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import project.datacollecting.seleniumhelper.BrowserManager;

public abstract class Scraper implements IScraper{
    protected String articlesListUrl;
    protected String filePath;
    protected WebDriver browser;

    public Scraper(String articlesListUrl, String filePath) {
        this.articlesListUrl = articlesListUrl;
        this.filePath = filePath;
        this.browser = setupBrowser();
    }

    @Override
    public abstract void scrape();

    protected abstract void scrapeArticle(WebDriver newBrowser, String link, JSONObject content);

    
    /** Default browser setup
     * @return WebDriver
     */
    protected WebDriver setupBrowser() {
        return BrowserManager.setUpEdgeBrowser();
    }

    public void closeBrowser() {
        if (browser != null) {
            browser.quit();
        }
    }

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
