package selenium.keyword.starter;

import java.io.IOException;

import selenium.keyword.Kwds.KeywordsDriven;
import selenium.keyword.common.Common_Engine;
import selenium.keyword.common.Constants;
import selenium.keyword.common.Screenshots;
import selenium.keyword.utility.ExcelUtils;
import selenium.keyword.utility.Log;

/**
 * 
 * TODO:这个类的处理逻辑是和Excel表格对应的，如果要更改Excel只需要改这个类，或者新增加一个和Excel对应的类
 *
 * @author Joe-Tester
 * @time 2021年8月27日
 * @file StartEngine.java
 */
public class StartEngine {

	public static String Keywords = null;
	public static String r;
	public static boolean bResult;

	public static void StartEngine(Object actionKeyWords) throws IOException {

		// 初始化结果状态
		bResult = true;
		// 打开excel用例文件
		ExcelUtils.setExcelFile(Constants.excelFile + Constants.excelName);
		// 获取执行场景总行数
		for (int j = 1; j < ExcelUtils.getLastRowNums(Constants.suitSheet); j++) {
			// 是否需要执行
			String caseStatus = ExcelUtils.getCellDate(j, Constants.suitRunmode,
					Constants.suitSheet);
			// 获取场景的id作为操作步骤的sheet
			String testStepsSheet = ExcelUtils.getCellDate(j,
					Constants.suitTestSuiteId, Constants.suitSheet);
			int row;
			// 遍历场景行数，判断是否需要执行
			if (caseStatus.equals("YES")) {
				Log.startTestCase("执行第" + j + "条用例:" + testStepsSheet);
				// 遍历操作步骤的关键字及操作元素
				// Log.info(suitTestSuiteId+"，sheet页，最后一行的行号："+ExcelUtils.getLastRowNums(suitTestSuiteId));
				for (row = 0; row < ExcelUtils.getLastRowNums(testStepsSheet); row++) {
					// 操作的关键字
					String Keywords = ExcelUtils.getCellDate(row,
							Constants.excelKWCloNum, testStepsSheet);
					// 操作的页面元素
					String r = ExcelUtils.getCellDate(row,
							Constants.excelPOCloNum, testStepsSheet);
					// 操作的值
					String v = ExcelUtils.getCellDate(row,
							Constants.excelVaCloNum, testStepsSheet);
					// 调用关键字，注意suitTestSuiteId作为用例步骤的sheet页签的名字
					Common_Engine.Action(Keywords, actionKeyWords, r, v, row,
							bResult, testStepsSheet);

					// 回写用例步骤页
					if (bResult == false) {
						ExcelUtils.setCellData(Constants.fail, j,
								Constants.suitResult, Constants.excelFile
										+ Constants.excelName,
								Constants.suitSheet);

						String destination = Screenshots.takeScreenshot(
								KeywordsDriven.driver, "error");

						ExcelUtils.insertPicExcel(destination, testStepsSheet);

					}
				}
				Log.endTestCase("执行第" + j + "条用例:" + testStepsSheet + "结束!!!");
				// 回写suit页
				if (bResult == true) {
					ExcelUtils.setCellData(Constants.pass, j,
							Constants.suitResult, Constants.excelFile
									+ Constants.excelName, Constants.suitSheet);
				}
			} else {
				Log.info("当前用例:" + testStepsSheet + ",执行状态为：" + caseStatus
						+ "，不执行!!!");
				// 如果出现YES-NO-YES的用例场景，在第一个NO之后的YES或者NO都不执行
				// break;
			}
		}
	}
}