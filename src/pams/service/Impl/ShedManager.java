package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.ShedDao;
import pams.model.Shed;

/**
 * 大棚管理实现类
 * @author 恶灵骑士
 *
 */
@Component("shedManager")
public class ShedManager {
	
	private ShedDao shedDao;

	@Resource(name="shedDaoImpl")
	public void setShedDao(ShedDao shedDao) {
		this.shedDao = shedDao;
	}

	/**
	 * 常用CRUD
	 */
	public void save(Shed shed){
		this.shedDao.save(shed);
	}
	/**
	 * 总体分页
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Shed> list(int start,int limit){
		return this.shedDao.list(Shed.class, start, limit);
	}
	/**
	 * 总体分页
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Shed> list(int start,int limit,String orderBy){
		return this.shedDao.list(Shed.class, start, limit, orderBy);
	}
	/**
	 * 局部分页
	 * shedTypeId=0则获取所有大棚信息
	 * @param shedTypeId
	 * @return
	 */
	public List<Shed> list(int shedTypeId){
		return this.shedDao.list(shedTypeId);
	}
	public void update(Shed shed){
		this.shedDao.update(shed);
	}
	
	public Shed get(int entityId){
		return this.shedDao.get(Shed.class, entityId);
	}
	/**
	 * 总体分页总数
	 * @return
	 */
	public Long getTotal(){
		return this.shedDao.getTotal(Shed.class);
	}
	/**
	 * 局部分页总数
	 * @param shedTypeId
	 * @return
	 */
	public Long getCount(int shedTypeId){
		return this.shedDao.getCount(shedTypeId);
	}
	public void delete(int entityId){
		this.shedDao.delete(Shed.class, entityId);
	}
	
	public void delete(String idsStr){
		//去除数组[]
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		this.shedDao.delete(Shed.class, temp);
	}
	
	public boolean checkExistByName(int shedId,String name){
		return this.shedDao.checkExistByName(shedId,name);
	}
	
	public boolean checkExistByName(String name){
		return this.shedDao.checkExistByName(name);
	}
	/**
	 * 大棚树形列表json字符串,用于用户账户模块
	 * @return
	 */
	public String list(){
		List<Shed> sheds = this.shedDao.list(0);
		return this.shedDao.getJsonString(sheds,true);
	}
	/**
	 * 大棚树形列表json字符串,用于大棚监测模块
	 * @param userId 用户ID
	 * @return
	 */
	public String load(int userId){
		List<Shed> sheds = this.shedDao.loadByUserId(userId);
		return this.shedDao.getJsonString(sheds, false);
	}
}
