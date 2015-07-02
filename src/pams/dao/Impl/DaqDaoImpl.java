package pams.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.DaqDao;
import pams.model.DaqData;
/**
 * 数据采集仪Dao层实现类
 * @author 恶灵骑士
 *
 */
@Component("daqDaoImpl")
public class DaqDaoImpl  extends SuperImpl implements DaqDao {
	/**
	 * 保存数据采集仪数据
	 * @param daqdata 
	 */
	@Override
	public void save(DaqData daqdata) {
		this.getHibernateTemplate().save(daqdata);
	}

	@Override
	public long getTotal(String startDate, String endDate) {
		final String sqlStr="select count(*) from DaqData where DATE(sampleDate) between '"+startDate+"' and '"+endDate+"'";
		return (Long) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Long doInHibernate(Session session) throws HibernateException,SQLException {
				Long rowCount = (Long) session.createQuery(sqlStr).uniqueResult();
				return rowCount;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DaqData> list(String startDate, String endDate, 
			final int start, final int limit) {
		final String sqlStr="from DaqData where DATE(sampleDate) between '"+startDate+"' and '"+endDate+"'";
		//System.out.println(sqlStr);
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public List<DaqData> doInHibernate(Session session)throws HibernateException {
				Query query = session.createQuery(sqlStr);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
	}
	
}
