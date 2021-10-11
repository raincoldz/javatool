package top.raincold.controller.bean;

import lombok.Data;

@Data
public class ExcelCombineConfig extends Config {
    /**
     * 导出保存路径
     */
    private String outputPath;

    /**
     * 操作参考的列
     */
    private Integer baseColumn;

    /**
     * 操作需要合并的列
     */
    private Integer combineColumn;
    
    /**
     * 分隔符
     */
    private String separator;

    /**
     * 是否在末尾添加个数统计列
     */
    private Boolean createStatisticsColumn;
}
