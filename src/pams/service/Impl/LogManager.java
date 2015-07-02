package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.LogDao;
import pams.model.Log;

/**
 * @author cjl
 * 日志Manager
 */
@Component("logManager")
public class LogManager 
{
	private LogDao logDao;

	@Resource(name="logDaoImpl")
	public void setLogDao(LogDao logDao) 
	{
		this.logDao = logDao;
	}
	
	public void save(Log log)
	{
		this.logDao.save(log);
	}
	public List<Log> list(int start,int limit)
	{
		return this.logDao.list(Log.class,start,limit);
	}
	public Long getTotal()
	{
		return this.logDao.getTotal(Log.class);
	}
}