package com.thanos.springboot.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Remember to start web driver before run this class<br/>
 * <a href="https://dzone.com/articles/selenium-with-java-google-search">source</a><br/>
 * <a href=
 * "http://web.archive.org/web/20051219043731/http://archive.ncsa.uiuc.edu/SDG/Software/Mosaic/Demo/url-primer.html">
 * <i>Chrome Web Driver</i></a>
 *
 * @author peiheng.zph created on 18/2/24 上午11:26
 * @version 1.0
 */
public class AutoTestApplication {

  public static void main(String[] args) throws MalformedURLException {
    // create a Chrome Web Driver
    URL local = new URL("http://localhost:9515");
    WebDriver driver = new RemoteWebDriver(local, DesiredCapabilities.chrome());

    // open the browser and go to open google.com
    driver.get("https://www.google.com");
    driver.findElement(By.id("lst-ib")).sendKeys("Selenium");
    driver.findElement(By.name("btnK")).click();
    driver.manage().window().maximize();


    // get the number of pages
    int size = driver.findElements(By.cssSelector("[valign='top'] > td")).size();
    for (int j = 1; j < size; j++) {
      if (j > 1) {// we don't need to navigate to the first page
        driver.findElement(By.cssSelector("[aria-label='Page " + j + "']")).click(); // navigate to page number j
      }
      String pagesearch = driver.getCurrentUrl();
      List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
      System.out.println(findElements.size());
      for (int i = 0; i < findElements.size(); i++) {
        findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
        findElements.get(i).click();
        driver.navigate().to(pagesearch);
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        //Scroll vertically downward by 250 pixels
        jse.executeScript("window.scrollBy(0,250)", "");
      }
    }
  }
}
