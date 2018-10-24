package com.qc.itaojin.common;

import com.tj.common.lang.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Properties;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class RedisConfig {
    private static final String REDIS_PROPERTIES_FILE = "redis-migration.properties";
    private static Properties prop;

    /**
     * 源Redis
     * */
    private static final String FROM_KEY_HOST = "redis.from.host";
    private static final String FROM_KEY_PORT = "redis.from.port";
    private static final String FROM_KEY_PASSWORD = "redis.from.password";
    private static final String FROM_KEY_DB_INDEX = "redis.from.dbIndex";
    private static final String FROM_KEY_CHARSET = "redis.from.charset";

    /**
     * 目标Redis
     * */
    private static final String TO_KEY_HOST = "redis.to.host";
    private static final String TO_KEY_PORT = "redis.to.port";
    private static final String TO_KEY_PASSWORD = "redis.to.password";
    private static final String TO_KEY_DB_INDEX = "redis.to.dbIndex";
    private static final String TO_KEY_CHARSET = "redis.to.charset";

    private static final String KEY_TIMEOUT = "redis.timeout";
    private static final String KEY_MAX_TOTAL = "redis.maxTotal";
    private static final String KEY_MAX_IDLE = "redis.maxIdle";
    private static final String KEY_MAX_WAIT_MILLIS = "redis.maxWaitMillis";
    private static final String KEY_TEST_ON_BORROW = "redis.testOnBorrow";
    private static final String MIGRATION_PATTERN = "redis.migration.pattern";
    private static final String MIGRATION_HASH_PATTERN = "redis.migration.hash.pattern";
    private static final String MIGRATION_SET_PATTERN = "redis.migration.set.pattern";
    private static final String MIGRATION_ZSET_PATTERN = "redis.migration.zset.pattern";
    private static final String MIGRATION_BATCH_SIZE = "redis.migration.batchSize";

    private static String fromHost;
    private static int fromPort = 6379;
    private static String fromPassword;
    private static int fromDbIndex = 0;
    private static String fromCharset = "UTF-8";

    private static String toHost;
    private static int toPort = 6379;
    private static String toPassword;
    private static int toDbIndex = 0;
    private static String toCharset = "UTF-8";

    private static int timeout = 5000 ;
    private static int maxTotal = 100;
    private static int maxIdle = 20;
    private static long maxWaitMillis = 2000;
    private static boolean testOnBorrow = false;
    private static String migrationPattern = "*";
    private static String migrationHashPattern = "*";
    private static String migrationSetPattern = "*";
    private static String migrationZsetPattern = "*";
    private static int migrationBatchSize = 1000;

    //静态块加载配置
    static {
        prop = PropertiesUtils.loadProperties(REDIS_PROPERTIES_FILE) ;

        checkParam(prop);

        // 源Redis
        fromHost = prop.getProperty(FROM_KEY_HOST);
        fromPassword = prop.getProperty(FROM_KEY_PASSWORD);
        if(StringUtils.isNotBlank(prop.getProperty(FROM_KEY_PORT))){
            fromPort = Integer.parseInt(prop.getProperty(FROM_KEY_PORT));
        }
        if(StringUtils.isNumeric(prop.getProperty(FROM_KEY_DB_INDEX))){
            fromDbIndex = Integer.parseInt(prop.getProperty(FROM_KEY_DB_INDEX));
        }
        if(StringUtils.isNotBlank(prop.getProperty(FROM_KEY_CHARSET))){
            fromCharset = prop.getProperty(FROM_KEY_CHARSET);
        }

        // 目标Redis
        toHost = prop.getProperty(TO_KEY_HOST);
        toPassword = prop.getProperty(TO_KEY_PASSWORD);
        if(StringUtils.isNotBlank(prop.getProperty(TO_KEY_PORT))){
            toPort = Integer.parseInt(prop.getProperty(TO_KEY_PORT));
        }
        if(StringUtils.isNumeric(prop.getProperty(TO_KEY_DB_INDEX))){
            toDbIndex = Integer.parseInt(prop.getProperty(TO_KEY_DB_INDEX));
        }
        if(StringUtils.isNotBlank(prop.getProperty(TO_KEY_CHARSET))){
            toCharset = prop.getProperty(TO_KEY_CHARSET);
        }

        if(StringUtils.isNumeric(prop.getProperty(KEY_TIMEOUT))){
            timeout = Integer.parseInt(prop.getProperty(KEY_TIMEOUT));
        }
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
        if(StringUtils.isNotBlank(prop.getProperty(MIGRATION_PATTERN))){
            migrationPattern = prop.getProperty(MIGRATION_PATTERN);
        }
        if(StringUtils.isNotBlank(prop.getProperty(MIGRATION_HASH_PATTERN))){
            migrationHashPattern = prop.getProperty(MIGRATION_HASH_PATTERN);
        }
        if(StringUtils.isNotBlank(prop.getProperty(MIGRATION_SET_PATTERN))){
            migrationSetPattern = prop.getProperty(MIGRATION_SET_PATTERN);
        }
        if(StringUtils.isNotBlank(prop.getProperty(MIGRATION_ZSET_PATTERN))){
            migrationZsetPattern = prop.getProperty(MIGRATION_ZSET_PATTERN);
        }
        if(StringUtils.isNumeric(prop.getProperty(MIGRATION_BATCH_SIZE))){
            migrationBatchSize = Integer.parseInt(prop.getProperty(MIGRATION_BATCH_SIZE));
        }
    }

    private static void checkParam(Properties prop) {
        if(StringUtils.isBlank(prop.getProperty(FROM_KEY_HOST))){
            throw new IllegalArgumentException("源Redis地址为空");
        }
        if(StringUtils.isBlank(prop.getProperty(FROM_KEY_PASSWORD))){
            throw new IllegalArgumentException("源Redis密码为空");
        }
        if(StringUtils.isBlank(prop.getProperty(TO_KEY_HOST))){
            throw new IllegalArgumentException("目标Redis地址为空");
        }
        if(StringUtils.isBlank(prop.getProperty(TO_KEY_PASSWORD))){
            throw new IllegalArgumentException("目标Redis密码为空");
        }
    }

    public static String getFromHost() {
        return fromHost;
    }
    public static int getFromPort() {
        return fromPort;
    }
    public static String getFromPassword() {
        return fromPassword;
    }
    public static int getFromDbIndex() {
        return fromDbIndex;
    }
    public static String getFromCharset() {
        return fromCharset;
    }

    public static String getToHost() {
        return toHost;
    }
    public static int getToPort() {
        return toPort;
    }
    public static String getToPassword() {
        return toPassword;
    }
    public static int getToDbIndex() {
        return toDbIndex;
    }
    public static String getToCharset() {
        return toCharset;
    }

    public static int getTimeout() {
        return timeout;
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
    public static String getMigrationPattern() {
        return migrationPattern;
    }
    public static String getMigrationHashPattern() {
        return migrationHashPattern;
    }
    public static String getMigrationSetPattern() {
        return migrationSetPattern;
    }
    public static String getMigrationZsetPattern() {
        return migrationZsetPattern;
    }
    public static int getMigrationBatchSize() {
        return migrationBatchSize;
    }
}
