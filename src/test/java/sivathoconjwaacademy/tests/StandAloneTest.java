package sivathoconjwaacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// New comments have been added
		String userEmail = "sivathoc@gmail.com";
		String userPassword = "Password1234$";
		String productName = "ZARA COAT 3";

		WebDriver driver = setUpBrowser();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // avoiding synchronization issue by
																				// using explicit where applicable
		Login(driver, userEmail, userPassword);
		List<WebElement> productListItems = getProductListItems(driver, wait);
		iterateListItemsAndItemToCart(productListItems, productName);
		waitForProductAddedTochart(driver, wait);
		viewItemsInCartAndCcheckOut(driver, productName, wait);
		placeOrder(driver, wait);
	}

	public static WebDriver setUpBrowser() {

		String domainHomeURL = "https://rahulshettyacademy.com/client";

		WebDriverManager.chromedriver().setup(); // Chrome Driver will be automatically downloaded into you system based
													// on ChromeDriver version.
		WebDriver driver = new ChromeDriver(); // WebDriver Object.
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(domainHomeURL);
		return driver;
	}

	public static void Login(WebDriver driver, String userEmail, String userPassword) {
		driver.findElement(By.id("userEmail")).sendKeys(userEmail);
		driver.findElement(By.id("userPassword")).sendKeys(userPassword);
		driver.findElement(By.id("login")).click();
	}

	public static List<WebElement> getProductListItems(WebDriver driver, WebDriverWait wait) {
		// products
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		return products;
	}

	public static void iterateListItemsAndItemToCart(List<WebElement> productListItems, String productName) {

		WebElement productItem = productListItems.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		productItem.findElement(By.cssSelector(".card-body button:last-of-type")).click();

	}

	public static void waitForProductAddedTochart(WebDriver driver, WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		// //Step is taking a long time which will cause performance issues
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();		
	}

	public static void viewItemsInCartAndCcheckOut(WebDriver driver, String productName, WebDriverWait wait) {
				
		List<WebElement> itemsInCart = driver.findElements(By.cssSelector(".cartSection h3"));

		Boolean match = itemsInCart.stream()
				.anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
	}

	public static void placeOrder(WebDriver driver, WebDriverWait wait) {
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india")
		.build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
	}
}