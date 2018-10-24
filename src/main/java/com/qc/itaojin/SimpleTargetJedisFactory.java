package com.qc.itaojin;

import com.tj.common.lang.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class SimpleTargetJedisFactory {
    private static final String TARGET_REDIS_PROPERTIES_FILE = "target-redis.properties";
    private static Properties prop;

    private static JedisPool jedisPool;
    private static JedisPoolConfig jedisPoolConfig;

    private static final String KEY_HOST = "redis.host";
    private static final String KEY_PORT = "redis.port";
    private static final String KEY_TIMEOUT = "redis.timeout";
    private static final String KEY_PASSWORD = "redis.password";
    private static final String KEY_MAX_TOTAL = "redis.maxTotal";
    private static final String KEY_MAX_IDLE = "redis.maxIdle";
    private static final String KEY_MAX_WAIT_MILLIS = "redis.maxWaitMillis";
    private static final String KEY_TEST_ON_BORROW = "redis.testOnBorrow";
    private static final String KEY_DB_INDEX = "redis.dbIndex";
    private static final String KEY_CHARSET = "redis.charset";

    private static String host;
    private static int port = 6379;
    private static int timeout = 5000 ;
    private static String password;
    private static int maxTotal = 100;
    private static int maxIdle = 20;
    private static long maxWaitMillis = 2000;
    private static boolean testOnBorrow = false;
    private static int dbIndex = 0;
    private static String charset;

    //静态块加载配置
    static {
        prop = PropertiesUtils.loadProperties(TARGET_REDIS_PROPERTIES_FILE) ;
        host = prop.getProperty(KEY_HOST);
        if(StringUtils.isNotBlank(prop.getProperty(KEY_PORT))){
            port = Integer.parseInt(prop.getProperty(KEY_PORT));
        }
        if(StringUtils.isNumeric(prop.getProperty(KEY_TIMEOUT))){
            timeout = Integer.parseInt(prop.getProperty(KEY_TIMEOUT));
        }
        password = prop.getProperty(KEY_PASSWORD);
        if(StringUtils.isNumeric(prop.getProperty(KEY_MAX_TOTAL))){
            maxTotal = Integer.parseInt(prop.getProperty(KEY_MAX_TOTAL));
        }
        if(StringUtils.isNumeric(prop.getProperty(KEY_MAX_IDLE))){
            maxIdle = Integer.parseInt(prop.getProperty(KEY_MAX_IDLE));
        }
        if(StringUtils.isNumeric(prop.getProperty(KEY_MAX_WAIT_MILLIS))){
            maxWaitMillis = Long.parseLong(prop.getProperty(KEY_MAX_WAIT_MILLIS));
        }
        if(StringUtils.isNotBlank(prop.getProperty(KEY_TEST_ON_BORROW))){
            testOnBorrow = Boolean.parseBoolean(prop.getProperty(KEY_TEST_ON_BORROW));
        }
        if(StringUtils.isNumeric(prop.getProperty(KEY_DB_INDEX))){
            dbIndex = Integer.parseInt(prop.getProperty(KEY_DB_INDEX));
        }
        charset = prop.getProperty(KEY_CHARSET);
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }

    public static int getTimeout() {
        return timeout;
    }

    public static String getPassword() {
        return password;
    }

    public static int getMaxTotal() {
        return maxTotal;
    }

    public static int getMaxIdle() {
        return maxIdle;
    }

    public static long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public static boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public static int getDbIndex() {
        return dbIndex;
    }

    public static String getCharset() {
        return charset;
    }

    /**
     * 初始化jedis连接池
     */
    private static void initJedisPool() {
        if(jedisPoolConfig == null){
            initPoolConfig() ;
        }
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, dbIndex);
    }

    /**
     * 初始化连接池配置
     */

    private static void initPoolConfig() {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
    }

    /**
     * 获取redis连接
     *
     * @return
     */
    private static Jedis getConnectionBySimple() {
        if (jedisPool == null) {
            throw new IllegalArgumentException("jedisSentinelPool为空");
        }
        return jedisPool.getResource();
    }

    public static Jedis getTargetJedis(){
        if(jedisPool == null){
            initJedisPool();
        }
        return getConnectionBySimple();
    }

    /**
     * 关闭连接（还回给连接池）
     *
     * @param jedis
     */
    private static void closeConnection(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
