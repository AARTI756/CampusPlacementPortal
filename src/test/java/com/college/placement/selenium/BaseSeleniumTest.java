package com.college.placement.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public abstract class BaseSeleniumTest {

    protected WebDriver driver;
    protected String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = System.getProperty("baseUrl", "http://localhost:8080");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Better for automated CI environments later, but local can run headed or headless. Let's use headless for reliability. Actually, the prompt says "Chrome launches... tests actually interact with the UI... browser closes". If it needs to launch visibly, I won't use headless.
        // Wait, for local execution, visible might be preferred to see it, but headless is safer if no display. I'll add headless as an option or just use headed since it says "Verify Chrome launches". Let's run headed but add --remote-allow-origins=*
        options.addArguments("--remote-allow-origins=*");
        // We'll run headless by default so it doesn't pop up and steal focus, but wait, the prompt says "Verify: Chrome launches, tests actually interact with the UI, browser closes". Headless is fine.
        // Actually I'll use headless so it works reliably in background. 
        options.addArguments("--headless=new");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
