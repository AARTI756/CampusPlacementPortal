package com.college.placement.selenium;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentSeleniumTest extends BaseSeleniumTest {

    private static String testEmail = "selenium-" + UUID.randomUUID().toString().substring(0,8) + "@example.com";
    private static String testName = "Selenium Test Student";

    @Test
    @Order(1)
    public void testCreateStudent() {
        driver.get(baseUrl + "/students");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add Student")));
        addBtn.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        
        driver.findElement(By.id("name")).sendKeys(testName);
        driver.findElement(By.id("email")).sendKeys(testEmail);
        driver.findElement(By.id("department")).sendKeys("Computer Engineering");
        driver.findElement(By.id("cgpa")).sendKeys("8.8");

        driver.findElement(By.cssSelector("form")).submit();

        // Wait for redirect to students page
        wait.until(ExpectedConditions.urlContains("/students"));
        
        // Verify student appears
        String pageText = driver.findElement(By.tagName("body")).getText();
        assertTrue(pageText.contains(testEmail), "Newly created student email should appear in list");
        assertTrue(pageText.contains(testName), "Newly created student name should appear in list");
    }

    @Test
    @Order(2)
    public void testStudentSearch() {
        driver.get(baseUrl + "/students");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchBox.sendKeys(testEmail);
        
        searchBox.submit();
        
        wait.until(ExpectedConditions.urlContains("keyword="));
        
        // Verify matching student appears
        String pageText = driver.findElement(By.tagName("body")).getText();
        assertTrue(pageText.contains(testEmail), "Search results should contain the test student");
        
        // Unrelated should not be there (assuming unique testEmail isolates it well)
        WebElement table = driver.findElement(By.tagName("tbody"));
        assertNotNull(table);
        assertTrue(table.findElements(By.tagName("tr")).size() >= 1, "Should have at least 1 row");
    }

    @Test
    @Order(3)
    public void testDeleteStudent() {
        driver.get(baseUrl + "/students");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Search first to isolate
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchBox.sendKeys(testEmail);
        searchBox.submit();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), '" + testEmail + "')]")));
        
        // Find the delete button in the row containing the testEmail and submit its form
        WebElement deleteBtn = driver.findElement(By.xpath("//tr[td[contains(text(), '" + testEmail + "')]]//button[contains(text(), 'Delete')]"));
        deleteBtn.submit();
        
        // Handle confirmation alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        
        wait.until(ExpectedConditions.urlContains("/students"));
        
        // Verify deletion
        String pageText = driver.findElement(By.tagName("body")).getText();
        assertFalse(pageText.contains(testEmail), "Deleted student should no longer appear");
    }
}
