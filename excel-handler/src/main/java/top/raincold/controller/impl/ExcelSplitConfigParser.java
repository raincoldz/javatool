package top.raincold.controller.impl;

import top.raincold.controller.ConfigParser;
import top.raincold.controller.bean.Config;
import top.raincold.controller.bean.ExcelSplitConfig;
import top.raincold.util.PropertiesUtils;

import java.util.Scanner;

public class ExcelSplitConfigParser implements ConfigParser {
    @Override
    public Config parseConfig() {
        ExcelSplitConfig config = new ExcelSplitConfig();
        config.setMode(1);

        Scanner in = new Scanner(System.in);
        String filePath = null;
        String sheetName = null;
        String outputPath = null;
        Integer baseColumn = null;
        Boolean deleteBaseColumn = null;

        if (!PropertiesUtils.containsKey("filePath")) {
            System.out.println("** 输入需要操作的Excel的路径");
            filePath = in.nextLine();
        } else {
            filePath = PropertiesUtils.getValue("filePath");
        }

        if (!PropertiesUtils.containsKey("sheetName")) {
            System.out.println("** 输入需要操作的Sheet的名字");
            sheetName = in.nextLine();
        } else {
            sheetName = PropertiesUtils.getValue("sheetName");
        }

        if (!PropertiesUtils.containsKey("outputPath")) {
            System.out.println("** 输入导出保存的路径，如果输入为空则默认为程序目录下的output目录");
            outputPath = in.nextLine();
            if (outputPath == null || outputPath.length() == 0) {
                outputPath = "./output/";
            }
        } else {
            outputPath = PropertiesUtils.getValue("outputPath");
        }

        if (!PropertiesUtils.containsKey("baseColumn")) {
            System.out.println("** 输入参考的列，从1开始计算");
            baseColumn = Integer.valueOf(in.nextLine()) - 1;
        } else {
            baseColumn = Integer.valueOf(PropertiesUtils.getValue("baseColumn")) - 1;
        }

        if (!PropertiesUtils.containsKey("deleteBaseColumn")) {
            System.out.println("** 导出时是否删除参考列，请输入true或false，如果输入为空则默认不删除。");
            String isDeleteStr = in.nextLine();
            if (isDeleteStr == null || isDeleteStr.length() == 0) {
                deleteBaseColumn = Boolean.FALSE;
            } else {
                deleteBaseColumn = Boolean.parseBoolean(isDeleteStr);
            }
        } else {
            deleteBaseColumn = PropertiesUtils.getDefaultFalseBoolean("deleteBaseColumn");
        }

        config.setFilePath(filePath);
        config.setSheetName(sheetName);
        config.setOutputPath(outputPath);
        config.setBaseColumn(baseColumn);
        config.setDeleteBaseColumn(deleteBaseColumn);
        return config;
    }
}
