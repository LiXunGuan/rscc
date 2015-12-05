package com.ruishengtech.rscc.crm.ui.mw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP客户端
 * 
 * put规则：当只有本地路径时，默认放到服务器的serverPath绝对路径
 *          当有服务器路径时，放到服务器该绝对路径下
 *          服务器的当前路径锁定为根目录
 *          本地路径是分为绝对路径和相对路径的，当输入绝对路径时就使用绝对路径，当输入相对路径时，与localPath合并
 *          
 */
public class FTPTool
{
    private String         host;
    private int            port;
    private String         username;
    private String         password;

    private boolean        binaryTransfer = true;
    private boolean        passiveMode    = true;
    private int            clientTimeout  = 60;
    private String         encoding       = "UTF-8";
    private String         serverPath     = "/";		//默认根目录
    private String         localPath      = "C:/";		//默认本地目录
    
    private FTPClient      ftpClient      = new FTPClient();
    
    private Thread         daemonThread   = null ;		//守护线程，防止自动断线
    /**
     * 构造方法，创建FTPClient实例
     * 
     * @param host 连接主机名
     * @param port 连接端口
     * @param username 连接用户名
     * @param password 连接密码
	 */
    
    public FTPTool(String host, int port, String username, String password){
    	this(host,port,username,password,true,true,60,"UTF-8");
    }
    
	/**
     * 构造方法，创建FTPClient实例
     * 
     * @param host 连接主机名
     * @param port 连接端口
     * @param username 连接用户名
     * @param password 连接密码
     * @param binaryTransfer 是否以二进制传输
	 */
    public FTPTool(String host, int port, String username, String password, boolean binaryTransfer){
    	this(host,port,username,password,binaryTransfer,true,60,"UTF-8");
    }
    
	/**
     * 构造方法，创建FTPClient实例
     * 
     * @param host 连接主机名
     * @param port 连接端口
     * @param username 连接用户名
     * @param password 连接密码
     * @param binaryTransfer 是否以二进制传输
     * @param passiveMode 是否以被动方式连接
	 */
    public FTPTool(String host, int port, String username, String password, boolean binaryTransfer, boolean passiveMode){
    	this(host,port,username,password,binaryTransfer,passiveMode,60,"UTF-8");
    }
    
	/**
     * 构造方法，创建FTPClient实例
     * 
     * @param host 连接主机名
     * @param port 连接端口
     * @param username 连接用户名
     * @param password 连接密码
     * @param binaryTransfer 是否以二进制传输
     * @param passiveMode 是否以被动方式连接
     * @param clientTimeout 客户端连接超时
	 */
    public FTPTool(String host, int port, String username, String password, boolean binaryTransfer, boolean passiveMode, int clientTimeout){
    	this(host,port,username,password,binaryTransfer,passiveMode,clientTimeout,"UTF-8");
    }
    
	/**
     * 构造方法，创建FTPClient实例
     * 
     * @param host 连接主机名
     * @param port 连接端口
     * @param username 连接用户名
     * @param password 连接密码
     * @param binaryTransfer 是否以二进制传输
     * @param passiveMode 是否以被动方式连接
     * @param clientTimeout 客户端连接超时
     * @param encoding 设置ftp命令编码
	 */
    public FTPTool(String host, int port, String username, String password, boolean binaryTransfer, boolean passiveMode, int clientTimeout, String encoding){
    	this.setHost(host);
    	this.setPort(port);
    	this.setUsername(username);
    	this.setPassword(password);
    	this.setBinaryTransfer(binaryTransfer);
    	this.setPassiveMode(passiveMode);
    	this.setClientTimeout(clientTimeout);
    	this.setEncoding(encoding);
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBinaryTransfer() {
        return binaryTransfer;
    }

    public void setBinaryTransfer(boolean binaryTransfer) {
        this.binaryTransfer = binaryTransfer;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }
    
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		if(localPath.endsWith("/") || localPath.endsWith("\\"))
			this.localPath = localPath;
		else
			this.localPath = localPath + "/";
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		if(serverPath.endsWith("/") || localPath.endsWith("\\"))
			this.serverPath = serverPath;
		else
			this.serverPath = serverPath + "/";
	}
	
	public void setServerAndLocal(String localPath,String serverPath) {
		this.setLocalPath(localPath);
		this.setServerPath(serverPath);
	}

