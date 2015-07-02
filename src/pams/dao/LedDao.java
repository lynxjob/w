package pams.dao;

import pams.model.Led;

public interface LedDao extends SuperDao{
	
	public Led load(int shed_id,int pinghao);

}
