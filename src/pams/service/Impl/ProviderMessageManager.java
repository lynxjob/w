package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.ProviderMessageDao;
import pams.model.ProviderMessage;


@Component("providerMessageManager")
public class ProviderMessageManager  {
	private ProviderMessageDao providerMessageDao;
	
	public ProviderMessageDao getProviderMessageDao() {
		return providerMessageDao;
	}
	@Resource(name="ProviderMessageDao")
	public void setProviderMessageDao(ProviderMessageDao providerMessageDao) {
		this.providerMessageDao = providerMessageDao;
	}
	/*
	 * 保存供应商信息
	 * */
	public void save(ProviderMessage message)
	{
		this.providerMessageDao.save(message);
	}
	/*
	 * 更新供应商信息
	 * */
	public void update(ProviderMessage message)
	{
		this.providerMessageDao.update(message);
	}
	/*
	 * 删除供应商信息
	 * */
	public void delete(int entityId)
	{
		this.providerMessageDao.delete(ProviderMessage.class, entityId);
	}
	
	/*
	 * 批量删除供应商信息
	 * */
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas)
		{
			int data = Integer.parseInt(sdata);
			this.providerMessageDao.delete(ProviderMessage.class, data);
		}
	}
	/*
	 * 获取供应商信息总数
	 * */
	public long getTotal(){
		return this.providerMessageDao.getTotal(ProviderMessage.class);
	}

	/*
	 * 获取供应商信息列表
	 * @param start
	 * @param limit
	 * */
	public List<ProviderMessage> list(final int start,final int limit){
		return this.providerMessageDao.list(ProviderMessage.class, start, limit);
	}
	/**
	 * 获取供应商信息列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<ProviderMessage> list(final int start,final int limit,String orderBy){
		return this.providerMessageDao.list(ProviderMessage.class, start, limit,orderBy);
	}
	/**
	 * 根据供应商ID获取作物
	 * @param entityId
	 * @return
	 */
	public ProviderMessage get(int entityId){
		return this.providerMessageDao.get(ProviderMessage.class, entityId);
	}
	public String load(){
		List<ProviderMessage> providers = this.providerMessageDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(ProviderMessage provider : providers){
			sb.append(provider.toString());
		}
		if(providers.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取供应商json字符串
	 * @param clients
	 * @return
	 */
	public String getJsonStr(List<ProviderMessage> providers){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(ProviderMessage  provider : providers){
			sb.append("{");
			sb.append("id:"+provider.getId());
			sb.append(",fullName:'"+provider.getFullName());
			sb.append("',shortName:'"+provider.getShortName());
			sb.append("',address:'"+provider.getAddress());
			sb.append("',postcode:'"+provider.getPostcode());
			sb.append("',telephone:'"+provider.getTelephone());
			sb.append("',linkmanName:'"+provider.getLinkmanName());
			sb.append("',linkmanPhone:'"+provider.getLinkmanPhone());
			sb.append("',e_mail:'"+provider.getE_mail());
			sb.append("'},");
		}
		if(providers.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}

}
