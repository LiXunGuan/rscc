package com.ruishengtech.rscc.crm.ui.mw.sys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ruishengtech.rscc.crm.ui.mw.send.MwMangaer;
import org.apache.commons.lang3.StringUtils;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruishengtech.rscc.crm.ui.mw.command.Commands;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.service.MWFsHostService;

/**
 * Created by yaoliceng on 2014/10/25.
 */
@Component
public class FsServerManager {

    @Autowired
    private MWFsHostService mwServerService;

    private List<FSClientControl> mwServerList = new ArrayList<FSClientControl>();

    public void init() {

        List<MWFsHost> mwServers = mwServerService.getAll(MWFsHost.class);
        for (MWFsHost server : mwServers) {
                mwServerList.add(new FSClientControl(server));
             
        }
    }

    public void sendAgentCommond(String url, String params, String hostName) {

        MwMangaer.sendAgentHttpCommond(url,params,hostName);


//        for (FSClientControl fsClientControl : mwServerList) {
//            if (StringUtils.isBlank(hostName) || fsClientControl.getHostname().equals(hostName)) {
//                fsClientControl.sendAgentCommond(Commands.build(url,fsClientControl.getMwServer().getAgip(),fsClientControl.getMwServer().getAgport()), params);
//            }
//        }
    }

    public void sendAsynFsCommond(String content, String hostName) {


        MwMangaer.sendAsynFsCommond(content, hostName);


//        for (FSClientControl fsClientControl : mwServerList) {
//
//            if (StringUtils.isBlank(hostName) || fsClientControl.getHostname().equals(hostName)) {
//                String command = content.substring(0, content.indexOf(" "));
//                String params = content.substring(content.indexOf(" ") + 1);
//                fsClientControl.sendAsynFsCommond(command, params);
//            }
//        }
    }

    public String sendSynFsCommond(String content, String hostName) {

        StringBuilder ret = new StringBuilder();

        for (FSClientControl fsClientControl : mwServerList) {

            if (StringUtils.isBlank(hostName) || fsClientControl.getHostname().equals(hostName)) {
                String command = content.substring(0, content.indexOf(" "));
                String params = content.substring(content.indexOf(" ") + 1);
                 ret.append(fsClientControl.sendSynFsCommond(command, params));
            }
        }
        return ret.toString();
    }

    public void killAll() {
        for (FSClientControl fsClientControl : mwServerList) {
            fsClientControl.kill();
        }
    }


    public void removeServer(String ip) {

        FSClientControl  removedFsClient = null;
        for (FSClientControl fsClientControl : mwServerList) {
            if(fsClientControl.getIp().equals(ip)){
                fsClientControl.kill();
                removedFsClient =fsClientControl;
            }
        }
        mwServerList.remove(removedFsClient);
    }

    public Client getClientByIp(String realm) {

        for (FSClientControl fsClientControl : mwServerList) {
            if(fsClientControl.getMwServer().getEslip().equals(realm)){
                return fsClientControl.getClient();
            }
        }

        return null;
    }

    public List<FSClientControl> getMwServerList() {
        return mwServerList;
    }

    public static AtomicInteger number=new AtomicInteger(0);

    public FSClientControl getClientByRandom() {

        synchronized (number) {

            if (getMwServerList().size() > 0) {

                if (number.intValue() >= getMwServerList().size()) {
                    number.set(0);
                }

                return getMwServerList().get(number.getAndIncrement());
            }
        }
        return null;
    }

    public Client getClientByID(Long fshost_id) {

        for (FSClientControl fsClientControl : mwServerList) {
            if(fsClientControl.getMwServer().getId().equals(fshost_id)){
                return fsClientControl.getClient();
            }
        }
        return null;
    }


    public void addServer(MWFsHost mwServer) throws InboundConnectionFailure {

        FSClientControl fsClientControl = new FSClientControl(mwServer,true);
        mwServerList.add(fsClientControl);
    }

    public void editServer(MWFsHost mwServer) throws InboundConnectionFailure {

        synchronized(FSClientControl.lock) {


            if(mwServerList.size()>0) {


                Iterator<FSClientControl> a = mwServerList.iterator();
                while (a.hasNext()) {

                    FSClientControl fsClientControl = a.next();
                    if (fsClientControl.getMwServer().getId().equals(mwServer.getId())) {
                        addServer(mwServer);
                        fsClientControl.kill();
                        mwServerList.remove(fsClientControl);
                    }
                }
            }else{
                addServer(mwServer);
            }
        }
    }
}
