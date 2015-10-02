package utility;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Gaurab Pradhan
 */
public class MergeExclesFiles {

    private static String filePath = PropertiesUtil.getFilePath();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<FileInputStream> list = new ArrayList<FileInputStream>();
        File file = new File(filePath);
        File newFile = new File(filePath + "CensusData.xlsx");
        File[] listOfFiles = file.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File filePath = new File(listOfFiles[i].getAbsolutePath());
            FileInputStream fis = new FileInputStream(filePath);
            list.add(fis);
        }
        mergeExcelFiles(newFile, list);
    }

    public static void mergeExcelFiles(File file, List<FileInputStream> list) throws IOException {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet();

        String[] headings = {"V.D.C./MUNICIPALITY", "DISTRICT", "WARD NUMBER", "HOUSEHOLD", "POPULATION"};
        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        int cellIndex = 0;
        row.createCell(cellIndex++).setCellValue(headings[0]);
        row.createCell(cellIndex++).setCellValue(headings[1]);
        row.createCell(cellIndex++).setCellValue(headings[2]);
        row.createCell(cellIndex++).setCellValue(headings[3]);
        row.createCell(cellIndex++).setCellValue(headings[4]);

        for (FileInputStream fin : list) {
            Workbook b = new XSSFWorkbook(fin);
            for (int i = 0; i < b.getNumberOfSheets(); i++) {
                copySheets(book, sheet, b.getSheetAt(i));
            }
        }

        try {
            writeFile(book, file);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected static void writeFile(Workbook book, File file) throws Exception {
        FileOutputStream out = new FileOutputStream(file);
        book.write(out);
        out.close();
    }

    private static void copySheets(Workbook newWorkbook, Sheet newSheet, Sheet sheet) {
        copySheets(newWorkbook, newSheet, sheet, true);
    }

    private static void copySheets(Workbook newWorkbook, Sheet newSheet, Sheet sheet, boolean copyStyle) {
        int newRownumber = newSheet.getLastRowNum() + 1;
        int maxColumnNum = 0;
        Map<Integer, CellStyle> styleMap = (copyStyle) ? new HashMap<Integer, CellStyle>() : null;

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row srcRow = sheet.getRow(i);
            Row destRow = newSheet.createRow(i + newRownumber);
            if (srcRow != null) {
                copyRow(newWorkbook, sheet, newSheet, srcRow, destRow, styleMap);
                if (srcRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = srcRow.getLastCellNum();
                }
            }
        }
        for (int i = 0; i <= maxColumnNum; i++) {
            newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
        }
    }

    public static void copyRow(Workbook newWorkbook, Sheet srcSheet, Sheet destSheet, Row srcRow, Row destRow, Map<Integer, CellStyle> styleMap) {
        destRow.setHeight(srcRow.getHeight());
        String[] headings = {"V.D.C./MUNICIPALITY", "DISTRICT", "WARD NUMBER", "HOUSEHOLD", "POPULATION"};
        for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
            Cell oldCell = srcRow.getCell(j);
            Cell newCell = destRow.getCell(j);
            if (oldCell != null) {
                if (newCell == null) {
                    if (!(oldCell.toString().contains(headings[0]) || oldCell.toString().contains(headings[1]) || oldCell.toString().contains(headings[2]) || oldCell.toString().contains(headings[3]) || oldCell.toString().contains(headings[4]))) {
                        newCell = destRow.createCell(j);
                        copyCell(newWorkbook, oldCell, newCell, styleMap);
                    }
                }
            }
        }
    }

    public static void copyCell(Workbook newWorkbook, Cell oldCell, Cell newCell, Map<Integer, CellStyle> styleMap) {
        if (styleMap != null) {
            int stHashCode = oldCell.getCellStyle().hashCode();
            CellStyle newCellStyle = styleMap.get(stHashCode);
            if (newCellStyle == null) {
                newCellStyle = newWorkbook.createCellStyle();
                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
                styleMap.put(stHashCode, newCellStyle);
            }
            newCell.setCellStyle(newCellStyle);
        }
        switch (oldCell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                newCell.setCellValue(oldCell.getRichStringCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                newCell.setCellType(Cell.CELL_TYPE_BLANK);
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            default:
                break;
        }
    }
}
