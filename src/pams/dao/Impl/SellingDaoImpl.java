package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.SellingDao;
import pams.model.Selling;

@Component("sellingDao")
public class SellingDaoImpl extends SuperImpl implements SellingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Selling> load() {
		// TODO Auto-generated method stub
		List<Selling> selling = new ArrayList<Selling>();
		selling = this.getHibernateTemplate().find("from Selling");
		return selling;
	}

}
