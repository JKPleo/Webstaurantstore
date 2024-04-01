package webstaurantstore.Utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {
	
	
	WebDriver driver;
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
	}
	public void customWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));	
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitforElementtodisplay(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));	
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

}
