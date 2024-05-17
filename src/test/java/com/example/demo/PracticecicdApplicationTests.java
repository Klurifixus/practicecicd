package com.example.demo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PracticecicdApplicationTests {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeEach
	void setup() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		driver.get("https://avrf.se");

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement cookieAcceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".v-btn.v-btn--outlined.theme--light.v-size--default.white--text")));
			cookieAcceptButton.click();
		} catch (Exception e) {
			System.out.println("Cookie Accept Button not found or not clickable.");
		}
	}

	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	void testLogo() {
		WebElement logo = driver.findElement(By.cssSelector("img[alt='Logotyp Alingsås kommun']"));

		String expectedAlt = "Logotyp Alingsås kommun";
		Assertions.assertEquals(expectedAlt, logo.getAttribute("alt"), "Logo alt text should match expected");
	}

	@Test
	public void testCookieBannerElements() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement cookieBanner = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.CookieBanner")));
			assertTrue(cookieBanner.isDisplayed(), "Cookie banner should be visible");

			WebElement learnMoreLink = driver.findElement(By.cssSelector("a[href='/sida/om-kakor-cookies']"));
			assertTrue(learnMoreLink.isDisplayed(), "Learn more link should be visible");
			assertEquals("Läs mer om hur vi hanterar kakor här", learnMoreLink.getText().trim(), "Verify link text");

			WebElement acceptCookiesButton = driver.findElement(By.xpath("//button[contains(.,'Jag accepterar cookies')]"));
			assertTrue(acceptCookiesButton.isDisplayed(), "Accept cookies button should be visible");
		} catch (NoSuchElementException e) {
			System.err.println("Element not found: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMetaDescription() {
		String expectedDescription = "Webbplats för Räddningstjänsten Alingsås-Vårgårda";
		WebElement metaTag = driver.findElement(By.xpath("//meta[@name='description']"));
		String actualDescription = metaTag.getAttribute("content");
		Assertions.assertEquals(expectedDescription, actualDescription, "Meta description should be correct");
	}

	@Test
	public void testMetaCharset() {
		WebElement charsetMetaTag = driver.findElement(By.xpath("//meta[@charset='utf-8']"));
		Assertions.assertNotNull(charsetMetaTag, "Charset meta tag should be present and set to UTF-8");
	}

	@Test
	public void testViewportMeta() {
		WebElement viewportMetaTag = driver.findElement(By.xpath("//meta[@name='viewport']"));
		String expectedViewportContent = "width=device-width, initial-scale=1";
		String actualViewportContent = viewportMetaTag.getAttribute("content");
		Assertions.assertEquals(expectedViewportContent, actualViewportContent, "Viewport settings should be correct");
	}

	@Test
	public void testFaviconLink() {
		WebElement faviconLink = driver.findElement(By.xpath("//link[@rel='icon']"));
		String faviconHref = faviconLink.getAttribute("href");
		Assertions.assertTrue(faviconHref.contains("favicon.ico"), "Favicon link should point to 'favicon.ico'");
	}

	@Test
	public void testCssLoad() {
		WebElement cssLink = driver.findElement(By.xpath("//link[@rel='stylesheet' and contains(@href, 'Roboto')]"));
		Assertions.assertNotNull(cssLink, "CSS link for Roboto font should be loaded");
	}

}
