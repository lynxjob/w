package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.Return_GoodsDao;
import pams.model.Return_Goods;

@Component("return_GoodsDao")
public class Return_GoodsDaoImpl extends SuperImpl implements Return_GoodsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Return_Goods> load() {
		// TODO Auto-generated method stub
		List<Return_Goods> return_Goods = new ArrayList<Return_Goods>();
		return_Goods = this.getHibernateTemplate().find("from Return_Goods");
		return return_Goods;
	}

}
