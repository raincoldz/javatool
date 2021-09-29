package top.raincold;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    // 读入Excel
    public static XSSFWorkbook importExcel(String filePath){
        FileInputStream fileInputStream = null;
        XSSFWorkbook workbook = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Excel路径错误:{}", filePath);
        }
        return workbook;
    }

    // 从 Workbook 中读取sheet
    public static XSSFSheet getSheetByName(XSSFWorkbook workbook, String sheetName) {
        if (workbook == null) {
            log.error("workbook不存在");
            return null;
        }

        XSSFSheet sheet = null;
        try {
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            log.error("名为{}的Sheet不存在", sheetName);
        }
        return sheet;
    }

    // 将sheet数据转换成字符串二维列表
    public static List<List<String>> convertFromSheet(XSSFSheet sheet){
        if (sheet == null) {
            log.error("sheet不存在");
            return null;
        }

        List<List<String>> table = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            List<String> line = new ArrayList<>();
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for (int j = 0; j < columns; j++) {
                String cell = row.getCell(j).toString();
                line.add(cell);
            }
            table.add(line);
        }
        return table;
    }

    //导出Execl
    public static void exportExcel(Workbook workbook, int sheetNum, String sheetTitle, List<String> header, List<List<String>> table) {
        //生成一个表格
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);

        //设置表格默认宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);

        //遍历集合数据，产生数据行
        if (table != null) {
            Row title = sheet.createRow(0);
            for (int column = 0; column < header.size(); column++) {
                Cell cell = title.createCell(column);
                cell.setCellValue(isStringEmpty(header.get(column)) ? "" : header.get(column));
            }

            for (int i = 1; i < table.size(); i++) {
                List<String> line = table.get(i);
                Row row = sheet.createRow(i);
                for (int j = 0; j < line.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(isStringEmpty(line.get(j)) ? "" : line.get(j));
                }
            }
        }
    }

    private static boolean isStringEmpty(String s) {
        return s == null || s == "";
    }




}


