package cn.xbmchina.shorturl.rest;

import cn.xbmchina.shorturl.annotation.RequestLimit;
import cn.xbmchina.shorturl.common.Result;
import cn.xbmchina.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class IndexController {

    @Autowired
    ShortUrlService shortUrlService;

    @RequestLimit(maxCount = 100, second = 5)
    @GetMapping("/jump/{shortUrl}")
    public Result<String> visit(@PathVariable String shortUrl) {
        String originUrl = shortUrlService.visitLink(shortUrl);
        return Result.ok(originUrl);
    }

    @RequestLimit(maxCount = 100, second = 5)
    @GetMapping("/{shortUrl}")
    public void visit(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originUrl = shortUrlService.visitLink(shortUrl);
        response.sendRedirect(originUrl);
    }

}