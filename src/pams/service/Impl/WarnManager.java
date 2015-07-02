package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.WarnDao;
import pams.model.Warn;

/**
 * 报警
 * @author 恶灵骑士
 *
 */
@Component("warnManager")
public class WarnManager {
	
	private WarnDao warnDao;
	
	@Resource(name="warnDaoImpl")
	public void setWarnDao(WarnDao warnDao){
		this.warnDao = warnDao;
	}
	/**
	 * 保存警报记录
	 * @param warn
	 */
	public void save(Warn warn){
		this.warnDao.save(warn);
	}
	/**
	 * 删除警报记录
	 * @param warnId
	 */
	public void delete(int warnId){
		this.warnDao.delete(Warn.class, warnId);
	}
	/**
	 * 获取警报记录列表
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Warn> list(int start,int limit){
		return this.warnDao.list(Warn.class, start, limit);
	}
	/**
	 *更新
	 * @param warn
	 */
	public void update(Warn warn){
		this.warnDao.update(warn);
	}
	/**
	 * 根据shedId和type获取Warn
	 * @param shedId
	 * @param type
	 * @return
	 */
	public Warn get(Integer shedId,int type){
		return this.warnDao.get(shedId, type);
	}
}
