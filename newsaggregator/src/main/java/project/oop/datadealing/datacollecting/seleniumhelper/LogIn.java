package project.oop.datadealing.datacollecting.seleniumhelper;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LogIn {

    private static  ConfigManager configManager = new ConfigManager();

    /** 
     * @param browser   browser should have Anti Automate Detection 
     * @return WebDriver
     */
    public static WebDriver logInTwitter(WebDriver browser){
        browser.navigate().to("https://twitter.com/i/flow/login");

        
        String username = configManager.getProperty("twitter.username");
        String password = configManager.getProperty("twitter.password");

        //Find and input the username

        Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(20));
        WebElement username_input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[autocomplete='username']")));
        
        username_input.sendKeys(username);
        username_input.sendKeys(Keys.ENTER);
    
        //Find and input the password

        WebElement password_input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        
        password_input.sendKeys(password);
        password_input.sendKeys(Keys.ENTER);
    
        // Wait for a short period to ensure the login process completes
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return browser;
    
    }
}
