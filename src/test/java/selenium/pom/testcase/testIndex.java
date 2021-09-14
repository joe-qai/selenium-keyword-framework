package selenium.pom.testcase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import selenium.keyword.common.Constants;
import selenium.pom.pageObjects.IndexPage;

public class testIndex {

	IndexPage index;
	
	@BeforeClass
	void setUp(){
		System.setProperty("webdriver.chrome.driver", Constants.driverUrl);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		index = new IndexPage(driver);
	
	}
	
	@AfterClass
	void tearDown(){
		index.CloseBrowser();
	}
	
	@Test
	void test_lottery() throws Exception{
		index.Lottery();
	}
}
