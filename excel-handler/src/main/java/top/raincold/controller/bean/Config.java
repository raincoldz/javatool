package top.raincold.controller.bean;

import lombok.Data;

@Data
public abstract class Config {
    /**
     * 模式 1：按列分割 2：按列合并 3：文件夹复制
     */
    private Integer mode;

    /**
     * 待操作Excel的路径
     */
    private String filePath;

    /**
     * 带操作的表名
     */
    private String sheetName;
}
