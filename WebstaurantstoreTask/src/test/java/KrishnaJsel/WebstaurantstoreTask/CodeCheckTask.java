package KrishnaJsel.WebstaurantstoreTask;
import io.github.bonigarcia.wdm.WebDriverManager;
import webstaurantstore.pageobjects.CartPage;
import webstaurantstore.pageobjects.LandingPage;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;


public class CodeCheckTask {

	public static void main(String[] args) throws InterruptedException {
		
		//WebDriverManager Setup
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		WebDriverManager.chromedriver().clearDriverCache().setup();
		WebDriver driver = new ChromeDriver();
		LandingPage LandingPage = new LandingPage(driver);		
		CartPage CartPage = new CartPage(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//Application launch
		LandingPage.LaunchAPP();
		//Searching for a value
		LandingPage.mastersearch("stainless work table");		
		Thread.sleep(2000);
		int NoOfPages=0;
		boolean flag=true;
		String StatusERRmessage="";	
		
		final String ANSI_RESET = "\u001B[0m"; 		
		final String ANSI_RED = "\u001B[41m"; 
		
		try {
		//Checking for result table availability
			if(driver.findElement(By.id("product_listing")).isDisplayed()) {
		//Checking for navigation bar
				try {					
					if(driver.findElement(By.id("paging")).isDisplayed()) {						
		//check for number of pages
						NoOfPages = LandingPage.pagecount();
					}
					}catch(Exception e) {
						System.out.println("All results are in single page");						
					}
		//checking for Table NOT present in title
				if(LandingPage.checkTitleforTable(NoOfPages)) {
					flag=false;
					StatusERRmessage="Table is NOT present in the Product title";
				}								
		//Adding last item to Cart
				LandingPage.addToCart();					
				
		//Check for item added to the Cart
				String ProductTitle = LandingPage.getproductTitle();					
				if(CartPage.ItemAddCheck(ProductTitle)) {
		//Empty the cart
					CartPage.emptycart();
		//checking for empty cart
					CartPage.cartcountcheck();					
				}else {
					flag=false;
					StatusERRmessage="Unable to add item to the cart";
				}	
			}else {		
				flag=false;
				StatusERRmessage="NO records found for the search criteria";
			}
		}catch(Exception e) {
			System.out.println("Exception in the execution"+e);
		}		
		if(flag) {
		System.out.println("Test case is successfully passed");
		}else {
			System.out.println(ANSI_RED+"Test case Failed "+ANSI_RESET+"due to " +StatusERRmessage);
		}		
		driver.quit();
	}
	
	

}
