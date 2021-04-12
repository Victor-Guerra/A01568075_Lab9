package mx.tec.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestHtml2ApplicationTests {
	private static WebDriver driver;
	
	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "/home/victorg/Downloads/chromedriver_89.0.4389.32");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}
	
	@Test
	public void givenAClient_whenEnteringAutomationPractice_thenPageTitleIsCorrect() throws Exception {
		// When
		driver.get("http://automationpractice.com/index.php");
		String title = driver.getTitle();
		
		//Then
		assertEquals("My Store", title);
	}
	
	@Test
	public void givenAClient_whenEnteringLoginCredentials_thenAuthenticationPageIsDisplayed()
	  throws Exception {
		// When
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568075@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("123abc");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		String title = driver.getTitle();
		
		// Then
		assertEquals("My Account - My Store", title);
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed()
	  throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568075@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("asdfgh");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		String title = driver.getTitle();
			
		assertEquals("Login - My Store", title);
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed()
	  throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568075@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("asdfgh");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		WebElement alertWarning = driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li"));
			
		assertEquals("Authentication failed", alertWarning.getText());
	}
	
	@Test
	public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsDisplayed()
	  throws Exception {

		driver.get("http://automationpractice.com/index.php");
		WebElement searchField = driver.findElement(By.id("search_query_top"));
		searchField.sendKeys("obo");
		WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default button-search']"));
		searchButton.click();
		WebElement alertWarning = driver.findElement(By.xpath("//p[@class='alert alert-warning']"));
		
		assertEquals("No results were found for your search \"obo\"", alertWarning.getText());
	}
	
	@Test
	public void givenAClient_whenSearchingEmptyString_thenPleseEnterDisplayed()
	  throws Exception {
		driver.get("http://automationpractice.com/index.php");
		WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default button-search']"));
		searchButton.click();
		WebElement alertWarning = driver.findElement(By.xpath("//p[@class='alert alert-warning']"));
		
		assertEquals("Please enter a search keyword", alertWarning.getText());
	}
	
	@Test
	public void givenAClient_whenSigningOut_thenAuthenticationPageIsDisplayed()
	  throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01568075@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("123abc");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		
		WebElement logoutButton = driver.findElement(By.xpath("//a[@class='logout']"));
		logoutButton.click();
		
		String title = driver.getTitle();
			
		assertEquals("Login - My Store", title);
	}
	
}
