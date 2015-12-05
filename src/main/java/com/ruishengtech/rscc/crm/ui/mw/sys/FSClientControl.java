package com.ruishengtech.rscc.crm.ui.mw.sys;

import java.util.List;

import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;

import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.util.HttpRequest;

/**
 * Created by yaoliceng on 2014/10/25.
 */

public class FSClientControl {

    public static Object lock =new Object();

    private final String ip;

    private MWFsHost mwServer;

    private Client client;

    private String hostname;


    public FSClientControl(MWFsHost server) {
        this.ip = server.getEslip();
        this.mwServer = server;
        this.hostname = server.getName();

        try {
            initClient();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public FSClientControl(MWFsHost server, Boolean add) throws InboundConnectionFailure {
        this.ip = server.getEslip();
        this.mwServer = server;
        this.hostname = server.getName();
        initClient();
    }

    public void initClient() throws InboundConnectionFailure {

        connect();
    }

    private void connect() throws InboundConnectionFailure {
        client = new Client();
        client.connect(mwServer.getEslip(),
                Integer.parseInt(mwServer.getEslport()),
                mwServer.getEslpassword(), 2);


         
         

            
        
    }


    public void sendAgentCommond(String content, String params) {
        try {
            HttpRequest.sendPost1(content, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAsynFsCommond(String content, String params) {
        check();
        client.sendAsyncApiCommand(content, params);
    }


    public String sendSynFsCommond(String content, String params) {
        check();
        StringBuilder ret =new StringBuilder();
        List<String> list = client.sendSyncApiCommand(content, params).getBodyLines();
        for (String s : list) {
            System.out.println("     " +s+"                  ");
            if(!s.startsWith("+OK")){
                ret.append(s);
            }
        }
        return ret.toString();
    }



    private void check() {
        if (client == null || !client.canSend()) {
            try {
                connect();
            } catch (InboundConnectionFailure inboundConnectionFailure) {
                inboundConnectionFailure.printStackTrace();
            }
        }
    }

    public void kill() {
        client.close();
    }

    public String getHostname() {
        return hostname;
    }


    public MWFsHost getMwServer() {
        return mwServer;
    }

    public Client getClient() {
        check();
        return client;
    }

    public String getIp() {
        return ip;
    }
}

