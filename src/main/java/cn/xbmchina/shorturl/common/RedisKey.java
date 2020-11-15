package cn.xbmchina.shorturl.common;

public class RedisKey {

    //短链接最大自增id
    public final static String LINK_MAX_ID = "@lk:Id";

    //极端链接最大自增id
    public final static String MIN_LINK_MAX_ID = "@lk:mId";

    //缓存最近一天的短链接
    public final static String LINK_LIST_LAST = "@lk:list_";

    //点击数队列
    public final static String LINK_CLICK_COUNT = "@lk:cllist";

}
