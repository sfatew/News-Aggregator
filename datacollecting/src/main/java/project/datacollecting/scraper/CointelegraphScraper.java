package project.datacollecting.scraper;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import project.datacollecting.seleniumhelper.BrowserManager;
import project.datacollecting.seleniumhelper.LoadMore;
import project.datacollecting.seleniumhelper.ScrapingHelper;
import project.datacollecting.seleniumhelper.StoringHelper;

public class CointelegraphScraper extends Scraper{
    
    private int loadLimit;
    
    public CointelegraphScraper(String articlesListUrl, String filePath, int loadLimit) {
        super(articlesListUrl, filePath);
        this.loadLimit = loadLimit;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void scrape() {

        browser.navigate().to(articlesListUrl);
        

        File f = new File(filePath);
        JSONArray jsonArray = StoringHelper.parseringArray(f);


        LoadMore.scroll2Load(browser, loadLimit);


        List<WebElement> articles = new LinkedList<WebElement>();

        articles.addAll(browser.findElements(By.className("post-card-inline"))) ; 
        
        // System.out.println(articles.size());

        WebDriver newBrowser = BrowserManager.setUpEdgeBrowser();

        for (WebElement ar : articles){

            try{
                // System.out.println(ar.getText());
                JSONObject content = new JSONObject();

                String link = ar.findElement(By.cssSelector("a")).getAttribute("href");

                content.put("url", link);
                content.put("title", ar.findElement(By.className("post-card-inline__title")).getText());
                content.put("author", ar.findElement(By.className("post-card-inline__author")).getText());
                content.put("summary", ar.findElement(By.className("post-card-inline__text")).getText());
                content.put("date", ar.findElement(By.cssSelector("time")).getAttribute("datetime"));

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

        try {
            WebElement post_cover_set = newBrowser.findElement(By.className("post-cover__wrp"));
            content.put("post_cover", post_cover_set.findElement(By.cssSelector("img")).getAttribute("src"));
        } catch (NoSuchElementException e) {
            content.put("post_cover", null);
        }
        

        WebElement post_content = newBrowser.findElement(By.cssSelector(".post-content.relative"));
        // System.out.println(post_content);

        content.put("post_content", post_content.getText());

        List<String> tags = ScrapingHelper.scrapeMultiSimilarEleClass(newBrowser, "tags-list__item");

        content.put("tags",  tags);
    }

    public int getLoadLimit() {
        return loadLimit;
    }

    public void setLoadLimit(int loadLimit) {
        this.loadLimit = loadLimit;
    }

}
