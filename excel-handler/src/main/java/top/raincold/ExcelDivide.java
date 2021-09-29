package top.raincold;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelDivide {

    private static Logger log = LoggerFactory.getLogger(ExcelDivide.class);

    private List<List<String>> table;


    public ExcelDivide(List<List<String>> table) {
        this.table = table;
    }

    //根据第 index 列不同，导出Excel，保留表头
    public void divideByColumeAndExport(int index, String outputPath) throws IOException {
        if (table == null || table.size() == 0) {
            return;
        }

        List<String> header = removeIndexElement(table.get(0), index);
        Map<String, List<List<String>>> map = new HashMap<>();

        for (int i = 1; i < table.size(); i++) {
            List<String> line = table.get(i);
            String key = line.get(index);
            map.putIfAbsent(key, new ArrayList<>());
            List<List<String>> tableTemp = map.get(key);
            tableTemp.add(removeIndexElement(line, index));
        }


        for (String key : map.keySet()) {
            List<List<String>> tableTemp = map.get(key);
            String savePath = outputPath + File.separator + key + ".xls";
            OutputStream out = new FileOutputStream(savePath);
            Workbook workbook = new HSSFWorkbook();
            ExcelUtils.exportExcel(workbook, 0, "Sheet1", header, tableTemp);
            workbook.write(out);
            log.info("{}.xls已完成导出，保存地址为:{}", key, savePath);
            out.close();
        }
    }

    //去除列表中的第 index 列元素
    private List<String> removeIndexElement(List<String> list, int index) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i != index) {
                result.add(list.get(i));
            }
        }
        return result;
    }


    public static void main(String[] args) {
        String filePath = PropertiesUtils.getValue("filePath");
        String sheetName = PropertiesUtils.getValue("sheetName");
        String outputPath = PropertiesUtils.getValue("outputPath");
        Integer operateColumn = Integer.valueOf(PropertiesUtils.getValue("operateColumn"));

        log.info("需要分割处理的 Excel 路径为:{}", filePath);
        log.info("需要分割处理的 Sheet 为:{}", sheetName);
        log.info("导出保存路径:{}", outputPath);
        log.info("需要分割的列是:{}", operateColumn);
        try {
            XSSFWorkbook workbook = ExcelUtils.importExcel(filePath);
            XSSFSheet sheet = ExcelUtils.getSheetByName(workbook, sheetName);
            List<List<String>> table = ExcelUtils.convertFromSheet(sheet);
            ExcelDivide excelDivide = new ExcelDivide(table);
            excelDivide.divideByColumeAndExport(operateColumn, outputPath);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), "导出流程错误");
        }
        System.out.println("全部导出完成！");
    }
}
