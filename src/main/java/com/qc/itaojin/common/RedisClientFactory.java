package com.qc.itaojin.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis工厂类
 *
 * @author fuqinqin
 * @since 2018-10-24
 */
public class RedisClientFactory {
    private static JedisPool fromJedisPool;
    private static JedisPoolConfig fromJedisPoolConfig;
    private static JedisPool toJedisPool;
    private static JedisPoolConfig toJedisPoolConfig;

    private static boolean inited = false;

    /**
     * 初始化jedis连接池
     */
    private synchronized static void initJedisPool() {
        if(fromJedisPoolConfig==null && toJedisPoolConfig==null){
            initPoolConfig() ;
        }

        fromJedisPool = new JedisPool(fromJedisPoolConfig,
                RedisConfig.getFromHost(),
                RedisConfig.getFromPort(),
                RedisConfig.getTimeout(),
                RedisConfig.getFromPassword(),
                RedisConfig.getFromDbIndex());

        toJedisPool = new JedisPool(toJedisPoolConfig,
                RedisConfig.getToHost(),
                RedisConfig.getToPort(),
                RedisConfig.getTimeout(),
                RedisConfig.getToPassword(),
                RedisConfig.getToDbIndex());

        inited = true;
    }

    /**
     * 初始化连接池配置
     */

    private static void initPoolConfig() {
        fromJedisPoolConfig = new JedisPoolConfig();
        fromJedisPoolConfig.setMaxIdle(RedisConfig.getMaxIdle());
        fromJedisPoolConfig.setMaxTotal(RedisConfig.getMaxTotal());
        fromJedisPoolConfig.setMaxWaitMillis(RedisConfig.getMaxWaitMillis());
        fromJedisPoolConfig.setTestOnBorrow(RedisConfig.isTestOnBorrow());

        toJedisPoolConfig = new JedisPoolConfig();
        toJedisPoolConfig.setMaxIdle(RedisConfig.getMaxIdle());
        toJedisPoolConfig.setMaxTotal(RedisConfig.getMaxTotal());
        toJedisPoolConfig.setMaxWaitMillis(RedisConfig.getMaxWaitMillis());
        toJedisPoolConfig.setTestOnBorrow(RedisConfig.isTestOnBorrow());
    }

    /**
     * 获取源redis连接
     *
     * @return
     */
    private static Jedis getFromJedisBySimple() {
        if (fromJedisPool==null && !inited) {
            initJedisPool();
        }
        return fromJedisPool.getResource();
    }

    /**
     * 获取目标redis连接
     *
     * @return
     */
    private static Jedis getToJedisBySimple() {
        if (toJedisPool==null && !inited) {
            initJedisPool();
        }
        return toJedisPool.getResource();
    }

    public static Jedis getFromJedis(){
        return getFromJedisBySimple();
    }

    public static Jedis getToJedis(){
        return getToJedisBySimple();
    }

    /**
     * 关闭连接（还回给连接池）
     *
     * @param jedis
     */
    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
