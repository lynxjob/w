package pams.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pams.model.Crop;
import pams.model.Shed;
import pams.model.User;
import pams.service.Impl.CropManager;
import pams.service.Impl.ShedManager;
import pams.service.Impl.ShedTypeManager;
import pams.vo.ShedInfo;

import com.opensymphony.xwork2.ModelDriven;

public class ShedManageAction extends BaseAction implements ModelDriven<ShedInfo> {
	
	private static final long serialVersionUID = 1L;
	private ShedManager shedManager;
	private ShedTypeManager shedTypeManager; 
	private CropManager cropManager;
	private ShedInfo shedInfo = new ShedInfo();

	@Resource(name="shedManager")
	public void setShedManager(ShedManager shedManager){
		this.shedManager = shedManager;
	}
	@Resource(name="shedTypeManager")
	public void setShedTypeManager(ShedTypeManager shedTypeManager){
		this.shedTypeManager = shedTypeManager;
	}
	@Resource(name="cropManager")
	public void setCropManager(CropManager cropManager){
		this.cropManager = cropManager;
	}
	@Override
	public ShedInfo getModel() {
		return  shedInfo;
	}
	
	//常用CRUD
	/**
	 * 用户账户模块中用于显示所有大棚列表
	 */
	public String list(){
		this.jsonString = this.shedManager.list();
		return "list";
	}
	/**
	 * 控制中心 监测中心模块中大棚列表显示
	 * @return 
	 */
	public String load(){
		User user = (User)this.getSessionAtrribute("user");
		if(user!=null){
			this.jsonString = this.shedManager.load(user.getId());
		}
		return "load";
	}
	/**
	 * 大棚管理中显示大棚表格列表
	 * @return
	 */
	public String listGrid(){
		Long rowCount = this.shedManager.getCount(this.shedInfo.getShedTypeId());
		List<Shed> sheds = this.shedManager.list(this.shedInfo.getShedTypeId());
		if(sheds == null || sheds.size()==0){
			this.jsonString = "{rowCount:0,result:[]}";
		}
		else{
			StringBuffer sb = new StringBuffer();
			sb.append("{rowCount:"+rowCount+",result:[");
			for(Shed shed : sheds){
				sb.append(shed.toString());
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]}");
			
			this.jsonString = sb.toString();	
			
		}
		System.out.println(this.jsonString);
		return "listGrid";
	}
	public String save(){
		if(this.shedManager.checkExistByName(this.shedInfo.getName())){
			this.jsonString = "{success:false,msg:'添加大棚信息失败,该大棚名已存在!'}";
		}
		else{
			User user = (User)this.getSessionAtrribute("user");
			Shed shed = new Shed();
			if(this.shedInfo.getCropId()>0){
				Crop crop = this.cropManager.get(this.shedInfo.getCropId());
				shed.setCrop(crop);
				crop.getSheds().add(shed);
				crop.setPlantDate(new Date());
				cropManager.update(crop);
			}
			shed.setName(this.shedInfo.getName());
			shed.setCreatorId(user.getId());
			shed.setCreatorName(user.getName());
			shed.setDes(this.shedInfo.getDes());
			shed.setCdate(new Date());
			shed.setShedType(shedTypeManager.get(this.shedInfo.getShedTypeId()));
			shedManager.save(shed);
			
			this.jsonString = "{success:true,msg:'添加大棚信息完成!'}";
			this.recordLog("添加大棚信息", "添加大棚："+shedInfo.getName());
		}
		
		return "save";
	}
	
	public String update(){
		if(this.shedManager.checkExistByName(this.shedInfo.getId(),this.shedInfo.getName())){
			this.jsonString = "{success:false,msg:'更新大棚信息失败,该大棚名已存在!'}";
		}
		else{
			Shed shed = shedManager.get(this.shedInfo.getId());
			if(this.shedInfo.getCropId()>0){
				Crop crop = cropManager.get(this.shedInfo.getCropId());
				shed.setCrop(crop);
				crop.getSheds().add(shed);
				crop.setPlantDate(new Date());
				cropManager.update(crop);
			}else{
				Crop crop = shed.getCrop();
				if(crop!=null){
					crop.setPlantDate(null);
					cropManager.update(crop);
				}
				shed.setCrop(null);
			}
			shed.setName(this.shedInfo.getName());
			shed.setDes(this.shedInfo.getDes());
			shedManager.update(shed);
			
			this.jsonString = "{success:true,msg:'更新大棚信息完成!'}";
			this.recordLog("更新大棚信息", "更新大棚："+shedInfo.getName());
			
		}
		
		return "update";
	}
	
	public String delete(){
		this.shedManager.delete(this.shedInfo.getIds());
		this.jsonString = "{success:true,msg:'删除大棚信息完成!'}";
		this.recordLog("删除大棚信息", "删除大棚："+shedInfo.getIds());
		return "delete";
	}
	public String move(){
		if(this.shedInfo.getShedTypeId()==0){
			this.jsonString = "{success:false,msg:'请选择一个大棚类型!'}";
		}
		else{
			Shed shed = this.shedManager.get(this.shedInfo.getId());
			shed.setShedType(this.shedTypeManager.get(this.shedInfo.getShedTypeId()));
			this.shedManager.update(shed);
			this.jsonString="{success:true,msg:'移动大棚信息完成！'}";
			this.recordLog("移动大棚信息", "移动大棚："+shedInfo.getId());
		}
		
		return "move";
	}
}
