package pams.dao.Impl;


import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.LedDao;
import pams.model.Led;
@Component("ledDaoImpl")
public class LedDaoImpl extends SuperImpl implements LedDao {

	@SuppressWarnings("unchecked")
	@Override
	public Led load(int shed_id, int pinghao) {
		Led led = null;
		List<Led> leds = this.getHibernateTemplate().find("from Led d where d.shed = "+shed_id+" and d.pinghao = "+pinghao);
		if(leds != null && leds.size()>0){
			led = leds.get(0);
		}
		return led;
	}	
}
