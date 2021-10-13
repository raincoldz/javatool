package top.raincold.service;

import top.raincold.controller.bean.Config;
import top.raincold.controller.enums.ModeEnum;
import top.raincold.service.impl.ExcelCombine;
import top.raincold.service.impl.ExcelMoveFile;
import top.raincold.service.impl.ExcelSplit;


public class ExcelServiceFactory {

    public static ExcelService getExcelService(Config config) {
        Integer mode = config.getMode();
        ExcelService excelService = null;

        if (ModeEnum.ONE.getValue().equals(mode)) {
            excelService = new ExcelSplit();
        } else if (ModeEnum.TWO.getValue().equals(mode)) {
            excelService = new ExcelCombine();
        } else if (ModeEnum.THREE.getValue().equals(mode)) {
            excelService = new ExcelMoveFile();
        }
        return excelService;
    }
}
