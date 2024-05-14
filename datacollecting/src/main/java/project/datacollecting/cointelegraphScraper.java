package project.datacollecting;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import project.datacollecting.seleniumhelper.BrowserSetup;
import project.datacollecting.seleniumhelper.LoadMore;
import project.datacollecting.seleniumhelper.seleniumHelper;

public class CointelegraphScraper {

    
    /** 
     * NOTE: Create a new web driver object to use this method
     * @param newBrowser
     * @param link
     * @param content
     */
    @SuppressWarnings("unchecked")
    private static void scrapeCointelegraphArticles(WebDriver newBrowser, String link, JSONObject content){

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

        List<String> tags = seleniumHelper.scrapeMultiSimilarEleClass(newBrowser, "tags-list__item");

        content.put("tags",  tags);

    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
    
        WebDriver browser = BrowserSetup.setUpEdgeBrowser();
        browser.navigate().to("https://cointelegraph.com/tags/blockchain");
 

        File f = new File("C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_cointelegraph.json");
        JSONArray jsonArray = seleniumHelper.parseringArray(f);


        LoadMore.scroll2Load(browser, 2);


        List<WebElement> articles = new LinkedList<WebElement>();

        articles.addAll(browser.findElements(By.className("post-card-inline"))) ; 
        
        // System.out.println(articles.size());

        WebDriver newBrowser = BrowserSetup.setUpEdgeBrowser();

        for (WebElement ar : articles){
            // System.out.println(ar.getText());
            JSONObject content = new JSONObject();

            String link = ar.findElement(By.cssSelector("a")).getAttribute("href");

            content.put("url", link);
            content.put("title", ar.findElement(By.className("post-card-inline__title")).getText());
            content.put("author", ar.findElement(By.className("post-card-inline__author")).getText());
            content.put("summary", ar.findElement(By.className("post-card-inline__text")).getText());
            content.put("date", ar.findElement(By.cssSelector("time")).getAttribute("datetime"));

            // content.put("all", ar.getText());
            
            scrapeCointelegraphArticles(newBrowser, link, content);

            jsonArray.add(content);
            

        }

        seleniumHelper.writeJSON(jsonArray, f);

        newBrowser.quit();
        
        browser.quit();
    }
}
