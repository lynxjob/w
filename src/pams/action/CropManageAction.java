package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.Crop;
import pams.model.Factor;
import pams.service.Impl.CropManager;
import pams.service.Impl.FactorManager;
import pams.util.JsonUtil;
import pams.vo.CropInfo;

import com.opensymphony.xwork2.ModelDriven;

public class CropManageAction extends BaseAction implements ModelDriven<CropInfo> {
	
	private static final long serialVersionUID = 1L;
	private CropInfo cropInfo = new CropInfo();
	private CropManager cropManager;
	private FactorManager factorManager;
	private String cropName;
	private Integer level;
	
	
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public String getCropName() {
		return cropName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Override
	public CropInfo getModel() {
		return this.cropInfo;
	}
	@Resource(name="cropManager")
	public void setCropManager(CropManager cropManager){
		this.cropManager = cropManager;
	}
	@Resource(name="factorManager")
	public void setFactorManager(FactorManager factorManager){
		this.factorManager = factorManager;
	}
	/**
	 * 加载作物表格列表
	 * @return
	 */
	public String list(){
		List<Crop> crops = this.cropManager.list(this.cropInfo.getStart(), this.cropInfo.getLimit());
		long rowCount = this.cropManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.cropManager.getJsonStr(crops)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加作物信息
	 * @return
	 */
	public String save(){
		String uploadfilename = "";
		if(this.cropInfo.getUploadFileName()==null){
			uploadfilename = "default.jpg";
		}else{
			uploadfilename = this.cropInfo.getUploadFileName();
		}
		StringBuffer jsonStr = new StringBuffer(this.cropInfo.getJsonStr());
		if(jsonStr!=null){
			jsonStr = jsonStr.deleteCharAt(jsonStr.length()-1);
		}
		Crop crop = new Crop();
		String img_url = "images/upload/"+uploadfilename;
		crop.setName(this.cropInfo.getName());
		crop.setDes(this.cropInfo.getDes().substring(1));
		crop.setImg_url(img_url);
		crop.setTotal(this.cropInfo.getTotal());
		System.out.println(jsonStr.toString());
		List<Factor> factors = JsonUtil.getInstance().jsonToFactors(jsonStr.toString().replace('\"', '\''));
		for(Factor f : factors){
			f.setCrop(crop);
		}
		crop.setFactors(factors);
		cropManager.save(crop);
		this.jsonString = "{success:true,msg:'作物信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新作物信息
	 * @return
	 */
	public String update(){
		String uploadfilename = "";
		if(this.cropInfo.getUploadFileName()==null){
			uploadfilename = "default.jpg";
		}else{
			uploadfilename = this.cropInfo.getUploadFileName();
		}
		StringBuffer jsonStr = new StringBuffer(this.cropInfo.getJsonStr());
		if(jsonStr!=null){
			jsonStr = jsonStr.deleteCharAt(jsonStr.length()-1);
		}
		Crop crop = this.cropManager.get(this.cropInfo.getId());
		String img_url = "images/upload/"+uploadfilename;
		crop.setName(this.cropInfo.getName());
		crop.setDes(this.cropInfo.getDes());
		crop.setImg_url(img_url);
		crop.setTotal(this.cropInfo.getTotal());
		List<Factor> oldFactors = crop.getFactors();
		for(Factor f : oldFactors){
			f.setCrop(null);
			factorManager.update(f);
		}
		factorManager.delete();
		List<Factor> factors = JsonUtil.getInstance().jsonToFactors(jsonStr.toString().replace('\"', '\''));
		for(Factor factor : factors){
			factor.setCrop(crop);
		}
		crop.setFactors(factors);
		cropManager.update(crop);
		this.jsonString = "{success:true,msg:'作物信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除作物信息
	 * @return
	 */
	public String delete(){
		this.cropManager.delete(this.cropInfo.getIds());
		this.jsonString = "{success:true,msg:'作物信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载作物信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.cropManager.load();
		
		return "load";
	}
	/**
	 * 加载作物各生长周期环境因素参数
	 * @return
	 */
	public String loadData(){
		this.jsonString = cropManager.loadDataByNameAndLevel(cropName,level);
		System.out.println(jsonString+"-----");
		return "loadData";
	}
}
