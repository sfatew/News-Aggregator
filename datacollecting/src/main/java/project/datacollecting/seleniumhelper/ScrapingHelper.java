package project.datacollecting.seleniumhelper;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrapingHelper {
    public static List<String> scrapeMultiSimilarEleCSS(WebDriver browser, String cssselector){

        List<WebElement> web_eles = new LinkedList<>();

        List<String> eles = new LinkedList<>();

        web_eles.addAll(browser.findElements(By.cssSelector(cssselector)));

        for (WebElement ele : web_eles){
            try {
                eles.add(ele.getText());
            } catch (StaleElementReferenceException e) {
                continue;
            }
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
}
