package sivathoconjwaacademy.setpDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sivathoconjwaacademy.TestComponents.BaseTest;
import sivathoconjwaacedemy.pageobjects.CartPage;
import sivathoconjwaacedemy.pageobjects.CheckoutPage;
import sivathoconjwaacedemy.pageobjects.ConfirmationPage;
import sivathoconjwaacedemy.pageobjects.LandingPage;
import sivathoconjwaacedemy.pageobjects.ProductCatalogue;

public class StepDefinitionImplementation extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CartPage cartPage;
	public CheckoutPage checkoutPage;
	ConfirmationPage confirmationPage;
	
	private String country = "india";

	/*
	 * Pre-requisite > Background: Given I landed on Ecommerce Page - This step in
	 * the feature does not have any outline data and static data
	 */
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}
	/*
	 * Test Case - parameters: we can not be written (can be a name or any variable)
	 * > The step definition has to know that this is something where it is
	 * parameterized and the value is coming at runtime > The value can be anything
	 * (string, integer) and we don't as it is defined at runtime. > This means we
	 * will need to add a regular expression that represent and character or value *
	 * syntax: "(.+)" - The parameters will be passed from the feature files
	 * parameter and the values are feed back to the step definition - it is
	 * important the values are kept generic > If the values are hard coded the step
	 * definition * Will try match the (string, integer) ~ If another user adds
	 * different value then the method will not be applicable *
	 * Example @Given("Logged in with username <sivathoc@gmail.com> and password <password1234$>"
	 * )
	 * 
	 * - When adding regular expression like this we have to tell your string that
	 * this is a regular expression > To represent a regular expression - Start with
	 * a Caret symbol: ^ - End with a dollar symbol: $ > The above is the regex
	 * (regular expression)
	 */
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password) 
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to cart$")
	public void I_add_product_to_cart(String productName)
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@And("^Checkout (.+) and submit the order$")
	public void Checkout_product_and_submit_the_order(String productName)
	{
		cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);

		checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(country);
		confirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} confirmation message is displayed")
	public void Verify_confirmation_message_is_displayed(String message)
	{
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase(message));
		driver.close();
	}
	
	@Then("^\"([^\"]*)\" message is displayed")
	public void LoginValidationError_message_is_displayed(String strArg)throws Throwable
	{
		Assert.assertEquals(strArg, landingPage.getErrorMessage());
		driver.close();
	}
}


/* "^\"([^\"]*)\"
 * 
 */
