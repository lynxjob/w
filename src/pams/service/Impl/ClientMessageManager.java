package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.ClientMessageDao;
import pams.model.ClientMessage;

@Component("clientMessageManager")
public class ClientMessageManager {
	
	private ClientMessageDao clientMessageDao;

	public ClientMessageDao getClientMessageDao() {
		return clientMessageDao;
	}
	@Resource(name="clientMessageDao")
	public void setClientMessageDao(ClientMessageDao clientMessageDao) {
		this.clientMessageDao = clientMessageDao;
	}
	/*
	 * 保存客户信息
	 * */
	public void save(ClientMessage message)
	{
		this.clientMessageDao.save(message);
	}
	/*
	 * 更新客户信息
	 * */
	public void update(ClientMessage message)
	{
		this.clientMessageDao.update(message);
	}
	/*
	 * 删除客户信息
	 * */
	public void delete(int entityId)
	{
		this.clientMessageDao.delete(ClientMessage.class, entityId);
	}
	
	/*
	 * 批量删除客户信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.clientMessageDao.delete(ClientMessage.class, data);
		}
	}
	/*
	 * 获取客户信息总数
	 * */
	public long getTotal(){
		return this.clientMessageDao.getTotal(ClientMessage.class);
	}

	/*
	 * 获取客户信息列表
	 * @param start
	 * @param limit
	 * */
	public List<ClientMessage> list(final int start,final int limit){
		return this.clientMessageDao.list(ClientMessage.class, start, limit);
	}
	/**
	 * 获取客户信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<ClientMessage> list(final int start,final int limit,String orderBy){
		return this.clientMessageDao.list(ClientMessage.class, start, limit,orderBy);
	}
	/**
	 * 根据客户ID获取作物
	 * @param entityId
	 * @return
	 */
	public ClientMessage get(int entityId){
		return this.clientMessageDao.get(ClientMessage.class, entityId);
	}
	/*
	public String load(){
		List<ClientMessage> clients = this.clientMessageDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(ClientMessage client : clients){
			sb.append(client.toString());
		}
		if(clients.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}*/
	public String load(){
		List<ClientMessage> names = this.clientMessageDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(ClientMessage name : names){
			sb.append(name.toString());
		}
		if(names.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取客户json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<ClientMessage> clients){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(ClientMessage client : clients){
			sb.append("{");
			sb.append("id:"+client.getId());
			sb.append(",fullName:'"+client.getFullName());
			sb.append("',shortName:'"+client.getShortName());
			sb.append("',address:'"+client.getAddress());
			sb.append("',postcode:'"+client.getPostcode());
			sb.append("',telephone:'"+client.getTelephone());
			sb.append("',linkmanName:'"+client.getLinkmanName());
			sb.append("',linkmanPhone:'"+client.getLinkmanPhone());
			sb.append("',e_mail:'"+client.getE_mail());
			sb.append("'},");
		}
		if(clients.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}
	
}
