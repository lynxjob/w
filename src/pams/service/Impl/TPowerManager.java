package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.TPowerDao;
import pams.model.TPower;

/**
 * @author cjl
 * 权限
 */
@Component("tpowerManager")
public class TPowerManager 
{
	private TPowerDao tpowerDao;

	@Resource(name="tpowerDaoImpl")
	public void setTpowerDao(TPowerDao tpowerDao) 
	{
		this.tpowerDao = tpowerDao;
	}
	
	/**
	 * 获取一级目录节点(全部)
	 */
	public List<TPower> listAllTPowers()
	{
		return this.tpowerDao.list(0);
	}
}