package org.fuelteam.watt.redis;

import java.util.Map;

import com.google.common.collect.Maps;

public enum RedisType {

    // 单节点模式
    SINGLE("Single"),

    // 集群模式
    CLUSTER("Cluster");

    private String mode;

    private static Map<String, RedisType> map = Maps.newHashMap();

    static {
        for (RedisType redisType : RedisType.values()) {
            map.put(redisType.getMode(), redisType);
        }
    }

    public static RedisType of(String mode) {
        return map.get(mode);
    }

    private RedisType(final String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}