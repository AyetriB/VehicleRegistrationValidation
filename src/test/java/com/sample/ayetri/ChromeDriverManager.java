package com.sample.ayetri;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

// This is a singleton Chromedriver class
public class ChromeDriverManager {
    private static ChromeDriverManager instance;
    private WebDriver driver;
    private ChromeDriverManager() {
        WebDriverManager.chromedriver().setup();
        driver = new org.openqa.selenium.chrome.ChromeDriver();
        driver.manage().window().maximize();
    }
    public static ChromeDriverManager getManagerInstance() {
        if (instance == null) {
            synchronized (ChromeDriverManager.class) {
                if (instance == null) {
                    instance = new ChromeDriverManager();
                }
            }
        }
        return instance;
    }
    public WebDriver getDriver() {
        return driver;
    }
}
