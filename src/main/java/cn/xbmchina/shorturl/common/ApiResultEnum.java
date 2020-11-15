package cn.xbmchina.shorturl.common;

public enum ApiResultEnum {

    OK(200,"请求成功"),
    REQUST_LIMIT(405,"访问过于频繁");



    private Integer code;
    private String msg;

    ApiResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
