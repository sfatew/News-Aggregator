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



public class wiredScraper {

    
    /** 
     * NOTE: Create a new web driver object to use this method
     * @param newBrowser
     * @param link
     * @param content
     */
    @SuppressWarnings("unchecked")
    private static void scrapeWiredArticles(WebDriver newBrowser, String link, JSONObject content){

        System.out.println(link);

        newBrowser.navigate().to(link);

        try{
            content.put("summary", newBrowser.findElement(By.cssSelector("div[class*='ContentHeaderDek']")).getText());
        } catch (NoSuchElementException e) {
            content.put("summary", null);

        }

        List<String> post_contents = seleniumHelper.scrapeMultiSimilarEleClass(newBrowser, "body__inner-container");

        String post_content = "";
        for (String p : post_contents){
            post_content += p;
        }

        content.put("post_content", post_content);


        List<String> tags = seleniumHelper.scrapeMultiSimilarEleCSS(newBrowser, "a[href^=\"/tag/\"]");
        
        content.put("tags", tags);

    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
    
        WebDriver browser = seleniumHelper.setUpEdgeBrowser();
        // browser.navigate().to("https://www.wired.com/search/?q=blockchain&sort=score+desc");
        browser.navigate().to("https://www.wired.com/search/?q=blockchain&sort=publishdate+desc");
 

        File f = new File("C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_wired.json");
        JSONArray jsonArray = seleniumHelper.parseringArray(f);

        seleniumHelper.click2Load(browser, 10, "//span[text()='More Stories']");


        List<WebElement> articles_list = browser.findElements(By.className("summary-list__items"));


        List<WebElement> articles = new LinkedList<WebElement>();
        for ( WebElement ar_l : articles_list ){
            articles.addAll(ar_l.findElements(By.cssSelector("div[class^='SummaryItemWrapper']"))); 
        }
        // System.out.println(articles.size());

        WebDriver newBrowser = seleniumHelper.setUpEdgeBrowser();

        for (WebElement ar : articles){
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
            
            jsonArray.add(content);

            scrapeWiredArticles(newBrowser, link, content);

        }

        seleniumHelper.writeJSON(jsonArray, f);

        newBrowser.quit();
        
        browser.quit();
    }
}
