package Example;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public interface IClientHandler extends IHandler{
	 /** 
     * 处理{@link SelectionKey#OP_CONNECT}事件 
     * @param key  
     * @throws IOException 
     */  
    void handleConnect(SelectionKey key) throws IOException;  

}
