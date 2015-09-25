import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class TestingAutomation {

	private WebDriver driver;
	private String loginUrl;
	private String username;
	private String password;
	private String searchTerm;
	Scanner input = new Scanner(System.in);


	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		loginUrl = "https://www.walmart.com/account/login";
		System.out.println("Enter Username");
		username = input.nextLine();
		System.out.println("Enter password");
		password = input.nextLine();	
		System.out.println("Enter search Term");
		searchTerm = input.nextLine();

		
	}

	@Test
	public void testAddToCartAutomation() throws Exception {
		//login to my account
		driver.get(loginUrl);
		fluentWait(By.id("login-username")).sendKeys(username);
		driver.findElement(By.id("login-password")).sendKeys(password);
		driver.findElement(By.className("login-sign-in-btn")).submit();	
		
		//check if logged in successfully
		WebElement login = null;
		login = fluentWait(By.className("recent-orders-heading"));
		if(login != null && login.getText() != null && login.getText().equalsIgnoreCase("welcome to your walmart account!")){
			System.out.println("logged in");
		}else{
			System.out.println("Not logged in");
			return;
		}
		//get the search bar element,enter the term and search
		WebElement searchBar = driver.findElement(By.className("searchbar-input"));
		searchBar.sendKeys(searchTerm);
		driver.findElement(By.className("searchbar-submit")).click();
        
		//select an item from result
		WebElement selectedItem = null;
		String selectedItemAttribute = null;
		if(searchTerm.equalsIgnoreCase("toys")){
			
			selectedItem = fluentWait(By.xpath("//*[@id='sponsored-container-middle-2']/div/div/div[2]/ol/div/div/li[1]/div/a"));
			selectedItemAttribute = selectedItem.getAttribute("data-uid");

		}else{
		selectedItem = fluentWait(By.xpath("//*[@id='tile-container']/div[1]/div/div/h4/a"));
		selectedItemAttribute = selectedItem.getAttribute("href");

		}
		selectedItem.click();
        
		//add to cart
		WebElement addItem = fluentWait(By.cssSelector("#WMItemAddToCartBtn"));
		
		addItem.click();
		
		//view cart
		fluentWait(By.id("PACViewCartBtn")).click();
		
		WebElement cartList = fluentWait(By.xpath("//*[@id='spa-layout']/div/div/div[1]/div/div[4]/div[2]"));

		//List of different items in the cart
		List<WebElement> differentCartItems = cartList.findElements(By.className("cart-item-row"));
		
		String numOfSameItems = driver.findElement(By.className("chooser-option-current")).getText();

		assertTrue("Only one item in the cart", (differentCartItems.size() == 1) && (numOfSameItems.equals("1")));
		
		String itemId = null;

		itemId = differentCartItems.get(0).findElement(By.tagName("a")).getAttribute("data-us-item-id");
		
		//comparing the itemId of the selected item and the item in the cart
		boolean assertCondition = false;
		if(searchTerm.equalsIgnoreCase("toys")){
			assertCondition = selectedItemAttribute.startsWith(itemId);
		}else{
			assertCondition = selectedItemAttribute.endsWith(itemId);
		}
		
	    assertTrue("Item added to cart is same as the selected",assertCondition);

	}


	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/* Wait till the required element is loaded by polling for every 5 secs*/
	private WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return element;
	};
}
