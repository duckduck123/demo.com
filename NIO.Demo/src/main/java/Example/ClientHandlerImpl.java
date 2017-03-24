package Example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public class ClientHandlerImpl implements IClientHandler{

	public void handleConnect(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		SocketChannel sc=(SocketChannel) key.channel();
		if(sc.isConnectionPending()){
			sc.finishConnect();
		}
		sc.configureBlocking(false);
		//Selector sel, int ops,Object att
		sc.register(key.selector(),SelectionKey.OP_WRITE,ByteBuffer.wrap(new String("i am client i connected success... ").getBytes()));
		
	}

	public void handleRead(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		SocketChannel sc=(SocketChannel) key.channel();
		ByteBuffer readBuffer=ByteBuffer.allocate(1024);
		int reader=sc.read(readBuffer);
		System.out.println("server message:"+new String(readBuffer.array(),0,reader));
		sc.configureBlocking(false);
		readBuffer.clear();
		//读取之后注册写事件,把要发送的内容发送给客户端
		sc.register(key.selector(),SelectionKey.OP_WRITE,ByteBuffer.wrap(new String("i am client i rec your message!").getBytes()));
	}

	public void handleWrite(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		SocketChannel sc=(SocketChannel) key.channel();
		//attachment  这个是在注册写事件的时候 第三个参数也就是要写的数据
		ByteBuffer writeBuffer=(ByteBuffer) key.attachment();
		sc.write(writeBuffer);
		writeBuffer.clear();
		sc.configureBlocking(false);
		//写之后 注册 读事件， 不然 客户端发来消息 会接收不到.
		sc.register(key.selector(),SelectionKey.OP_READ);
	}

}
