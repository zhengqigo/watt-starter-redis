package org.fuelteam.watt.redis.redission;

public enum RedisType {

    // 单节点模式
    SINGLE,

    // 集群模式
    CLUSTER,

    // 哨兵模式
    SENTINEL
}