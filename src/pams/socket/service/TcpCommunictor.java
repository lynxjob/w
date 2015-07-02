package pams.socket.service;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import pams.events.AbstractEvent;
import pams.socket.Client;
import pams.socket.TcpManager;
import pams.timer.ReadDAQTask;


/**
 * Tcp连接控制器
 * @author 恶灵骑士
 *
 */

public class TcpCommunictor implements ICommunicator {
	
	private ReadDAQTask rdt = null;
	private String clientName = "daq_0_0";
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientName(){
		return this.clientName;
	}
	
	public byte[] getData(String clientName, AbstractEvent commEvent) {
		byte[] datas = null;
		Client client = TcpManager.clientBuffer.getClient(clientName);
		if(client != null)
		{
			try {
				InputStream in = client.getSocket().getInputStream();
				OutputStream out = client.getSocket().getOutputStream();
				//将控制命令发送给数据采集卡或继电器
				System.out.println("命令："+commEvent.getCommd());
				out.write(commEvent.getCommd());
				out.flush();
				byte[] buffer = new byte[1024*2];
				//接收数据采集卡数据或继电器数据
				int receive = in.read(buffer);
				datas = new byte[receive];
				System.arraycopy(buffer, 0, datas, 0, receive);
     			System.out.println("连接成功:"+receive+"---"+datas.toString());
			}catch(SocketException e){
				System.out.println("Socket连接出错-->"+e.getMessage());
				TcpManager.clientBuffer.removeClient(clientName);
			}catch (UnsupportedEncodingException e) {
				System.out.println("传输数据编码错误-->"+e.getMessage());
				TcpManager.clientBuffer.removeClient(clientName);
			} catch (IOException e) {
				System.out.println("IO异常-->"+e.getMessage());
				TcpManager.clientBuffer.removeClient(clientName);
			}
		}
		return datas;
	}
	
	public synchronized void restartTimer()
	{
		if( null != this.rdt)
		{
			this.stopTimer();
			this.startTimer();
		}
	}

	public synchronized void stopTimer() {
		if( null != this.rdt)
		{
			this.rdt.stopTimer();
			this.rdt = null;
		}
	}

	public synchronized void startTimer() {
		if( null == this.rdt)
		{	
			int shedId = Integer.parseInt(clientName.split("_")[1]);
			if(shedId < 0)
				shedId = 0;
			this.rdt = new ReadDAQTask(this,shedId);
			rdt.setClientName(clientName);
			rdt.startTimer();
		}
	}
	
	public ReadDAQTask getRdt() {
		return rdt;
	}

	public void setRdt(ReadDAQTask rdt) {
		this.rdt = rdt;
	}
}
