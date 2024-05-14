package project.datacollecting.seleniumhelper;

import java.util.List;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserSetup {
    public static WebDriver setUpEdgeBrowser(){
        System.setProperty("webdriver.edge.driver", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\edgedriver_win64\\msedgedriver.exe");
        
        WebDriver browser = new EdgeDriver();

        return browser;
    }

    /** 
     * @param options  
     * @return WebDriver
     */
    public static WebDriver setUpEdgeBrowser(EdgeOptions options){
        System.setProperty("webdriver.edge.driver", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\edgedriver_win64\\msedgedriver.exe");
        
        WebDriver browser = new EdgeDriver(options);

        return browser;
    }

    /** 
     * @param proxyAddress
     * @param proxyPort
     * @return EdgeOptions
     */
    public static EdgeOptions setProxy(EdgeOptions option, String proxyAddress, int proxyPort){
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress + ":" + proxyPort);

        option.setCapability("proxy", proxy);
        return option;
    }

    public static EdgeOptions setAntiAutomateDetection(EdgeOptions option){
        option.addArguments("--start-maximized");
        option.setExperimentalOption("excludeSwitches", List.of("enable-automation"));

        return option;
    }
}
