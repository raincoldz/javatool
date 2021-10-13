package top.raincold.controller.impl;

import top.raincold.controller.ConfigParser;
import top.raincold.controller.bean.Config;
import top.raincold.controller.bean.ExcelMoveFileConfig;
import top.raincold.util.PropertiesUtils;

import java.util.Scanner;

public class ExcelMoveFileConfigParser implements ConfigParser {
    @Override
    public Config parseConfig() {
        ExcelMoveFileConfig config = new ExcelMoveFileConfig();
        config.setMode(3);

        Scanner in = new Scanner(System.in);
        String filePath = null;
        String sheetName = null;
        Integer srcColumn = null;
        Integer dstColumn = null;

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


        if (!PropertiesUtils.containsKey("srcColumn")) {
            System.out.println("** 输入源文件路径所在的列，从1开始计算");
            srcColumn = Integer.valueOf(in.nextLine()) - 1;
        } else {
            srcColumn = Integer.valueOf(PropertiesUtils.getValue("srcColumn")) - 1;
        }

        if (!PropertiesUtils.containsKey("dstColumn")) {
            System.out.println("** 输入目标文件路径所在的列，从1开始计算");
            dstColumn = Integer.valueOf(in.nextLine()) - 1;
        } else {
            dstColumn = Integer.valueOf(PropertiesUtils.getValue("dstColumn")) - 1;
        }

        config.setFilePath(filePath);
        config.setSheetName(sheetName);
        config.setSrcColumnn(srcColumn);
        config.setDstColumn(dstColumn);
        return config;
    }
}
