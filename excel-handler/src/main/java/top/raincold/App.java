package top.raincold;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.raincold.controller.ConfigParser;
import top.raincold.controller.ConfigParserFactory;
import top.raincold.controller.bean.Config;
import top.raincold.service.ExcelService;
import top.raincold.service.ExcelServiceFactory;

public class App {
    private static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            ConfigParser configParser = ConfigParserFactory.getParseConfig();
            Config config = configParser.parseConfig();
            ExcelService excelService = ExcelServiceFactory.getExcelService(config);
            excelService.doService(config);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }
}
