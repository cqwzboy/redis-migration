package com.qc.itaojin;

import static org.junit.Assert.assertTrue;

import com.qc.itaojin.enums.RedisDataType;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private RedisClient redisClient = new RedisSimpleClient();
    private RedisMigrator redisMigrator;

    @Before
    public void init(){
        redisMigrator = new RedisMigrator();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void scanTest(){
        long start = System.currentTimeMillis();
        long temp = start;
        int cursor = 0;
        int batchSize = 1000;
        int num = 0;
        boolean lock = true;
        int i = 0;
        do{
            ScanResult<String> scan = redisClient.scan(cursor, batchSize, "*");
            cursor = scan.getCursor();
            List<String> keys = scan.getResults();
            for (String key : keys) {
                int hashCursor = 0;
                num ++;
                if("KEY_LASK_SCORE".equals(key)){
                    RedisDataType dataType = redisClient.type(key);
                    if(RedisDataType.HASH.equals(dataType)){
                        do{
                            System.out.println(key);
                            ScanResult<Map.Entry<String, String>> hscanResult = redisClient.hscan(key, hashCursor, batchSize, "*");
                            hashCursor = hscanResult.getCursor();
                            List<Map.Entry<String, String>> results = hscanResult.getResults();
                            for (Map.Entry<String, String> result : results) {
                                System.out.println("\t key="+result.getKey()+", value="+result.getValue());
                            }
                        }while (hashCursor > 0);
                    }
                }

                if(num % 1000 == 0){
                    long now = System.currentTimeMillis();
                    System.out.println("============ num = "+num+"\t耗时："+(now-temp)+" 毫秒 ============");
                    temp = now;
                }
                /*
//                System.out.println(key);
                if("KEY_LASK_SCORE".equals(key)){
                    lock = false;
                    i = num;
                }
                if(!lock && num>=i){
                    System.out.println(key);
                }*/
            }
        }while (cursor > 0);
        System.out.println("-- scan success num="+num+" --");
        System.out.println("耗时："+(System.currentTimeMillis() - start)+" 毫秒");
    }

    @Test
    public void beginMigrate(){
        System.out.println("耗时："+redisMigrator.migrateAll()+" 毫秒");
    }

}
