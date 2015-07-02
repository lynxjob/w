package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.StockDao;
import pams.model.Stock;

@Component("stockDao")
public class StockDaoImpl extends SuperImpl implements StockDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> load() {
		// TODO Auto-generated method stub
		List<Stock> stocks = new ArrayList<Stock>();
		stocks = this.getHibernateTemplate().find("from Stock");
		return stocks;
	}

}
