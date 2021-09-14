package selenium.keyword.Kwds;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.keyword.common.Constants;
import selenium.keyword.starter.StartEngine;
import selenium.keyword.utility.Log;

/**
 * 
 * TODO:关键字驱动
 *
 * @author Joe-Tester
 * @time 2021年8月27日
 * @file Keywords_Driven.java
 */
public class KeywordsDriven {

	// 日志收集器
	// private static final Logger log =
	// LogManager.getLogger(KeywordsDriven.class.getName());

	public static WebDriver driver = null;
	public static WebElement element = null;
	public static List<WebElement> elements = null;
	public static int TIMEOUT = 20;
	public static JavascriptExecutor js = (JavascriptExecutor) driver;

	/**
	 * 只是驱动打开浏览器
	 * 
	 * @param locator
	 * @param value
	 */
	public static void OpenBrowser(String locator, String value) {

		try {
			System.setProperty("webdriver.chrome.driver", Constants.driverUrl);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts()
					.implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
			Log.info("Just Open the Browser...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * 输入测试地址url
	 * 
	 * @param locator
	 *            :没值的时候没有实际意义
	 * @param value
	 */
	public static void Navigate(String locator, String value) {
		Log.info("Navigating to URL：" + value);
		driver.get(value);
	}

	/**
	 * 通用点击元素的操作
	 * 
	 * @param locator
	 * @param value
	 */
	public static void Click(String locator, String value) {
		try {
			element = getElement(locator);
			Log.info("Clicked on Webelement");
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通用输入文本的操作
	 * 
	 * @param locator
	 * @param value
	 */
	public static void Input(String locator, String value) {
		try {
			element = getElement(locator);
			element.clear();
			Log.info("Input enter the text：" + value);
			element.sendKeys(value);
		} catch (Exception e) {
			e.printStackTrace();
			// 同样赋值一个结果false
			// StartEngine.bResult = false;
		}
	}

	/**
	 * 关闭浏览器
	 * 
	 * @param locator
	 * @param value
	 */
	public static void CloseBrowser(String locator, String value) {
		try {
			Thread.sleep(500);
			Log.info("Closing the Browser...");
			driver.quit();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 强制睡眠
	 * 
	 * @param locator
	 * @param value
	 */
	public static void WaitMoment(String locator, String value) {
		long t = Long.parseLong(value);
		try {
			Log.info("Wait For a Moment");
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * js一般定位元素、或操作元素；可以参考这个方法或click或input
	 * 
	 * @param locator
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static String JScriptExcuteClick(String locator, String value)
			throws IOException {

		if (!locator.contains(";")) {
			Log.error("element format is Error：" + locator);
			WebElement el = (WebElement) js.executeScript(locator);
			el.clear();
			if (!value.isEmpty()) {
				el.sendKeys(value);
			} else {
				el.click();
			}
		} else {
			element = getElement(locator);
			js.executeScript(value, element);
		}
		return value;
	}

	/**
	 * 封装通用查找元素方法
	 * 
	 * @param locator
	 * @return
	 * @throws IOException
	 */
	public static WebElement getElement(String locator) throws IOException {
		// 约定大于配置，表达式id;kw；定位元素方法和元素以;分号隔开
		if (!locator.contains(";")) {
			Log.error("element format is Error：" + locator);
			return element;
		}
		// 拆分字符串数组
		String[] obj_ele = locator.split(";");
		String by = String.valueOf(obj_ele[0]).toLowerCase();// 第一个元素最小化
		String el = String.valueOf(obj_ele[1]);// 第二个为元素表达式

		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

		try {
			// 条件判断定位元素方法
			if (by.equals("id")) {
				// element = driver.findElement(By.id(el));
				// 下面这种操作，每次都需要显示等待元素出现
				element = wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.id(el)));
			} else if (by.equals("xpath")) {
				element = driver.findElement(By.xpath(el));
			} else if (by.equals("css")) {
				element = driver.findElement(By.cssSelector(el));
			} else if (by.equals("name")) {
				element = driver.findElement(By.name(el));
			} else if (by.equals("partiallinktext")) {
				element = driver.findElement(By.partialLinkText(el));
			} else if (by.equals("linktext")) {
				element = driver.findElement(By.linkText(el));
			} else if (by.equals("tagname")) {
				element = driver.findElement(By.tagName(el));
			} else if (by.equals("classname")) {
				element = driver.findElement(By.className(el));
			} else {
				Log.error("selenium can't support this method: " + by);
				return null;
			}
		} catch (Exception e) {
			StartEngine.bResult = false;
			e.printStackTrace();
		}
		Log.info("Finding element is：" + el);
		return element;
	}

	/**
	 * 封装通用查找元素方法
	 * 
	 * @param locator
	 * @return
	 */
	public static List<WebElement> getElements(String locator) {
		// 约定大于配置，表达式id;kw；定位元素方法和元素以;分号隔开
		if (!locator.contains(";")) {
			Log.error("element format is Error：" + locator);
			return elements;
		}
		// 拆分字符串数组
		String[] obj_ele = locator.split(";");
		String by = String.valueOf(obj_ele[0]).toLowerCase();// 第一个元素最小化
		String el = String.valueOf(obj_ele[1]);// 第二个为元素表达式

		// 条件判断定位元素方法
		if (by.equals("id")) {
			elements = driver.findElements(By.id(el));
		} else if (by.equals("xpath")) {
			elements = driver.findElements(By.xpath(el));
		} else if (by.equals("css")) {
			elements = driver.findElements(By.cssSelector(el));
		} else if (by.equals("name")) {
			elements = driver.findElements(By.name(el));
		} else if (by.equals("partiallinktext")) {
			elements = driver.findElements(By.partialLinkText(el));
		} else if (by.equals("linktext")) {
			elements = driver.findElements(By.linkText(el));
		} else if (by.equals("tagname")) {
			elements = driver.findElements(By.tagName(el));
		} else if (by.equals("classname")) {
			elements = driver.findElements(By.className(el));
		} else {
			Log.error("selenium can't support this method: " + by);
			return null;
		}
		Log.info("Finding element is：" + el);
		return elements;
	}

	/**
	 * 这个方法很鸡肋
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isElementPresent(String locator) {
		List<WebElement> elementList = getElements(locator);
		int size = elementList.size();
		if (size > 0) {
			return true;
		} else {
			return false;
		}
	}
}