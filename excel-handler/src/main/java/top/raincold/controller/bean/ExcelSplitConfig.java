package top.raincold.controller.bean;

import lombok.Data;

@Data
public class ExcelSplitConfig extends Config{
    /**
     * 导出保存路径
     */
    private String outputPath;

    /**
     * 操作参考的列
     */
    private Integer baseColumn;

    /**
     * 是否删除参考列
     */
    private Boolean deleteBaseColumn;

}
