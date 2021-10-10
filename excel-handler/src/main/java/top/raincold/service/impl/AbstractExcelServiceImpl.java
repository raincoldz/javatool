package top.raincold.service.impl;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.raincold.controller.bean.Config;
import top.raincold.service.ExcelService;
import top.raincold.util.ExcelUtils;
import java.util.List;


public class AbstractExcelServiceImpl implements ExcelService {

    private static Logger log = LoggerFactory.getLogger(AbstractExcelServiceImpl.class);

    protected List<List<String>> table;

    public void initExcelTable(String filePath, String sheetName) {
        XSSFWorkbook workbook = ExcelUtils.importExcel(filePath);
        XSSFSheet sheet = ExcelUtils.getSheetByName(workbook, sheetName);
        this.table = ExcelUtils.convertFromSheet(sheet);
    }

    @Override
    public void doService(Config config) {
    }
}
