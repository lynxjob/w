package pams.dao;

import java.util.List;

import pams.model.Crop;
import pams.model.Factor;
import pams.vo.FactorInfo;

public interface CropDao extends SuperDao {
	/**
	 * 加载作物列表
	 * @return
	 */
	public List<Crop> load();
	/**
	 * 根据作物Id获取生长阶段
	 * @param cropId
	 * @return
	 */
	public int getLevel(int cropId);
	/**
	 * 根据作物Id获取作物
	 * 当前阶段生长条件
	 * @param cropId
	 * @return
	 */
	public Factor getCondition(int cropId);
	/**
	 * 加载作物各生长周期环境因素参数
	 */
	public Factor loadDataByNameAndLevel(String name, Integer level);

}
