package com.example.daegubusapi.service;

import com.example.daegubusapi.model.Bus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScraperService {
    WebDriver webDriver;

    public static List<Bus> retrieveBusesByScraping(String busStopName) {
        System.out.println("Scraping Start!");
        String url = String.format("https://businfo.daegu.go.kr:8095/dbms_web/map?searchText=%s", busStopName);


        // Set the path to the ChromeDriver executable
       // System.setProperty("webdriver.edge.driver", "edgedriver/msedgedriver");
        //System.setProperty("webdriver.edge.driver", "edgedriver/msedgedriverLinux");

        // Enable headless mode
        //EdgeOptions options = new EdgeOptions();
        //options.addArguments("headless");

        //options.addArguments("disable-gpu");
        WebDriverManager.chromedriver().setup();


//        WebDriver driver = new EdgeDriver();
        WebDriver driver = new ChromeDriver();


        // Navigate to the URL of the dynamic page
        driver.get(url);

        // Wait for some time to allow dynamic content to load
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));//2에서 10으로...
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // Get the text of the document
                String str = Jsoup.parse(driver.getPageSource()).text();
                System.out.println("☆PageSource를 성공적으로 읽었습니다.");
               // System.out.println(str);
                //System.out.println("----");
                int length = str.length();

                // Check if the text length is at least 500
                if(length>=500){
                    System.out.println("☆성공적으로 데이터를 수집 했습니다!");
                }else {
                    System.out.println("☆아직 목표하는 데이터를 수집 못 했습니다. wait...");
                }
                //System.out.println(length);
                return length >= 500;
            }
        });


        // Parse the page source using Jsoup
        Document document = Jsoup.parse(driver.getPageSource());
        System.out.println("☆parsing 완료.");
        //System.out.println(document.text());

        // Use Jsoup to extract information from the parsed document
        String title = document.title();
        //System.out.println("Page Title: " + title);

        // You can use Jsoup selectors to extract specific elements
        // For example, extracting text from an element with id="exampleElement"
        Elements selectRouteElements = document.select("[id^=selectRoute]");

        Elements real = new Elements();

        for (Element selectRouteElement : selectRouteElements) {
            String text = selectRouteElement.select("div > a > div > h6").text();
            if (!(text.contains(" ")) && !text.isEmpty()) {
                real.add(selectRouteElement);
            }
        }


        // Elements elements = document.select("h6.mb-0");
        // Loop through each selectRoute element
        List<Bus> buses = new ArrayList<>();
        for (Element selectRouteElement : real) {
            // Extract information from each selectRoute element
            String routeName = selectRouteElement.select("div > a > div > h6").text();
            String remainingBusStops = selectRouteElement.select("ul > li:nth-child(1) > div:nth-child(2) > small").text();
            String remainingTime = selectRouteElement.select("ul > li:nth-child(1) > div:nth-child(3) > span").text();

            if (remainingBusStops.isEmpty()) {
                if (remainingTime.equals("전")) {
                    remainingBusStops = "1";
                }
                if (remainingTime.equals("전전")) {
                    remainingBusStops = "2";
                }
            }

            buses.add(new Bus(routeName, extractNumber(remainingBusStops)));

        }
        System.out.println("☆전처리 완료. ");
        System.out.println("☆response 성공...끝");
        return buses;
    }

    private static int extractNumber(String input) {
        // 숫자를 제외한 모든 문자를 제거
        String numberOnly = input.replaceAll("[^0-9]", "");

        // 문자열이 비어있는지 확인 후 정수로 변환
        if (!numberOnly.isEmpty()) {
            return Integer.parseInt(numberOnly);
        } else {
            // 숫자가 없는 경우 0 또는 다른 값을 반환하거나 예외를 처리할 수 있음
            return 0;
        }
    }

}
