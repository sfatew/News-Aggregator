package project.webscrapping;


import java.io.*;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TetScrapeSelenium {

    private static int scrollLimit = 20;

    public static JSONArray parseringArray(File f){
        JSONParser jsonParser = new JSONParser();
        
        JSONArray jsonArray = new JSONArray();
        try {
            if (f.exists() && !f.isDirectory()){
                // System.out.println(".");
                Object obj = jsonParser.parse(new FileReader(f));
                // System.out.println(obj);
                jsonArray = (JSONArray)obj;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        // System.out.println(jsonArray);
        return jsonArray;
    }

    public static int getsScrollLimit() {
        return scrollLimit;
    }

    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        System.setProperty("webdriver.edge.driver", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\edgedriver_win64\\msedgedriver.exe");
        
        WebDriver browser = new EdgeDriver();
        browser.navigate().to("https://cointelegraph.com/tags/blockchain");

        List<WebElement> articles = new LinkedList<WebElement>(); 

        File f = new File("C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output.json");


        JSONArray jsonArray = parseringArray(f);

        JavascriptExecutor js = (JavascriptExecutor) browser;

        Object lastHeight = js.executeScript("return document.body.scrollHeight");
        System.out.println(lastHeight);

        int scrollCounter = 0;

        while (scrollLimit > scrollCounter) {

            System.out.println(".");

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // try {
            //     Thread.sleep(100 );
            // } catch (InterruptedException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }

            Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn__icon.posts-listing__more-icon")));

            Object newHeight = js.executeScript("return document.body.scrollHeight");
            System.out.println(newHeight);

            if (newHeight == (lastHeight) ){
                System.out.println("..");
                break;
            }

            lastHeight = newHeight;

            scrollCounter ++;

        }

        articles.addAll(browser.findElements(By.className("post-card-inline"))) ; 
        
        // System.out.println(articles.size());

        for (WebElement ar : articles){
            // System.out.println(ar.getText());
            JSONObject content = new JSONObject();
            content.put("url", ar.findElement(By.cssSelector("a")).getAttribute("href"));
            content.put("title", ar.findElement(By.className("post-card-inline__title")).getText());
            content.put("author", ar.findElement(By.className("post-card-inline__author")).getText());
            content.put("summary", ar.findElement(By.className("post-card-inline__text")).getText());
            content.put("date", ar.findElement(By.cssSelector("time")).getAttribute("datetime"));

            // content.put("all", ar.getText());
            
            jsonArray.add(content);

        }

        try {
            FileWriter file = new FileWriter(f);
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("added to JSON file: " + jsonArray.size());


        browser.quit();
    }


}
