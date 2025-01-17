package com.sonata.BankManagerLogin;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

//import com.sonata.BankManagerLogin.TakeScreenshot;

public class ManagerLogin {

	private static WebDriver driver;
	
	//driver instantiating
	ManagerLogin(WebDriver driver){
		ManagerLogin.driver = driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	
	public static void setFirstName(String firstname) {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input")).sendKeys(firstname);
	}
	
	public static void setLasttName(String lastname) {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input")).sendKeys(lastname);
	}
	
	public static void setPincode(String pincode) {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input")).sendKeys(pincode);
	}
	
	public static void clickAddButton() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
	}
	
	public static void managerLogin() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
        driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public static void clickAddCustomer() {
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();
	}
	
	
	public static void alert() {
		driver.switchTo().alert();
	}
	
	public static void home() {
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
	}
	
	//adding customer Testing function
	public void addCustomerTest(String firstName, String lastName, String pin, ExtentTest test) throws IOException, InterruptedException, AWTException {
		managerLogin();
		clickAddCustomer();
		setFirstName(firstName);
		setLasttName(lastName);
		setPincode(pin);
		clickAddButton();
		
		Pattern patternName = Pattern.compile("^[a-zA-Z]+$");
		Matcher firstNameMatcher = patternName.matcher(firstName);
		Matcher lastNameMatcher = patternName.matcher(lastName);
		
		Pattern patternPincode = Pattern.compile("^[0-9]+$");
		Matcher pincodeMatcher = patternPincode.matcher(pin);
		try {
			if(firstName.length()==0  || lastName.length()==0 || pin.length()==0) { 
				driver.switchTo().alert();
				Thread.sleep(1000);
				TakeScreenshot.takeScreenshot(test);
				throw new AssertionError("Failed to validate empty fields");
		    }
		    if(!firstNameMatcher.find() || !lastNameMatcher.find() || !pincodeMatcher.find()){
			   driver.switchTo().alert();
			   Thread.sleep(1000);
			   TakeScreenshot.takeScreenshot(test);
			   throw new AssertionError("Failed to Validate fields");
		    }
		    if((pin.length()>6 || pin.length()<6) || pin.equals("000000")) {
			   driver.switchTo().alert();
			   Thread.sleep(1000);
			   TakeScreenshot.takeScreenshot(test);
			   throw new AssertionError("Failed to Validate pincode");
		   }
		    else {
		    	driver.switchTo().alert().accept();
		    }
		      
		}catch(AssertionError e) {}
		catch(NoAlertPresentException e) {}
		
		home();
		
	}
}
