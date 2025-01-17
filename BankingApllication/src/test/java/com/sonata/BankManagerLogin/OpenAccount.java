package com.sonata.BankManagerLogin;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class OpenAccount {
	
	private static WebDriver driver;
	
	OpenAccount(WebDriver driver){
		OpenAccount.driver = driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	
	}
	
	public static void openSetup() {
		home();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]")).click();
	}
	
	public static void openAccount(String indexValueOfCustomer, String indexValueOfCurrency) {
		
		try{
			int indexCustomer = Integer.parseInt(indexValueOfCustomer);
			int indexCurrency = Integer.parseInt(indexValueOfCurrency);
			Select customerSelector = new Select(driver.findElement(By.name("userSelect")));
			customerSelector.selectByIndex(indexCustomer);
			Select currencySelector = new Select(driver.findElement(By.xpath("//*[@id=\"currency\"]")));
			currencySelector.selectByIndex(indexCurrency);
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
		}catch(Exception e) {}
		
	}
	
	public static void clickAlert() {
		driver.switchTo().alert().accept();
	}
	
	public static void home() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
	}
	
	public void openAccountTesting1(String indexValueOfCustomer, String indexValueOfCurrency, String expectedOutput, ITestResult result) throws InterruptedException, IOException, AWTException {
		openSetup();
		openAccount(indexValueOfCustomer, indexValueOfCurrency);
		 
		 try {
			 Assert.assertTrue(driver.switchTo().alert().getText().toLowerCase().contains(indexValueOfCustomer));
		     driver.switchTo().alert().accept();
		 }catch(NoAlertPresentException e){}
		home();
	}
	
	public void openAccountTesting2() {
		openSetup();
		openAccount("1","1");
		try {
			clickAlert();
		}catch(UnhandledAlertException e) {}
		openAccount("1", "1");
		Assert.assertEquals(driver.switchTo().alert().getText().toLowerCase(), "duplicate account in the same name");
	}
}
