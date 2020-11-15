package cn.xbmchina.shorturl.mapper;

import cn.xbmchina.shorturl.entity.MinShortUrl;
import cn.xbmchina.shorturl.entity.ShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MinShortUrlMapper {

    int insertUrl(MinShortUrl shortUrl);

    int updateShortUrl(MinShortUrl shortUrl);

    MinShortUrl getOriginUrl(String shortUrl);

    int batchUpdateClick(@Param("urlList") List<ShortUrl> minShortUrlList);
}
