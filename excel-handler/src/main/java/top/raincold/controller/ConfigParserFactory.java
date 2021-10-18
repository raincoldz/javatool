package top.raincold.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.raincold.controller.enums.ModeEnum;
import top.raincold.controller.impl.ExcelCombineConfigParser;
import top.raincold.controller.impl.ExcelMoveFileConfigParser;
import top.raincold.controller.impl.ExcelSplitConfigParser;
import top.raincold.util.PropertiesUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigParserFactory{

    private static Logger log = LoggerFactory.getLogger(ConfigParserFactory.class);

    private static ExecutorService postProcessExecutor = Executors.newSingleThreadExecutor();

    public static ConfigParser getParseConfig() {
        printLogo();
        Scanner in = new Scanner(System.in);
        Integer mode = null;
        if (!PropertiesUtils.containsKey("mode")) {
            printNotice();
            mode = Integer.valueOf(in.nextLine());
        } else {
            mode = Integer.valueOf(PropertiesUtils.getValue("mode"));
        }

        ConfigParser configParser = null;
        if (ModeEnum.ONE.getValue().equals(mode)) {
            configParser = new ExcelSplitConfigParser();
        } else if (ModeEnum.TWO.getValue().equals(mode)) {
            configParser = new ExcelCombineConfigParser();
        } else if (ModeEnum.THREE.getValue().equals(mode)) {
            configParser = new ExcelMoveFileConfigParser();
        }

        postProcessExecutor.submit(()->{
            try {
                File conn = new File("./conn.properties");
                FileWriter fwConn = new FileWriter(conn);
                BufferedWriter bwConn = new BufferedWriter(fwConn);
                bwConn.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return configParser;
    }

    private static void printLogo() {
        System.out.println("\n" +
                " ____       _                 _     _\n" +
                "|  _ \\ __ _(_)_ __   ___ ___ | | __| |\n" +
                "| |_) / _` | | '_ \\ / __/ _ \\| |/ _` |\n" +
                "|  _ < (_| | | | | | (_| (_) | | (_| |\n" +
                "|_| \\_\\__,_|_|_| |_|\\___\\___/|_|\\__,_|\n" +
                "\n");
    }

    private static void printNotice() {
        System.out.println("==================================");
        System.out.println("** 请输入要选择的模式，以回车键结束");
        System.out.println("** 1：按列分割");
        System.out.println("** 2：按列合并");
        System.out.println("** 3：文件夹复制");
        System.out.println("==================================");
    }
}
