package com.ruishengtech.framework.core.licence;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import com.ruishengtech.framework.core.licence.util.LicenseDataUtil;

public class ClientHandler {

	private String host ;
	
	private int port ;
	
	private EventLoopGroup group ;
	
	private Bootstrap b ;
	
	private Channel ch ;
	
	private int isConnSuccess = 0;
	
	public ClientHandler(String host, int port) throws SSLException {
		this.host = host ;
		this.port = port ;
		
		// Configure SSL.
		SslContext sslCtx = SslContextBuilder.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(new HeartbeatInitializer(sslCtx));
	}

	/**
	 * 链接服务器
	 * @throws InterruptedException
	 */
	public void connServer() throws InterruptedException{
		while(true){
			try {
				if(ch != null && ch.isOpen()){
					ch.close();
				}
				ch = b.connect(host, port).sync().channel();
				// 存放channel
				ChannelManager.getInstance().setChannel(ch);
				System.out.println("已经成功存储channel" + ChannelManager.getInstance().getChannel().remoteAddress());
				isConnSuccess = 0;
			} catch (Exception e) {
				isConnSuccess++;
			} finally{
				switch (isConnSuccess) {
				case 0: {
					System.out.println("客户端成功链接服务器....");
					return;
				}
				case 1:
					Thread.sleep(5000);  break;// 等待5秒重新链接
				case 2:
					Thread.sleep(30000); break;// 等待30秒重新链接
				case 3:
					Thread.sleep(60000); break;// 等待60秒重新链接
				default:
					Thread.sleep(300000);break;// 每间隔5分钟重新链接
				}
				System.out.println("第"+isConnSuccess+"次正在尝试链接服务器....");
			}
		}
	}
	
	public class HeartbeatInitializer extends ChannelInitializer<SocketChannel> {
		
		private SslContext sslCtx;

	    public HeartbeatInitializer(SslContext sslCtx) {
	        this.sslCtx = sslCtx;
	    }
		
		@Override
	    protected void initChannel(SocketChannel ch) throws Exception {
			
			ch.pipeline().addLast(sslCtx.newHandler(ch.alloc(), host, port));
			
			ch.pipeline().addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
     	    ch.pipeline().addLast("encoder", new LengthFieldPrepender(4, false));
			
     	    // 客户端的业务逻辑handler
			ch.pipeline().addLast(new ClientDataHandler());
	    }
	}

	public class ClientDataHandler extends ChannelInboundHandlerAdapter {

		@Override
	    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

			// 发送第一次报道消息
	    	ctx.writeAndFlush(LicenseDataUtil.clientFirstMessage());
	    	
	    	new Thread(){
	    		public void run(){
	    			try{
	    				while(true){
	    					// 间隔5秒发送心跳消息
	    					ctx.writeAndFlush(LicenseDataUtil.hearBeatMessage());
	    					sleep(5000);
	    				}
	    			}catch(Exception e){
	    				e.printStackTrace();
	    			}
	    		}
	    	}.start();
	    }
		
		@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			String content = LicenseDataUtil.convertMsg(msg);
			System.out.println("客户端:服务器端返回的数据:" + content);
			LicenseDataUtil.handleBackData(content);
	    }
		

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
	        System.out.println("客户端:客户端readComplete 响应完成");
//	        if(!ctx.channel().isActive()){
//	        	connServer();
//	        }
		}
		
		@Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    	cause.printStackTrace();
	    	System.out.println(cause.getMessage());
	    	ctx.close();
	    	connServer();
	    }
	}
}
