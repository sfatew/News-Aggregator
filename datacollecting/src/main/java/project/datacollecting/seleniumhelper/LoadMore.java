package project.datacollecting.seleniumhelper;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class LoadMore {

    /** 
     * @param browser
     * @param scrollLimit  : number of times to scroll
     */
    public static void scroll2Load(WebDriver browser, int scrollLimit){
        
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
     * @param browser
     */
    public static void scroll2Load(WebDriver browser){
        
        JavascriptExecutor js = (JavascriptExecutor) browser;


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
        
    }

    
    /** 
     * @param browser
     * @param clickLimit
     * @param loadButtonLocation   : using xpath
     */
    public static void click2Load(WebDriver browser, int clickLimit, String loadButtonLocation){

        int clickCounter = 0;
        while (clickLimit > clickCounter){

            try {
                SeleniumAction.clickOn(browser, loadButtonLocation);
            } catch (ElementClickInterceptedException e) {
                JavascriptExecutor js = (JavascriptExecutor) browser;
                js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight)");  
                System.out.println(js.executeScript("return document.documentElement.scrollHeight"));
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                            
            } catch (NoSuchElementException e2){
                break;
            }

            System.out.println("!");

            clickCounter ++;
        }

    }



}
