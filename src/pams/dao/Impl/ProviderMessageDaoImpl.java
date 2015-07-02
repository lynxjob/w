package pams.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pams.dao.ProviderMessageDao;
import pams.model.ProviderMessage;
@Component("ProviderMessageDao")
public class ProviderMessageDaoImpl extends SuperImpl implements ProviderMessageDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<ProviderMessage> load() {
		// TODO Auto-generated method stub
		List<ProviderMessage> providers = new ArrayList<ProviderMessage>();
		providers=this.getHibernateTemplate().find("from ProviderMessage");
		return providers;
	}

}
