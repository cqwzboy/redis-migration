package com.qc.itaojin;

import com.qc.itaojin.enums.RedisDataType;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 基于tj-common redis工具类的扩展
 */
public interface RedisClient extends com.tj.common.redis.RedisClient {

    /**
     * 扫描
     * */
    ScanResult scan(int cursor);
    ScanResult scan(int cursor, int count, String match);

    /**
     * 扫描Hash
     * */
   ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor);
   ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor, int count, String match);

    /**
     * 返回Redis数据类型
     * */
    RedisDataType type(String key);

    /**
     * 返回一个客户端
     * */
    Jedis getJedis();
}
