package webstaurantstore.pageobjects;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webstaurantstore.Utilities.AbstractComponent;

public class LandingPage extends AbstractComponent{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		//Initializing WebDriver
		this.driver = driver;
		PageFactory.initElements(driver,this);
			
	}
	
	@FindBy(xpath="//*[@data-testid='logo'][@aria-label='Homepage, WebstaurantStore']")
	 WebElement Logo;
	
	@FindBy(id="searchval")
	 WebElement searchTextBox;
	
	@FindBy(xpath="//div[contains(@class,'hidden flex')]//button[@value='Search'][@type='submit']")
	 WebElement magnifyingGlass;
	
	@FindBy(xpath="//li[contains(@class,'inline-block')]")
	 WebElement NavigationItems;
	
	@FindBy(id="product_listing")
	 WebElement ResultTable;
	
	@FindBy(id="paging")
	 WebElement NavigationMenu;
	
	@FindBy(xpath="//li[contains(@class,'inline-block')]")
	 List<WebElement> Pageslist;		
	
	@FindBy(xpath="//a[contains(@class,'rounded-r-md')]")
	 WebElement LastPageLink;
	
	@FindBy(xpath="//a[contains(text(),'View Cart')]")
	 WebElement PopupViewCart;
	
	@FindBy(xpath="//a[@data-testid='cart-button']")
	 WebElement MainViewCart;
		
	
	public int productListSize; 
	public List<String> productnames = new ArrayList<>();
	public String productTitle;
	
	public  String getproductTitle()
	{
		return productTitle;		
	}
	
	public void setproductTitle(String ProductTitle)
	{
		productTitle = ProductTitle;
	}
	
	
	 
	public void LaunchAPP() {
		driver.get("https://www.webstaurantstore.com/");
		customWait(Logo);
		try
		{	
			System.out.println("Webstaurants Application launched successfully");
		}catch(Exception e) {
			System.out.println("Webstaurants Application launche Failed:" + e);
		}
	}
	
	public void mastersearch(String searchVal) {
		searchTextBox.sendKeys(searchVal);
		magnifyingGlass.click();
		customWait(Logo);
		System.out.println("Search for \"Stainless Work Table\" step is successfull");
	}
	
	public int pagecount() {
		int NoOfPages=1;
		try {
		NoOfPages = Pageslist.size();		
		}catch(Exception e) {
			
		}
		return NoOfPages;
	}
	
	public boolean checkTitleforTable(int noofpages)
	{
		int temp=0;
		productListSize=0;
		boolean Tableflag=false;	
		do {
			List<WebElement> itemsList= driver.findElements(By.xpath("//span[@data-testid='itemDescription']"));
			productListSize  = itemsList.size();
						
			for(WebElement Product:itemsList) 
			{
				productTitle = Product.getText();
				if(!productTitle.contains("Table"))
				{
					Tableflag=true;							
					//System.out.println("Failure Case: Product Does not have Table in title: "+productTitle); //DEBUG only
				}
				//collecting all list items
				productnames.add(productTitle);
			}
			if(LastPageLink.isEnabled()) {
				LastPageLink.click();
				temp++;
			}else {
				break;
			}
			
		}while(noofpages>=temp);
		
		
		//checking for items NOT having Table in the title					
		if(!Tableflag){
			System.out.println("\"Check the search results ensuring every products has 'Table' in its title\" step is successfull");
		}else {
			System.out.println("\"Check the search results ensuring every products has 'Table' in its title\" step is FAILED");
		}
		return Tableflag;
	}
	
	public void addToCart() {
		ListIterator revItr = productnames.listIterator(productnames.size());
		
		while(revItr.hasPrevious()) {
			String ProdName =revItr.previous().toString();
			try {
				String AddtoCartXpath = "//*[@data-testid='itemDescription'][contains(text(),'"+ProdName +"')]//following::input[contains(@value,'Add to Cart')]";					
				driver.findElement(By.xpath(AddtoCartXpath)).click();
				System.out.println("\"Add the last of found items to cart\" step is successfull");
				setproductTitle(ProdName);
				break;
				
			}catch(Exception e) {
				System.out.println("Warning: Item is not available for \"Add to Cart\", Checking previous Item");
			}
			
		}
				
		waitforElementtodisplay(By.cssSelector(".notification__action"));		
		
		//Navigating to cart
		try {
			PopupViewCart.click();
			System.out.println("View Cart Click Successfull");
		}catch(Exception e) {
			MainViewCart.click();
			System.out.println("View Cart Click Successfull");
		}		
		productListSize=0;
	}
	
	
	
	
	
}
