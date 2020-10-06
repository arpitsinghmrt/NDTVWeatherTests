package util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {

    private static final String excelFilePath = "src/test/resources/Acceptable_Variance.xlsx";


    public double readVarianceForUnit(String unit) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        double variance = 0;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(0).getStringCellValue().equalsIgnoreCase(unit))
                variance = row.getCell(1).getNumericCellValue();
        }
        workbook.close();
        inputStream.close();
        return variance;
    }

}

