package selenium.pom.pageObjects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.keyword.common.Constants;
import selenium.keyword.utility.Log;

public class IndexPage {

	// 初始化驱动对象
	private WebDriver driver;

	// 初始化页面元素
	public IndexPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// PageFactory.initElements(new AjaxElementLocatorFactory(driver,
		// TIMEOUT), this);
	}

	@FindBy(css = ".first-line>button")
	@CacheLookup
	// 缓存
	WebElement signIn;

	@FindBy(xpath = "//div[@id='juejin']//div[contains(text(),'幸运抽奖')]")
	@CacheLookup
	List<WebElement> luckyLottery;

	@FindBy(xpath = "//div[contains(text(),'次')]")
	@CacheLookup
	WebElement cost;

	@FindBy(css = ".wrapper>button.submit")
	@CacheLookup
	WebElement againCost;

	@FindBy(css = "span.value")
	@CacheLookup
	WebElement value;

	@FindBy(xpath = "//button[text()='去看看']")
	@CacheLookup
	WebElement toSee;

	@FindBy(xpath = "//button[text()='跳过']")
	@CacheLookup
	WebElement jumping;

	/**
	 * 打开浏览器，中间有cookie操作，然后在刷新页面
	 * 
	 * @throws Exception
	 */
	public void OpenBrowser() throws Exception {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(Constants.JjinUrl);
		ReadCookieFromText();
		driver.navigate().refresh();
	}

	public void CloseBrowser() {
		if (driver != null) {
			driver.quit();
			Log.info("退出浏览器");
		} else {
			Log.warn("浏览器已经在操作步骤中关闭了");
		}
	}

	public void AgainCost() {
		waitForVisibility(againCost);
		againCost.click();
		driver.navigate().refresh();
	}

	/**
	 * 这一步比较复杂，就是需要判断是否有toast弹窗；然后在一步步的点击
	 */
	public void ClickSignIn() {
		if (toSee.isDisplayed()) {
			toSee.click();
			jumping.click();
			signIn.click();
		} else {
			signIn.click();
		}
	}

	/**
	 * 点击幸运抽奖，因为它有一个隐藏元素，所以是list返回，然后再判断点击显示的那个元素
	 */
	public void ClickLottery() {
		for (WebElement el : luckyLottery) {
			if (el.isDisplayed()) {
				el.click();
			}
		}
	}

	/**
	 * 需要判断，没有钻石则关闭浏览器
	 */
	public void ClickCostLottery() {
		if (CheckIsLottery()) {
			waitForClickable(cost);
			cost.click();
		} else {
			driver.quit();
			System.out.println("没有钻石抽奖了,关闭浏览器");
		}
	}

	/**
	 * 写一个通用的等待元素方法
	 * 
	 * @param element
	 * @throws Error
	 */
	private void waitForVisibility(WebElement element) throws Error {
		new WebDriverWait(driver, 30).until(ExpectedConditions
				.visibilityOf(element));
	}

	/**
	 * 可点击元素
	 * 
	 * @param element
	 * @throws Error
	 */
	private void waitForClickable(WebElement element) throws Error {
		new WebDriverWait(driver, 10).until(ExpectedConditions
				.elementToBeClickable(element));
	}

	/**
	 * 检查是否够钻石抽奖
	 * 
	 * @return
	 */
	public boolean CheckIsLottery() {
		waitForVisibility(value);
		int v = Integer.parseInt(value.getText());
		System.out.println(v);
		if (v < 200) {
			return false;
		}
		return true;
	}

	/**
	 * 这个方法用来手动登录获取cookies
	 * 
	 * @throws Exception
	 */
	public void WriteCookieToText() throws Exception {
		driver.get(Constants.JjinUrl);
		Thread.sleep(20000);
		Set<Cookie> cookies = driver.manage().getCookies();
		File cookieFile = new File("cookie.txt");
		cookieFile.delete();
		cookieFile.createNewFile();
		FileWriter fileWriter = new FileWriter(cookieFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (Cookie cookie : cookies) {
			bufferedWriter.write(cookie.getDomain() + ";" + cookie.getName()
					+ ";" + cookie.getValue() + ";" + cookie.getExpiry() + ";"
					+ cookie.getPath());
			bufferedWriter.newLine();
		}
		bufferedWriter.flush();
		bufferedWriter.close();
		fileWriter.close();
		driver.quit();
	}

	/**
	 * 用来读取txt中的cookie信息并加入driver
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public void ReadCookieFromText() throws Exception {
		BufferedReader bufferedReader;
		File cookieFile = new File("cookie.txt");
		FileReader fileReader = new FileReader(cookieFile);
		bufferedReader = new BufferedReader(fileReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
			while (stringTokenizer.hasMoreTokens()) {
				String domain = stringTokenizer.nextToken();
				String name = stringTokenizer.nextToken();
				String value = stringTokenizer.nextToken();
				Date expiry = null;
				String dt;
				if (!(dt = stringTokenizer.nextToken()).equals("null")) {
					expiry = new Date(dt);
				}
				String path = stringTokenizer.nextToken();
				Cookie cookie = new Cookie(name, value, domain, path, expiry);
				driver.manage().addCookie(cookie);
			}
		}
	}

	/**
	 * 抽奖业务流：想抽取几次都可以在这里控制
	 * 
	 * @throws Exception
	 */
	public void Lottery() throws Exception {
		OpenBrowser();
		ClickSignIn();
		ClickLottery();
		for (int i = 0; i < 20; i++) {
			ClickCostLottery();
			AgainCost();
		}
	}

	/**
	 * 程序执行入口，没有再去另起测试用例了
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", Constants.driverUrl);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		IndexPage index = new IndexPage(driver);
		index.Lottery();
	}
}