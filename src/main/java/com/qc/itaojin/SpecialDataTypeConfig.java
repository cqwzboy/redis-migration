package com.qc.itaojin;

import com.tj.common.lang.PropertiesUtils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by fuqinqin on 2018/10/23.
 */
public class SpecialDataTypeConfig {
    public static final Set<String> HASH_SET = new HashSet<>();
    private static final String SEPARATOR = ",";
    private static final String SPECIAL_DATA_TYPE_FILE = "special-data-type.properties";

    static {
        Properties pros = PropertiesUtils.loadProperties(SPECIAL_DATA_TYPE_FILE);
        String hashString = pros.getProperty("hash");
        String[] hashArray = hashString.split(SEPARATOR);
        for (String hash : hashArray) {
            HASH_SET.add(hash);
        }
    }
}
