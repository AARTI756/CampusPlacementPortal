package com.college.placement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DashboardSeleniumTest extends BaseSeleniumTest {

    @Test
    public void testDashboardLoads() {
        driver.get(baseUrl + "/");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2")));
        
        // Verify title/header contains "Placement Portal"
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Placement Portal") || driver.getTitle().contains("Placement Portal"), 
                "Page should contain 'Placement Portal'");

        // Verify dashboard content is visible (we can check for cards/stats)
        List<WebElement> cards = driver.findElements(By.className("card"));
        assertFalse(cards.isEmpty(), "Dashboard should have summary cards");

        // Verify navigation contains expected links
        WebElement nav = driver.findElement(By.tagName("nav"));
        String navText = nav.getAttribute("textContent");
        assertTrue(navText.contains("Students"), "Nav should contain Students");
        assertTrue(navText.contains("Companies"), "Nav should contain Companies");
        assertTrue(navText.contains("Placement Drives"), "Nav should contain Placement Drives");
        assertTrue(navText.contains("Applications"), "Nav should contain Applications");
    }
}
