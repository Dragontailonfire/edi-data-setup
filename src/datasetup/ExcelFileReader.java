package datasetup;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExcelFileReader {
	private static final Logger logger = LogManager.getLogger(ExcelFileReader.class);
	
	private static XSSFWorkbook ExcelWorkBook = null;
	private static XSSFSheet ExcelWorkSheet = null;
	private static XSSFCell ExcelCell = null;
	
	private static HSSFWorkbook Workbook;
	private static HSSFSheet Worksheet;
	private static HSSFCell Cell;

	public static void setExcelFile(String Path, String SheetName){

		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			
			if(Path.contains(".xslx")){
				ExcelWorkBook = new XSSFWorkbook(ExcelFile);
				ExcelWorkSheet = ExcelWorkBook.getSheet(SheetName);
			} else {			
				Workbook = new HSSFWorkbook(ExcelFile);
				Worksheet = Workbook.getSheet(SheetName);
			}
			logger.info("Loading excel sheet " + SheetName + "...");
		} catch (Exception e) {
			logger.fatal("Unable to load Excel sheet!", e);
			System.exit(0);
		}
	}

	/*public static String getCellData(int rowNum, int colNum){
		try {
			String CellData = null;
			if (ExcelWorkBook != null) {
				ExcelCell = ExcelWorkSheet.getRow(rowNum).getCell(colNum);
				CellData = ExcelCell.getStringCellValue();
			} else {
				Cell = Worksheet.getRow(rowNum).getCell(colNum);
				CellData = Cell.getStringCellValue();
			}
			return CellData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}*/

	public static String getInputFileName(int row){
		try {
			String CellData = null;
			if (ExcelWorkBook != null) {
				ExcelCell = ExcelWorkSheet.getRow(row).getCell(0);
				CellData = ExcelCell.getStringCellValue() + ExcelWorkSheet.getRow(row).getCell(1);
			} else {
				Cell = Worksheet.getRow(row).getCell(0);
				CellData = Cell.getStringCellValue() + Worksheet.getRow(row).getCell(1);
			}
			logger.info("Reading File name value for row: " + row + "....");
			return CellData;
		} catch (Exception e) {
			logger.warn("Cell does not contain any data!", e);
			return null;
		}
	}
	public static String getAppendingPhrase(int row, int col){
		try {
			String CellData = null;
			if (ExcelWorkBook != null) {
				ExcelCell = ExcelWorkSheet.getRow(row).getCell(col);
				CellData = ExcelCell.getStringCellValue();
			} else {
				Cell = Worksheet.getRow(row).getCell(col);
				CellData = Cell.getStringCellValue();
			}
			logger.info("Reading Modification value for row: " + row + " col:" + col);
			return CellData;
		} catch (Exception e) {
			logger.warn("Cell does not contain any data!", e);
			return null;
		}
	}
}
