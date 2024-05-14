package project.datacollecting;


import java.io.*;
import java.time.Duration;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import project.datacollecting.seleniumhelper.seleniumHelper;
import project.datacollecting.seleniumhelper.BrowserSetup;
import project.datacollecting.seleniumhelper.LoadMore;
import project.datacollecting.seleniumhelper.LogIn;



public class TwitterScraper {

    
    /** 
     * NOTE: Create a new web driver object to use this method
     * @param newBrowser
     * @param link
     * @param content
     */
    @SuppressWarnings("unchecked")
    private static void scrapeTwitterPost(WebDriver newBrowser, String link, JSONObject content){

        System.out.println(link);

        newBrowser.navigate().to(link);

        Wait<WebDriver> wait = new WebDriverWait(newBrowser, Duration.ofSeconds(20));
        String author = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid='User-Name']"))).getText();
        System.out.println(author);
        content.put("author", author);


        content.put("summary", null);
     

        String post_content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid='tweetText']"))).getText();
        content.put("post_content", post_content);

        try {
            List<String> tags = seleniumHelper.scrapeMultiSimilarEleCSS(newBrowser, "a[href^='/hashtag/']");
            content.put("tags", tags);
        } catch (NoSuchElementException e) {
            content.put("tags", null);
        }

    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
    
        EdgeOptions options = new EdgeOptions();
        BrowserSetup.setAntiAutomateDetection(options);

        WebDriver browser = BrowserSetup.setUpEdgeBrowser(options);
        LogIn.logInTwitter("ewpumpkin", "pumpkin124@142", browser);
    
        browser.navigate().to("https://twitter.com/search?q=blockchain&src=recent_search_click");
 

        File f = new File("C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_twitter.json");
        JSONArray jsonArray = seleniumHelper.parseringArray(f);

        WebDriver newBrowser = BrowserSetup.setUpEdgeBrowser(options);
        LogIn.logInTwitter("ewpumpkin", "pumpkin124@142", newBrowser);


        int scrollLimit = 10;

        int scrollCounter = 0;

        while (scrollLimit > scrollCounter) {


            List<WebElement> articles = browser.findElements(By.cssSelector("article[data-testid='tweet']"));
            System.out.println(articles.size());


            for (WebElement ar : articles){
                // System.out.println(ar.getText());
                JSONObject content = new JSONObject();


                WebElement user = ar.findElement(By.cssSelector("div[data-testid='User-Name']"));
                // System.out.println(user.getText());

                String link =  user.findElement(By.cssSelector("a[dir='ltr']")).getAttribute("href");
                content.put("url",link);


                content.put("date", user.findElement(By.cssSelector("time")).getAttribute("datetime"));

                content.put("post_cover", null);

                content.put("title", null);
    

                // content.put("all", ar.getText());
                
                try {
                    scrapeTwitterPost(newBrowser, link, content);
                    
                } catch (TimeoutException e) {
                    System.out.println("time out");
                    continue;
                }
                
                jsonArray.add(content);
            }

            LoadMore.scroll2Load(browser);

            scrollCounter++;

        }

        seleniumHelper.writeJSON(jsonArray, f);


        newBrowser.quit();
        
        browser.quit();
    }
}
