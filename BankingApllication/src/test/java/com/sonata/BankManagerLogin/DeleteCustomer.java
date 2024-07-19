package com.sonata.BankManagerLogin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class DeleteCustomer{
	
	private WebDriver driver;
	
	private List<WebElement> firstNames; 
	private List<WebElement> deleteCustomerList;
	private int noOfRows;
	
	DeleteCustomer(WebDriver driver){
		this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        try {
			driver.switchTo().alert().accept();
		}catch(Exception e) {}
	}
	
	public void home() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
	}
	
	public void deleteSetup() {
		home();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[3]")).click();
	}

    public void stoteData() {
    	ExtractStoredData storedData = new ExtractStoredData(driver);
    	storedData.storeData();
    	noOfRows = storedData.getNoOfRows();
    }
    
    public void delete() throws InterruptedException {
    	
    	int min = 1;
    	int max = noOfRows;
    	Random random = new Random();
    	int i = random.nextInt(max - min + 1) + min;
    	Thread.sleep(1000);
    	WebElement element  = driver.findElement(By.cssSelector("tbody > tr:nth-child(" + i + ") > td:nth-child(5) > button"));
    	element.click();
    	Thread.sleep(1000);
    	try {
    		Assert.assertTrue(element.isDisplayed());
    		throw new AssertionError("Failed to Delete a customer");
    	}catch(StaleElementReferenceException e) {}
    	
    }
    
    public void deleteCustomerTesting() throws InterruptedException {
    	this.deleteSetup();
    	this.stoteData();
    	Thread.sleep(3000);
    	this.delete();
    	Thread.sleep(3000);
    }
}