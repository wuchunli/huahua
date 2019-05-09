package huahua.common;

import lombok.Data;

/**
 * 公共的返回类
 */
@Data
public class Result {

    private boolean flag;

    private Integer code;

    private String message;

    private Object data;

    public Result( boolean flag, Integer code, String message ) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result( boolean flag, Integer code, String message, Object data ) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {

    }
}
