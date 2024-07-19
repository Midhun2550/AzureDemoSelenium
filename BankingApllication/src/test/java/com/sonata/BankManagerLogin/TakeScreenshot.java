package com.sonata.BankManagerLogin;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aventstack.extentreports.ExtentTest;

public class TakeScreenshot {

	public static int failure=0; //failure count
	
	public static void takeScreenshot(ExtentTest test) throws IOException, AWTException {
		failure++;
		
		Robot robot = new Robot();
		
		Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage scrrenshot = robot.createScreenCapture(rect);//Taking screenshot
		File screenshotFile = new File("./test-output\\failedTestCasesOutputScreenShots\\managerLogin\\failure" + failure + ".png");
		ImageIO.write(scrrenshot, "png", screenshotFile);
		test.addScreenCaptureFromPath(screenshotFile.getAbsolutePath());		
    }
}
