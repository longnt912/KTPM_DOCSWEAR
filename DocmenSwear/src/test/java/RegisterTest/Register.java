package RegisterTest;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//import org.testng.Assert;
import org.testng.annotations.*;
//import static org.testng.Assert.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class Register {// tạo phương thức đọc dữ liệu excel và trả về mảng chuỗi.
	public static WebDriver driver;
	//WebElement lbNotify = null;
	String chrome_path;
	public static HSSFWorkbook workbook;
	public static HSSFSheet worksheet;
	public static DataFormatter formatter = new DataFormatter();
	public static String file_location = "P:\\DocmenSwear\\src\\test\\java\\RegisterTest\\RegisterData.xls";
	static String SheetName = "Sheet1";
	public int DataSet = -1;
	
	@BeforeSuite
	public void OpenBrowser() throws InterruptedException //Tạo các phương thức giúp lấy trình duyệt và đóng chúng khi hoàn tất.

	{
		System.setProperty("webdriver.gecko.driver", "D:\\geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--no-sandbox"); // Bypass OS security model
		driver = new FirefoxDriver();
		driver.get("https://docmenswear.vn/user/signup");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);
	}
	
	
	@Test(dataProvider = "LoginData") //Tạo một nhà cung cấp dữ liệu thực sự nhận các giá trị bằng cách đọc trang tính excel
	  public void testRegister(String UserName, String FullName, String Mobile, 
			  		String Email, String PassWord, String ReEnterPassword, String Result) throws Exception {
		
		DataSet++; 
		WebDriverWait wait =  new WebDriverWait(driver, 90);
		driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys(UserName);
	    Thread.sleep(2000);
	    driver.findElement(By.id("fullName")).clear();
	    driver.findElement(By.id("fullName")).sendKeys(FullName);
	    Thread.sleep(2000);
	    driver.findElement(By.id("mobile")).clear();
	    driver.findElement(By.id("mobile")).sendKeys(Mobile);
	    Thread.sleep(2000);
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys(Email);
	    Thread.sleep(2000);
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys(PassWord);
	    Thread.sleep(2000);
	    driver.findElement(By.id("rePassword")).clear();
	    driver.findElement(By.id("rePassword")).sendKeys(ReEnterPassword);
	    Thread.sleep(2000);
	    driver.findElement(By.id("btnRegister")).click();
	    Thread.sleep(5000);
	  
	  }	
	  
	  @AfterClass(alwaysRun = true)
	  public void tearDown() throws Exception {
		  Thread.sleep(5000);
		  driver.quit();
	  }

	  @DataProvider (name = "LoginData")
	  public Object[][] LoginData() throws IOException {
			FileInputStream fileInputStream = new FileInputStream (file_location); // Excel sheet file location get mentioned
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
