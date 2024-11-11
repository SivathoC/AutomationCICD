package sivathoconjwaacedemy.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sivathoconjwaacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver); //Passing varaible from child to parent
		this.driver = driver;
		
		/* PageFactory
		 * - Created a method called initElements 
		 * 		> This means that will trigger initializing all the elements
		 */
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	
	/* Page Factory
	 * - is a design pattern
	 * - Helps reduce the syntax for creating a web element
	 * 		> Syntax @FindBy(attribute = value)
	 * - driver.findElement(By.Attribute("Value")) will be constructed at runtime.
	 * - notice that driver is not present you will find this in the constructor
	 */
	@FindBy(id="userEmail")
	private WebElement userEmail;
	
	@FindBy(id="userPassword")
	private WebElement userPassword;
	
	@FindBy(id="login")
	private WebElement login;
	
	@FindBy(css= "[class*='flyInOut']")
	private WebElement errorMessage;
	
	public void goToLandingPage()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage()
	{ 
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	//Action Methods
	public ProductCatalogue loginApplication(String email, String password) 
	{		
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		login.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
}
