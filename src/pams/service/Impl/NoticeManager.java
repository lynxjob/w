package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.NoticeDao;
import pams.model.Notice;

/**
 * @author cjl
 * 公告业务操作
 */
@Component("noticeManager")
public class NoticeManager
{
	private NoticeDao noticeDao;

	@Resource(name="noticeDaoImpl")
	public void setNoticeDao(NoticeDao noticeDao)
	{
		this.noticeDao = noticeDao;
	}
	
	public List<Notice> list(int start,int limit,String orderBy)
	{
		return this.noticeDao.list(Notice.class,start,limit,orderBy);
	}
	public Long getTotal()
	{
		return this.noticeDao.getTotal(Notice.class);
	}
	public void save(Notice notice)
	{
		this.noticeDao.save(notice);
	}
	public void delete(int entityId)
	{
		this.noticeDao.delete(Notice.class, entityId);
	}
	public void delete(String idsStr)
	{
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		this.noticeDao.delete(Notice.class,temp);
	}
	public Notice get(int entityId)
	{
		return this.noticeDao.get(Notice.class,entityId);
	}
	public void update(Notice notice)
	{
		this.noticeDao.update(notice);
	}
}