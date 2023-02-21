package Login;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import io.github.bonigarcia.wdm.WebDriverManager;
import fixtures.validateUIHelpers;

//import account data

public class Login {

	WebDriver driver;
	SoftAssert soft;
	private validateUIHelpers validate;
	// Element at Sing In page
	private By customerEmain = By.id("CustomerEmail");
	private By CustomerPassword = By.id("CustomerPassword");
	private By signinBtn = By.xpath("//*[@id=\"customer_login\"]/p[1]/input");
	private By btn_logIn = By.linkText("Đăng nhập");

	// Element at Information account
	private By a_Address = By.xpath("//*[@id=\"page-wrapper\"]/div/div/div/div[2]/p[2]/a");
	private By a_Edit = By.xpath("//a[contains(text(),'Sửa')]");
	private By in_addressCompany = By.id("AddressCompany_1122743388");
	private By in_address1 = By.id("AddressAddress1_1122743388");
	private By in_addressCity = By.id("AddressCity_1122743388");
	private By addressDefault = By.id("address_default_address_1122743388");
	private By btn_update = By.xpath("//*[@id=\"EditAddress_1122743388\"]/p[2]/input");

	@BeforeClass
	public void beforeClass() {
		soft = new SoftAssert();
		startBrowser("chrome");
		validate = new validateUIHelpers(driver);
		// Waiting time to start web driver
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Maximize browser
		driver.manage().window().maximize();
		// Open the website
		driver.get("https://giaybom.com");
		System.out.println(driver.getTitle());

		/*
		 * Hard Assertion: At bellow, test Execution will stop if this statement will
		 * throw an error
		 */
		// Assert.assertTrue(driver.getTitle().equals("Tài khoản– giayBOM"));

		/*
		 * Soft Asertion The test execution will not stop at here
		 */
		soft.assertTrue(driver.getTitle().equals("Tài khoản– giayBOM"));
		validate.SleepInSecond(5);
	}

	@Test
	// Testing for Login website
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
		validate.SleepInSecond(5);

	}

	@Test
	// Update infomation
	public void updateAccount() throws IOException {
		File jsonFile = new File(System.getProperty("user.dir") + "/src/fixtures/account_GiayBom.json");
		Object addressCompany = JsonPath.read(jsonFile, "$." + "informationAccount.AddressCompany");
		Object AddressAddress1 = JsonPath.read(jsonFile, "$." + "informationAccount.AddressAddress1");
		Object AddressCity = JsonPath.read(jsonFile, "$." + "informationAccount.AddressCity");

		// Click on "Xem địa chỉ"
		validate.clickElement(a_Address);
		// Click "Sửa"
		validate.clickElement(a_Edit);
		// Edit address Company
		validate.clearElement(in_addressCompany);
		validate.setText(in_addressCompany, addressCompany.toString());
		// Edit address 1
		validate.clearElement(in_address1);
		validate.setText(in_address1, AddressAddress1.toString());
		// Edit addtess city\
		validate.clearElement(in_addressCity);
		validate.setText(in_addressCity, AddressCity.toString());
		// address[default]
		validate.clickElement(addressDefault);
		// Click on "Cập nhật"
		validate.clickElement(btn_update);
		validate.SleepInSecond(5);
	}

	@AfterClass
	public void afterClass() {
		// Exist web driver
		driver.quit();
	}

	public void startBrowser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {

			// Because we using WebdriverManager, so we don't need to configure the system
			// properties of Selenium driver
			// Configure the system properties of google chrome driver
			// System.setProperty("webdriver.chrome.driver","chromedriver.exe");

			WebDriverManager.chromedriver().setup();
			// Initating the brower driver
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			// Initating the brower driver
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("ie")) {

			WebDriverManager.iedriver().setup();
			// Initating the brower driver
			driver = new InternetExplorerDriver();
		}
	}

}
