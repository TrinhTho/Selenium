package Login;

import java.io.IOException;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.asn1.BEROctetStringGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


import java.io.File;

//import account data


public class Login {

WebDriver driver;
SoftAssert soft;
	
	@BeforeClass
	public void beforeClass() {
		soft 	= new SoftAssert();
		
		startBrowser("chrome");
		
		//Waiting time to start web driver
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 //Maximize browser
        driver.manage().window().maximize();
        //Open the website
        driver.get("https://giaybom.com");
        System.out.println(driver.getTitle());
        
        /*Hard Assertion:
          * At bellow, test Execution will stop if this statement will throw an error */
        //Assert.assertTrue(driver.getTitle().equals("Tài khoản– giayBOM")); 
        
        /*Soft Asertion
         * The test execution will not stop at here
        */
        soft.assertTrue(driver.getTitle().equals("Tài khoản– giayBOM"));
        SleepInSecond(5);
	}
	
	@Test()
	// Testing for Login website
	public void TC_01() throws IOException {

		File jsonFile = new File(System.getProperty("user.dir")+"/src/fixtures/account_GiayBom.json");
		
		Object myEmai = JsonPath.read(jsonFile, "informationAccount.Email");
		Object myPassword = JsonPath.read(jsonFile, "$."+"informationAccount.Password");

		//Click Login button
		WebElement btn_logInElement = driver.findElement(By.linkText("Đăng nhập"));
		btn_logInElement.click();
        
        // Information account
        WebElement customerEmainElement = driver.findElement(By.id("CustomerEmail"));
        customerEmainElement.sendKeys(myEmai.toString());
        SleepInSecond(2);
        WebElement customerPasswordElement = driver.findElement(By.id("CustomerPassword"));
        customerPasswordElement.sendKeys(myPassword.toString());
        
		// Click "Đăng nhập" button
        WebElement btn_singInElement = driver.findElement(By.xpath("//*[@id=\"customer_login\"]/p[1]/input"));
        btn_singInElement.click();
		SleepInSecond(5);

	}
	
	public void jsonReader()  throws IOException {
		File jsonFile = new File(System.getProperty("user.dir")+"/src/fixtures/account_GiayBom.json");
		
		
	}
	@Test
	//Update infomation
	public void TC_02() throws IOException {
		File  jsonFile = new File(System.getProperty("user.dir")+"/src/fixtures/account_GiayBom.json");
		Object addressCompany = JsonPath.read(jsonFile, "$."+"informationAccount.AddressCompany");
		Object AddressAddress1 = JsonPath.read(jsonFile, "$."+"informationAccount.AddressAddress1");
		Object AddressCity = JsonPath.read(jsonFile, "$."+"informationAccount.AddressCity");
		
		
		//Click on "Xem địa chỉ"
		WebElement a_Address = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div/div/div[2]/p[2]/a"));
		a_Address.click();
		
		//Click "Sửa"
		WebElement a_EditElement = driver.findElement(By.xpath("//a[contains(text(),'Sửa')]"));
		a_EditElement.click();
		
		//Edit address
		//Edit Company name
		WebElement input_addressCompany = driver.findElement(By.id("AddressCompany_1122743388"));
		input_addressCompany.clear();
		input_addressCompany.sendKeys(addressCompany.toString());
		
		//Edit address 1
		WebElement input_address1 = driver.findElement(By.id("AddressAddress1_1122743388"));
		input_address1.clear();
		input_address1.sendKeys(AddressAddress1.toString());
		
		//Edit addtess city
		WebElement input_addressCity = driver.findElement(By.id("AddressCity_1122743388"));
		input_addressCity.clear();
		input_addressCity.sendKeys(AddressCity.toString());
		
		
		//address[default]
		WebElement addressDefault = driver.findElement(By.id("address_default_address_1122743388"));
		addressDefault.click();
		
		//Click on "Cập nhật"
		WebElement p_update = driver.findElement(By.xpath("//*[@id=\"EditAddress_1122743388\"]/p[2]/input"));
		p_update.click();
		
		SleepInSecond(5);
	}
	
	
	@AfterClass
	  public void afterClass() {
		  //Exist web driver
		  driver.quit();
	}
	
	public void SleepInSecond(long time) {
		try {
		
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startBrowser(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			
			//Because we using WebdriverManager, so we don't need to configure the system properties of Selenium driver
			//Configure the system properties of google chrome driver
//			System.setProperty("webdriver.chrome.driver","chromedriver.exe");
			
			WebDriverManager.chromedriver().setup();
			//Initating the brower driver
			driver 	= new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			//Initating the brower driver
			driver 	= new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("ie")) {

			WebDriverManager.iedriver().setup();
			//Initating the brower driver
			driver 	= new InternetExplorerDriver();
		}
	}

}
