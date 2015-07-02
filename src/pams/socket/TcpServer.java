package pams.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @see  数据采用主动上报   Lynx
 * TCP网络服务程序
 * 创建服务器Socket
 * daq:数据采集卡
 * relay/realy:继电器
 * monitor:监控器,监控
 * */

public class TcpServer implements Runnable{
	//任务标识
	private boolean isRunning = true;
	//服务器socket
	private ServerSocket server = null;
	//lock
	private Lock lock = new ReentrantLock();
	//服务器server标识：类型[采集仪 继电器]+大棚ID+端口号
	private String serverId = "";
	
	public TcpServer(Integer port)
	{
		try {
			server = new ServerSocket(port);
			System.out.println(serverId+"端口:"+port);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(isRunning){
			try {
				Socket customer = server.accept();
				Client client = new Client(serverId, customer);
				TcpManager.clientBuffer.addClient(client);
			}catch (IOException e) {
				System.out.println("服务器server关闭-->"+e.getMessage());
			}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public Lock getLock() {
		return lock;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerId() {
		return serverId;
	}
}