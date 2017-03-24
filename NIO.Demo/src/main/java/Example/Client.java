package Example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月23日
 * 类说明
 */
public class Client implements Runnable{
	
	private SocketChannel channel;
	private Selector selector;
	private InetSocketAddress inetSocketAddress;
	private IClientHandler handler;
	public Client(int port){
		try {
			inetSocketAddress=new InetSocketAddress(port);
			handler=new ClientHandlerImpl();
			channel=SocketChannel.open();
			channel.configureBlocking(false);
			this.selector=Selector.open();
			channel.connect(inetSocketAddress);
			channel.register(selector, SelectionKey.OP_CONNECT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void pollSelect(){
		try {
			System.out.println("select poll...");
			while (true) {
				if(this.selector.select()>0){
					Iterator<SelectionKey> keys= this.selector.selectedKeys().iterator();
					while(keys.hasNext()){
						SelectionKey item= keys.next();
						if(item.isConnectable()){
							handler.handleConnect(item);
						}else if (item.isReadable()) {
							handler.handleRead(item);
						}else if (item.isWritable()) {
							handler.handleWrite(item);
						}
						keys.remove();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		pollSelect();
		
	}
}
