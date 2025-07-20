package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class ExtentReportManager implements ITestListener {

    public ExtentReports extent;
    public ExtentSparkReporter sparkReporter;
//    private ExtentTest test;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    String repName;

    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("RestAssured Automation Report");
        sparkReporter.config().setReportName("PetStores Users API");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("user", "Ravi");

    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
//    	test=extent.createTest(result.getName());
//        test.createNode(result.getName());
//        test.assignCategory(result.getMethod().getGroups());
    	extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
//    	test=extent.createTest(result.getName());
//        test.createNode(result.getName());
//        test.assignCategory(result.getMethod().getGroups());
    	extentTest.get().log(Status.FAIL, "Test Failed");
    	extentTest.get().log(Status.FAIL, result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
//        test=extent.createTest(result.getName());
//        test.createNode(result.getName());
//        test.assignCategory(result.getMethod().getGroups());
    	extentTest.get().log(Status.SKIP, "Test Skipped");
    	extentTest.get().log(Status.SKIP, result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
