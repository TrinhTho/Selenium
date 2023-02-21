package fixtures;

import javax.management.loading.PrivateClassLoader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class validateUIHelpers {

	private WebDriver driver;

	public validateUIHelpers(WebDriver _driver) {
		driver = _driver;
		SleepInSecond(5);
	}

	/* Function using to send Keys value
	 * Example: 
	 * WebElement customerEmainElement = driver.findElement(By.id("CustomerEmail"));
	 * customerEmainElement.sendKeys(myEmai.toString());
	 */
	public void setText(By element, String value) {
		SleepInSecond(3);
		driver.findElement(element).sendKeys(value);
	}

	/* Function using to click
	 * Example: 
	 * WebElement customerEmainElement = driver.findElement(By.xpath(//*[@id=\"customer_login\"]/p[1]/input));
	 * customerEmainElement.sendKeys(customerEmainElement.toString());
	 */
	public void clickElement(By element) {
		SleepInSecond(3);
		driver.findElement(element).click();
	}

	/* Function using to clear
	 * Example: 
	 * WebElement input_addressCity = driver.findElement(By.id("AddressCity_1122743388"));
	 * input_addressCity.clear();
	 */
	public void clearElement(By element) {
		SleepInSecond(3);
		driver.findElement(element).clear();
	}
	 
	public void SleepInSecond(long time) {
		try {

			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
