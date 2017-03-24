package Example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月23日 类说明
 */
public class Server implements Runnable {

	private InetSocketAddress inetSocketAddress;
	private ServerSocketChannel schannel;
	private Selector selector;
	private IServerHandler handler;
	public Server(int port) {
		try {
			System.out.println("init server param...");
			handler = new ServerHandlerImpl();
			inetSocketAddress = new InetSocketAddress(port);
			schannel = ServerSocketChannel.open();
			selector = Selector.open();
			schannel.configureBlocking(false);
			schannel.socket().bind(inetSocketAddress);
			schannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("init server param complate !");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pollSelect() {
		try {
			System.out.println("select poll...");
			while (true) {
				if (this.selector.select() > 0) {
					Iterator<SelectionKey> selectKeys = this.selector
							.selectedKeys().iterator();
					while (selectKeys.hasNext()) {
						SelectionKey key = selectKeys.next();
						if (key.isAcceptable()) {
							handler.handleAccept(key);
						} else if (key.isReadable()) {
							handler.handleRead(key);
						} else if (key.isWritable()) {
							handler.handleWrite(key);
						}
						selectKeys.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	public void run() {
		pollSelect();
	}
}
