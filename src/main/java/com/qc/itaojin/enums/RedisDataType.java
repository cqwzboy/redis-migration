package com.qc.itaojin.enums;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public enum RedisDataType {
    STRING("string"),
    HASH("hash"),
    LIST("list"),
    SET("set"),
    ZSET("zset"),
    ;

    private String type;

    RedisDataType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RedisDataType typeOf(String type){
        if(type==null || "".equals(type.trim())){
            return null;
        }

        for (RedisDataType redisDataType : RedisDataType.values()) {
            if(type.equals(redisDataType.getType())){
                return redisDataType;
            }
        }

        return null;
    }
}
