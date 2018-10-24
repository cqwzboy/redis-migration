package com.qc.itaojin;

import com.qc.itaojin.enums.RedisDataType;
import com.tj.common.redis.RedisTemplate;
import com.tj.common.redis.TJKRedisCallBack;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;

import java.util.Map;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class RedisSimpleClient extends com.tj.common.redis.simple.RedisSimpleClient implements RedisClient {

    @Override
    public ScanResult scan(int cursor) {
        redis.clients.jedis.ScanResult<String> scanResult = RedisTemplate.excuteBySimple(new TJKRedisCallBack() {
            @Override
            public redis.clients.jedis.ScanResult<String> call(Jedis jedis) {
                return jedis.scan(String.valueOf(cursor));
            }
        });
        return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
    }

    @Override
    public ScanResult scan(int cursor, int count, String match) {
        ScanParams scanParams = new ScanParams();
        if(count > 0){
            scanParams.count(count);
        }
        if(StringUtils.isNotBlank(match)){
            scanParams.match(match);
        }
        redis.clients.jedis.ScanResult<String> scanResult = RedisTemplate.excuteBySimple(new TJKRedisCallBack() {
            @Override
            public redis.clients.jedis.ScanResult<String> call(Jedis jedis) {
                return jedis.scan(String.valueOf(cursor), scanParams);
            }
        });
        return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        redis.clients.jedis.ScanResult<Map.Entry<String, String>> scanResult = RedisTemplate.excuteBySimple(new TJKRedisCallBack() {
            @Override
            public redis.clients.jedis.ScanResult<Map.Entry<String, String>> call(Jedis jedis) {
                return jedis.hscan(key, String.valueOf(cursor));
            }
        });
        return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor, int count, String match) {
        ScanParams scanParams = new ScanParams();
        if(count > 0){
            scanParams.count(count);
        }
        if(StringUtils.isNotBlank(match)){
            scanParams.match(match);
        }
        redis.clients.jedis.ScanResult<Map.Entry<String, String>> scanResult = RedisTemplate.excuteBySimple(new TJKRedisCallBack() {
            @Override
            public redis.clients.jedis.ScanResult<Map.Entry<String, String>> call(Jedis jedis) {
                return jedis.hscan(key, String.valueOf(cursor), scanParams);
            }
        });
        return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
    }

    @Override
    public RedisDataType type(String key) {
        return RedisTemplate.excuteBySimple(new TJKRedisCallBack() {
            @Override
            public RedisDataType call(Jedis jedis) {
                return RedisDataType.typeOf(jedis.type(key));
            }
        });
    }

    @Override
    public Jedis getJedis() {
        return RedisTemplate.excuteBySimple(new TJKRedisCallBack() {
            @Override
            public Jedis call(Jedis jedis) {
                return jedis;
            }
        });
    }
}
