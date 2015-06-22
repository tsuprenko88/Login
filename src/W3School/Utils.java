package W3School;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class Utils {
	
	public String xlPath = "excel-input//W3School.xlsx";
	public String xlSheetName;
	public String xlWritePath;
	public String localArray[][];
	public int xlRows, xlCols;
	
	public WebDriver driver;
	public WebDriverWait wait;
	public Actions action;
	public WebElement element;
	
	boolean fail = false;
	List<String> failName = new ArrayList<String>();
	
	
	public void xlRead(String xlPath, String xlSheetName) throws Exception {
		File xlFile = new File(xlPath);
		FileInputStream xlInput = new FileInputStream(xlFile);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(xlInput);
		XSSFSheet sheet = workbook.getSheet(xlSheetName);
			xlRows = sheet.getLastRowNum() + 1;
			xlCols = sheet.getRow(0).getLastCellNum();
			//System.out.println("Total rows: " + xlRows);
			//System.out.println("Total columns: " + xlCols);
			localArray = new String[xlRows][xlCols];
			for (int i = 0; i < xlRows; i++) {
				XSSFRow row = sheet.getRow(i);
				for (int j = 0; j < xlCols; j++) {
					XSSFCell cell = row.getCell(j);
					String value = cellToString(cell);
					localArray[i][j] = value;
					//System.out.print(value + " ");
				}
				//System.out.println();
			}
	}
	
	public static String cellToString(XSSFCell cell) {
		int type = cell.getCellType();
		Object result;
		switch(type) {
		case 0:
			result = cell.getNumericCellValue();
			break;
		case 1:
			result = cell.getStringCellValue();
			break;
		default:
			throw new RuntimeException("This type is not supported");
		}
		return result.toString();
	}
	
	public void xlWrite(String xlWritePath, String xlSheetName, String[][] localArray) throws Exception {
		File xlOutput = new File(xlWritePath);
		WritableWorkbook workbook = Workbook.createWorkbook(xlOutput);
		workbook.createSheet(xlSheetName, 0);
		WritableSheet sheet = workbook.getSheet(0);
			for (int i = 0; i < localArray.length; i++) {
				for (int j = 0; j < localArray[i].length; j++) {
					if (localArray[i][j] != null) {
						Label data = new Label(j, i, localArray[i][j]);
						sheet.addCell(data);
					}
				}
			}
			workbook.write();
			workbook.close();
	}
	
	public void driverSetup() throws MalformedURLException {
		
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 4);
		action = new Actions(driver);
	}
	
	public void verifyFailandCloseDriver() throws Exception {
		if (fail) {
			Reporter.log("--------------------FAILED TESTS--------------------", true);
			for (int i = 0; i < failName.size(); i++) {
				Reporter.log(failName.get(i), true);
			}
			driver.close();
			driver.quit();
			throw new Exception("Look at \"FAILED TESTS\" section above");
		}
		driver.close();
		driver.quit();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
