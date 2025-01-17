package com.sonata.BankManagerLogin;

import java.awt.AWTException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
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
	static int passed=0;
	
	private static ExtentReports extent = BankReport.getExtent();
	private ExtentTest test;
	
	static String msg="";
	
	
	//Whatever the events happened before the class
	@BeforeClass
	public void setUp() {
		//Driver setup
		System.setProperty("webdriver.chrome.driver", "./src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
		driver.manage().window().maximize();
		
		
	}
	
	//TestCase for adding customer
	@Test(dataProvider = "testDataAddCustomer", priority=1)
	public void managerAddCustomer(String firstName, String lastName, String pin) throws IOException, InterruptedException, AWTException{
		test = extent.createTest("Module 1 Report", "Bank Manager Login Module");
		ManagerLogin managerAddCustomer = new ManagerLogin(driver);
		managerAddCustomer.addCustomerTest(firstName, lastName, pin, test);
	    msg="Adding Customer in Manager Login";	
	}
	
	//TestCase for opening customer
	@Test(dataProvider = "testDataOpenAccount", groups="OpenAccount", priority=2)
	public void managerOpenAccount1(String indexValueOfCustomer, String indexValueOfCurrency, String expectedOutput, ITestResult result) throws InterruptedException, IOException, AWTException {
		test = extent.createTest("Module 1 Report", "Bank Manager Login Module");
		OpenAccount openAcc = new OpenAccount(driver);
		openAcc.openAccountTesting1(indexValueOfCustomer, indexValueOfCurrency, expectedOutput, result);
		msg="Adding Customer in Manager Login";
	}
	
	//TestCase for ambiguity in opening customer
	@Test(groups="OpenAccount", priority=3)
	public void managerOpenAccount2(){
		test = extent.createTest("Module 1 Report", "Bank Manager Login Module");
		OpenAccount openAcc = new OpenAccount(driver);
		openAcc.openAccountTesting2();
		msg="Oppening Account in Manager Login";
	}
	
	//TestCase for searching customer
	@Test(dataProvider="testDataSearch", priority=4)
	public void search(String data, String category) throws InterruptedException {
		test = extent.createTest("Module 1 Report", "Bank Manager Login Module");
		Search search = new Search(driver);
		search.searchTesting(data, category);
		msg="Searching Customer in Manager Login";
	}
	
	//TestCase for Delete customer
	@Test(groups="deleteCustomer", priority=5)
	public void deleteCustomer() throws InterruptedException {
		test = extent.createTest("Module 1 Report", "Bank Manager Login Module");
		DeleteCustomer delete = new DeleteCustomer(driver);
		delete.stoteData();
		delete.deleteCustomerTesting();
		msg="Deleting Customer in Manager Login";
	}
	
	//DataProvider for searching customer
	@DataProvider(name="testDataSearch")
	public Iterator<Object[]> providerSearch() throws CsvValidationException, IOException{
		List<Object[]> data = new ArrayList();
		
		CSVReader reader = new CSVReader(new FileReader("./src\\test\\resources\\searchData.csv"));
		String[] cell = reader.readNext();
		while((cell = reader.readNext()) != null){
			data.add(new Object[]{cell[0], cell[1]});
		}
		return data.iterator();
	}
	
	//DataProvider for adding customer
	@DataProvider(name="testDataAddCustomer")
	public Iterator<Object[]> providerAddCustomer() throws CsvValidationException, IOException{
		List<Object[]> data = new ArrayList();
		
		CSVReader reader = new CSVReader(new FileReader("./src\\test\\resources\\customerData.csv"));
		String[] cell;
		cell = reader.readNext();
		while((cell = reader.readNext()) != null) {
			String firstName = cell[0];
			String lastName = cell[1];
			String pin = cell[2];
			
			data.add(new Object[] {firstName, lastName, pin});
		}
		return data.iterator();		
	}
	
	//DataProvider for Opening Account customer
	@DataProvider(name="testDataOpenAccount")
	public Iterator<Object[]> providerOpenAccount() throws CsvValidationException, IOException{
		List<Object[]> data = new ArrayList();
		CSVReader reader = new CSVReader(new FileReader("./src\\test\\resources\\openAccountData.csv"));
		String[] cell;
		cell = reader.readNext();
		while((cell = reader.readNext()) != null) {
			data.add(new Object[] {cell[0], cell[1], cell[2]});
		}
		return data.iterator();
	}
	
	//Whatever the events are going after each test method
	@AfterMethod
	public void takeScreenshot(ITestResult result) throws IOException {
		
		if(result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, msg);
		}
		if(result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, result.getThrowable());
		}
	}
	
	//Whatever the events happened before the class i.e closing the Browser
	@AfterClass
	public void tearDown() {
		extent.flush();
		if(driver!=null)
			driver.quit();
	}
}
