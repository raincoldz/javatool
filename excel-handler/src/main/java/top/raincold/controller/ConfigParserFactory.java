package top.raincold.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.raincold.controller.enums.ModeEnum;
import top.raincold.controller.impl.ExcelSplitConfigParser;
import top.raincold.util.PropertiesUtils;

import java.util.Scanner;

public class ConfigParserFactory{

    private static Logger log = LoggerFactory.getLogger(ConfigParserFactory.class);

    public static ConfigParser getParseConfig() {
        Scanner in = new Scanner(System.in);
        Integer mode = null;
        if (PropertiesUtils.getValue("mode") == null) {
            System.out.println("请输入要选择的模式,1：按列分割 2：按列合并 3：文件夹复制");
            mode = Integer.valueOf(in.nextLine());
        } else {
            mode = Integer.valueOf(PropertiesUtils.getValue("mode"));
        }

        ConfigParser configParser = null;
        if (ModeEnum.ONE.getValue().equals(mode)) {
            configParser = new ExcelSplitConfigParser();
        } else if (ModeEnum.TWO.getValue().equals(mode)) {
        } else if (ModeEnum.THREE.getValue().equals(mode)) {
        }

        return configParser;
    }
}
