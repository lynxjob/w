package pams.dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import pams.dao.CropDao;
import pams.model.Crop;
import pams.model.Factor;
import pams.vo.FactorInfo;
@Component("cropDaoImpl")//注册容器bean，名字为cropDaoImpl
public class CropDaoImpl extends SuperImpl implements CropDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Crop> load() {
		List<Crop> crops = new ArrayList<Crop>();
		crops = this.getHibernateTemplate().find("from Crop");
		return crops;
	}

	@Override
	public Factor getCondition(final int cropId) {
		return (Factor) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryStr = "select * from factor where cropId = "+cropId+" and DATE((select plantDate from crop where id = "+cropId+"))+start <= DATE(now()) and DATE(now()) <DATE((select plantDate from crop where id = "+cropId+"))+end";
				System.out.println(queryStr);
				return session.createSQLQuery(queryStr).addEntity(Factor.class).uniqueResult(); 
			}
			
		});
	}

	@Override
	public int getLevel(final int cropId) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryStr = "select con.level from Factor con where con.crop.id = ? and (year(CURRENT_DATE())*365+month(CURRENT_DATE())*30+day(CURRENT_DATE())-year(con.crop.plantDate)*365-month(con.crop.plantDate)*30-day(con.crop.plantDate))>= con.start and "+
				   "(year(CURRENT_DATE())*365+month(CURRENT_DATE())*30+day(CURRENT_DATE())-year(con.crop.plantDate)*365-month(con.crop.plantDate)*30-day(con.crop.plantDate)) < con.end";
				return (Integer)session.createQuery(queryStr).setParameter(0, cropId).uniqueResult();
			}
			
		});
	}

	@Override
	public Factor loadDataByNameAndLevel(final String name, final Integer level) {
		return (Factor) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryStr = "select * from factor where level = "+level+" and cropId = (select id from crop where name = '"+name+"')";
				return session.createSQLQuery(queryStr).addEntity(Factor.class).uniqueResult();
			}
		});
	}
}
