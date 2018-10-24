package com.qc.itaojin.client;

import com.qc.itaojin.domain.ScanResult;
import com.qc.itaojin.enums.RedisDataType;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;

/**
 * 基于tj-common redis工具类的扩展
 */
public interface RedisClient {

    /**
     * 返回Redis数据类型
     * */
    RedisDataType type(String key);

    /**
     * 计算剩余有效时间
     * */
    long ttl(String key);

    String getStr(String key);

    /**
     * 扫描
     * */
    ScanResult scan(int cursor);
    ScanResult scan(int cursor, int count, String match);

    /**
     * Hash 相关操作
     * */
   ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor);
   ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor, int count, String match);

    /**
     * list 相关操作
     * */
    long llen(String key);
    List<String> lrange(String key, long start, long end);

    /**
     * set 相关操作
     * */
    ScanResult<String> sscan(String key, int cursor);
    ScanResult<String> sscan(String key, int cursor, int count, String match);

    /**
     * zset 相关操作
     * */
    ScanResult<Tuple> zscan(String key, int cursor);
    ScanResult<Tuple> zscan(String key, int cursor, int count, String match);

    /********************************************************************************/

    void hset(final String key, final String field, final String value);
    void set(final String key, final String value);
    void expire(String key, int ttl);

    /**
     * list 相关操作
     * */
    long rpush(String key, String... strings);

    /**
     * set 相关操作
     * */
    long sadd(String key, String... members);

    /**
     * zset 相关操作
     * */
    long zadd(String key, double score, String member);
}
