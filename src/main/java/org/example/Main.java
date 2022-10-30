package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.SystemUtils;
import org.example.helper.MessageTemplate;
import org.example.helper.PropertiesCache;
import org.example.helper.RecipientsLoader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
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
        String UserHomeDirectoryPath;
        if (SystemUtils.IS_OS_LINUX) {
            UserHomeDirectoryPath = System.getProperty("user.home") + "/.config/google-chrome/whatsapp-sender";
        } else if (SystemUtils.IS_OS_WINDOWS) {
            UserHomeDirectoryPath = System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/whatsapp-sender";
        } else if (SystemUtils.IS_OS_MAC) {
            UserHomeDirectoryPath = System.getProperty("user.home") + "/Library/Application Support/Google/Chrome/whatsapp-sender";
        } else {
            System.out.println("ERROR, OS is not compatible");
            return;
        }
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=" + UserHomeDirectoryPath);
        ChromeDriver driver = new ChromeDriver(options);

        System.out.println("Opening Whatsapp Web....");
        driver.get("https://web.whatsapp.com/");
        System.out.println("Scanning the barcode....");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("side")));
        System.out.println("Scanned the Barcode and loaded the web WhatsApp....");

        String resourcePath = String.valueOf(Main.class.getResource("Main.class"));
        boolean isJar = resourcePath.startsWith("jar:");


        List<String[]> recipientsList;
        try {
            recipientsList = new RecipientsLoader(isJar, "recipients.csv").readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> message;
        try {
            message = new MessageTemplate(isJar, "message_template.txt").readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (String[] strings : recipientsList) {
            String recipientName = strings[0];
            String recipientNumber = strings[1];
            message.set(0, "Halo " + recipientName + ",");
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

            Actions actions = new Actions(driver);
            for (String line : message) {
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