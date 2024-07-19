package com.sonata.CustomerLogin;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Withdraw {
 
	private static WebDriver driver;
	
	public Withdraw(WebDriver driver) {
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	
	
	
	public static void sendData(String data) {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(data);
	}
	
	public static void chooseWithdraw() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
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
	
	public static void withdrawClick() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
	}
	
	public static void customerClick() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();
	}
	
	public static void home() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
	}
	
	public static String balanceAmt() {
		return driver.findElement(By.cssSelector("div:nth-child(3) > strong:nth-child(2)")).getText();
	}
	
	public static void withdrawTesting1(String data,String customerNo,String accountIndx, String expected ) {
		
		home();
		customerClick();
		customerSelect(Integer.parseInt(customerNo));
		accountSelect(Integer.parseInt(accountIndx));
		String balance = balanceAmt();
		chooseWithdraw();
		sendData(data);
		withdrawClick();
		
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher dataMatcher = pattern.matcher(data);
		
		    if(balance.equals("0")) {
		    	Assert.assertTrue(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText().equalsIgnoreCase("Transaction Failed. You can not withdraw amount more than the balance."));
		    }
		    else if(data.charAt(0)=='-' || data.length()>9 || (!dataMatcher.find())) {
				Assert.assertEquals(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText().toLowerCase(), expected);
				home();
			}
			else {
				Assert.assertEquals(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText().toLowerCase(), expected);
				home();
			}
	}
	
}
