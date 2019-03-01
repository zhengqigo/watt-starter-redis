package org.fuelteam.watt.redis.redission;

public interface Customizer<T> {
    
    public void customize(T bean);
}