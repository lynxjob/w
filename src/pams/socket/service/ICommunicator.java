package pams.socket.service;

import pams.events.AbstractEvent;



public interface ICommunicator {
	
	public byte[] getData(String identify, AbstractEvent commEvent);
	public String getClientName();
}
