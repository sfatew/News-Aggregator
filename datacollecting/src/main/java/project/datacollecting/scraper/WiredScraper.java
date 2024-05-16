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



public class WiredScraper extends Scraper{

    private int loadLimit;
    
    public WiredScraper(String articlesListUrl, String filePath, int loadLimit) {
        super(articlesListUrl, filePath);
        this.loadLimit = loadLimit;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void scrape() {

        browser.navigate().to(articlesListUrl);
 

        File f = new File(filePath);
        JSONArray jsonArray = StoringHelper.parseringArray(f);

        LoadMore.click2Load(browser, loadLimit, "//span[text()='More Stories']");

        List<WebElement> articles_list = browser.findElements(By.className("summary-list__items"));


        List<WebElement> articles = new LinkedList<WebElement>();
        for ( WebElement ar_l : articles_list ){
            articles.addAll(ar_l.findElements(By.cssSelector("div[class^='SummaryItemWrapper']"))); 
        }
        // System.out.println(articles.size());

        WebDriver newBrowser = BrowserManager.setUpEdgeBrowser();

        for (WebElement ar : articles){

            try {
            
                // System.out.println(ar.getText());
                JSONObject content = new JSONObject();

                String link = ar.findElement(By.cssSelector("a[class^='SummaryItemHedLink']")).getAttribute("href");

                content.put("url", link);

                try {
                    content.put("post_cover", ar.findElement(By.cssSelector("img")).getAttribute("src"));
                } catch (NoSuchElementException e) {
                    content.put("post_cover", null);
                }
                

                content.put("title", ar.findElement(By.cssSelector("h3")).getText());

                try{
                    content.put("author", ar.findElement(By.cssSelector("span[data-testid=\"BylineName\"]")).getText());
                } catch (NoSuchElementException e) {
                    content.put("author", null);

                }

                content.put("date", ar.findElement(By.cssSelector("time")).getText());

                

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
        System.out.println(link);

        newBrowser.navigate().to(link);

        try{
            content.put("summary", newBrowser.findElement(By.cssSelector("div[class*='ContentHeaderDek']")).getText());
        } catch (NoSuchElementException e) {
            content.put("summary", null);

        }

        List<String> post_contents = ScrapingHelper.scrapeMultiSimilarEleClass(newBrowser, "body__inner-container");

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
