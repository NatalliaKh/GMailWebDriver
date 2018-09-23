package com.epam.gmailwebdriver.reporting;

import com.epam.gmailwebdriver.utils.Screenshoter;
import com.epam.reportportal.testng.BaseTestNGListener;
import com.epam.reportportal.testng.ITestNGService;
import com.epam.reportportal.testng.TestNGService;
import org.testng.ITestResult;
import rp.com.google.common.base.Supplier;
import rp.com.google.common.base.Suppliers;

public class ReportPortalTestListener extends BaseTestNGListener {
    private static final Supplier<ITestNGService> SERVICE = Suppliers.memoize(new Supplier<ITestNGService>() {
        public ITestNGService get() {
            return new TestNGService();
        }
    });

    public ReportPortalTestListener() {
        super((ITestNGService)SERVICE.get());
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);

        MyLogger.info("Test failed, screenshot can be found here: " + Screenshoter.takeScreenshot());
    }
}
