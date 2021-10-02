package SearchProductTest;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
public class SearchProduct {
	public static WebDriver driver;
	String chrome_path;
	public static HSSFWorkbook workbook;
	public static HSSFSheet worksheet;
	public static DataFormatter formatter = new DataFormatter();
	public static String file_location = "P:\\DocmenSwear\\src\\test\\java\\SearchProductTest\\DataSearch.xls";
	static String SheetName = "Sheet1";
	public int DataSet = -1;
	  @BeforeClass(alwaysRun = true)
	  public void OpenBrowser() throws InterruptedException

		{
			System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox"); // Bypass OS security model
			driver = new FirefoxDriver();
			driver.get("https://docmenswear.vn");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

			Thread.sleep(2000);
		}
 
  @Test (dataProvider = "SearchData") 
  public void testSearchProduct(String ProductName, String Result) throws Exception {
	DataSet++;
    driver.findElement(By.name("q")).clear();
    driver.findElement(By.name("q")).sendKeys(ProductName);
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    Thread.sleep (2000);
  }

//  @AfterTest(alwaysRun = true)
//  public void tearDown() throws Exception {
//    driver.quit();
////    String verificationErrorString = verificationErrors.toString();
////    if (!"".equals(verificationErrorString)) {
////      fail(verificationErrorString);
////    }
//  }

	@DataProvider(name = "SearchData")
	public Object[][] ReadDataSearch() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file_location); // Excel sheet file location get mentioned
																				// here
		workbook = new HSSFWorkbook(fileInputStream); // get my workbook
		worksheet = workbook.getSheet(SheetName);// get my sheet from workbook
		HSSFRow Row = worksheet.getRow(0); // get my Row which start from 0
		int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		int ColNum = Row.getLastCellNum(); // get last ColNum
		Object Data[][] = new Object[RowNum - 1][ColNum]; // pass my count data in array

		for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
		{
			HSSFRow row = worksheet.getRow(i + 1);

			for (int j = 0; j < ColNum; j++) // Loop work for colNum
			{
				if (row == null)
					Data[i][j] = "";
				else {
					HSSFCell cell = row.getCell(j);
					if (cell == null)
						Data[i][j] = ""; // if it get Null value it pass no data
					else {
						String value = formatter.formatCellValue(cell);
						Data[i][j] = value; // This formatter get my all values as string i.e integer, float all type
											// data value
					}
				}
			}
		}

		return Data;
	}
}
