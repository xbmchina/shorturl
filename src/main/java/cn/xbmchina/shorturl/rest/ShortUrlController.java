package cn.xbmchina.shorturl.rest;

import cn.xbmchina.shorturl.annotation.RequestLimit;
import cn.xbmchina.shorturl.common.Result;
import cn.xbmchina.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class ShortUrlController {

    @Autowired
    ShortUrlService shortUrlService;

    @RequestLimit(maxCount = 20, second = 5)
    @GetMapping("/getMinLink")
    public Result<String> getMinLink(String longLink) {
        String link = shortUrlService.getMinLink(longLink);
        return Result.ok(link);
    }

    @RequestLimit(maxCount = 20, second = 5)
    @GetMapping("/getLink")
    public Result<String> getLink(String longLink) {
        String link = shortUrlService.getLink(longLink);
        return Result.ok(link);
    }

    @RequestLimit(maxCount = 20, second = 5)
    @GetMapping("/getLinks")
    public Result<List<String>> getLinks(List<String> longLinks) {
        List<String> links = shortUrlService.getLinks(longLinks);
        return Result.ok(links);
    }
}
