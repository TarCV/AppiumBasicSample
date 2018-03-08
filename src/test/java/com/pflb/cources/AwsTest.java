package com.pflb.cources;

import com.pflb.cources.screens.CalculatorScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AwsTest {
    private AndroidDriver<AndroidElement> driver;

    public AwsTest() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.APP,
                new File("aws-device-farm-sample-app-for-android.apk").getAbsolutePath());
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
        caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        caps.setCapability("chromedriverExecutable", new File("chromedriver").getAbsolutePath());
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAws() {
        AndroidElement menuBtn = driver.findElement(MobileBy.AccessibilityId("ReferenceApp"));
        menuBtn.click();
        AndroidElement inputControlsItem =
                driver.findElement(MobileBy.AndroidUIAutomator(
                        "new UiSelector().text(\"Web\").enabled(true)"));
        inputControlsItem.click();

        AndroidElement urlBox = driver.findElement(MobileBy.id("website_input"));
        urlBox.sendKeys("https://www.desmos.com/fourfunction");
        driver.pressKeyCode(AndroidKeyCode.ENTER);

        switchToWebview();

        Assert.assertTrue(driver.getPageSource().contains("<html"), "Switched to WebView");
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
