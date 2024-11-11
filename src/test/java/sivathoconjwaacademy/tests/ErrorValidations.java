package sivathoconjwaacademy.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import sivathoconjwaacademy.TestComponents.BaseTest;
import sivathoconjwaacedemy.pageobjects.CartPage;
import sivathoconjwaacedemy.pageobjects.ProductCatalogue;
import sivathoconjwaacademy.TestComponents.Retry;
public class ErrorValidations extends BaseTest {

	private String userEmail = "sivathoc@gmail.com";
	private String userPassword = "Password1234$";
	private String productName = "ZARA COAT 3";

	@Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException {
		userPassword = "password1234$";

		landingPage.loginApplication(userEmail, userPassword);
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}

	@Test
	public void ProductErrorValidations() throws IOException {
		userEmail = "zintlec@gmail.com";
		userPassword = "Password1234$";
		String invalidProductName  = "ZARA COAT 33";
		ProductCatalogue productCatalogue = landingPage.loginApplication(userEmail, userPassword);

		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(invalidProductName);
		Assert.assertFalse(match);
	}
}
