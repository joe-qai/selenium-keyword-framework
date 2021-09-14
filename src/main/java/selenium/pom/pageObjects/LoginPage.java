package selenium.pom.pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.keyword.common.Constants;

/**
 * 
 * TODO:封装页面元素及操作对象库
 *
 * @author Joe-Tester
 * @time 2021年9月10日
 * @file LoginPage.java
 */
public class LoginPage {

	private WebDriver driver;
	private WebElement element = null;

	/**
	 * 约定元素书写格式：By>WebElement
	 */
	private String login_button = "linkText>登录";
	private String login_name = "name>phone";
	private String login_passwd = "name>password";

	@FindBy(linkText = "登录")
	@CacheLookup
	WebElement login_btn;

	@FindBy(name = "phone")
	@CacheLookup
	WebElement login_userName;

	@FindBy(name = "password")
	@CacheLookup
	WebElement login_passWord;

	@FindBy(xpath = "//button[text()='登录']")
	@CacheLookup
	WebElement login_btn2;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void loginPage_success() {

		driver.get(Constants.lemonUrl);
		login_btn.click();
		login_userName.sendKeys(Constants.userName);
		login_passWord.sendKeys(Constants.passwd);
		login_btn2.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();

	}

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", Constants.driverUrl);

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginPage_success();
	}

	/**
	 * 构造方法，后面设计会需要的
	 */
	// public LoginPage(WebDriver driver) {
	// this.driver = driver;
	// }

	/**
	 * 点击
	 */
	public void Click_Login_Btn() {
		element = getElement(login_button);
		element.click();
	}

	/**
	 * 输入帐号
	 * 
	 * @param userName
	 */
	public void Input_userName(String userName) {
		element = getElement(login_name);
		element.sendKeys(userName);
	}

	/**
	 * 输入密码
	 * 
	 * @param Passwd
	 */
	public void Input_Passwd(String Passwd) {
		element = getElement(login_passwd);
		element.sendKeys(Passwd);
	}

	/**
	 * 针对form表单提交数据
	 */
	public void Submit() {
		element.submit();
	}

	/**
	 * 打开浏览器
	 */
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", Constants.driverUrl);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(Constants.lemonUrl);
	}

	/**
	 * 关闭浏览器
	 */
	public void CloseBrowser() {
		try {
			Thread.sleep(2000);
			driver.quit();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 组装一个简单测试流程，可以用于测试，也可以简化测试用例流程
	 */
	public void Login_Page() {
		Click_Login_Btn();
		Input_userName(Constants.userName);
		Input_Passwd(Constants.passwd);
		Submit();
	}

	/**
	 * 封装通用查找元素方法:这个可以封装在BasePage通用页面中
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getElement(String locator) {

		String by = null;
		String el = null;

		if (locator.contains(">")) {
			by = locator.split(">")[0];
			el = locator.split(">")[1];
		}
		if (by.equals("id")) {
			element = driver.findElement(By.id(el));
		} else if (by.equals("name")) {
			element = driver.findElement(By.name(el));
		} else if (by.equals("tagName")) {
			element = driver.findElement(By.tagName(el));
		} else if (by.equals("className")) {
			element = driver.findElement(By.className(el));
		} else if (by.equals("linkText")) {
			element = driver.findElement(By.linkText(el));
		} else if (by.equals("partialLinkText")) {
			element = driver.findElement(By.partialLinkText(el));
		} else if (by.equals("cssSelector")) {
			element = driver.findElement(By.cssSelector(el));
		} else if (by.equals("xpath")) {
			element = driver.findElement(By.xpath(el));
		} else {
			return element;
		}
		return element;
	}
}