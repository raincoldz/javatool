package top.raincold.controller.bean;

import lombok.Data;

@Data
public class ExcelMoveFileConfig extends Config {

    /**
     * 源目录所在的列
     */
    private Integer srcColumnn;

    /**
     * 目标目录所在的列
     */
    private Integer dstColumn;
}
