package com.sonata.CustomerLogin;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Transaction {

	private static WebDriver driver;
	
	Transaction(WebDriver driver){
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public static void sendData(String data) {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(data);
	}
	
	public static void chooseDeposit() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click();
	}
	
	public static void home() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
	}
	
	public static void customerClick() {
		home();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();
	}
	
	
    public static void customerSelect(int customerNo) {
		
		Select customerSelector = new Select(driver.findElement(By.xpath("//*[@id=\"userSelect\"]")));
		customerSelector.selectByIndex(customerNo);
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();
	}
	
	
    public static void accountSelect(int accountIndx) {
		
		Select accSelector = new Select(driver.findElement(By.xpath("//*[@id=\"accountSelect\"]")));
		accSelector.selectByIndex(accountIndx);
	}
	
    public static void depositClick() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
	}
    
	public static void transactionSetup() {
		
		customerClick();
		customerSelect(2);
		accountSelect(1);
		chooseDeposit();
		sendData("5000");
		depositClick();
	}
	
	public static void transactionClick() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[1]")).click();
	}
	
	public static void reset() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]")).click();
	}
	
	public static void back() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();
	}
	
	public void transactionTesting() {
		transactionSetup();
		
		String balanceBeforeReset = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
		transactionClick();
		reset();
		back();
		String balanceAfterReset = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
		
		Assert.assertEquals(balanceBeforeReset, balanceAfterReset);
	}
}
