package org.fuelteam.watt.redis.redission;

public enum ClientMode {

    // 默认客户端
    DEFAULT,

    // 异步响应客户端
    REACTIVE,

    // 注册上述两个客户端
    BOTH,

    // 禁用 Redisson
    NONE
}