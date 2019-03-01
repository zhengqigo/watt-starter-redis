package org.fuelteam.watt.redis.redission;

import org.redisson.connection.balancer.LoadBalancer;
import org.redisson.connection.balancer.RandomLoadBalancer;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;

public enum LoadBalancerType {

    // 轮询
    ROUND_ROBIN {
        @Override
        public LoadBalancer getInstance() {
            return new RoundRobinLoadBalancer();
        }
    },

    // 随机
    RANDOM {
        @Override
        public LoadBalancer getInstance() {
            return new RandomLoadBalancer();
        }
    };

    public abstract LoadBalancer getInstance();
}