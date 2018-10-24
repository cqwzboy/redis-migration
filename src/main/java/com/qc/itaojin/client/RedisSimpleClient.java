package com.qc.itaojin.client;

import com.qc.itaojin.common.RedisClientFactory;
import com.qc.itaojin.domain.ScanResult;
import com.qc.itaojin.enums.RedisDataType;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class RedisSimpleClient implements RedisClient {

    @Override
    public ScanResult scan(int cursor) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult scanResult = jedis.scan(String.valueOf(cursor));
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
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
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult scanResult = jedis.scan(String.valueOf(cursor), scanParams);
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult scanResult = jedis.hscan(key, String.valueOf(cursor));
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
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
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult scanResult = jedis.hscan(key, String.valueOf(cursor), scanParams);
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    @Override
    public RedisDataType type(String key) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            return RedisDataType.typeOf(jedis.type(key));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            return jedis.ttl(key);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    @Override
    public String getStr(String key) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /**
     * list 相关操作
     * */
    @Override
    public long llen(String key) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            return jedis.llen(key);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }
    @Override
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            return jedis.lrange(key, start, end);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /**
     * set 相关操作
     * */
    @Override
    public ScanResult<String> sscan(String key, int cursor) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult scanResult = jedis.sscan(key, String.valueOf(cursor));
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }
    @Override
    public ScanResult<String> sscan(String key, int cursor, int count, String match) {
        ScanParams scanParams = new ScanParams();
        if(count > 0){
            scanParams.count(count);
        }
        if(StringUtils.isNotBlank(match)){
            scanParams.match(match);
        }
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult scanResult = jedis.sscan(key, String.valueOf(cursor), scanParams);
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /**
     * zset 相关
     * */
    @Override
    public ScanResult<Tuple> zscan(String key, int cursor) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult<Tuple> scanResult = jedis.zscan(key, String.valueOf(cursor));
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }
    @Override
    public ScanResult<Tuple> zscan(String key, int cursor, int count, String match) {
        ScanParams scanParams = new ScanParams();
        if(count > 0){
            scanParams.count(count);
        }
        if(StringUtils.isNotBlank(match)){
            scanParams.match(match);
        }
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getFromJedis();
            redis.clients.jedis.ScanResult<Tuple> scanResult = jedis.zscan(key, String.valueOf(cursor), scanParams);
            return new ScanResult(Integer.parseInt(scanResult.getStringCursor()), scanResult.getResult());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /******************************************************************************************************************/

    @Override
    public void hset(String key, String field, String value) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getToJedis();
            jedis.hset(key, field, value);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getToJedis();
            jedis.set(key, value);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    @Override
    public void expire(String key, int ttl) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getToJedis();
            jedis.expire(key, ttl);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /**
     * list 相关操作
     * */
    @Override
    public long rpush(String key, String... strings) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getToJedis();
            return jedis.rpush(key, strings);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /**
     * set 相关操作
     * */
    @Override
    public long sadd(String key, String... members) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getToJedis();
            return jedis.sadd(key, members);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }

    /**
     * zset 相关操作
     * */
    @Override
    public long zadd(String key, double score, String member) {
        Jedis jedis = null;
        try{
            jedis = RedisClientFactory.getToJedis();
            return jedis.zadd(key, score,  member);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            RedisClientFactory.closeJedis(jedis);
        }
    }
}
