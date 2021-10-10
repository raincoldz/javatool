package top.raincold.controller.enums;

public enum ModeEnum {
    ONE(1,"按列分割其余"),
    TWO(2,"按列合并某列"),
    THREE(3,"文件夹复制")
    ;

    private Integer value;
    private String message;

    ModeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
