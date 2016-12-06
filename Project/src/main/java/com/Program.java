package com;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;

public class Program {

    private WebDriver driver;
    private BrowserType browserType;

    public Program(BrowserType browserType) throws Exception {
        this.browserType = browserType;
        switch (browserType) {
            case FIREFOX:
                driver = createFirefoxDriver();
                break;
            case CHROME:
                driver = createChromeDriver();
                break;
            default:
                throw new Exception("Undefined WebDriver");
        }
        driver.manage().window().maximize();
    }

    private WebDriver createFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "/PATH_FOR_THE GECKO_DRIVER");
        return new FirefoxDriver();
    }

    private WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "PATH_FOR_THE_DRIVE_CHROMEDRIVER");
        return new ChromeDriver();
    }

    public File takeScreenshot(String siteUrl) throws IOException {
        driver.get(siteUrl);
        File res = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(res, new File(browserType + ".png"));
        return res;
    }

    public void close() {
        driver.quit();
    }
}
