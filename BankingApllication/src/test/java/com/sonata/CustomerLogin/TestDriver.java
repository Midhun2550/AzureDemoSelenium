package com.sonata.CustomerLogin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sonata.ExtentReportModule.BankReport;

public class TestDriver {

	private static WebDriver driver;
	static int failure=0;
	static int passed=0;
	
	private static ExtentReports extent = BankReport.getExtent();
	private ExtentTest test;
	
	static String msg="";
	
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));	
		
	}
	
	@Test(dataProvider="testDataDeposit", priority=1)
	public void deposit(String amt, String custNo, String acc, String expected) {
		Deposit d = new Deposit(driver);
		d.depositTesting1(amt, custNo, acc, expected);
		msg="Testing Deposit Fucntionality";
	}
	
	@Test(dataProvider="testDataWithdraw", priority=2)
	public void withdraw(String amt, String custNo, String acc, String expected) {
		Withdraw w = new Withdraw(driver);
		w.withdrawTesting1(amt, custNo, acc, expected);
		msg="Testing Withdraw Functionality";
	}
	
	@Test(priority=3)
	public void transaction() {
		Transaction t = new Transaction(driver);
		t.transactionTesting();
		msg="Testing Transtion Functionality";
	}
	
	@DataProvider(name="testDataWithdraw")
	public Iterator<Object[]> providerWithdraw() throws CsvValidationException, IOException{
		List<Object[]> data = new ArrayList();
		CSVReader reader = new CSVReader(new FileReader("./src\\test\\resources\\withdrawData.csv"));
		String[] cell;
		while((cell = reader.readNext()) != null) {
			data.add(new Object[] {cell[0], cell[1], cell[2], cell[3]});
		}
		return data.iterator();
	}
	
	@DataProvider(name="testDataDeposit")
	public Iterator<Object[]> providerDeposit() throws CsvValidationException, IOException{
		List<Object[]> data = new ArrayList();
		CSVReader reader = new CSVReader(new FileReader("./src\\test\\resources\\depositData.csv"));
		String[] cell;
		while((cell = reader.readNext()) != null) {
			data.add(new Object[] {cell[0], cell[1], cell[2], cell[3]});
		}
		return data.iterator();
	}
	
	@AfterMethod
	public void takeScreenshot(ITestResult result) throws IOException {
		test = extent.createTest("Module 2 Report", "Customer Login");
		if(result.getStatus() == ITestResult.FAILURE) {
			failure++;
			TakesScreenshot screenshot = (TakesScreenshot)driver;
			File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
			File saveFile = new File("./test-output\\failedTestCasesOutputScreenShots\\customerLogin\\failure" + failure + ".png");
			FileUtils.copyFile(screenshotFile, saveFile);
			
			
			test.log(Status.FAIL, result.getThrowable());
			test.addScreenCaptureFromPath(saveFile.getAbsolutePath());
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, msg);
		}
	}
	
	@AfterClass
	public void tearDown() {
		extent.flush();
		if(driver!=null)
			driver.quit();
	}
}
