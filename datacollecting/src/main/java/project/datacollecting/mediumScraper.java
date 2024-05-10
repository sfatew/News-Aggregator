package project.datacollecting;


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

public class mediumScraper {

    @SuppressWarnings("unchecked")
    private static void scrapeMediumArticles(WebDriver newBrowser, String link, JSONObject content){
        newBrowser.navigate().to(link);

        Wait<WebDriver> wait = new WebDriverWait(newBrowser, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid=\"authorName\"]")));
        content.put("author", newBrowser.findElement(By.cssSelector("a[data-testid=\"authorName\"]")).getText());

        try {
            content.put("date", newBrowser.findElement(By.cssSelector("span[data-testid=\"storyPublishDate\"]")).getText());
        } catch (NoSuchElementException e) {
            content.put("date", null);
        }

        List<String> post_contents = seleniumHelper.scrapeMultiSimilarEleClass(newBrowser, "pw-post-body-paragraph");

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
    
        EdgeOptions options = seleniumHelper.setProxy("209.146.104.56", 80);

        WebDriver browser = seleniumHelper.setUpEdgeBrowser(options);

        browser.navigate().to("https://medium.com/tag/blockchain/recommended");

        List<WebElement> articles = new LinkedList<WebElement>(); 

        File f = new File("C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_medium.json");


        JSONArray jsonArray = seleniumHelper.parseringArray(f);


        seleniumHelper.scroll(browser, 10);


        articles.addAll(browser.findElements(By.cssSelector("article"))) ; 
        
        // System.out.println(articles.size());

        WebDriver newBrowser = seleniumHelper.setUpEdgeBrowser(options);

        for (WebElement ar : articles){
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
            
            jsonArray.add(content);

            scrapeMediumArticles(newBrowser, link, content);

        }

        seleniumHelper.writeJSON(jsonArray, f);

        newBrowser.quit();

        browser.quit();
    }
}
