package project.datacollecting.seleniumhelper;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LogIn {

    
    /** 
     * @param username  
     * @param password
     * @param browser   browser should have Anti Automate Detection 
     * @param url   Log in page url
     * @return WebDriver
     */
    public static WebDriver logInTwitter(WebDriver browser ){
        browser.navigate().to("https://twitter.com/i/flow/login");

        Scanner scanner = new Scanner(System.in);
        // Take user input for username and password
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        scanner.close();

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return browser;
    
    }
}
