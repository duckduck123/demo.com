package Example;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public interface IServerHandler extends IHandler{
	/** 
     * 处理{@link SelectionKey#OP_ACCEPT}事件 
     * @param key  
     * @throws IOException 
     */  
    void handleAccept(SelectionKey key) throws IOException;  
  
}