    /**
     * 设置文件传输类型
     * 
     * @throws Exception
     * @throws java.io.IOException
     */
    private void setFileType() throws Exception {
        try {
            if (binaryTransfer) {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            } else {
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
            }
		} catch (Exception e) {
			throw new Exception("Set filetype error", e);
		}

    }
    
    /**
     * 连接到ftp服务器
     * 
     * @param ftpClient
     * @return 连接成功返回true，否则返回false
     * @throws Exception
     */
    public void connect() throws Exception {
        try {
            //ftpClient = new FTPClient();
            ftpClient.setControlEncoding(encoding); //设置命令字符集，要在连接之前
            
            ftpClient.connect(host, port);
            int reply = ftpClient.getReplyCode(); // 连接后检测返回码来校验连接是否成功

            if (FTPReply.isPositiveCompletion(reply)) {
                if (ftpClient.login(username, password)) { //登陆到ftp服务器
						setFileType(); //设置文件传输类型           
                    if (passiveMode) {
                        ftpClient.enterLocalPassiveMode(); //设置为passive模式
                    }
                    try {
                        ftpClient.setSoTimeout(clientTimeout); //设置客户端超时时长，0为无限制，准确的说是两次连接间隔时间，超过这个时间第二次再连接视为超时
                    } catch (SocketException e) {
                        throw new Exception("Set timeout error.", e);
                    }
                }
            } else {
                this.disconnect();
                throw new Exception("FTP server refused connection.");	//服务器拒绝连接
            }
        } catch (IOException e) {	//连接出现异常
            if (ftpClient.isConnected()) {
            	this.disconnect(); //断开连接
            }
            throw new Exception("Could not connect to server.", e); //连接失败
        }
    }

