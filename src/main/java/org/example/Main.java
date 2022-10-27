package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.helper.MessageTemplate;
import org.example.helper.RecipientsLoader;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.SHIFT;


public class Main {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("user-data-dir=C:/Users/KornedBeef/tmp");
        options.addArguments("user-data-dir=C:/Users/KornedBeef/AppData/Local/Google/Chrome/User Data");
        ChromeDriver driver = new ChromeDriver(options);

        System.out.println("Opening Whatsapp Web....");
        driver.get("https://web.whatsapp.com/");
        System.out.println("Scanning the barcode....");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("side")));
        System.out.println("Scanned the Barcode and loaded the web WhatsApp....");

//        List<String> SendToList = new ArrayList<>();
//        SendToList.add("6282113084939");
//        SendToList.add("6289658896504");
//        SendToList.add("628119202304");
//        SendToList.add("628118692304");
        List<String[]> recipientsList;
        try {
            recipientsList = RecipientsLoader.readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> messageTemplate;
        try {
            messageTemplate = MessageTemplate.readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (String[] strings : recipientsList) {
            String recipientName = strings[0];
            String recipientNumber = strings[1];
            messageTemplate.set(0, "Halo " + recipientName + ",");
            System.out.println("Opening new chat...");
            driver.get("https://web.whatsapp.com/send?phone=" + recipientNumber);

            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                System.out.println("Leave Page Alert accepted....");
            } catch (NoAlertPresentException e) {
                System.out.println("Leave Page Alert not detected....");
            }

            driver.switchTo().activeElement().sendKeys("test");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@title=\"Type a message\"]")));

//            new Actions(driver)
//                    .sendKeys(message)
//                    .sendKeys(ENTER)
//                    .perform();

            Actions actions = new Actions(driver);
            for (String line : messageTemplate) {
                actions.sendKeys(line)
                        .keyDown(SHIFT)
                        .sendKeys(ENTER)
                        .keyUp(SHIFT)
                        .build()
                        .perform();
            }
            actions.sendKeys(ENTER).perform();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Typing the message text here....");

        }
    }
}