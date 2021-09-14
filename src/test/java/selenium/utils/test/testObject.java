package selenium.utils.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import selenium.keyword.common.Constants;
import selenium.keyword.utility.ExcelUtilss;

public class testObject {

	@DataProvider(name = "dataProvider")
	Object[][] dataProvider() throws Exception {
		ExcelUtilss.setExcelFile(Constants.excelFile + Constants.excelName,
				Constants.suitSheet);
		return ExcelUtilss.dataProviderByExcel(Constants.suitSheet);
	}

	@Test(enabled = false, dataProvider = "dataProvider")
	void testDataProvider(String a, String b, String c, String d) {
		System.out.println(a + b + c);
		for (int i = 1; i < 6; i++) {
			int s = ExcelUtilss.getlastColNums(Constants.suitSheet, i);
			ExcelUtilss.setCellData("PASS", i, s, Constants.suitSheet);
		}
	}

	@DataProvider(name = "test")
	Object[][] Provider() {
		return new Object[][] { { "a", "b", "c" }, { "a1", "b2", "c3" } };
	}

	@Test(dataProvider = "test")
	void testProvider(String a, String b, String c) {
		System.out.println(a + b + c);
	}
}
