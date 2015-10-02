package utility;

import bean.CensusBean;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;

/**
 *
 * @author Gaurab Pradhan
 */
public class WriteToExcle {

    private static final String filePath = "excleFiles/";

    public void writeIntoExcle(List<CensusBean> list) {
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();

        Sheet beansSheet = workbook.createSheet("Sheet1");
        String fileName = "temp.xlsx";
        String[] headings = {"V.D.C./MUNICIPALITY", "DISTRICT", "WARD NUMBER", "HOUSEHOLD", "POPULATION"};
        int rowIndex = 0;
        Row row = beansSheet.createRow(rowIndex++);
        int cellIndex = 0;
        row.createCell(cellIndex++).setCellValue(headings[0]);
        row.createCell(cellIndex++).setCellValue(headings[1]);
        row.createCell(cellIndex++).setCellValue(headings[2]);
        row.createCell(cellIndex++).setCellValue(headings[3]);
        row.createCell(cellIndex++).setCellValue(headings[4]);

        for (CensusBean bean : list) {
            fileName = bean.getDistrict() + ".xlsx";
            row = beansSheet.createRow(rowIndex++);
            cellIndex = 0;
            row.createCell(cellIndex++).setCellValue(bean.getVdc());
            row.createCell(cellIndex++).setCellValue(bean.getDistrict());
            row.createCell(cellIndex++).setCellValue(bean.getWardNum());
            row.createCell(cellIndex++).setCellValue(bean.getHouseHold());
            row.createCell(cellIndex++).setCellValue(bean.getPopu());
        }

        //write this workbook in excel file.
        try {
            FileOutputStream fos = new FileOutputStream(filePath + fileName);
            workbook.write(fos);
            fos.close();

            File file = new File(filePath + fileName);
            System.out.println(file.getAbsolutePath() + " is successfully written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
