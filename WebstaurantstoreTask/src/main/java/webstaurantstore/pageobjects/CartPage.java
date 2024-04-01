package webstaurantstore.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import webstaurantstore.Utilities.AbstractComponent;

public class CartPage extends AbstractComponent{
	WebDriver driver;
	//Constructor
	public CartPage(WebDriver driver)
	{
		super(driver);
		//Initializing WebDriver
		this.driver = driver;
		PageFactory.initElements(driver,this);
			
	}
	//Instantiating the LandingPage class object
	LandingPage nobj = new LandingPage(driver);	
	
	//Creating Web elements
	@FindBy(xpath="//span[@class='itemDescription description overflow-visible']/a")
	 WebElement CartItemname;	
	@FindBy(id="react_0HN2F3HQ1I9CD")
	 WebElement CartHeader;	
	@FindBy(xpath="//button[contains(text(),'Empty Cart')]")
	 WebElement EmptyCartButoon;	
	@FindBy(xpath="//footer[@data-testid='modal-footer']/button[contains(text(),'Empty')]")
	 WebElement EmptyCartOKButoon;	
	@FindBy(xpath="//*[@id='cartItemCountSpan']")
	 WebElement CartCount;
	
	//Method to check if the item added to cart ornot
	public boolean ItemAddCheck(String ProductTitle) {
		//String ProductTitle = nobj.getproductTitle();		
		String cartitemName = CartItemname.getAttribute("title").toString();
		if(cartitemName.contains(ProductTitle))
		{
			return true;
		}else {
			return false;
		}	
	}
	//Method to check whether the Cart is empty or not
	public void emptycart() {
		waitforElementtodisplay(By.xpath("//button[contains(text(),'Empty Cart')]"));		
		try {			
			EmptyCartButoon.click();
			waitforElementtodisplay(By.xpath("//footer[@data-testid='modal-footer']/button[contains(text(),'Empty')]"));
			EmptyCartOKButoon.click();
			
		}catch(Exception e) {
			System.out.println("Step 5: Exception in clearing the Cart \n" + e);
		}		
		waitforElementtodisplay(By.cssSelector(".empty-cart__text"));					
	}
	//Method to check the count is 0 or not
	public void cartcountcheck()
	{
		//checking for empty cart
				String itemcount = CartCount.getText();
				Assert.assertEquals(itemcount,"0");	
				System.out.println("Step 5: \"Empty cart\" step is successfull \n");
	}	
	
	
}
