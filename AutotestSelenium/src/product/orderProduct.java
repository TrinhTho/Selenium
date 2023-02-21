package product;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.crypto.util.Pack;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import javax.management.loading.PrivateClassLoader;

import org.apache.commons.lang3.Validate;
import io.github.bonigarcia.wdm.WebDriverManager;

import Login.Login;
import fixtures.validateUIHelpers;

public class orderProduct {

	// Selenium to website
	WebDriver driver;
	SoftAssert soft;
	private validateUIHelpers validate;

	// Element at Sing In page
	private By customerEmain = By.id("CustomerEmail");
	private By CustomerPassword = By.id("CustomerPassword");
	private By signinBtn = By.xpath("//*[@id=\"customer_login\"]/p[1]/input");
	private By btn_logIn = By.linkText("Đăng nhập");

	//Orther product
	private By btn_New2022 = By.xpath("//div[contains(text(),'NEW 2022')]");
	private By product = By.linkText("GB WARRIORSS B1856");
	private By addToCarbutton = 	By.id("AddToCart");
	private By addCarIntruction = By.id("CartSpecialInstructions");
	private By btn_Cart = By.xpath("//a[contains(text(),'Tiến hành thanh toán')]");
//	private By btn_District = 	By.id("customer_shipping_district");
//	private By btn_Ward = 	By.id("customer_shipping_ward");
	private By btn_methodPayment = 	By.xpath("//span[contains(text(),'Phương thức thanh toán')]");
	private By placeOrderButton = 	By.xpath("//*[@id=\"checkout_complete\"]/button/span");
	
	@BeforeClass
	public void beforeClass() throws IOException {
		soft = new SoftAssert();
		startBrowser("chrome");
		validate = new validateUIHelpers(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://giaybom.com");
		validate.SleepInSecond(5);
		Login();
	}

	@Test
	public void orderProductTest() throws IOException {

		soft = new SoftAssert();
		File jsonFile = new File(System.getProperty("user.dir") + "/src/fixtures/account_GiayBom.json");
		Object myDistrict = JsonPath.read(jsonFile, "informationAccount.AddressDistrict");
		Object myWard = JsonPath.read(jsonFile, "informationAccount.AddressWard");

		// Find and click "NEW 2022"
		validate.clickElement(btn_New2022);
		// Find and click on the product to orther
		validate.clickElement(product);
		// Add the product to the cart
		validate.clickElement(addToCarbutton);
		// Add cart instruction
		validate.clearElement(addCarIntruction);
		validate.setText(addCarIntruction,"Testing order product");
		// Navigate the cart page
		validate.clickElement(btn_Cart);

		// Add address District
//		validate.clickElement(btn_District);
//		Select selectDistrict = new Select(btn_District);
//		selectDistrict.selectByVisibleText(myDistrict.toString());
//		
//		// Add address Ward
//		validate.clickElement(btn_Ward);
//		Select selectWard = new Select(btn_Ward);
//		selectWard.selectByVisibleText(myWard.toString());
				
		// Proceed to checkout: Method of payment
		validate.clickElement(btn_methodPayment);

		// Submit the form to complete the order
		validate.clickElement(placeOrderButton);
	}

	@AfterClass
	public void afterClass() {
		// Exist web driver
		driver.quit();
	}

	// Login website
	public void Login() throws IOException {
		File jsonFile = new File(System.getProperty("user.dir") + "/src/fixtures/account_GiayBom.json");
		Object myEmai = JsonPath.read(jsonFile, "informationAccount.Email");
		Object myPassword = JsonPath.read(jsonFile, "informationAccount.Password");
		// Click Login button
		validate.clickElement(btn_logIn);
		// Information account
		validate.setText(customerEmain, myEmai.toString());
		validate.setText(CustomerPassword, myPassword.toString());
		// Click "Đăng nhập" button
		validate.clickElement(signinBtn);
		validate.SleepInSecond(4);

	}

	// Initating the brower driver
	public void startBrowser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();

			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			// Initating the brower driver
			driver = new InternetExplorerDriver();
		}
	}

}
