package com.qc.itaojin;

import com.qc.itaojin.client.RedisClient;
import com.qc.itaojin.client.RedisSimpleClient;
import com.qc.itaojin.common.RedisConfig;
import com.qc.itaojin.common.DataTypeConfig;
import com.qc.itaojin.domain.ScanResult;
import com.qc.itaojin.enums.RedisDataType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Redis数据迁移器
 * @author qinqin Fu
 * @since 2018-10-23
 */
public class RedisMigrator {
    /**
     * scan 批处理数量
     * */
    private int batchSize = RedisConfig.getMigrationBatchSize();
    /**
     * scan hscan sscan zscan 的正则
     * */
    private String pattern = RedisConfig.getMigrationPattern();
    private String hashPattern = RedisConfig.getMigrationHashPattern();
    private String setPattern = RedisConfig.getMigrationSetPattern();
    private String zsetPattern = RedisConfig.getMigrationZsetPattern();

    private RedisClient redisClient;

    public RedisMigrator(){
        redisClient = new RedisSimpleClient();
    }

    public long excute(){
        long start = System.currentTimeMillis();

        // 扫描源redis
        int cursor = 0;
        ScanResult<String> scanResult;
        do{
            scanResult = redisClient.scan(cursor, batchSize, pattern);
            cursor = scanResult.getCursor();
            List<String> keys = scanResult.getResults();
            for (String key : keys) {
                System.out.println(key);
                // 获取有效时间
                Long ttl = redisClient.ttl(key);

                // 开始向目标redis插入数据
                RedisDataType dataType = redisDataType(key);
                if(RedisDataType.STRING.equals(dataType)){// string
                    String value = redisClient.getStr(key);
                    value = StringUtils.isBlank(value) ? "" : value;
                    redisClient.set(key, value);
                }else if(RedisDataType.HASH.equals(dataType)){// hash
                    int hashCursor = 0;
                    do{
                        ScanResult<Map.Entry<String, String>> hscanResult = redisClient.hscan(key, hashCursor, batchSize, hashPattern);
                        hashCursor = hscanResult.getCursor();
                        List<Map.Entry<String, String>> results = hscanResult.getResults();
                        for (Map.Entry<String, String> result : results) {
                            redisClient.hset(key, result.getKey(), result.getValue());
                        }
                    }while (hashCursor > 0);
                }else if(RedisDataType.LIST.equals(dataType)){// list
                    long len = redisClient.llen(key);
                    if(len > 0){
                        List<String> listData = redisClient.lrange(key, 0, len);
                        String[] strings = listData.toArray(new String[0]);
                        redisClient.rpush(key, strings);
                    }
                }else if(RedisDataType.SET.equals(dataType)){// set
                    int setCursor = 0;
                    do{
                        ScanResult<String> sscanResult = redisClient.sscan(key, setCursor, batchSize, setPattern);
                        setCursor = sscanResult.getCursor();
                        List<String> results = sscanResult.getResults();
                        redisClient.sadd(key, results.toArray(new String[0]));
                    }while (setCursor > 0);
                }else{// zset
                    int zsetCursor = 0;
                    do{
                        ScanResult<Tuple> sscanResult = redisClient.zscan(key, zsetCursor, batchSize, zsetPattern);
                        zsetCursor = sscanResult.getCursor();
                        List<Tuple> results = sscanResult.getResults();
                        for (Tuple result : results) {
                            redisClient.zadd(key, result.getScore(), result.getElement());
                        }
                    }while (zsetCursor > 0);
                }

                if(ttl > 0){
                    redisClient.expire(key, ttl.intValue());
                }
            }
        }while (cursor > 0);

        return System.currentTimeMillis() - start;
    }

    /**
     * 根据key计算出对应的数据类型
     * */
    private RedisDataType redisDataType(String key){
        if(!DataTypeConfig.realTimeType){
            if(DataTypeConfig.HASH.contains(key)){
                return RedisDataType.HASH;
            }
            if(DataTypeConfig.LIST.contains(key)){
                return RedisDataType.LIST;
            }
            if(DataTypeConfig.SET.contains(key)){
                return RedisDataType.SET;
            }
            if(DataTypeConfig.ZSET.contains(key)){
                return RedisDataType.ZSET;
            }
            return RedisDataType.STRING;
        }else{
            return redisClient.type(key);
        }
    }
}
