package sivathoconjwaacedemy.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sivathoconjwaacademy.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent{

	WebDriver driver;
	
	public OrderPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css =".totalRow button")
	WebElement checkoutElement;
	
	@FindBy(css ="tr td:nth-child(3)")
	private List<WebElement> ordersProductNames;
	
	public Boolean verifyOrderDisplay(String productName)
	{
		Boolean match = ordersProductNames.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
}
