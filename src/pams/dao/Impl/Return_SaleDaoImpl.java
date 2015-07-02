package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.Return_SaleDao;
import pams.model.Return_Sale;

@Component("return_SaleDao")
public class Return_SaleDaoImpl extends SuperImpl implements Return_SaleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Return_Sale> load() {
		// TODO Auto-generated method stub
		List<Return_Sale> return_Sale = new ArrayList<Return_Sale>();
		return_Sale = this.getHibernateTemplate().find("from Return_Sale");
		return return_Sale;
	}

}
