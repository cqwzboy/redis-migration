package com.qc.itaojin.common;

import com.qc.itaojin.enums.RedisDataType;
import com.tj.common.lang.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class DataTypeConfig {
    public static final Set<String> HASH = new HashSet<>();
    public static final Set<String> LIST = new HashSet<>();
    public static final Set<String> SET = new HashSet<>();
    public static final Set<String> ZSET = new HashSet<>();
    private static final String SEPARATOR = ",";
    private static final String DATA_TYPE_PROPERTIES_FILE = "data-type.properties";

    /**
     * ① 实时查询redis，执行type
     * ② 将非string类型的的键值配置于data-type.properties配置文件中，以hash,list,set,zset作为键，值为各个redis key以逗号分隔的字符串
     * 方式①通用性强，但是对于性能不高，或者要求执行速度快的业务场景将不适合
     * 方式②虽然繁琐，但是在大多数数据类型为string类型的情况下，将少数特殊结构罗列出来，则少去了type的时间，提醒性能
     * */
    public static  boolean realTimeType = true;

    static {
        try{
            Properties dataTypeProp = PropertiesUtils.loadProperties(DATA_TYPE_PROPERTIES_FILE);
            if(!CollectionUtils.isEmpty(dataTypeProp.stringPropertyNames())){
                realTimeType = false;

                // hash
                String hashString = dataTypeProp.getProperty(RedisDataType.HASH.getType());
                if(StringUtils.isNotBlank(hashString)){
                    String[] hashArray = hashString.split(SEPARATOR);
                    for (String hash : hashArray) {
                        HASH.add(hash);
                    }
                }

                // list
                String listString = dataTypeProp.getProperty(RedisDataType.LIST.getType());
                if(StringUtils.isNotBlank(listString)){
                    String[] listArray = listString.split(SEPARATOR);
                    for (String list : listArray) {
                        LIST.add(list);
                    }
                }

                // set
                String setString = dataTypeProp.getProperty(RedisDataType.SET.getType());
                if(StringUtils.isNotBlank(setString)){
                    String[] setArray = setString.split(SEPARATOR);
                    for (String set : setArray) {
                        SET.add(set);
                    }
                }

                // zset
                String zsetString = dataTypeProp.getProperty(RedisDataType.ZSET.getType());
                if(StringUtils.isNotBlank(zsetString)){
                    String[] zsetArray = zsetString.split(SEPARATOR);
                    for (String zset : zsetArray) {
                        ZSET.add(zset);
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
