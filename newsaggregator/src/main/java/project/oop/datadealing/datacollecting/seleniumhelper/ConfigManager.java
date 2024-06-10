package project.oop.datadealing.datacollecting.seleniumhelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private Properties properties;

    public ConfigManager(String fileName) {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigManager() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("newsaggregator\\src\\main\\java\\project\\oop\\datadealing\\datacollecting\\resources\\config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}