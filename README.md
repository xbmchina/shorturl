## 操作步骤
* 1、安装好redis、mysql
* 2、创建2个数据库short_link_01、short_link_02
* 3、修改配置application.yml文件,连接redis、mysql
* 4、运行测试类BatchGenerateTable.java，批量生成表。
* 5、启动项目即可，访问http://localhost:8888/link/getLink?longLink=https://gitee.com/
生成短链接
* 6、访问短链接：http://localhost:8888/AAAB



## 相关技术
* redis生成自增主键ID
* sharding-jdbc实现分库分表
* 采用10进制转62进制Base62，生成短链接





