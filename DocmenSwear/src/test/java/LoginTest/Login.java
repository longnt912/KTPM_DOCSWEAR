package LoginTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeSuite;



public class Login {
	public static WebDriver driver;
	String chrome_path;
	public static HSSFWorkbook workbook; // ghi file excle
	public static HSSFSheet worksheet;
	public static DataFormatter formatter = new DataFormatter();
	public static String file_location = "P:\\DocmenSwear\\src\\test\\java\\LoginTest\\DataLogin.xls";
	static String SheetName = "Sheet1";
	public int DataSet = -1;

	@BeforeSuite
	public void OpenBrowser() throws InterruptedException

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
		driver.get("https://docmenswear.vn/user/signin");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Thread.sleep(3000);
	}
	
	@Test// Test method
	(dataProvider = "ReadVariant") // It get values from ReadVariant function method
	// Here my all parameters from excel sheet are mentioned.
	public void AddVariants(String UserName, String PassWord, String Result) throws Exception {
		DataSet++;
		System.out.println("UserName: " + UserName);
		System.out.println("PassWord: " + PassWord);

//		// User get Login
		driver.findElement(By.id("username")).clear(); // xác định phần từ qua thẻ id
		driver.findElement(By.id("username")).sendKeys(UserName); 
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(PassWord);
		driver.findElement(By.id("btnSignIn")).click();
		Thread.sleep(5000); 
	}

	@DataProvider(name = "ReadVariant") // kiểm tra hướng dữ liệu
	public Object[][] ReadVariant() throws IOException {
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
