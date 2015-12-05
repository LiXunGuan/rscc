package com.ruishengtech.framework.core.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ruishengtech.framework.core.websocket.handler.WebSocketBrokerHandler;
import com.ruishengtech.framework.core.websocket.interceptor.HandshakeInterceptor;

/**
 * @author Wangyao
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Broker {

    String URL();

    Class<? extends WebSocketBrokerHandler> handler() default WebSocketBrokerHandler.class;
    
    Class<? extends HandshakeInterceptor> interceptor() default HandshakeInterceptor.class;

}
