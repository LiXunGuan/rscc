package com.ruishengtech.framework.core.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author Wangyao
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Messager {

    String URL();

    Class<WebSocketHandler> handler();
    
    Class<HandshakeInterceptor> interceptor();

}