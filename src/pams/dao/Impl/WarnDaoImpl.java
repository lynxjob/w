package pams.dao.Impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.WarnDao;
import pams.model.Warn;
/**
 * 报警
 * @author 恶灵骑士
 *
 */
@Component("warnDaoImpl")
public class WarnDaoImpl extends SuperImpl implements WarnDao {


	@Override
	public Warn get(final Integer shedId, final int type) {
		return (Warn) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("from Warn where shed.id = ? and type = ?")
				              .setParameter(0, shedId)
				              .setParameter(1, type)
				              .uniqueResult();
			}
			
		});
	}

	@Override
	public void save(Warn warn) {
		this.getHibernateTemplate().save(warn);
		
	}

	@Override
	public void update(Warn warn) {
		this.getHibernateTemplate().update(warn);
		
	}
	
}
