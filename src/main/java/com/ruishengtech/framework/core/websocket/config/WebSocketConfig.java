package com.ruishengtech.framework.core.websocket.config;

import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ruishengtech.framework.core.util.ClassPathScanHandler;
import com.ruishengtech.framework.core.websocket.Broker;
import com.ruishengtech.framework.core.websocket.Messager;
import com.ruishengtech.framework.core.websocket.WebSocketContext;
import com.ruishengtech.framework.core.websocket.handler.WebSocketBrokerHandler;
import com.ruishengtech.framework.core.websocket.interceptor.HandshakeInterceptor;
import com.ruishengtech.framework.core.websocket.service.BrokerService;

/**
 * @author Wangyao
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	
	@Autowired
	protected BrokerService brokerService;
	
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        
    	doScan();
    	for (Class<?> aClass : WebSocketContext.getInstance().getWebSocketClassHolder()) {

            if (aClass.isAnnotationPresent(Broker.class)) {
            	Broker b = aClass.getAnnotation(Broker.class);
            	WebSocketBrokerHandler handler = (WebSocketBrokerHandler)BeanUtils.instantiate(b.handler());
            	HandshakeInterceptor interceptor = (HandshakeInterceptor)BeanUtils.instantiate(b.interceptor());
            	handler.setBrokerURL(b.URL());
            	registry.addHandler(handler, b.URL())
            	.addInterceptors(interceptor).withSockJS();
            	brokerService.add(b.URL());
            }
            else if(aClass.isAnnotationPresent(Messager.class)) {
            	Messager m = aClass.getAnnotation(Messager.class);
            	registry.addHandler
            	(BeanUtils.instantiate(m.handler()),m.URL())
            	.addInterceptors(BeanUtils.instantiate(m.interceptor()));
            }
        }
    }
    
    private void doScan() {

        ClassPathScanHandler classPathScanHandler = new ClassPathScanHandler(true, true, null);
        Set<Class<?>> ret = classPathScanHandler.getPackageAllClasses("com.ruishengtech", true);
        for (Class<?> aClass : ret) {
            if (aClass.isAnnotationPresent(Broker.class)) {
            	WebSocketContext.getInstance().addWebSocket(aClass);
            }
        }
    }
}
