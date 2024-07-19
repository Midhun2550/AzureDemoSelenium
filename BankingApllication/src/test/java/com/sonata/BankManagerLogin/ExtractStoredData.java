package com.sonata.BankManagerLogin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExtractStoredData{
	
	private WebDriver driver;
	
	public List<WebElement> firstNames; 
	public List<WebElement> lastNames;
	public List<WebElement> pincodes;
	public List<WebElement> deleteCustomerList;
	int noOfRows;
	
	ExtractStoredData(WebDriver driver){
		this.driver = driver;
        this.driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list");
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
    public List<WebElement> getFirstNames() {
		return firstNames;
	}

	public List<WebElement> getLastNames() {
		return lastNames;
	}

	public List<WebElement> getPincodes() {
		return pincodes;
	}
	
	public List<WebElement> getDeleteCustomerList() {
		return deleteCustomerList;
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}

	
	public void storeData(){
    	//Values in the webpage Table 
        WebElement table = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/table"));
        //Number of Rows in the table
        noOfRows = table.findElements(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr")).size();
        
        
        //Creating to the list
        firstNames = new ArrayList(); 
        lastNames = new ArrayList();
        pincodes = new ArrayList();
        deleteCustomerList = new ArrayList();
        
        for(int i=1; i<=noOfRows; i++) {
        	//Adding to the list
        	firstNames.add(driver.findElement(By.cssSelector("tbody >tr:nth-child(" + i + ") > td:nth-child(" + 1 + ")")));
        	lastNames.add(driver.findElement(By.cssSelector("tbody >tr:nth-child(" + i + ") > td:nth-child(" + 2 + ")")));
        	pincodes.add(driver.findElement(By.cssSelector("tbody >tr:nth-child(" + i + ") > td:nth-child(" + 3 + ")")));
        	deleteCustomerList.add(driver.findElement(By.cssSelector("tbody > tr:nth-child(" + i +  ") > td:nth-child(5) > button")));
        }
    }
}