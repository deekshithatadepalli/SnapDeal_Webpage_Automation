package mainTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class MiniProject {
	
	
	// GIVING URL
	public static void getUrl(String url, WebDriver driver) {
		driver.get(url);
	}
	
	// SEARCHING METHOD for Bluetooth headphones
	public static void toSearch(String searchName, WebDriver driver) {
		// search box - id -> inputValEnter
		driver.findElement(By.id("inputValEnter")).sendKeys(searchName);
		
		// search icon - class -> searchTextSpan
		driver.findElement(By.className("searchTextSpan")).click();
	}
	
	// SORTING METHOD based on Popularity
	public static void toSort(WebDriver driver) {
		// sort by - click on dropdown
		// after visibility of all elements in dropdown, append to list

		driver.findElement(By.className("sort-selected")).click();
		List<WebElement> sort_list = driver.findElements(By.className("search-li"));
		
		
		for(WebElement sort_element : sort_list) {
			// if any element in list equals to Popularity
			if(sort_element.getText().equals("Popularity"))
			{
				sort_element.click();
				
			}
		}
	}
	
	// PRICE RANGE method within limits
	public static void toSetPriceRange(String min, String max, WebDriver driver) {
		// price range
		// first clear existing values
		// then give required values
		driver.findElement(By.name("fromVal")).clear();
		driver.findElement(By.name("fromVal")).sendKeys(min);
		driver.findElement(By.name("toVal")).clear();
		driver.findElement(By.name("toVal")).sendKeys(max);
		// then click go button
		driver.findElement(By.cssSelector("div[class='price-go-arrow btn btn-line btn-theme-secondary']")).click();
	}
	
	// LIST OF PRODUCT TITLES
	public static List<WebElement> listOfNames(WebDriver driver){
		// after sorting & waiting, appending all elements to names using common classname -> product-title
		List<WebElement> titles = driver.findElements(By.className("product-title"));
		return titles;
	}
	
	// LIST OF PRODUCT PRICES
	public static List<WebElement> listOfPrices(WebDriver driver){
		// appending all elements to prices using common classname and id 
		// used both because classname is space separated
		// for finding all common elements, this is common relation between them
		List<WebElement> prices = driver.findElements(By.cssSelector(".product-price[id*='display-price']"));
		return prices;
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("MINI-PROJECT");
		
		// READING EXCEL FILE using APACHE POI
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\mainTest\\MiniProjectData.xlsx");
		
		// giving file reference to workbook
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		// getting XL sheet from workbook
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		
		// step - 1
		WebDriver driver = DriverSetup.createDriver();
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		// step - 2
		
		XSSFRow row = sheet.getRow(0);
		XSSFCell value = row.getCell(0);
		String baseUrl = value.toString();
		
		getUrl(baseUrl, driver);
		
		// step - 3
		
		XSSFRow row2 = sheet.getRow(1);
		XSSFCell value2 = row2.getCell(0);
		String product = value2.toString();
		
		toSearch(product,driver);
		
		// step - 4
		toSort(driver);
		
		// step - 5
		
		XSSFRow row3 = sheet.getRow(2);
		XSSFCell value3 = row3.getCell(0);
		String minPrice = value3.toString().substring(0,value3.toString().length()-2 );
		
		XSSFRow row4 = sheet.getRow(3);
		XSSFCell value4 = row4.getCell(0);
		String maxPrice = value4.toString().substring(0,value4.toString().length()-2 );
		
		
		toSetPriceRange(minPrice, maxPrice, driver);
		
		// waiting for sometime for browser loading
		Thread.sleep(5000);
		
		// step - 6
		List<WebElement> titlesList =  listOfNames(driver);
		
		// waiting for sometime for browser loading
		Thread.sleep(5000);
		
		// step - 7
		List<WebElement> pricesList =  listOfPrices(driver);
		
		// printing first 5 product title - price
		int k = 0;
		for(int i = 0; i<5; i++) {
			if(k<5) {
				System.out.println(titlesList.get(i).getText() +" - "+ pricesList.get(i).getText());
				k++;
			}
		}
		
		workbook.close();
		file.close();
		
		// closing driver
		//driver.quit();
		
	}

}
