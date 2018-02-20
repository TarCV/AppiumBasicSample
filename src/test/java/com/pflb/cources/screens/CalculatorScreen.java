package com.pflb.cources.screens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CalculatorScreen extends BaseScreen {
    @AndroidFindBy(id = "op_add")
    @FindBy(css = "a[aria-label='Plus']")
    MobileElement plusButton;

    @AndroidFindBy(id = "eq")
    @FindBy(css = "a[aria-label='Answer']")
    MobileElement evaluateButton;

    @AndroidFindBy(id = "result")
    @FindBy(css = ".dcg-basic-expression-value span.dcg-mq-root-block")
    MobileElement resultText;

    public CalculatorScreen(WebDriver driver) {
        super(driver);
    }

    public CalculatorScreen digit(String digit) {
        WebElement btn;
        if (!isWebView()) {
            String selector = String.format("digit_%s", digit);
            btn = driver.findElement(MobileBy.id(selector));
        } else {
            String selector = String.format("a[aria-label='%s']", digit);
            btn = driver.findElement(By.cssSelector(selector));
        }
        btn.click();
        return this;
    }

    private boolean isWebView() {
        return !(driver instanceof AndroidDriver)
                || ((AndroidDriver) driver).getContext().contains("CHROMIUM")
                || ((AndroidDriver) driver).getContext().contains("WEBVIEW");
    }

    public CalculatorScreen plus() {
        plusButton.click();
        return this;
    }

    public CalculatorScreen evaluate() {
        if (!isWebView()) {
            evaluateButton.click();
        }
        return this;
    }

    public String getResult() {
        String result = resultText.getText();
        if (result.startsWith("=")) {
            return result.substring(1);
        } else {
            return result;
        }
    }
}
