package selenium.keyword.common;

import java.lang.reflect.Method;

import selenium.keyword.utility.ExcelUtils;

/**
 * 
 * TODO:通用引擎，执行的是test steps关键字操作步骤，java反射获取关键字方法
 *
 * @author Joe-Tester
 * @time 2021年8月27日
 * @file Common_Engine.java
 */
public class Common_Engine {

	// 日志收集器
	// private static final Logger log =
	// LogManager.getLogger(Common_Engine.class.getName());

	/**
	 * 
	 * @param Keywords
	 *            ：读取excel中关键字，判断是否在关键字对象中
	 * @param actionKeyWords
	 *            ：关键字对象
	 * @param element
	 *            :关键操作对象的元素
	 * @param value
	 *            ：输入值
	 * @param rowNum
	 *            : 操作步骤的行号
	 * @param bResult
	 *            : 结果
	 * @param SheetName
	 *            ：执行场景步骤sheet页
	 */
	public static void Action(String Keywords, Object actionKeyWords,
			String element, String value, int rowNum, boolean bResult,
			String SheetName) {
		// java反射获取类对象的所有方法
		/* actionKeyWords= new Keywords_Driven(); */
		Method[] method = actionKeyWords.getClass().getMethods();
		// 遍历反射类对象
		for (int i = 0; i < method.length; i++) {
			// 判断获取对象的方法等于读取excel的关键字
			if (method[i].getName().trim().equals(Keywords)) {
				try {
					// invoke调用关键字方法，字符串参数
					method[i].invoke(actionKeyWords, element, value);
					// Log.info("调用关键字<" + Keywords + ">方法.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 如果执行关键字方法回写用例执行步骤excel
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.pass, rowNum,
							Constants.testResult, Constants.excelFile
									+ Constants.excelName, SheetName);
				} else {
					ExcelUtils.setCellData(Constants.fail, rowNum,
							Constants.testResult, Constants.excelFile
									+ Constants.excelName, SheetName);
				}
				break;
			}
		}
	}
}