package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.ReserveDao;
import pams.model.Device;
import pams.model.Reserve;

@Component("reserveDao")
public class ReserveDaoImpl extends SuperImpl implements ReserveDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Reserve> load() {
		// TODO Auto-generated method stub
		List<Reserve> reserves = new ArrayList<Reserve>();
		reserves=this.getHibernateTemplate().find("from Reserve");
		return reserves;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getName() {
		// TODO Auto-generated method stub
		List<String> names = new ArrayList<String>();
		names = this.getHibernateTemplate().find("select goodsName from Reserve");
		return names;
	}
	@SuppressWarnings("unchecked")
	@Override
	public double getRemainder(String name) {
		// TODO Auto-generated method stub
		double remainder = 0;
		List<String> remainders = (List<String>)this.getHibernateTemplate().find("select R.remainder from Reserve R where R.goodsName = '"+name+"'");
		System.out.println("remainders:"+Double.parseDouble(remainders.toString().substring(1, (remainders.toString().length()-1))));
		if(remainders != null && remainders.size()>0){
			remainder = Double.parseDouble(remainders.toString().substring(1, (remainders.toString().length()-1)));
		}
		return remainder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getTotal(String name) {
		// TODO Auto-generated method stub
		double total = 0;
		List<String> totals = this.getHibernateTemplate().find("select R.total from Reserve R where R.goodsName ='"+name+"'");
		if(totals != null && totals.size()>0){
			total = Double.parseDouble(totals.toString().substring(1, (totals.toString().length()-1)));
		}
		return total;
	}
	@SuppressWarnings("unchecked")
	public int getBh(String name)
	{
		int id = 0;
		List<String> ids = this.getHibernateTemplate().find("select R.id from Reserve R where R.goodsName ='"+name+"'");
		if(ids != null && ids.size()>0){
			id = Integer.parseInt(ids.toString().substring(1,(ids.toString().length()-1)));
		}
		return id;
	}
}
