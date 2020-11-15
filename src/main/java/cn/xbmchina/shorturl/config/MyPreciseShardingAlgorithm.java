package cn.xbmchina.shorturl.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@Slf4j
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 根据短链接最后一位进行定位表名
     *
     * @param collection           表名
     * @param preciseShardingValue 列值
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String value = preciseShardingValue.getValue();
        String flag = value.substring(value.length() - 1);
        for (String name : collection) {
            if (name.endsWith(flag)) {
                log.info("return name:" + name);
                return name;
            }
        }
        return null;
    }
}