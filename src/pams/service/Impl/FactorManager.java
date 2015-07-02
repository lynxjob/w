package pams.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.FactorDao;
import pams.model.Factor;

@Component("factorManager")
public class FactorManager {
	private FactorDao factorDao;

	@Resource(name="factorDaoImpl")
	public void setFactorDao(FactorDao factorDao) {
		this.factorDao = factorDao;
	}
	
	/**
	 * 删除
	 * @param factorId
	 */
	public void delete(Integer factorId){
		this.factorDao.delete(Factor.class, factorId);
	}
	/**
	 * 更新
	 * @param factor
	 */
	public void update(Factor factor){
		this.factorDao.update(factor);
	}
	/**
	 * 删除全部cropId为空的记录
	 */
	public void delete(){
		this.factorDao.deleteNull();
	}
}
