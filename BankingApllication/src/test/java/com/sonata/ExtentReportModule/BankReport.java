package com.sonata.ExtentReportModule;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BankReport {

	public static ExtentReports extentReport;
	
	public static synchronized ExtentReports getExtent() {
		if(extentReport == null) {
			extentReport = new ExtentReports();
			String path="./test-output/Extent Report/ExtentReport.html";
			ExtentSparkReporter sparkReport = new ExtentSparkReporter(path);
			extentReport.attachReporter(sparkReport);
		}
		return extentReport;
	}
}
