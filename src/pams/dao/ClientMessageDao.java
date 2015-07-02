package pams.dao;

import java.util.List;

import pams.model.ClientMessage;

/*
 * 客户基本信息
 * */
public interface ClientMessageDao extends SuperDao{

	/**
	 * 加载客户信息列表
	 * @return
	 */
//	public List<String> load();
	
	public List<ClientMessage> load();
}
