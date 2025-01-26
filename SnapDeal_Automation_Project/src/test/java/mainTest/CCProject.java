package mainTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CCProject {
	
	// GIVING URL
	public static void getUrl(String url, WebDriver driver) {
		driver.get(url);
	}
		
		

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String baseUrl = "https://my.clevelandclinic.org/";
		System.out.println("------------------- Cleveland Project started -------------------");
		System.out.println("");
		
		// step - 1
		WebDriver driver = DriverSetup.createDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		// step - 2
		getUrl(baseUrl, driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		// step - 3
		String LocationsDirectionXpath = "(//a[@href='/locations' and text()='Locations & Directions'])[2]";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LocationsDirectionXpath)));

        String mainTask = element.getText();
        if (mainTask.equalsIgnoreCase("Locations & Directions")) {
            element.click();
            System.out.println("------------------- Locations and Directions have been clicked -------------------");
            System.out.println("");
        } else {
            System.out.println("------------------- Locations webElement have not been captured -------------------");
        }
        
        
        // step 4
        String LocationTypeXpath = "//input[@value='Filter by Location Types...']";
        
        Thread.sleep(10000);
        WebElement locationType = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LocationTypeXpath)));
        js.executeScript("arguments[0].scrollIntoView(true);", locationType);
        if (locationType.isEnabled()) {
            System.out.println("------------------- Location element is clickable, scrolling and clicking -------------------");

            // Click the element using JavaScript
            js.executeScript("arguments[0].click();", locationType);
            Thread.sleep(2000);

            System.out.println("------------------- Location input is clicked -------------------");
            System.out.println("");
            locationType.clear(); // Clears the existing text in the input field

        } else {
            System.out.println("------------------- Location element is not enabled or clickable -------------------");
            System.out.println("");
        }
        
        Thread.sleep(10000);
        locationType.sendKeys("hospitals");
        
        Thread.sleep(10000);
        String locationListXpath = "//div[@id='types_chosen']/div/ul";
        
        WebElement locationListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locationListXpath)));	
        List<WebElement> locationListItems = locationListContainer.findElements(By.tagName("li"));  // Extract the child LI elements

        System.out.println("------------------- Location list items  -------------------");
        System.out.println("");
        for (WebElement loc : locationListItems) {
            System.out.println(loc.getText());
        }
        System.out.println("");
        
        
        Thread.sleep(8000);
        
        for (WebElement loc : locationListItems) {
        	String locValue = loc.getText().trim();  // Trim any leading/trailing spaces
            
            if (locValue.equalsIgnoreCase("Hospitals")) {  // Match text exactly
                System.out.println("Matching location type found: " + locValue);
                
//                String locXpath  = loc.
//                WebElement locMatch = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LocationTypeXpath)));
//                if()
                
                js.executeScript("arguments[0].click();",loc);  // Click the matching element
                System.out.println("------------------- Hospitals clicked -------------------");
                break;  // Exit the loop once the element is found and clicked
            }
        	else {
        		System.out.println("Location type is not matched and not clciked");
        	}
        }

        
        
		System.out.println("------------------- Currenlty completed -------------------");

	}

}
