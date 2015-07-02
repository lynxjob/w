package pams.dao;

import pams.model.Warn;

/**
 * 报警
 * @author 恶灵骑士
 *
 */
public interface WarnDao extends SuperDao{
		public Warn get(Integer shedId,int type);
		public void update(Warn warn);
		public void save(Warn warn);
}
