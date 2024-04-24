package project.webscrapping;


import java.io.*;

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


public class TetScrapeSelenium {

    private final static int itemTargetCount = 1000;

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

    public static int getItemtargetcount() {
        return itemTargetCount;
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

        while (itemTargetCount > articles.size()) {

            System.out.println(".");

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Object newHeight = js.executeScript("return document.body.scrollHeight");

            if (newHeight == lastHeight){
                System.out.println("..");
                break;
            }
            lastHeight = newHeight;

            articles.addAll(browser.findElements(By.className("post-card-inline"))) ; 
        
            // System.out.println(articles.size());

            for (WebElement ar : articles){
                // System.out.println(ar.getText());
                JSONObject content = new JSONObject();
                content.put("title", ar.findElement(By.className("post-card-inline__title")).getText());
                jsonArray.add(content);

            }

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

        System.out.println("added to JSON file: "+ jsonArray);


        browser.quit();
    }


}
