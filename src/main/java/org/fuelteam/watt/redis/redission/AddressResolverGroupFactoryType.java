package org.fuelteam.watt.redis.redission;

import org.redisson.connection.AddressResolverGroupFactory;
import org.redisson.connection.DnsAddressResolverGroupFactory;
import org.redisson.connection.RoundRobinDnsAddressResolverGroupFactory;

public enum AddressResolverGroupFactoryType {

    // 默认
    DEFAULT {
        @Override
        public AddressResolverGroupFactory getInstance() {
            return new DnsAddressResolverGroupFactory();
        }
    },

    // 轮询
    ROUND_ROBIN {
        @Override
        public AddressResolverGroupFactory getInstance() {
            return new RoundRobinDnsAddressResolverGroupFactory();
        }
    };

    public abstract AddressResolverGroupFactory getInstance();
}