package sivathoconjwaacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import sivathoconjwaacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	
	/* Thread Safe 
	 * -  means no matter if you are running concurrently each object creation have its own thread.
	 * -  it will not interrupt the other overriding variable
	 */
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal();

	@Override
	public void onTestStart(ITestResult result) {
		/*
		 * We need to create an entry for the test in the extent report
		 * "result.getMethod().getMethodName()" displays the test related data will get
		 * the test case name
		 */
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); // for the test object it will assign one unique thread ID (Error Validation) -> test
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// Log status and message
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Log status and message
		// test.log(Status.FAIL, "Test Failed");

		/*
		 * "test.fail" method will display the test error/failure message
		 * "result.getThrowable()" displays the test related data
		 */
		extentTest.get().fail(result.getThrowable()); //

		// Screenshot, attach it to the report
		String filePath = null;

		// Getting driver variable from test case
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		} catch (Exception e) { // "Exception" is generic or parent if all exceptions
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			filePath = getScreenShot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); // (file path, and the name you want
																						// the screenshot to display on
																						// the report).
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// not implemented
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// not implemented
	}

	@Override
	public void onFinish(ITestContext context) {
		// Entire execution of all test cases is done. This is the final method that is
		// going to execute at last before we show the report.

		/*
		 * Flush extent report This will tell us to finally grab the report and generate
		 * it.
		 */
		extent.flush();
	}

}
