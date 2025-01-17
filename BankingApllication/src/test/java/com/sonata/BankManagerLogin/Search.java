package com.sonata.BankManagerLogin;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Search{
	
	private WebDriver driver;
	
	private List<WebElement> firstNames; 	
	private List<WebElement> lastNames;
	private List<WebElement> pincodes;

	
	Search(WebDriver driver){
		this.driver = driver;
	    try {
	    	this.driver.switchTo().alert().accept();
	    }catch(Exception e) {}
		this.driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list");
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public void home() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
	}
	
	public void searchSetup() {
		this.home();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[3]")).click();
	}

    public void storeSearchData(){
      
    	//storing the webelements in a list
    	ExtractStoredData storedData = new ExtractStoredData(driver);
    	storedData.storeData();
        firstNames = storedData.getFirstNames(); 
        lastNames = storedData.getLastNames();
        pincodes = storedData.getPincodes();
        
    }
    
    public void enterSearch(String searchData) {
    	driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input")).sendKeys(searchData);
    }
    
    //Testing for search 
    public void searchTesting(String searchData, String category) throws InterruptedException {
    	
    	this.searchSetup();
    	
    	this.storeSearchData();
    	
    	this.enterSearch(searchData);
    	Thread.sleep(1000);
    	
    	    
    		if(category.equalsIgnoreCase("first name")) {
    			int count = 0, countAll=0;
    			boolean status=false;
    			WebElement tempElement = null;
    			ExtractStoredData storedData = new ExtractStoredData(driver);
    	    	storedData.storeData();
    	        firstNames = storedData.getFirstNames();
        		for(WebElement e : firstNames) {
            		if(e.getText().equalsIgnoreCase(searchData)) {
            			tempElement=e;
            			status=true;
            			count++;
            		}
            		countAll++;
            	}
        		if(status) {
        			Assert.assertTrue(count==1 && (tempElement.isDisplayed()));
        		}
        		else
        			Assert.assertTrue(countAll==0);
        	}
    		
    		if(category.equalsIgnoreCase("last name")) {
    			int count = 0, countAll=0;
    			boolean status=false;
    			WebElement tempElement = null;
    			ExtractStoredData storedData = new ExtractStoredData(driver);
    	    	storedData.storeData();
    	        lastNames = storedData.getLastNames();
    	        for(WebElement e : lastNames) {
            		if(e.getText().equalsIgnoreCase(searchData)) {
            			tempElement=e;
            			status=true;
            			count++;
            		}
            	}
        		System.out.println(status);
        		if(status) {
        			Assert.assertTrue(count==1 && (tempElement.isDisplayed()));
        		}else
        			Assert.assertTrue(countAll==0);
        	}
    		if(category.equalsIgnoreCase("pincode")) {
    			int count = 0, countAll=0;
    			boolean status=false;
    			WebElement tempElement = null;
    			ExtractStoredData storedData = new ExtractStoredData(driver);
    	    	storedData.storeData();
    	        pincodes = storedData.getPincodes();
    	        for(WebElement e : pincodes) {
            		if(e.getText().equalsIgnoreCase(searchData)) {
            			tempElement=e;
            			status=true;
            			count++;
            		}
            	}
        		System.out.println(status);
        		if(status) {
        			Assert.assertTrue(count==1 && (tempElement.isDisplayed()));
        		}else
        			Assert.assertTrue(countAll==0);
        	}
    		
    }
}