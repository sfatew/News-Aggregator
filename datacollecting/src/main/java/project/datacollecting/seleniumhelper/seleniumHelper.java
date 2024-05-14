package project.datacollecting.seleniumhelper;

import java.io.*;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.By;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class seleniumHelper {



    public static List<String> scrapeMultiSimilarEleCSS(WebDriver browser, String cssselector){

        List<WebElement> web_eles = new LinkedList<>();

        List<String> eles = new LinkedList<>();

        web_eles.addAll(browser.findElements(By.cssSelector(cssselector)));

        for (WebElement ele : web_eles){
            eles.add(ele.getText());
        }

        return eles;
    }

    public static List<String> scrapeMultiSimilarEleClass(WebDriver browser, String className){

        List<WebElement> web_eles = new LinkedList<>();

        List<String> eles = new LinkedList<>();
       

        web_eles.addAll(browser.findElements(By.className(className)));
        System.out.println(web_eles.size());

        for (WebElement ele : web_eles){
            try {
            eles.add(ele.getText());
            System.out.println("__");
            } catch (StaleElementReferenceException e) {
                System.out.println("stale");
                continue;
            }
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


}
