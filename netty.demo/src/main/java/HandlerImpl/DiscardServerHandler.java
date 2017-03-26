package HandlerImpl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	/*
	 * 这里我们覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
	 * 这个例子中，收到的消息的类型是ByteBuf
	 * 
	 * @param ctx 通道处理的上下文信息
	 * 
	 * @param msg 接收的消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
//		//回写接收到的信息
		//ctx.write(Object)不能保证完全写入，底层存在缓存，需要通过ctx.flush()刷新,保证完全写入。
//		 ctx.write(msg); // (1)
//	     ctx.flush(); // (2)
		 ByteBuf in = (ByteBuf) msg;
		  try {
	           while (in.isReadable()) { // (1)
	             System.out.print((char) in.readByte());
	            System.out.flush();
	             }
	           
	    } finally {
	            ReferenceCountUtil.release(msg); // (2)
	    }
	}

	/***
	 * 
	 * 这个方法会在发生异常时触发
	 * 
	 * @param ctx
	 * 
	 * @param cause
	 * 
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
