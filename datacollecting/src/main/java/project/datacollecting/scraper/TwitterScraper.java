package project.datacollecting.scraper;


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

import project.datacollecting.seleniumhelper.StoringHelper;
import project.datacollecting.seleniumhelper.BrowserSetup;
import project.datacollecting.seleniumhelper.LoadMore;
import project.datacollecting.seleniumhelper.LogIn;
import project.datacollecting.seleniumhelper.ScrapingHelper;



public class TwitterScraper extends Scraper{

    private int loadLimit;
     
    public TwitterScraper(String articlesListUrl, String filePath, int loadLimit) {
        super(articlesListUrl, filePath);
        this.loadLimit = loadLimit;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void scrapeArticlesList() {
        EdgeOptions options = new EdgeOptions();
        BrowserSetup.setAntiAutomateDetection(options);

        WebDriver browser = BrowserSetup.setUpEdgeBrowser(options);
        LogIn.logInTwitter(browser);
        

        browser.navigate().to(articlesListUrl);
 

        File f = new File(filePath);
        JSONArray jsonArray = StoringHelper.parseringArray(f);

        WebDriver newBrowser = BrowserSetup.setUpEdgeBrowser(options);
        LogIn.logInTwitter(newBrowser);


        int scrollCounter = 0;

        while (loadLimit > scrollCounter) {


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
                    scrapeArticle(newBrowser, link, content);
                    
                } catch (TimeoutException e) {
                    System.out.println("time out");
                    continue;
                }
                
                jsonArray.add(content);
            }

            LoadMore.scroll2Load(browser);

            scrollCounter++;

        }

        StoringHelper.writeJSON(jsonArray, f);


        newBrowser.quit();
        
        browser.quit();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void scrapeArticle(WebDriver newBrowser, String link, JSONObject content) {
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
            List<String> tags = ScrapingHelper.scrapeMultiSimilarEleCSS(newBrowser, "a[href^='/hashtag/']");
            content.put("tags", tags);
        } catch (NoSuchElementException e) {
            content.put("tags", null);
        }

    }


    public int getLoadLimit() {
        return loadLimit;
    }


    public void setLoadLimit(int loadLimit) {
        this.loadLimit = loadLimit;
    }
}
