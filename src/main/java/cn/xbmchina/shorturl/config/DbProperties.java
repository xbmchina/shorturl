package cn.xbmchina.shorturl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "jdbc")
public class DbProperties {
    private List<DsEntity> dsList;
}
