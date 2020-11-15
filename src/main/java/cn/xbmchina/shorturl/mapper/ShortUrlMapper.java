package cn.xbmchina.shorturl.mapper;

import cn.xbmchina.shorturl.entity.ShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShortUrlMapper {

    int insertUrl(ShortUrl shortUrl);

    int updateShortUrl(ShortUrl shortUrl);

    int batchInsertUrl(@Param("shortUrlList") List<ShortUrl> shortUrlList);

    ShortUrl getOriginUrl(String shortUrl);

    int batchUpdateClick(@Param("urlList")List<ShortUrl> shortUrlList);
}
