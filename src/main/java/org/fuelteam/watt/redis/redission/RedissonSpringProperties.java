package org.fuelteam.watt.redis.redission;

import java.util.HashMap;
import java.util.Map;

import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonSpringProperties {

    private RedissonCacheManagerProperties cacheManager = new RedissonCacheManagerProperties();

    private RedissonTransactionManagerProperties transaction = new RedissonTransactionManagerProperties();

    public static class RedissonCacheManagerProperties {
        // 是否开启 RedissonSpringCacheManager, 默认true
        private boolean enabled = true;

        // 是否缓存null值, 默认true
        private boolean allowNullValues = true;

        private CodecType codec;

        private Map<String, CacheConfig> configs = new HashMap<>();

        // 是否开启动态缓存, 默认false
        private boolean dynamic = false;

        // 缓存配置路径
        private String configLocation;

        // 是否回滚到 NoOpCacheManager, 默认true
        private boolean fallbackToNoOpCache = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isAllowNullValues() {
            return allowNullValues;
        }

        public void setAllowNullValues(boolean allowNullValues) {
            this.allowNullValues = allowNullValues;
        }

        public CodecType getCodec() {
            return codec;
        }

        public void setCodec(CodecType codec) {
            this.codec = codec;
        }

        public Map<String, CacheConfig> getConfigs() {
            return configs;
        }

        public void setConfigs(Map<String, CacheConfig> configs) {
            this.configs = configs;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public void setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
        }

        public String getConfigLocation() {
            return configLocation;
        }

        public void setConfigLocation(String configLocation) {
            this.configLocation = configLocation;
        }

        public boolean isFallbackToNoOpCache() {
            return fallbackToNoOpCache;
        }

        public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
            this.fallbackToNoOpCache = fallbackToNoOpCache;
        }
    }

    public static class RedissonTransactionManagerProperties {

        // 是否开启RedissonTransactionManager, 默认false
        private boolean enabled = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public RedissonCacheManagerProperties getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(RedissonCacheManagerProperties cacheManager) {
        this.cacheManager = cacheManager;
    }

    public RedissonTransactionManagerProperties getTransaction() {
        return transaction;
    }

    public void setTransaction(RedissonTransactionManagerProperties transaction) {
        this.transaction = transaction;
    }
}