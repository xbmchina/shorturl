package cn.xbmchina.shorturl.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result<String> error(ApiResultEnum apiResultEnum) {
        return new Result<>(apiResultEnum.getCode(), apiResultEnum.getMsg());
    }

    public static Result<String> ok() {
        return new Result<>(ApiResultEnum.OK.getCode(), ApiResultEnum.OK.getMsg());
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(ApiResultEnum.OK.getCode(), ApiResultEnum.OK.getMsg(), data);
    }
}
