package selenium.keyword.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import selenium.keyword.common.Constants;

public class ExcelUtilss {

	private static Sheet ExcelWSheet;
	private static Workbook workbook;
	private static Row row;
	private static Cell cell;

	/**
	 * 处理Excel文件
	 * 
	 * @param Path
	 * @param SheetName
	 */
	public static void setExcelFile(String Path, String SheetName) {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// 按道理应该是需要判断xlsx和xls的后缀名，可以split分割数组在判断
			if (Path.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(ExcelFile);
			} else if (Path.endsWith("xls")) {
				workbook = new HSSFWorkbook(ExcelFile);
			} else {
				Log.warn(Path + ",文件格式不正确!!!");
			}
			ExcelWSheet = workbook.getSheet(SheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取指定行和列单元格的值
	 * 
	 * @param RowNum
	 * @param ColNum
	 * @return
	 */
	public static String getCellData(int RowNum, int ColNum) {

		try {

			cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = cell.getStringCellValue();

			return CellData;

		} catch (Exception e) {

			return "";

		}

	}

	/**
	 * 获取最后sheet最后一行
	 * 
	 * @param SheetName
	 * @return
	 */
	public static int getLastRowNums(String SheetName) {
		try {
			ExcelWSheet = workbook.getSheet(SheetName);
			int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
			// getLastRowNum()+1;//最后一行行标，比行数小1
			return rowCount;
		} catch (Exception e) {
			throw (e);
		}
	}

	/**
	 * 获取sheet页的某行的列数
	 * 
	 * @param SheetName
	 * @param rowNum
	 *            行号
	 * @return
	 */
	public static int getlastColNums(String SheetName, int rowNum) {
		try {
			ExcelWSheet = workbook.getSheet(SheetName);
			row = ExcelWSheet.getRow(rowNum);
			int colCount = row.getLastCellNum() - 1;
			// getLastCellNum()-1; 获取的值从1开始，列在excel同行，索引从0开始，那么需要-1
			return colCount;
		} catch (Exception e) {
			throw (e);
		}
	}

	/**
	 * 来自Excel的数据源
	 * 
	 * @param SheetName
	 * @return
	 */
	public static Object[][] dataProviderByExcel(String SheetName) {
		ExcelWSheet = workbook.getSheet(SheetName);

		List<Object[]> records = new ArrayList<Object[]>();
		// getLastRowNum()+1;//最后一行行标，比行数小1
		for (int i = 1; i < ExcelWSheet.getLastRowNum() + 1; i++) {
			row = ExcelWSheet.getRow(i);
			// 声明一个数组files，用来存储excel数据文件每行数据，数组的大小用getLastCellNum方法来进行动态声明。
			String files[] = new String[row.getLastCellNum()];
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Cannot get a STRING value from a NUMERIC cell
				// ;为了解决单元格数据读取数字类型异常，先储存数据前强制转换String類型
				row.getCell(j).setCellType(CellType.STRING);
				files[j] = row.getCell(j).getStringCellValue();
			}
			// 把files的数据对象存在records中
			records.add(files);
		}
		Object[][] results = new Object[records.size()][];
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		return results;
	}

	/**
	 * 回写数据：要知道行和列，最好知道sheet页
	 * 
	 * @param Result
	 * @param RowNum
	 * @param ColNum
	 */
	public static void setCellData(String Result, int RowNum, int ColNum,
			String SheetName) {
		try {
			ExcelWSheet = workbook.getSheet(SheetName);
			row = ExcelWSheet.getRow(RowNum);
			cell = row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(Result);
			} else {
				cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(Constants.excelFile
					+ Constants.excelName);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}