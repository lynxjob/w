package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.ClientMessageDao;
import pams.model.ClientMessage;
@Component("clientMessageDao")
public class ClientMessageDaoImpl extends SuperImpl implements ClientMessageDao{

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientMessage> load() {
		// TODO Auto-generated method stub
		List<ClientMessage> clients = new ArrayList<ClientMessage>();
		clients=this.getHibernateTemplate().find("from ClientMessage");
		return clients;
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<String> load() {
		// TODO Auto-generated method stub
		List<String> clients = new ArrayList<String>();
		clients=this.getHibernateTemplate().find("select fullName from ClientMessage");
		return clients;
	}*/
	
}
