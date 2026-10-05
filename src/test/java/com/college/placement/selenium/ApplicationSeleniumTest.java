package com.college.placement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApplicationSeleniumTest extends BaseSeleniumTest {

    @Test
    public void testApplicationStatusWorkflow() {
        driver.get(baseUrl + "/applications");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for page load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2")));
        assertTrue(driver.findElement(By.tagName("h2")).getText().contains("Applications"), "Page should be Applications");

        // Verify status filter is present
        WebElement filterSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[name='status']")));
        assertNotNull(filterSelect, "Status filter dropdown should exist");
        
        Select select = new Select(filterSelect);
        
        // Select an available status (e.g., SELECTED)
        select.selectByValue("SELECTED");
        
        // Form auto-submits on change, wait for URL to reflect filter
        wait.until(ExpectedConditions.urlContains("status=SELECTED"));
        
        // Verify the dropdown has the correct option selected now
        WebElement newFilterSelect = driver.findElement(By.cssSelector("select[name='status']"));
        Select newSelect = new Select(newFilterSelect);
        assertTrue(newSelect.getFirstSelectedOption().getText().toUpperCase().contains("SELECTED"), 
            "Filter should reflect the selected status");
    }
}
