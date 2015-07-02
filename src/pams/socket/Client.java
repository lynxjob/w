package pams.socket;

import java.net.Socket;

public class Client {
	
	private String clientName;
	
	private Socket socket;
	
	private Client next = null;
	
	private Client pre = null;
	
	public Client(String clientName, Socket socket)
	{
		this.clientName = clientName;
		this.socket = socket;
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Client getPre() {
		return pre;
	}

	public void setPre(Client pre) {
		this.pre = pre;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Client getNext() {
		return next;
	}

	public void setNext(Client next) {
		this.next = next;
	}
	
	

}
