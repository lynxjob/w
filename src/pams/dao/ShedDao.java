package pams.dao;

import java.util.List;

import pams.model.Shed;

/**
 * 大棚Dao
 * @author 恶灵骑士
 *
 */
public interface ShedDao extends SuperDao {
	/**
	 * 获取某大棚类型下的大棚数量
	 * @param shedTypeId 大棚类型
	 * @return
	 */
	public Long getCount(int shedTypeId);
	/**
	 * 获取某大棚类型下的大棚列表
	 * shedTypeId = 0 则获取所有大棚信息
	 * @param shedTypeId 大棚类型
	 * @return
	 */
	public List<Shed> list(int shedTypeId);
	/**
	 * 检测大棚是否重名
	 * @param name
	 * @return
	 */
	public boolean checkExistByName(String name);
	/**
	 * 检测大棚是否重名
	 * @param name
	 * @return
	 */
	public boolean checkExistByName(int shedId,String name);
	/**
	 * 获取整棵树的json字符串
	 * @param sheds 待获取的sheds
	 * @param checked 获取的树是否带有单选框
	 * @return jsonString
	 */
	public String getJsonString(List<Shed> sheds,boolean checked);
	/**
	 * 获取某用户所管理的大棚列表
	 * @param userId 用户ID
	 * @return 大棚列表
	 */
	public List<Shed> loadByUserId(int userId);
}
