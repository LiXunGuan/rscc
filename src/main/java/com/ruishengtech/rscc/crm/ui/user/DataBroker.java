package com.ruishengtech.rscc.crm.ui.user;

import com.ruishengtech.framework.core.websocket.Broker;

//@Broker(URL="/user", handler=UserBrokerHandler.class, interceptor=UserBrokerInterceptor.class)
@Broker(URL="/data", handler=UserBrokerHandler.class, interceptor=UserBrokerInterceptor.class)
public class DataBroker {

}
