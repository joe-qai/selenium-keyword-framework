package selenium.keyword.testcase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import selenium.keyword.Kwds.KeywordsDriven;
import selenium.keyword.common.Constants;
import selenium.keyword.starter.StartEngine;

/**
 * 
 * TODO: 用例演示
 *
 * @author Joe-Tester
 * @time 2021年9月8日
 * @file testExamlpe.java
 */
public class testExamlpe {

	// 初始化关键字对象类
	public static Object actionKeyWords;

	/**
	 * 测试用例
	 * 
	 * @throws IOException
	 */
	@Test(groups = { "p0" }, enabled = false)
	void KeywordsTestCase() throws IOException {
		actionKeyWords = new KeywordsDriven();
		StartEngine.StartEngine(actionKeyWords);

	}

	@Test
	void test() {
		System.setProperty("webdriver.chrome.driver", Constants.driverUrl);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(Constants.lemonUrl);
		driver.findElement(By.linkText("登录")).click();
		driver.findElement(By.name("phone")).sendKeys("selenium");
		driver.findElement(By.name("password")).sendKeys("12345");
		driver.findElement(By.xpath("//button[text()='登录']")).click();
		driver.quit();
	}

}