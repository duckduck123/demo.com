package Example;
/**
 * @author 作者 geyangyang:
 * @version 创建时间：2017年3月24日
 * 类说明
 */
public class ClientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new Client(1990)).start();
	}

}
