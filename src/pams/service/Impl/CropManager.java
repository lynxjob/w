package pams.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.CropDao;
import pams.model.Crop;
import pams.model.Factor;
import pams.util.JsonUtil;
import pams.vo.FactorInfo;

@Component("cropManager")
public class CropManager {
	
	private CropDao cropDao;
	
	public CropDao getCropDao() {
		return cropDao;
	}
	@Resource(name="cropDaoImpl")
	public void setCropDao(CropDao cropDao) {
		this.cropDao = cropDao;
	}
	/**
	 * 保存作物信息
	 * @param crop
	 */
	public void save(Crop crop){
		this.cropDao.save(crop);
	}
	/**
	 * 更新作物信息
	 * @param crop
	 */
	public void update(Crop crop){
		this.cropDao.update(crop);
	}
	/**
	 * 删除作物信息
	 * @param entityId
	 */
	public void delete(int entityId){
		this.cropDao.delete(Crop.class,entityId);
	}
	/**
	 * 批量删除作物信息
	 * @param idsStr
	 */
	public void delete(String idsStr){
		String temp=(String) idsStr.subSequence(1,idsStr.length()-1);
		String []datas = temp.split(",");
		for(String sdata : datas){
			int data = Integer.parseInt(sdata);
			this.cropDao.delete(Crop.class, data);
		}
	}
	/**
	 * 获取作物总数
	 * @return
	 */
	public long getTotal(){
		return this.cropDao.getTotal(Crop.class);
	}
	/**
	 * 获取作物列表
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Crop> list(final int start,final int limit){
		return this.cropDao.list(Crop.class, start, limit);
	}
	/**
	 * 获取作物列表,支持排序
	 * @param start
	 * @param limit
	 * @param orderBy
	 * @return
	 */
	public List<Crop> list(final int start,final int limit,String orderBy){
		return this.cropDao.list(Crop.class, start, limit,orderBy);
	}
	/**
	 * 根据作物ID获取作物
	 * @param entityId
	 * @return
	 */
	public Crop get(int entityId){
		return this.cropDao.get(Crop.class, entityId);
	}
	/**
	 * 获取作物生长阶段
	 * @param cropId
	 * @return
	 */
	public int getLevel(int cropId){
		return this.cropDao.getLevel(cropId);
	}
	/**
	 * 获取作物适宜环境条件
	 * @param cropId
	 * @return
	 */
	public Factor getCondition(int cropId){
		return this.cropDao.getCondition(cropId);
	}
	/**
	 * 获取作物数组信息
	 * @return
	 */
	public String load(){
		List<Crop> crops = this.cropDao.load();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Crop crop : crops){
			sb.append(crop.toString());
		}
		if(crops.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 获取作物json字符串
	 * @param crops
	 * @return
	 */
	public String getJsonStr(List<Crop> crops){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Crop crop : crops){
			sb.append("{");
			sb.append("'id':"+crop.getId());
			sb.append(",'name':'"+crop.getName());
			sb.append("','des':'"+crop.getDes());
			sb.append("','total':"+crop.getTotal());
			sb.append(",'planDate':'"+(crop.getPlantDate()==null?"":crop.getPlantDate()));
			sb.append("','img_url':'"+crop.getImg_url());
			sb.append("'},");
		}
		if(crops.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}
	/**
	 * 加载作物各生长周期环境因素参数
	 */
	public String loadDataByNameAndLevel(String name, Integer level) {
		String json = "";
		try{
		Factor info = cropDao.loadDataByNameAndLevel(name,level);
		if(info!=null){
			FactorInfo fi = new FactorInfo();
			fi.setCoc(info.getCoc());
			fi.setDadiwendu(info.getDadiwendu());
			fi.setDaqishidu(info.getDaqishidu());
			fi.setDaqiwendu(info.getDaqiwendu());
			fi.setGuanghe(info.getGuanghe());
			fi.setTurangshuifen(info.getTurangshuifen());
			fi.setStart(info.getStart());
			fi.setEnd(info.getEnd());
			json = JsonUtil.getInstance().objectToJson(fi);
			System.out.println(name+"---"+level+": "+json);
		}
		}catch(Exception e){
			System.out.println("加载环境因素出错－－－"+e.getMessage());
		}
		return json;
	}
}
