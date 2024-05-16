package project.datacollecting.scraper;


import java.io.*;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import project.datacollecting.seleniumhelper.BrowserManager;
import project.datacollecting.seleniumhelper.ConfigManager;
import project.datacollecting.seleniumhelper.LoadMore;
import project.datacollecting.seleniumhelper.ScrapingHelper;
import project.datacollecting.seleniumhelper.StoringHelper;

public class MediumScraper extends Scraper{

    private int loadLimit;
    private String proxyAddress;
    private String proxyPort;

    public MediumScraper(String articlesListUrl, String filePath, int loadLimit, ConfigManager configManager) {
        super(articlesListUrl, filePath);
        this.loadLimit = loadLimit;
        this.proxyAddress = configManager.getProperty("proxy.proxyAddress");
        this.proxyPort = configManager.getProperty("proxy.proxyPort");
    }

    @Override
    protected WebDriver setupBrowser() {
        EdgeOptions options = new EdgeOptions();
        BrowserManager.setProxy(options, proxyAddress, proxyPort);

        return BrowserManager.setUpEdgeBrowser(options);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void scrape() {
        
        browser.navigate().to(articlesListUrl);
        // "https://medium.com/tag/blockchain/recommended"

        List<WebElement> articles = new LinkedList<WebElement>(); 

        File f = new File(filePath);
        // "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_medium.json"
        JSONArray jsonArray = StoringHelper.parseringArray(f);


        LoadMore.scroll2Load(browser, loadLimit);


        articles.addAll(browser.findElements(By.cssSelector("article"))) ; 
        
        // System.out.println(articles.size());

        WebDriver newBrowser = setupBrowser();

        for (WebElement ar : articles){

            try{
                // System.out.println(ar.getText());
                JSONObject content = new JSONObject();

                String link = ar.findElement(By.cssSelector("div[role=\"link\"]")).getAttribute("data-href");
                System.out.println(link);

                content.put("url", link);
                content.put("title", ar.findElement(By.cssSelector("h2")).getText());

                try {
                    content.put("summary", ar.findElement(By.cssSelector("h3")).getText());
                } catch (NoSuchElementException e) {
                    content.put("summary", null);

                }
                
                try {
                    content.put("post_cover", ar.findElement(By.cssSelector("img[width=\"160\"]")).getAttribute("src"));
                } catch (NoSuchElementException e) {
                    content.put("post_cover", null);
                }
                

                // content.put("all", ar.getText());
                
                scrapeArticle(newBrowser, link, content);
                
                jsonArray.add(content);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        StoringHelper.writeJSON(jsonArray, f);

        newBrowser.quit();

        closeBrowser();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void scrapeArticle(WebDriver newBrowser, String link, JSONObject content) {
        newBrowser.navigate().to(link);

        Wait<WebDriver> wait = new WebDriverWait(newBrowser, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid=\"authorName\"]")));
        content.put("author", newBrowser.findElement(By.cssSelector("a[data-testid=\"authorName\"]")).getText());

        try {
            content.put("date", newBrowser.findElement(By.cssSelector("span[data-testid=\"storyPublishDate\"]")).getText());
        } catch (NoSuchElementException e) {
            content.put("date", null);
        }

        List<String> post_contents = ScrapingHelper.scrapeMultiSimilarEleClass(newBrowser, "pw-post-body-paragraph");

        String post_content = "";
        for (String p : post_contents){
            post_content += p;
        }

        content.put("post_content", post_content);
        
        
        List<String> tags = ScrapingHelper.scrapeMultiSimilarEleCSS(newBrowser, "a[href^=\"/tag/\"]");
        
        content.put("tags", tags);
    }

    public int getLoadLimit() {
        return loadLimit;
    }

    public void setLoadLimit(int loadLimit) {
        this.loadLimit = loadLimit;
    }



}
