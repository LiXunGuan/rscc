package com.ruishengtech.framework.core;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Test {

	@SuppressWarnings("rawtypes")
    private static String Ipaddr() throws SocketException {

        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
       InetAddress ip = null;
        while ( allNetInterfaces.hasMoreElements()) {
             NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration addresses = netInterface.getInetAddresses();
              while ( addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if ( ip != null && ip instanceof Inet4Address && !ip.isLoopbackAddress()) {
                    	
                          getLocalMac(ip);
                          System.out.println("本机的IP = " + ip.getHostAddress());
                   }
             }
       }

        return null;
 }
    
    private static void getLocalMac(InetAddress ia) throws SocketException {

        // 获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        System.out.println("mac数组长度：" + mac. length);
       StringBuffer sb = new StringBuffer( "");
        for ( int i = 0; i < mac. length; i++) {
              if ( i != 0) {
                    sb.append( "-");
             }
              int temp = mac[ i] & 0xff;
             String str = Integer. toHexString(temp);
              if ( str.length() == 1) {
                    sb.append( "0" + str);

             } else {
                    sb.append( str);
             }
       }
        System.out.println("本机MAC地址:" + sb.toString().toUpperCase());
 }
    
    public static void main(String[] args) throws SocketException {
		
    	System.out.println(System.getProperty("user.dir"));
    }




	
}
