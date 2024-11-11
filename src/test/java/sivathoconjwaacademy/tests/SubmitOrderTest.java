package sivathoconjwaacademy.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sivathoconjwaacademy.TestComponents.BaseTest;
import sivathoconjwaacedemy.pageobjects.CartPage;
import sivathoconjwaacedemy.pageobjects.CheckoutPage;
import sivathoconjwaacedemy.pageobjects.ConfirmationPage;
import sivathoconjwaacedemy.pageobjects.OrderPage;
import sivathoconjwaacedemy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	private String country = "india";

	@Test(dataProvider = "getData", groups = "Purchase")
	public void submitOrder(HashMap<String, String> input) throws IOException {
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("userEmail"),
				input.get("userPassword"));

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);

		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(country);
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	// To verify ZARA COAT 3 is displaying in orders page
	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHostoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("sivathoc@gmail.com", "Password1234$");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Boolean match = ordersPage.verifyOrderDisplay("ZARA COAT 3");
		Assert.assertTrue(match);
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\sivathoconjwaacademy\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

	/*
	 * HashMap<String, String> map = new HashMap<String, String>();
	 * map.put("userEmail", "sivathoc@gmail.com");
	 * map.put("userPassword","Password1234$");
	 * map.put("productName", "ZARA COAT 3");
	 * 
	 * HashMap<String, String> map1 = new HashMap<String, String>();
	 * map1.put("userEmail", "zintlec@gmail.com"); 
	 * map1.put("userPassword", "Password1234$");
	 * map1.put("productName", "ADIDAS ORIGINAL");
	 */	

}
