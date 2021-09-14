package selenium.keyword.common;

/**
 * 
 * TODO:常量池
 *
 * @author Joe-Tester
 * @time 2021年8月27日
 * @file Contants.java
 */
public class Constants {
	
	public static String JjinUrl="https://juejin.cn/";
	public static String lemonUrl = "http://8.139.91.122:8765";
	public static String userName = "15662319372";
	public static String passwd = "hui121356";
	// 定义基本路径
	public static String baseUrl = System.getProperty("user.dir");
	// 定义驱动路径
	public static String driverUrl = baseUrl + "/drivers/chromedriver.exe";
	// Excel的路径和名称
	public static String excelFile = baseUrl + "/src/test/resources/";
	public static String excelName = "dataEngine.xls";

	public static String envFile = baseUrl + "/src/test/resources/";
	public static String envName = "env.properties";

	// 用例场景的sheet页
	public static String suitSheet = "TestSuites";
	// Suite页的设置
	public static int suitTestSuiteId = 0;
	public static int suitRunmode = 2;
	public static int suitResult = 3;

	// 用例步骤页的设置
	public static int testStepId = 0;
	public static int excelKWCloNum = 2;
	public static int excelPOCloNum = 3;
	public static int excelVaCloNum = 4;
	public static int testResult = 5;
	// 成功和失败
	public static String fail = "FAIL";
	public static String pass = "PASS";
}
