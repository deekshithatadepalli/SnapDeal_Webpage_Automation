package mainTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSetup {

	static WebDriver driver;
	
	// CREATING DRIVER
	public static WebDriver createDriver() {
		driver = new ChromeDriver();
		return driver;
	}
	
}
