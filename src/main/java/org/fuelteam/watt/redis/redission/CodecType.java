package org.fuelteam.watt.redis.redission;

import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.KryoCodec;
import org.redisson.codec.LZ4Codec;
import org.redisson.codec.SerializationCodec;
import org.redisson.codec.SnappyCodec;

public enum CodecType {

    // Jackson JSON编码 默认
    JACKSON {
        @Override
        public Codec getInstance() {
            return new JsonJacksonCodec();
        }
    },

    // Kryo 二进制对象序列化编码
    KRYO {
        @Override
        public Codec getInstance() {
            return new KryoCodec();
        }
    },

    // JDK 序列化编码
    JDK {
        @Override
        public Codec getInstance() {
            return new SerializationCodec();
        }
    },

    // Fst 二进制对象序列化编码
    FST {
        @Override
        public Codec getInstance() {
            return new FstCodec();
        }
    },

    // 压缩型序列化对象编码
    LZ4 {
        @Override
        public Codec getInstance() {
            return new LZ4Codec();
        }
    },

    // 另一个压缩型序列化对象编码
    SNAPPY {
        @Override
        public Codec getInstance() {
            return new SnappyCodec();
        }
    },

    // 纯字符串编码
    STRING {
        @Override
        public Codec getInstance() {
            return new StringCodec();
        }
    };

    public abstract Codec getInstance();
}