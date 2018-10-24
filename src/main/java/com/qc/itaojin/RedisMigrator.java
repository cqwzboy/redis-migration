package com.qc.itaojin;

import com.qc.itaojin.enums.RedisDataType;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Redis数据迁移器
 * @author qinqin Fu
 * @since 2018-10-23
 */
public class RedisMigrator {
    private static final int DEFAULT_BATCH_SIZE = 1000;
    private int batchSize = DEFAULT_BATCH_SIZE;

    public long migrateAll(){
        long start = System.currentTimeMillis();

        RedisClient sourceClient = new RedisSimpleClient();
        Jedis targetJedis = SimpleTargetJedisFactory.getTargetJedis();

        // 扫描源redis
        int cursor = 0;
        ScanResult<String> scanResult;
        do{
            scanResult = sourceClient.scan(cursor, batchSize, "*");
            cursor = scanResult.getCursor();
            List<String> keys = scanResult.getResults();
            if(keys!=null && keys.size()>0){
                for (String key : keys) {
                    System.out.println(key);
                    // 获取有效时间
                    Long ttl = -1L;
                    if(!SpecialDataTypeConfig.HASH_SET.contains(key)){
                        ttl = sourceClient.ttl(key);
                    }

                    // 开始向目标redis插入数据
                    if(SpecialDataTypeConfig.HASH_SET.contains(key)){
                        int hashCursor = 0;
                        do{
                            ScanResult<Map.Entry<String, String>> hscanResult = sourceClient.hscan(key, hashCursor, batchSize, "*");
                            hashCursor = hscanResult.getCursor();
                            List<Map.Entry<String, String>> results = hscanResult.getResults();
                            for (Map.Entry<String, String> result : results) {
                                targetJedis.hset(key, result.getKey(), result.getValue());
                            }
                        }while (hashCursor > 0);
                    }else{
                        String value = sourceClient.getStr(key);
                        value = StringUtils.isBlank(value) ? "" : value;
                        targetJedis.set(key, value);
                        if(ttl.longValue() > 0){
                            targetJedis.expire(key, ttl.intValue());
                        }
                    }
                }
            }
        }while (cursor > 0);

        return System.currentTimeMillis() - start;
    }


}
