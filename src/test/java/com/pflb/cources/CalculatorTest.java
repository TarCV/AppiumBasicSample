package com.pflb.cources;

import com.pflb.cources.screens.CalculatorScreen;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class CalculatorTest {
    private AndroidDriver<AndroidElement> driver;

    @Parameters({"applicationName", "platform"})
    public CalculatorTest(String applicationName, String platform) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.APPLICATION_NAME, applicationName);
        caps.setCapability(MobileCapabilityType.PLATFORM, platform);

        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.calculator2");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Calculator");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4444/wd/hub"), caps);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test() {
        CalculatorScreen calculatorScreen = new CalculatorScreen(driver);
        calculatorScreen
                .digit("2")
                .plus()
                .digit("2")
                .evaluate();
        Assert.assertEquals(calculatorScreen.getResult(), "4");
    }

    @Test
    public void test1() {
        CalculatorScreen calculatorScreen = new CalculatorScreen(driver);
        calculatorScreen
                .digit("3")
                .plus()
                .digit("3")
                .evaluate();
        Assert.assertEquals(calculatorScreen.getResult(), "6");
    }

    @Test
    public void test2() {
        CalculatorScreen calculatorScreen = new CalculatorScreen(driver);
        calculatorScreen
                .digit("4")
                .plus()
                .digit("4")
                .evaluate();
        Assert.assertEquals(calculatorScreen.getResult(), "8");
    }

    private String switchToWebview() {
        String oldContext = driver.getContext();
        String webContext = driver.getContextHandles().stream()
                .filter(context -> context.startsWith("WEBVIEW"))
                .findFirst()
                .get();
        driver.context(webContext);
        return oldContext;
    }
}
