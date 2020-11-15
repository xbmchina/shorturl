package cn.xbmchina.shorturl.service.impl;

import cn.xbmchina.shorturl.common.RedisKey;
import cn.xbmchina.shorturl.entity.MinShortUrl;
import cn.xbmchina.shorturl.entity.ShortUrl;
import cn.xbmchina.shorturl.mapper.MinShortUrlMapper;
import cn.xbmchina.shorturl.mapper.ShortUrlMapper;
import cn.xbmchina.shorturl.service.ShortUrlService;
import cn.xbmchina.shorturl.util.Base62;
import cn.xbmchina.shorturl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Transactional(rollbackFor = Exception.class)
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ShortUrlMapper shortUrlMapper;
    @Autowired
    private MinShortUrlMapper minShortUrlMapper;

    @Override
    public String getMinLink(String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return null;
        }
        MinShortUrl shortUrl = new MinShortUrl();
        shortUrl.setId(generateId(RedisKey.MIN_LINK_MAX_ID, 1));
        shortUrl.setShortUrl(Base62.generateLink(shortUrl.getId(), 4));
        shortUrl.setUrl(longLink);
        shortUrl.setUsername("su");
        shortUrl.setCreateTime(new Date());
        shortUrl.setClientCount(0);
        int i = minShortUrlMapper.insertUrl(shortUrl);
        if (i > 0) {
//            redisUtil.setEx(LINK_LIST_LAST + shortUrl.getShortUrl(), shortUrl.getUrl(), 1, TimeUnit.DAYS);
        }
        return shortUrl.getShortUrl();
    }


    @Override
    public String getLink(String longLink) {
        if (StringUtils.isEmpty(longLink)) {
            return null;
        }
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(generateId(RedisKey.LINK_MAX_ID, 1));
        shortUrl.setShortUrl(Base62.generateLink(shortUrl.getId(), 6));
        shortUrl.setUrl(longLink);
        shortUrl.setUsername("su");
        shortUrl.setCreateTime(new Date());
        shortUrl.setClientCount(0);
        int i = shortUrlMapper.insertUrl(shortUrl);
        if (i > 0) {
//            redisUtil.setEx(LINK_LIST_LAST + shortUrl.getShortUrl(), shortUrl.getUrl(), 1, TimeUnit.DAYS);
        }
        return shortUrl.getShortUrl();
    }


    @Override
    public List<String> getLinks(List<String> longLinks) {
        if (CollectionUtils.isEmpty(longLinks)) {
            return null;
        }
        List<ShortUrl> shortUrlList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        for (String link : longLinks) {
            ShortUrl shortUrl = new ShortUrl();
            shortUrl.setId(generateId(RedisKey.LINK_MAX_ID, 1));
            shortUrl.setShortUrl(Base62.generateLink(shortUrl.getId(), 6));
            shortUrl.setUrl(link);
            shortUrl.setUsername("su");
            shortUrl.setCreateTime(new Date());
            shortUrl.setClientCount(0);
            shortUrlList.add(shortUrl);
            urlList.add(shortUrl.getShortUrl());
        }
        int count = shortUrlMapper.batchInsertUrl(shortUrlList);
        if (count > 0) {
//            for (ShortUrl sl : shortUrlList) {
//                redisUtil.setEx(LINK_LIST_LAST + sl.getShortUrl(), sl.getUrl(), 1, TimeUnit.DAYS);
//            }
        }
        return urlList;
    }

    @Override
    public String visitLink(String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)) {
            return null;
        }
        //此处可以将最近1天生成的短链接加入到缓存，提高响应速度。
        //将点击数缓存，使用异步线程批量更新。
        String resultStr = redisUtil.get(RedisKey.LINK_LIST_LAST + shortUrl);
        if (!StringUtils.isEmpty(resultStr)) {
            redisUtil.lRightPush(RedisKey.LINK_CLICK_COUNT, shortUrl);
            return resultStr;
        }
        switch (shortUrl.length()) {
            case 4:
                //极短链接
                MinShortUrl originUrl = minShortUrlMapper.getOriginUrl(shortUrl);
                if (originUrl != null) {
                    minShortUrlMapper.updateShortUrl(originUrl);
                }
                resultStr = originUrl.getUrl();
                break;
            case 6:
                //普通短链接
                ShortUrl oUrl = shortUrlMapper.getOriginUrl(shortUrl);
                if (oUrl != null) {
                    shortUrlMapper.updateShortUrl(oUrl);
                }
                resultStr = oUrl.getUrl();
                break;
            default:
                break;
        }
        if (!StringUtils.isEmpty(resultStr)) {
            redisUtil.setEx(RedisKey.LINK_LIST_LAST + shortUrl, resultStr, 1, TimeUnit.DAYS);
        }
        return resultStr;
    }

    @Override
    public int batchUpdateClickCount(Map<String, Integer> urlMap) {
        int result = 0;
        if (CollectionUtils.isEmpty(urlMap)) {
            return result;
        }
        //极短连接
        List<ShortUrl> minShortUrlList = new ArrayList<>();
        //普通短链接
        List<ShortUrl> shortUrlList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : urlMap.entrySet()) {
            ShortUrl shortUrl = new ShortUrl();
            shortUrl.setShortUrl(entry.getKey());
            shortUrl.setClientCount(entry.getValue());
            switch (entry.getKey().length()) {
                case 4:
                    minShortUrlList.add(shortUrl);
                    break;
                case 6:
                    shortUrlList.add(shortUrl);
                    break;
            }
        }

        if (CollectionUtils.isEmpty(minShortUrlList)) {
            result += minShortUrlMapper.batchUpdateClick(minShortUrlList);
        }

        if (CollectionUtils.isEmpty(shortUrlList)) {
            result += shortUrlMapper.batchUpdateClick(shortUrlList);
        }

        return result;
    }

    /**
     * redis生成全局自增ID
     */
    public long generateId(String key, int increment) {
        try {
            RedisAtomicLong counter = new RedisAtomicLong(key, redisUtil.getRedisTemplate().getConnectionFactory());
            return counter.addAndGet(increment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() + new Random(1000000).nextInt(1000000) + 1;
    }
}
