package org.fuelteam.watt.redis.redission;

import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.noMatch;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RedissonConditions {

    private static final String PROPERTY_CLIENT_MODE = "spring.redisson.client-mode";

    private final static String CLIENT = "Client";

    // RedissonAutoConfiguration生效条件 client-mode != none
    static class RedissonCondition extends SpringBootCondition {

        private final static String CONDITION = "RedissonCondition";

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment environment = context.getEnvironment();
            BindResult<String> bindResult = Binder.get(environment).bind(PROPERTY_CLIENT_MODE, String.class);
            ConditionMessage.Builder otherMessage = ConditionMessage.forCondition(CONDITION);
            if (!bindResult.isBound()) {
                String reason = String.format("%s=%s", PROPERTY_CLIENT_MODE, ClientMode.DEFAULT);
                return match(otherMessage.because(reason));
            }
            try {
                BindResult<ClientMode> specified = Binder.get(environment).bind(PROPERTY_CLIENT_MODE, ClientMode.class);
                String reason = String.format("%s=%s", PROPERTY_CLIENT_MODE, bindResult.get());
                ConditionMessage message = ConditionMessage.forCondition(CONDITION, reason).found(CLIENT).items(specified.get());
                return specified.get() != ClientMode.NONE ? match(message) : noMatch(message);
            } catch (BindException be) {
                return noMatch(otherMessage.found("unknown").items(bindResult.get()));
            }
        }
    }

    // RedissonClient生效条件client-mode == DEFATLT || client-mode == both
    static class RedissonClientCondition extends SpringBootCondition {

        private final static String CONDITION = "RedissonClientCondition";

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment environment = context.getEnvironment();
            BindResult<String> bindResult = Binder.get(environment).bind(PROPERTY_CLIENT_MODE, String.class);
            if (!bindResult.isBound()) {
                String reason = String.format("%s=%s", PROPERTY_CLIENT_MODE, ClientMode.DEFAULT);
                return match(ConditionMessage.forCondition(CONDITION).because(reason));
            }
            BindResult<ClientMode> specified = Binder.get(environment).bind(PROPERTY_CLIENT_MODE, ClientMode.class);
            String reason = String.format("%s=%s", PROPERTY_CLIENT_MODE, bindResult.get());
            ConditionMessage message = ConditionMessage.forCondition(CONDITION, reason).found(CLIENT).items(specified.get());
            return specified.get() != ClientMode.REACTIVE ? match(message) : noMatch(message);
        }
    }

    // RedissonReactiveClient生效条件 client-mode == reactive || client-mode == both
    static class RedissonReactiveClientCondition extends SpringBootCondition {

        private final static String CONDITION = "RedissonReactiveClientCondition";

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment environment = context.getEnvironment();
            BindResult<String> bindResult = Binder.get(environment).bind(PROPERTY_CLIENT_MODE, String.class);
            if (!bindResult.isBound()) {
                String reason = String.format("%s=%s", PROPERTY_CLIENT_MODE, ClientMode.DEFAULT);
                return noMatch(ConditionMessage.forCondition(CONDITION).because(reason));
            }
            BindResult<ClientMode> specified = Binder.get(environment).bind(PROPERTY_CLIENT_MODE, ClientMode.class);
            String reason = String.format("%s=%s", PROPERTY_CLIENT_MODE, bindResult.get());
            ConditionMessage message = ConditionMessage.forCondition(CONDITION, reason).found(CLIENT).items(specified.get());
            return specified.get() != ClientMode.DEFAULT ? match(message) : noMatch(message);
        }
    }
}