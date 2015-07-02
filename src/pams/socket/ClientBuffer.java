package pams.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class ClientBuffer {
	
	private Client header = new Client("header", null);	//链表头部 循环时使用
	
	private HashMap<String, Client> buffer = new HashMap<String, Client>();
	
	private static final byte[] test = {0};
	
	public ClientBuffer()
	{
		buffer.put("header", header);
	}
	
	//遍历客户端如果出现异常 将其从缓存中删除
	public void checkClients()
	{
		Client token = header.getNext();
		while(token != null)
		{
			try {
				OutputStream out = token.getSocket().getOutputStream();
				out.write(test);
				out.flush();
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				removeClient(token.getClientName());
			}
			token = token.getNext();
		}
	}
	
	//获取客户端
	public Client getClient(String clientName)
	{
		return buffer.get(clientName);
	}
	
	//添加客户端到缓存
	public synchronized void addClient(Client client)
	{
		if(header.getNext() == null)
		{
			header.setNext(client);
			client.setPre(header);
		} else 
		{
			client.setNext(header.getNext());
			client.getNext().setPre(client);
			header.setNext(client);
			client.setPre(header);
		}
		buffer.put(client.getClientName(), client);
	}
	
	//删除客户端
	public synchronized void removeClient(String clientName)
	{
		Client client = buffer.get(clientName);
		if(client.getNext() == null)
		{
			client.getPre().setNext(null);
		} else 
		{
			client.getNext().setPre(client.getPre());
			client.getPre().setNext(client.getNext());
		}
		buffer.remove(clientName);
	}

	public Client getHeader() {
		return header;
	}

	public void setHeader(Client header) {
		this.header = header;
	}

}
