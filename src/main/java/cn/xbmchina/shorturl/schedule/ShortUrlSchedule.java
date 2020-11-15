package cn.xbmchina.shorturl.schedule;

import cn.xbmchina.shorturl.common.RedisKey;
import cn.xbmchina.shorturl.service.ShortUrlService;
import cn.xbmchina.shorturl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计短链接定时任务
 */
@Component
public class ShortUrlSchedule {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    ShortUrlService shortUrlService;

    //每10分钟执行一次
    @Scheduled(cron = "0 0/10 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void calculateClickCount() {
        Long size = redisUtil.size(RedisKey.LINK_CLICK_COUNT);
        if (size != null && size > 0) {
            //统计短链接点击数
            Map<String, Integer> urlMap = new HashMap<>();
            Long batchSize = 10000L;
            do {
                Long pageSize = size > batchSize ? batchSize : size;
                List<String> tmpList = redisUtil.lRange(RedisKey.LINK_CLICK_COUNT, 0, pageSize);
                if (CollectionUtils.isEmpty(tmpList)) {
                    return;
                }
                for (String shortUrl : tmpList) {
                    //处理短链接被点击数
                    Integer count = urlMap.get(shortUrl);
                    if (count == null || count == 0) {
                        count = 0;
                    }
                    urlMap.put(shortUrl, ++count);
                }
                //批量更新
                int i = shortUrlService.batchUpdateClickCount(urlMap);
                //弹出
                redisUtil.getRedisTemplate().executePipelined(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        RedisConnection pl = redisConnection;
                        for (int i = 0; i <= tmpList.size(); i++) {
                            pl.lPop(RedisKey.LINK_CLICK_COUNT.getBytes());
                        }
                        return null;
                    }
                });

                size = size - tmpList.size();
            } while (size > 0);
        }
    }
}
