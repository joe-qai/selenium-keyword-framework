package selenium.keyword.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

/**
 * 
 * TODO:截图方法
 *
 * @author Joe-Tester
 * @time 2021年8月27日
 * @file Screenshots.java
 */
public class Screenshots {

	public static String takeScreenshot(WebDriver driver, String fileName)
			throws IOException {
		// 加入时间格式，会生成不一样文件名的截图
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String mDateTime = formatter.format(new Date());
		fileName = fileName + "_" + mDateTime + ".png";
		// 保存图片目录
		String directory = Constants.baseUrl + "\\images\\";
		// 如何路径不存在则创建
		File reportDir = new File(directory);
		if (!reportDir.exists() && !reportDir.isDirectory()) {
			reportDir.mkdir();
		}
		// 截图
		File sourceFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		Files.copy(sourceFile, new File(directory + fileName));
		String destination = directory + fileName;
		return destination;
	}
}