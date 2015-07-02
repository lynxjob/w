package pams.dao.Impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.FactorDao;
@Component("factorDaoImpl")
public class FactorDaoImpl extends SuperImpl implements FactorDao {

	@Override
	public void deleteNull() {
		this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				int total = session.createSQLQuery("delete from factor where cropId is NULL").executeUpdate();
				System.out.println("删除条数: "+total);
				return null;
			}
		});
	}

}
