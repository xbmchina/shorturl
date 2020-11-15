package cn.xbmchina.shorturl.config;

import lombok.Data;

@Data
public class DsEntity {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Boolean testWhileIdle;
    private Long timeBetweenEvictionRunsMillis;
    private String validationQuery;
}
