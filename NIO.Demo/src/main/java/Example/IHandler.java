package Example;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public interface IHandler {
	  /** 
     * 处理{@link SelectionKey#OP_READ}事件 
     * @param key  
     * @throws IOException 
     */  
    void handleRead(SelectionKey key) throws IOException;  
    /** 
     * 处理{@link SelectionKey#OP_WRITE}事件 
     * @param key  
     * @throws IOException 
     */  
    void handleWrite(SelectionKey key) throws IOException;  
}
