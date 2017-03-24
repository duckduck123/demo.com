package Example;
/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public class ServerMain {
	public static void main(String[] args) {
		new Thread(new Server(1990)).start();
	}
}
