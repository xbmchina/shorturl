package cn.xbmchina.shorturl.service;

import java.util.List;
import java.util.Map;

public interface ShortUrlService {


    /**
     * 生成单条短链接(4个字符组成）
     *
     * @param longLink
     * @return
     */
    String getMinLink(String longLink);

    /**
     * 生成单条短链接(6个字符组成）
     *
     * @param longLink
     * @return
     */
    String getLink(String longLink);

    /**
     * 生成批量短链接(6个字符组成）
     *
     * @param longLinks
     * @return
     */
    List<String> getLinks(List<String> longLinks);


    /**
     * 访问短链接
     *
     * @param shortUrl
     * @return
     */
    String visitLink(String shortUrl);


    /**
     * 批量更新点击数
     * @param urlMap
     * @return
     */
    int batchUpdateClickCount(Map<String,Integer> urlMap);
}