    /**
     * 断开ftp连接
     * 
     * @throws Exception
     */
    public void disconnect() throws Exception {
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new Exception("Could not disconnect from server.", e);
        }
    }
    /**
     * 上传一个本地文件到远程指定文件
     * 
     * @param localFile 本地文件名(包括完整路径)
     * @param serverFile 服务器端文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception 断线重连如果异常再返回，有可能是网络中断或者服务器关闭
     */
    public boolean put(String localFile, String serverFile) throws Exception {
		localFile = sourceFullPath(localFile, localPath);
        serverFile = targetFullPath(serverFile, localFile, serverPath);
        try(InputStream input = new FileInputStream(localFile)){ // 处理传输
            createServerFolder(serverFile);	//若服务器文件中路径不存在则创建
            ftpClient.storeFile(serverFile, input);
            input.close();
            return isFileExist(serverFile);
//        } catch (FTPConnectionClosedException e) {	//若断开则重新连接
//        	try {
//				this.connect();
//			} catch (Exception e1) {
//				return false;
//			}
//        	return put(localFile, serverFile) ;
        } catch (Exception e) {
        	try {
				this.connect();
			} catch (Exception e1) {
				return false;
			}
        }
        return false;
    }
    
    /**
     * 上传一个本地文件到远程已配置目录，serverPath
     * 
     * @param localFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean put(String localFile) throws Exception {
    	return this.put(localFile, serverPath);
    }

    /**
     * 下载一个远程文件到本地的指定文件
     * 
     * @param serverFile 服务器端文件名(包括完整路径)
     * @param localFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean get(String serverFile, String localFile) throws Exception {
        return get(serverFile, localFile, false);
    }

    public boolean get(String serverFile) throws Exception {
    	return get(serverFile, localPath, false);
    }
    /**
     * 下载一个远程文件到本地的指定文件
     * 
     * @param serverFile 服务器端文件名(包括完整路径)
     * @param localFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws Exception
     */
    public boolean get(String serverFile, String localFile, boolean delFile) throws Exception {
    	serverFile = sourceFullPath(serverFile, serverPath);
    	localFile = targetFullPath(localFile, serverFile, localPath);
    	createLocalFolder(localFile);	//若本地路径不存在则创建
        try(OutputStream output = new FileOutputStream(localFile)) {
            return get(serverFile, output, delFile);
        } catch (Exception e) {
            return false;
        } 
    }
    
    /**
     * 下载一个远程文件到指定的流
     * 处理完后记得关闭流
     * 
     * @param serverFile
     * @param output
     * @return
     * @throws Exception
     */
    public boolean get(String serverFile, OutputStream output) throws Exception {
        return get(serverFile, output, false);
    }
    
    /**
     * 下载一个远程文件到指定的流
     * 处理完后记得关闭流
     * 
     * @param serverFile
     * @param output
     * @param delFile
     * @return
     * @throws Exception
     */
    public boolean get(String serverFile, OutputStream output, boolean delFile) throws Exception {
        try {
            // 处理传输
            ftpClient.retrieveFile(serverFile, output);
            if (delFile) { // 删除远程文件
                ftpClient.deleteFile(serverFile);
            }
            return true;
        } catch (FTPConnectionClosedException e) {	//若断开则重新连接
        	try {
				this.connect();
			} catch (Exception e1) {
				throw new Exception("Server connect error", e1); //若连接失败，则抛出连接失败
			}
        	return get(serverFile, output, delFile) ;
        } catch (Exception e) {
        	return false;
        }
    }
    /**
     * 从ftp服务器上删除一个文件
     * 
     * @param delFile
     * @return
     * @throws Exception
     */
    public void delete(String delFile) throws Exception {
        try {
            ftpClient.deleteFile(delFile);
        } catch (IOException e) {
            throw new Exception("Couldn't delete file from server.", e);
        } 
    }
    
    /**
     * 批量删除
     * 
     * @param delFiles
     * @return
     * @throws Exception
     */
    public void delete(String[] delFiles) throws Exception {
        for (String s : delFiles) {
            this.delete(s);
        }
    }
    
    /**
     * 获取源文件的绝对路径
     * @param sourceFile
     * @param currentPath
     * @return
     */
    private String sourceFullPath(String sourceFile, String currentPath) {
    	sourceFile = sourceFile.replace('\\', '/'); //处理不同的路径分割符
    	if(!sourceFile.startsWith("/") && sourceFile.indexOf(":") < 0) //如果不是绝对路径，第一个linux下的绝对路径，第二个Windows下的绝对路径
    		sourceFile = currentPath + sourceFile;
    	return sourceFile;
    }
    
    /**
     * 获取目标文件的绝对路径
     * @param targetFile
     * @param sourceFile
     * @param currentPath
     * @return
     */
    private String targetFullPath(String targetFile, String sourceFile, String currentPath) {
    	targetFile = targetFile.replace('\\', '/'); //处理不同的路径分割符
    	if(!targetFile.startsWith("/") && targetFile.indexOf(":") < 0) 	//如果不是绝对路径，第一个linux下的绝对路径，第二个Windows下的绝对路径
    		targetFile = currentPath + targetFile;
    	if(targetFile.endsWith("/")) 		//是路径名
    		targetFile = targetFile + sourceFile.substring(sourceFile.lastIndexOf('/') + 1);
    	return targetFile;
    }
    
    /**
     * 在服务器上创建文件夹
     * 
     * @param folderName
     * @throws Exception
     */
    public void createServerFolder(String folderName) throws Exception {
    	folderName = folderName.substring(0,folderName.lastIndexOf("/"));
    	String[] folders = folderName.split("/");	//分割各个目录
		for(String folder:folders) 
			if(folder.length()>0)
			{
				if(!ftpClient.changeWorkingDirectory(folder)){
    				ftpClient.makeDirectory(folder);
    				ftpClient.changeWorkingDirectory(folder);
				}
			}
		ftpClient.changeWorkingDirectory("/"); //返回主目录
    }
    
    /**
     * 创建本地路径
     * 
     * @param folderName
     */
    public void createLocalFolder(String folderName) {
    	folderName = folderName.substring(0,folderName.lastIndexOf("/"));
		File folder = new File(folderName);
		if  (!folder.exists()  && !folder.isDirectory())
			folder.mkdirs();
    }
    
    public boolean isFileExist(String fileName) throws IOException {
    	FTPFile[] files = ftpClient.listFiles(fileName);
    	return files.length == 1;
    }
    /**
     * 守护线程，不自动断线
     */
    public void startDaemon() {
    	if(ftpClient != null) {
    		daemonThread = new Thread(new Runnable(){
    			public void run() {
	    			try{
	    				while(!Thread.interrupted()) {
	    					ftpClient.sendNoOp();
	    					Thread.sleep(600000);	//60秒发送一次noop防止掉线
	    				}
	    			}
	    			catch(Exception e) {
	    			}
    			}
    		});
    		daemonThread.start();
    	}
    }
    
    /**
     * 停止守护
     */
    public void stopDaemon() {
    	if(daemonThread!=null)
    		daemonThread.interrupt();
    }
}
