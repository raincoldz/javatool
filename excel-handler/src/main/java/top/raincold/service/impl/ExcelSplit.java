package top.raincold.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.raincold.controller.bean.Config;
import top.raincold.controller.bean.ExcelSplitConfig;
import top.raincold.util.ExcelUtils;
import top.raincold.util.FileUtils;

public class ExcelSplit extends AbstractExcelServiceImpl{

    private static Logger log = LoggerFactory.getLogger(ExcelSplit.class);

    @Override
    public void doService(Config config) {
        initExcelTable(config.getFilePath(), config.getSheetName());
        ExcelSplitConfig excelSplitConfig = (ExcelSplitConfig) config;
        try {
            divideByColumeAndExport(excelSplitConfig.getBaseColumn(), excelSplitConfig.getOutputPath(), excelSplitConfig.getDeleteBaseColumn());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("按列分割业务出错", e);
        }
    }

    //根据第 index 列不同，导出Excel，保留表头
    public void divideByColumeAndExport(int baseColumn, String outputPath, Boolean deleteBaseColumn) throws IOException {
        if (table == null || table.size() == 0) {
            return;
        }

        FileUtils.createDirPathIfNotExist(outputPath);

        List<String> header = getFilterLine(table.get(0), baseColumn, deleteBaseColumn);
        Map<String, List<List<String>>> map = new HashMap<>();

        for (int i = 1; i < table.size(); i++) {
            List<String> line = table.get(i);
            String key = line.get(baseColumn);
            map.putIfAbsent(key, new ArrayList<>());
            List<List<String>> tableTemp = map.get(key);
            tableTemp.add(getFilterLine(line, baseColumn, deleteBaseColumn));
        }


        for (String key : map.keySet()) {
            List<List<String>> tableTemp = map.get(key);
            String savePath = outputPath + File.separator + key + ".xls";
            OutputStream out = new FileOutputStream(savePath);
            Workbook workbook = new HSSFWorkbook();
            ExcelUtils.exportExcel(workbook, 0, "Sheet1", header, tableTemp);
            workbook.write(out);
            log.info("{}.xls已完成导出，保存地址为:{}", key, savePath);
            System.out.println(String.format("%s.xls已完成导出，保存地址为:%s", key, savePath));
            out.close();
        }
        log.info("全部导出完成！");
        System.out.println("全部导出完成！");
    }

    //根据是否去除列表中的第 index 列元素，返回处理后的行数据
    protected List<String> getFilterLine(List<String> list, int baseColumn, Boolean deteleBaseColumn) {
        if (deteleBaseColumn == null || !deteleBaseColumn) {
            return list;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i != baseColumn) {
                result.add(list.get(i));
            }
        }
        return result;
    }
}
