package project.datacollecting.seleniumhelper;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumAction {
    public static void clickOn(WebDriver browser, String loadButtonLocation){

        Wait<WebDriver> wait = new WebDriverWait(browser, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loadButtonLocation))).click();

    }
}
