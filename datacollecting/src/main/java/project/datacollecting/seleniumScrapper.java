package project.datacollecting;

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

public class seleniumScrapper {

    // private static int scrollLimit ;

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

    public void scraping(){

    }

    public void writeJSON(JSONArray jsonArray, File f){
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
    }

    public static void scroll(WebDriver browser, int scrollLimit){
        
        JavascriptExecutor js = (JavascriptExecutor) browser;

        Object lastHeight = js.executeScript("return document.body.scrollHeight");

        int scrollCounter = 0;

        while (scrollLimit > scrollCounter) {

            System.out.println(".");

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

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
    }


    public static void main(String[] args) {
    
        WebDriver browser = new EdgeDriver();
        browser.navigate().to("https://cointelegraph.com/tags/blockchain");


        scroll(browser, 20);
    }

}
