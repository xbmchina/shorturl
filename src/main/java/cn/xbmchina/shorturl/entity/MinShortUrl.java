package cn.xbmchina.shorturl.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class MinShortUrl {
    private Long id;
    private String username;
    private String url;
    private String shortUrl;
    private Integer clientCount;
    private Date createTime;
    private Date lastClickTime;
}
