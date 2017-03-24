package Example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public class ServerHandlerImpl implements IServerHandler{
	public void handleAccept(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
		SocketChannel sc=ssc.accept();
		sc.configureBlocking(false);
		sc.write(ByteBuffer.wrap(new String("hello！i am server").getBytes()));
		sc.register(key.selector(),SelectionKey.OP_READ);
	}

	public void handleRead(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		
		ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
		//根据其支持数据读取操作可知，这是一个SocketChannel。
		SocketChannel sc=(SocketChannel) key.channel();
		int reader=sc.read(byteBuffer);
		System.out.println("client bytes: readBytes = " + reader);
		System.out.println("client message: data = " + new String(byteBuffer.array(), 0, reader));
		//byteBuffer.flip();
//		if(!byteBuffer.hasRemaining()){
//			byteBuffer.capacity();
//		}
		byteBuffer.clear();
		sc.register(key.selector(),SelectionKey.OP_WRITE,ByteBuffer.wrap(new String("i rec your message!").getBytes()));
	}

	public void handleWrite(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		//附加到SelectionKey上的ByteBuffer包含了之前从信道中读取的数据。
		ByteBuffer byteBuffer = (ByteBuffer) key.attachment();  
		//该方法用来修改缓冲区的postion值
        byteBuffer.flip();  
        //获取信道
        SocketChannel socketChannel = (SocketChannel)key.channel();  
        //向信道中写数据
        socketChannel.write(byteBuffer);  
        //如有剩余数据可读，则修改该键关联的操作集，指示其只能进行读操作了
//        if(byteBuffer.hasRemaining()) {  
//            key.interestOps(SelectionKey.OP_READ);  
//        }  
//        //如果缓冲区中还有剩余数据，该操作将剩余数据移到缓冲区前端，以使下次迭代能读入更多数据。
//        byteBuffer.compact();  
        byteBuffer.clear();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(),SelectionKey.OP_READ);
		
	}
}
