package ru.avito.m;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class FirstTest {

    public ChromeDriver driver;

    @Before
    public void setUp() {
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void testOfTabs(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/tabs/button(lines)']")).click();
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/lines/trigger']")).click();
        driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/lines/station']")).click();
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/tabs/button(stations)']")).click();
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/tabs/button(lines)']")).click();
        assertEquals("true", driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/lines/station']")).getAttribute("aria-checked"));
    }

    @Test
    public void testOfFloatingButton(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        try {
            driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/apply']")).click();
        }
        catch (ElementClickInterceptedException e){
            assertFalse(false);
        }
        int numberOfStations = 0;
        for (int i = 1; i < 4; i++) {
            driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']:nth-child(" + i + ")")).click();
            numberOfStations = i;
        }
        String textFromFilter  = driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/apply']")).getText();
        String text = textFromFilter.substring(textFromFilter.indexOf(' ') + 1, textFromFilter.indexOf(' ', textFromFilter.indexOf(' ') + 1));
        int numberOfStationsFromFilter = Integer.parseInt(text);
        assertEquals(numberOfStationsFromFilter, numberOfStations);
    }

    @Test
    public void testOfStationsDouble(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']")).click();
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/tabs/button(lines)']")).click();
        try {
            driver.findElement(By.cssSelector(("div[data-marker='metro-select-dialog/lines/expanded']")));
            fail();
        }
        catch (NoSuchElementException e){
            assertTrue(true);
        }
        driver.findElement(By.xpath("//span[contains(text(),'Некрасовская')]")).click();
        String station = driver.findElement(By.xpath("//span[contains(text(),'Авиамоторная')] /.. /../../label")).getAttribute("aria-checked");
        assertEquals("true", station);

    }

    @Test
    public void testOfSearchLine(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        driver.findElement(By.cssSelector("input[data-marker='metro-select-dialog/search']")).sendKeys("Автозаводская");
        driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']")).click();
        String isFocused = driver.findElement(By.cssSelector("label[class=\"css-1qbrq45\"")).getAttribute("data-state");
        String StrSearch = (driver.findElement(By.cssSelector("input[data-marker='metro-select-dialog/search']")).getText());
        assertEquals(" ", isFocused);
        assertEquals("", StrSearch);
    }

    @Test
    public void testOfResetButton(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        assertFalse(driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/reset']")).isEnabled());
        driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']")).click();
        assertTrue(driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/reset']")).isEnabled());
        driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']")).click();
        assertFalse(driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/reset']")).isEnabled());
    }

    @Test
    public void testOfSelectedStations(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        int numberOfStations = 0;
        for (int i = 1; i < 4; i++) {
            driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']:nth-child(" + i + ")")).click();
            numberOfStations = i;
        }
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/apply']")).click();
        String textFromFilter  = driver.findElement(By.cssSelector("span[data-marker='metro-select/value']")).getText();
        String text = textFromFilter.substring(textFromFilter.indexOf(' ') + 1, textFromFilter.indexOf(' ', textFromFilter.indexOf(' ') + 1));
        int numberOfStationsFromFilter = Integer.parseInt(text);
        assertEquals(numberOfStationsFromFilter, numberOfStations);
    }

    /*
    Последний тест проверяет корректность отображения фильтра на экране уточнить при выборе всего лишь одной станции.
    Тест не будет пройден, так как в реализации при выборе одной станции отображается не "Выбрано n станций", а название выбранной станции.
     */
    @Test
    public void testOfSelectedStationsWithOne(){
        driver.get("https://m.avito.ru/moskva/kommercheskaya_nedvizhimost?cd=1&searchForm=true");
        driver.findElement(By.cssSelector("div[data-marker='metro-select/withoutValue']")).click();
        int numberOfStations = 1;
        driver.findElement(By.cssSelector("label[data-marker='metro-select-dialog/stations/item']:nth-child(1)")).click();
        driver.findElement(By.cssSelector("button[data-marker='metro-select-dialog/apply']")).click();
        String textFromFilter  = driver.findElement(By.cssSelector("span[data-marker='metro-select/value']")).getText();
        assertEquals("Выбрано", textFromFilter.substring(0, 7));
        System.out.println(textFromFilter.substring(0, 7));
        String text = textFromFilter.substring(textFromFilter.indexOf(' ') + 1, textFromFilter.indexOf(' ', textFromFilter.indexOf(' ') + 1));
        int numberOfStationsFromFilter = 0;
        try {
            numberOfStationsFromFilter = Integer.parseInt(text);
        }
        catch (NumberFormatException e){
            fail();
        }
        assertEquals(numberOfStationsFromFilter, numberOfStations);
    }

    @After
    public void close(){
        driver.quit();
    }
}
