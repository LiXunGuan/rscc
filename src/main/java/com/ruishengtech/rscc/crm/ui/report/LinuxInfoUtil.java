package com.ruishengtech.rscc.crm.ui.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.ruishengtech.framework.core.SpringPropertiesUtil;

public class LinuxInfoUtil {


	public static void getNetName(){
		BufferedReader bufferedReader = null;
		Process process = null;
			try {
				process = Runtime.getRuntime().exec("ifconfig");
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					if(line.toLowerCase().indexOf("eth0")!=-1){
						SysCacheManager.getInstance().setNetname("eth0");
						break;
					}
					if(line.toLowerCase().indexOf("em1")!=-1){
						SysCacheManager.getInstance().setNetname("em1");
						break;
					}
				}
			} catch (IOException e) {
				SysCacheManager.getInstance().setNetname("eht0");
//				e.printStackTrace();
			}finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bufferedReader = null;
				process = null;
			}
			
	}
	
	public static void getLinuxInfo() {
		BufferedReader bufferedReader = null;
		Map<String, String> linuxInfo = SysCacheManager.getInstance().getLinuxInfoMap();
		try {
			getNetInfo(linuxInfo);
//			process = Runtime.getRuntime().exec("ifconfig "+SysCacheManager.getInstance().getNetname());
//			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			String line = null;
//			int macIndex = -1;
//			int ipIndex = -1;
//			int maskIndex = -1;
//			while ((line = bufferedReader.readLine()) != null) {
//				String lowLine = line.toLowerCase();
//				macIndex = lowLine.indexOf("hwaddr");
//				if (macIndex != -1) {
//					linuxInfo.put(Infos.LINUX_MACADDR,line.substring(macIndex + "hwaddr".length()).trim());
//					macIndex=-1;
//					continue;
//				}
//				ipIndex = lowLine.indexOf("inet addr");
//				if (ipIndex != -1) {
//					int windex = lowLine.indexOf("bcast");
//					linuxInfo.put(Infos.LINUX_IPADDR,line.substring(ipIndex + "inet addr".length()+1,windex).trim());
//				}
//				maskIndex = lowLine.indexOf("mask");
//				if (maskIndex != -1) {
//					linuxInfo.put(Infos.LINUX_NETMASK,line.substring(maskIndex + "mask".length()+1).trim());
//					break;
//				}
//			}
//			process = Runtime.getRuntime().exec("route -n");
//			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			int count = 0;
//			while ((line = bufferedReader.readLine()) != null) {
//				count++;
//				if(count==5){
//					int a = line.indexOf("0.0.0.0");
//					int b = line.lastIndexOf("0.0.0.0");
//					linuxInfo.put(Infos.LINUX_GATEWAY, line.substring(a+"0.0.0.0".length()+1,b).trim());
//				}
//			}
		} catch (Exception e) {
			linuxInfo.put(Infos.LINUX_MACADDR,"aa-bb-cc-dd-ee-ff");
			linuxInfo.put(Infos.LINUX_IPADDR,"192.168.1.140");
			linuxInfo.put(Infos.LINUX_NETMASK,"255.255.255.0");
			linuxInfo.put(Infos.LINUX_GATEWAY,"192.168.1.1");
//			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
		}
	}

	public static void updNetCfgFile(String ipAddr,String gateWay,String netMask) {
		String netPath = SpringPropertiesUtil.getProperty("linux.net")+"ifcfg-"+SysCacheManager.getInstance().getNetname();
		File file = new File(netPath);
		StringBuffer sbu = new StringBuffer();
		sbu.append("DEVICE="+SysCacheManager.getInstance().getNetname()+"\n");
		sbu.append("BOOTPROTO=static\n");
		sbu.append("ONBOOT=yes\n");
		sbu.append("IPADDR="+ipAddr+"\n");
		sbu.append("GATEWAY="+gateWay+"\n");
		sbu.append("NETMASK="+netMask+"\n");
		sbu.append("DNS1=8.8.8.8");
//		sbu.append("IPV4_FAILURE_FATAL=yes\n");
//		sbu.append("IPV6INIT=no\n");
		
		try {
			FileUtils.write(file, sbu);
			Runtime.getRuntime().exec("service network restart");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param oldIp 未修改前的ip
	 * @param sipPort
	 */
	public static void updSipPortFile(String oldIp,String sipPort){
		String exPath = SpringPropertiesUtil.getProperty("fs.external");
		String oldIpStr = oldIp.replaceAll(".", "");
		File file = new File(exPath+oldIpStr+".xml");
        if(file.exists()){
            file.delete();
        }
		String ipAddr = SysCacheManager.getInstance().getLinuxInfoMap().get(Infos.LINUX_IPADDR);
		String newIpStr = ipAddr.replaceAll(".", "");
		StringBuffer sbuff = new StringBuffer();
		sbuff.append("<profile name="+newIpStr+">");
		sbuff.append("<gateways>");
		sbuff.append("<X-PRE-PROCESS cmd=\"include\" data="+newIpStr+"/*.xml\"/>");
		sbuff.append("</gateways>");
		sbuff.append("<aliases>");
		sbuff.append("</aliases>");
		sbuff.append("<domains>");
		sbuff.append("<domain name=\"all\" alias=\"false\" parse=\"true\"/>");
		sbuff.append("</domains>");
		sbuff.append("<settings>");
		sbuff.append("<param name=\"debug\" value=\"0\"/>");
		sbuff.append("<param name=\"sip-trace\" value=\"no\"/>");
		sbuff.append("<param name=\"sip-capture\" value=\"no\"/>");
		sbuff.append("<param name=\"rfc2833-pt\" value=\"101\"/>");
		sbuff.append("<param name=\"sip-port\" value=\""+sipPort+"\"/>");
		sbuff.append("<param name=\"dialplan\" value=\"XML\"/>");
		sbuff.append("<param name=\"context\" value=\"public\"/>");
		sbuff.append("<param name=\"dtmf-duration\" value=\"2000\"/>");
		sbuff.append("<param name=\"inbound-codec-prefs\" value=\"$${global_codec_prefs}\"/>");
		sbuff.append("<param name=\"outbound-codec-prefs\" value=\"$${outbound_codec_prefs}\"/>");
		sbuff.append("<param name=\"hold-music\" value=\"$${hold_music}\"/>");
		sbuff.append("<param name=\"rtp-timer-name\" value=\"soft\"/>");
		sbuff.append("<param name=\"local-network-acl\" value=\"localnet.auto\"/>");
		sbuff.append("<param name=\"manage-presence\" value=\"false\"/>");
		sbuff.append("<param name=\"rtp-autofix-timing\" value=\"false\"/>");
		sbuff.append("<param name=\"inbound-codec-negotiation\" value=\"generous\"/>");
		sbuff.append("<param name=\"nonce-ttl\" value=\"60\"/>");
		sbuff.append("<param name=\"auth-calls\" value=\"false\"/>");
		sbuff.append("<param name=\"inbound-late-negotiation\" value=\"true\"/>");
		sbuff.append("<param name=\"inbound-zrtp-passthru\" value=\"true\"/>");
		sbuff.append("<param name=\"rtp-ip\" value=\""+ipAddr+"\"/>");
		sbuff.append("<param name=\"sip-ip\" value=\""+ipAddr+"\"/>");
		sbuff.append("<param name=\"ext-rtp-ip\" value=\"auto-nat\"/>");
		sbuff.append("<param name=\"ext-sip-ip\" value=\"auto-nat\"/>");
		sbuff.append("<param name=\"rtp-timeout-sec\" value=\"300\"/>");
		sbuff.append("<param name=\"rtp-hold-timeout-sec\" value=\"1800\"/>");
		sbuff.append("<param name=\"tls\" value=\"$${external_ssl_enable}\"/>");
		sbuff.append("<param name=\"tls-only\" value=\"false\"/>");
		sbuff.append("<param name=\"tls-bind-params\" value=\"transport=tls\"/>");
		sbuff.append("<param name=\"tls-sip-port\" value=\"$${external_tls_port}\"/>");
		sbuff.append("<param name=\"tls-passphrase\" value=\"\"/>");
		sbuff.append("<param name=\"tls-verify-date\" value=\"true\"/>");
		sbuff.append("<param name=\"tls-verify-policy\" value=\"none\"/>");
		sbuff.append("<param name=\"tls-verify-depth\" value=\"2\"/>");
		sbuff.append("<param name=\"tls-verify-in-subjects\" value=\"\"/>");
		sbuff.append("<param name=\"tls-version\" value=\"$${sip_tls_version}\"/>");
		sbuff.append("<param name=\"liberal-dtmf\" value=\"true\"/>");
		sbuff.append("</settings>");
		sbuff.append("</profile>");
		File newFile = new File(exPath+newIpStr+".xml");
		try {
			FileUtils.write(newFile, sbuff);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getNetInfo(Map<String, String> map) throws UnknownHostException, SocketException {

        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            List<InterfaceAddress> interfaceAddress = netInterface.getInterfaceAddresses();
            for (InterfaceAddress iA : interfaceAddress) {
            	ip = iA.getAddress();
    			if (ip != null && ip instanceof Inet4Address && !ip.isLoopbackAddress() && netInterface.isUp()) {
    				SysCacheManager.getInstance().setNetname(netInterface.getDisplayName());
    				map.put(Infos.LINUX_MACADDR, getMac(netInterface.getHardwareAddress()));
    				map.put(Infos.LINUX_IPADDR, ip.getHostAddress());
    				map.put(Infos.LINUX_NETMASK, getMask(iA.getNetworkPrefixLength()));
    				map.put(Infos.LINUX_GATEWAY, getGate(iA.getBroadcast().getHostAddress()));
    				return;
    			}
			}
        }
    }
	
	private static String getMask(int maskLength){
        StringBuffer maskStr = new StringBuffer();
        int mask = 0xFFFFFFFF << 32 - maskLength ;
        for(int i = 3 ;i >= 0;i--){
            maskStr.append( (  mask  >> (8*i) ) & 0xFF);
            if(i>0){
                maskStr.append(".");
            }
        }
        return maskStr.toString();
    }
	
	private static String getMac(byte[] mac){
        StringBuffer macStr = new StringBuffer();
        for (byte b : mac) {
        	macStr.append(Integer.toHexString(b & 0xFF).toUpperCase() + "-");
        }
        return macStr.substring(0, macStr.length()-1);
    }
	
	private static String getGate(String broadCast){
//		String addr = broadCast.substring(1);
		int last = broadCast.lastIndexOf(".");
		return broadCast.substring(0, last + 1) + "1";
    }
	
}