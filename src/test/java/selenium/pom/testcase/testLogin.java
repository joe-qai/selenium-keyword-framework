package selenium.pom.testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.keyword.common.Constants;
import selenium.pom.pageObjects.LoginPage;

public class testLogin {

	// 这就是一个页面对象
	LoginPage loginPage;

	/**
	 * 初始化driver操作，打开浏览器
	 */
	@BeforeClass(enabled=false)
	void setUp() {
		loginPage = new LoginPage(null);
		loginPage.OpenBrowser();
	}

	/**
	 * 关闭浏览器
	 */
	@AfterClass(enabled=false)
	void tearDown() {
		loginPage.CloseBrowser();
	}

	/**
	 * 数据驱动注解
	 * 
	 * @return
	 */
	@DataProvider(name = "datas")
	Object[][] data() {
		return null;

	}
	
	
	@Test(groups = { "p0" })
	@Parameters({"params1","params2"})
	void test1(String xx,String yy){
		System.out.println(xx+yy);
	}
	/**
	 * 测试用例
	 * 
	 * 如果有引用数据驱动的话，就在test中只想@DataProvider注解的name;dataProvider="datas";
	 * 
	 * 如果数据量小，可以使用@Parameters()注解，只不过需要在testng的xml中指明
	 */
	@Test(enabled=false)
	void testLoginLemon() {
		// loginPage.Login_Page();
		loginPage.Click_Login_Btn();
		loginPage.Input_userName(Constants.userName);
		loginPage.Input_Passwd(Constants.passwd);
		loginPage.Submit();
	}

}
