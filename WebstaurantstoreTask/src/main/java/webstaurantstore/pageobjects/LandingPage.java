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
	//Constructor
	public LandingPage(WebDriver driver)
	{
		super(driver);
		//Initializing WebDriver
		this.driver = driver;
		PageFactory.initElements(driver,this);
			
	}
	
	//Creating web elements
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
		
	//variables
	public int productListSize; 
	public List<String> productnames = new ArrayList<>();
	public String productTitle;	
	public  String getproductTitle()
	{return productTitle;}
	public void setproductTitle(String ProductTitle)
	{productTitle = ProductTitle;}
	public static final String ANSI_RESET = "\u001B[0m"; 
	public static final String ANSI_YELLOW = "\u001B[33m";  
	public static final String ANSI_RED = "\u001B[41m"; 
	
	
	 //Sub methods
	
	//Method to launch application
	public void LaunchAPP() {
		driver.get("https://www.webstaurantstore.com/");
		customWait(Logo);
		try
		{	
			System.out.println("Step 1: Webstaurants Application launched successfully \n");
		}catch(Exception e) {
			System.out.println("Step 1: Webstaurants Application launche Failed:\n" + e);
		}
	}
	//Method to search items on the landing screen
	public void mastersearch(String searchVal) {
		searchTextBox.sendKeys(searchVal);
		magnifyingGlass.click();
		customWait(Logo);
		System.out.println("Step 2: Search for \"Stainless Work Table\" step is successfull \n");
	}
	//Method to get page count after the search performed
	public int pagecount() {
		int NoOfPages=1;
		try {
		NoOfPages = Pageslist.size();		
		}catch(Exception e) {
			
		}
		return NoOfPages;
	}
	//Method to check the table title in all search results
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
			System.out.println("Step 3: \"Check the search results ensuring every products has 'Table' in its title\" step is successfull \n");
		}else {
			System.out.println("Step 3: \"Check the search results ensuring every products has 'Table' in its title\" step is "+ANSI_RED + "FAILED \n" + ANSI_RESET);
		}
		return Tableflag;
	}
	//Method to add the last item to the card
	//if last item is not having "add to cart" button (for example, items with Request to Quote button) it will check the previous item and add it to the cart
	public void addToCart() {
		ListIterator revItr = productnames.listIterator(productnames.size());
		
		while(revItr.hasPrevious()) {
			String ProdName =revItr.previous().toString();
			try {
				String AddtoCartXpath = "//*[@data-testid='itemDescription'][contains(text(),'"+ProdName +"')]//following::input[contains(@value,'Add to Cart')]";					
				driver.findElement(By.xpath(AddtoCartXpath)).click();
				System.out.println("Step 4: \"Add the last of found items to cart\" step is successfull\n");
				setproductTitle(ProdName);
				break;
				
			}catch(Exception e) {
				System.out.println(ANSI_YELLOW+"Step 4: Warning: Item is not available for \"Add to Cart\", Checking previous Item \n"+ANSI_RESET);
			}
			
		}
				
		waitforElementtodisplay(By.cssSelector(".notification__action"));		
		
		//Method to check Navigating to cart is successful or not
		try {
			PopupViewCart.click();
			System.out.println("View Cart Click Successfull \n");
		}catch(Exception e) {
			MainViewCart.click();
			System.out.println("View Cart Click Successfull \n");
		}		
		productListSize=0;
	}
	
	
	
	
	
}
