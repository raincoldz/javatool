package top.raincold.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import top.raincold.controller.bean.Config;
import top.raincold.controller.bean.ExcelCombineConfig;
import top.raincold.util.ExcelUtils;
import top.raincold.util.FileUtils;

import java.io.*;
import java.util.*;

@Slf4j
public class ExcelCombine extends AbstractExcelServiceImpl {
    @Override
    public void doService(Config config) {
        initExcelTable(config.getFilePath(), config.getSheetName());
        ExcelCombineConfig excelCombineConfig = (ExcelCombineConfig) config;
        try {
            combineByColumnAndExport(excelCombineConfig.getBaseColumn(), excelCombineConfig.getCombineColumn(), excelCombineConfig.getOutputPath(), excelCombineConfig.getSeparator(), excelCombineConfig.getCreateStatisticsColumn());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("按列合并业务出错", e);
        }
    }

    private void combineByColumnAndExport(int baseColumn, int combineColumn, String outputPath, String separator, boolean createStatisticsColumn) throws IOException {
        if (table == null || table.size() == 0) {
            return;
        }

        FileUtils.createDirPathIfNotExist(outputPath);
        Map<String, List<List<String>>> map = new HashMap<>();
        List<String> header = getHeader(table.get(0), createStatisticsColumn);

        for (int i = 1; i < table.size(); i++) {
            List<String> line = table.get(i);
            String key = line.get(baseColumn);
            List<List<String>> value = map.getOrDefault(key, new ArrayList<>());
            value.add(line);
            map.put(key, value);
        }

        List<List<String>> finalTable = new ArrayList<>();
        for (String key : map.keySet()) {
            List<List<String>> tableTemp = map.get(key);
            finalTable.add(combine(tableTemp, baseColumn, combineColumn, separator, createStatisticsColumn));
        }

        String savePath = outputPath + File.separator + "newExcel.xls";
        OutputStream out = new FileOutputStream(savePath);
        Workbook workbook = new HSSFWorkbook();
        ExcelUtils.exportExcel(workbook, 0, "Sheet1", header, finalTable);
        workbook.write(out);
        log.info("Excel已完成合并并导出，保存地址为:{}", savePath);
        System.out.println(String.format("Excel已完成合并并导出，保存地址为:{}", savePath));
        out.close();
    }


    private List<String> combine(List<List<String>> table, int baseColumn, int combineColumn, String separator, boolean createStatisticsColumn) {
        List<String> result = new ArrayList<>();
        String baseStr = table.get(table.size() - 1).get(combineColumn);
        result.add(baseStr);

        Set<String> set = new HashSet<>();
        for (int i = 0; i < table.size(); i++) {
            String combineTempStr = table.get(i).get(combineColumn);
            set.add(combineTempStr);
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String comnineTempStr : set) {
            sb.append(comnineTempStr);
            if (count < set.size() - 1) {
                sb.append(separator);
            }
        }
        result.add(sb.toString());

        if (createStatisticsColumn) {
            result.add(String.valueOf(set.size()));
        }
        return result;
    }


    private List<String> getHeader(List<String> line, boolean createStatisticsColumn) {
        if (createStatisticsColumn) {
            line.add("统计");
        }
        return line;
    }
}
