package com.pflb.cources;

import com.pflb.cources.screens.CalculatorScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTest {
    private AndroidDriver<AndroidElement> driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        /*
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.calculator2");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Calculator");
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        */
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        caps.setCapability(AndroidMobileCapabilityType.BROWSER_NAME, "Chrome");
        caps.setCapability("chromedriverExecutable", new File("chromedriver").getAbsolutePath());
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.get("https://www.desmos.com/fourfunction");
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

    private String switchToWebview() {
        String oldContext = driver.getContext();
        String webContext = driver.getContextHandles().stream()
                .filter(context -> context.startsWith("WEBVIEW"))
                .findFirst()
                .get();
        driver.context(webContext);
        return oldContext;
    }


    /*
        private DesiredCapabilities getAwsAppCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.APP,
                "/Users/const/StudioProjects/aws-device-farm-sample-app-for-android/app/build/outputs/apk/app-debug.apk");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        caps.setCapability("chromedriverExecutable", new File("chromedriver").getAbsolutePath());
        return caps;
    }

    @Test
    public void testAws() {
        MobileElement menuBtn = driver.findElement(MobileBy.AccessibilityId("ReferenceApp"));
        menuBtn.click();
        MobileElement inputControlsItem =
                driver.findElement(MobileBy.AndroidUIAutomator(
                        "new UiSelector().text(\"Web\").enabled(true)"));
        inputControlsItem.click();

        AndroidElement urlBox = driver.findElement(MobileBy.id("website_input"));
        urlBox.sendKeys("https://www.desmos.com/fourfunction");
        driver.pressKeyCode(AndroidKeyCode.ENTER);

        String tmp = urlBox.getAttribute("not existing attribute");

        switchToWebview();

        driver.getPageSource();
    }
     */
}
