package utils;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import config.Configuration;
import config.ConfigurationManager;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;


@Log4j2
public class ExcelDataUtils {

    private static Sheet ExcelWSheet;
    private static Workbook ExcelWBook;
    private static Cell Cell;


    public static Object[][] getExcelData() throws Exception {

        Configuration configuration = ConfigurationManager.getConfiguration();
        String[][] dataArray = null;

        try {
            log.info(configuration.excelDataFile());
            FileInputStream ExcelFile = new FileInputStream(configuration.excelDataFile());
            ExcelWBook = WorkbookFactory.create(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(configuration.excelDataSheet());

            int startRow = 1;
            int startCol = 0;
            int ci, cj;
            int totalRows = ExcelWSheet.getLastRowNum();
            int totalCols = new Integer(configuration.excelTotalCols());
            dataArray = new String[totalRows][totalCols];

            ci = 0;
            for (int i = startRow; i <= totalRows; i++, ci++) {
                cj = 0;
                for (int j = startCol; j < totalCols; j++, cj++) {
                    dataArray[ci][cj] = getCellData(i, j);
                    log.info(dataArray[ci][cj]);
                }
            }

        } catch (FileNotFoundException e) {
            log.error("Excel Data File not found. Check file location", e);
        } catch (IOException e) {
            log.error("Could not read the Excel sheet", e);
        }

        return (dataArray);

    }


    public static String getCellData (int RowNum, int ColNum) throws Exception {

        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw (e);
        }

    }

}




