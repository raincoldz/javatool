package top.raincold.controller.impl;

import top.raincold.controller.ConfigParser;
import top.raincold.controller.bean.Config;
import top.raincold.controller.bean.ExcelSplitConfig;
import top.raincold.util.PropertiesUtils;

import java.util.Scanner;

public class ExcelCombineConfigParser implements ConfigParser {
    @Override
    public Config parseConfig() {
        ExcelSplitConfig config = new ExcelSplitConfig();
        config.setMode(2);

        Scanner in = new Scanner(System.in);
        String filePath = null;
        String sheetName = null;
        String outputPath = null;
        String separator = null;
        Integer baseColumn = null;
        Integer combineColumn = null;
        Boolean createStatisticsColumn = null;

        if (PropertiesUtils.getValue("filePath") == null) {
            System.out.println("输入需要操作的Excel的路径");
            filePath = in.nextLine();
        } else {
            filePath = PropertiesUtils.getValue("filePath");
        }

        if (PropertiesUtils.getValue("sheetName") == null) {
            System.out.println("输入需要操作的Sheet的名字");
            sheetName = in.nextLine();
        } else {
            sheetName = PropertiesUtils.getValue("sheetName");
        }

        if (PropertiesUtils.getValue("outputPath") == null) {
            System.out.println("输入导出保存的路径，如果不输入则默认为程序目录下的 output目录");
            outputPath = in.nextLine();
            if (outputPath == null || outputPath.length() == 0) {
                outputPath = "./output/";
            }
        } else {
            outputPath = PropertiesUtils.getValue("outputPath");
        }

        if (PropertiesUtils.getValue("separator") == null) {
            System.out.println("输入合并分割符，如果不输入则默认为\"、\"");
            separator = in.nextLine();
            if (separator == null || separator.length() == 0) {
                separator = "、";
            }
        } else {
            separator = PropertiesUtils.getValue("separator");
        }

        if (PropertiesUtils.getValue("baseColumn") == null) {
            System.out.println("输入参考的列，从1开始");
            baseColumn = Integer.valueOf(in.nextLine()) - 1;
        } else {
            baseColumn = Integer.valueOf(PropertiesUtils.getValue("baseColumn")) - 1;
        }

        if (PropertiesUtils.getValue("combineColumn") == null) {
            System.out.println("输入需要合并的列，从1开始");
            combineColumn = Integer.valueOf(in.nextLine()) - 1;
        } else {
            combineColumn = Integer.valueOf(PropertiesUtils.getValue("combineColumn")) - 1;
        }

        if (PropertiesUtils.getValue("createStatisticsColumn") == null) {
            System.out.println("是否生成个数统计列");
            String createStatisticsColumnStr = in.nextLine();
            if (createStatisticsColumnStr == null || createStatisticsColumnStr.length() == 0) {
                createStatisticsColumn = Boolean.FALSE;
            } else {
                createStatisticsColumn = Boolean.parseBoolean(createStatisticsColumnStr);
            }
        } else {
            createStatisticsColumn = PropertiesUtils.getDefaultFalseBoolean("deleteBaseColumn");
        }

        config.setFilePath(filePath);
        config.setSheetName(sheetName);
        config.setOutputPath(outputPath);
        config.setBaseColumn(baseColumn);
        config.setBaseColumn(combineColumn);
        config.setDeleteBaseColumn(createStatisticsColumn);
        return config;
    }
}
