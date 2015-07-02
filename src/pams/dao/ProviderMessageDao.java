package pams.dao;

import java.util.List;

import pams.model.ProviderMessage;

public interface ProviderMessageDao extends SuperDao 
{
	/**
	 * 加载供应商信息列表
	 * @return
	 */
	public List<ProviderMessage> load();
}
