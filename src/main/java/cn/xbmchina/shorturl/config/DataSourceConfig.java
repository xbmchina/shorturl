package cn.xbmchina.shorturl.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Autowired
    DbProperties dbProperties;

    @Bean
    public DataSource buildDataSource() throws SQLException {

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        List<DsEntity> dsList = dbProperties.getDsList();
        for (int i = 0; i < dsList.size(); i++) {
            DsEntity dsEntity = dsList.get(i);
            DruidDataSource dataSource1 = new DruidDataSource();
            dataSource1.setDriverClassName(dsEntity.getDriverClassName());
            dataSource1.setUrl(dsEntity.getUrl());
            dataSource1.setUsername(dsEntity.getUsername());
            dataSource1.setPassword(dsEntity.getPassword());
            dataSourceMap.put("ds" + i, dataSource1);
        }


        // 配置short_url表规则
        TableRuleConfiguration shortUrlTableRuleConfig = new TableRuleConfiguration("short_url", "ds${0..1}.short_url_${[" +
                "'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M','N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'," +
                "'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'," +
                "'0', '1', '2', '3', '4', '5', '6', '7', '8', '9']}");
        // 配置分库 + 分表策略
        shortUrlTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds${id % 2}"));
        shortUrlTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("short_url", new MyPreciseShardingAlgorithm()));

        // 配置min_short_url表规则
        TableRuleConfiguration minShortUrlTableRuleConfig = new TableRuleConfiguration("min_short_url", "ds${0..1}.min_short_url_${[" +
                "'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M','N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'," +
                "'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'," +
                "'0', '1', '2', '3', '4', '5', '6', '7', '8', '9']}");

        // 配置分库 + 分表策略
        minShortUrlTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds${id % 2}"));
        minShortUrlTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("short_url", new MyPreciseShardingAlgorithm()));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(shortUrlTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(minShortUrlTableRuleConfig);
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());

        return dataSource;
    }
}