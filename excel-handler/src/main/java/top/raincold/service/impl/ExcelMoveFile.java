package top.raincold.service.impl;

import lombok.extern.slf4j.Slf4j;
import top.raincold.controller.bean.Config;
import top.raincold.controller.bean.ExcelMoveFileConfig;
import top.raincold.util.FileUtils;

import java.util.List;


@Slf4j
public class ExcelMoveFile extends AbstractExcelServiceImpl{
    @Override
    public void doService(Config config) {
        initExcelTable(config.getFilePath(), config.getSheetName());
        ExcelMoveFileConfig excelMoveFileConfig = (ExcelMoveFileConfig) config;

        batchMove(excelMoveFileConfig.getSrcColumnn(), excelMoveFileConfig.getDstColumn());
    }

    private void batchMove(int srcColumnn, int dstColumn) {
        if (table == null || table.size() == 0) {
            return;
        }

        for (int i = 0; i < table.size(); i++) {
            List<String> line = table.get(i);
            String srcFilePath = line.get(srcColumnn);
            String dstFilePath = line.get(dstColumn);
            FileUtils.moveFiles(srcFilePath, dstFilePath);
            System.out.println(String.format("正在复制{}->{}", srcFilePath, dstFilePath));
        }
    }
}
