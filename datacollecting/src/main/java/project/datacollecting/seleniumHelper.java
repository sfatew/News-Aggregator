package project.datacollecting;

import java.io.*;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class seleniumHelper {

    public static WebDriver setUpEdgeBrowser(){
        System.setProperty("webdriver.edge.driver", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\edgedriver_win64\\msedgedriver.exe");
        
        WebDriver browser = new EdgeDriver();

        return browser;
    }

    public static WebDriver setUpEdgeBrowser(EdgeOptions options){
        System.setProperty("webdriver.edge.driver", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\edgedriver_win64\\msedgedriver.exe");
        
        WebDriver browser = new EdgeDriver(options);

        return browser;
    }

    public static List<String> scrapeMultiSimilarEleCSS(WebDriver browser, String cssselector){

        List<WebElement> web_eles = new LinkedList<>();
        web_eles.addAll(browser.findElements(By.cssSelector(cssselector)));

        List<String> eles = new LinkedList<>();

        for (WebElement ele : web_eles){
            eles.add(ele.getText());
        }

        return eles;
    }

    public static List<String> scrapeMultiSimilarEleClass(WebDriver browser, String className){

        List<WebElement> web_eles = new LinkedList<>();
        web_eles.addAll(browser.findElements(By.className(className)));

        List<String> eles = new LinkedList<>();

        for (WebElement ele : web_eles){
            eles.add(ele.getText());
        }

        return eles;
    }

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

    @SuppressWarnings("null")
    public static void writeJSON(JSONArray jsonArray, File f){
        FileWriter file = null;
        try {
            file = new FileWriter(f);
            file.write(jsonArray.toJSONString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("added to JSON file: " + jsonArray.size());
    }

    
    /** 
     * @param browser
     * @param scrollLimit  : number of times to scroll
     */
    public static void scroll(WebDriver browser, int scrollLimit){
        
        JavascriptExecutor js = (JavascriptExecutor) browser;

        Object lastHeight = js.executeScript("return document.body.scrollHeight");

        int scrollCounter = 0;

        while (scrollLimit > scrollCounter) {

            System.out.println(".");

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(30));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn__icon.posts-listing__more-icon")));

            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
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

    
    /** 
     * @param proxyAddress
     * @param proxyPort
     * @return EdgeOptions
     */
    public static EdgeOptions setProxy(String proxyAddress, int proxyPort){
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress + ":" + proxyPort);

        EdgeOptions option = new EdgeOptions();

        option.setCapability("proxy", proxy);
        return option;
    }

}
