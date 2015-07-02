package pams.action;

import javax.annotation.Resource;

import pams.model.ShedType;
import pams.service.Impl.ShedTypeManager;
import pams.vo.ShedTypeInfo;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @author cjl
 * 处理树形结构
 */
public class ShedTypeManageAction extends BaseAction implements ModelDriven<ShedTypeInfo>
{
	private static final long serialVersionUID = 1L;
	private ShedTypeManager shedTypeManager;
	private ShedTypeInfo shedTypeInfo=new ShedTypeInfo();
	private int id = 0;
	
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	@Resource(name="shedTypeManager")
	public void setShedTypeManager(ShedTypeManager shedManager){
		this.shedTypeManager = shedManager;
	}
	@Override
	public ShedTypeInfo getModel() 
	{
		return shedTypeInfo;
	}
	
	/**
	 * CRUD处理
	 */
	public String list()
	{
		this.jsonString=this.shedTypeManager.list();
		return "list";
	}
	public String save()
	{
		if(this.shedTypeManager.checkExistByName(this.shedTypeInfo.getName())){
			this.jsonString = "{success:false,msg:'添加大棚类型失败,该类型名已存在!'}";
		}
		else{
			ShedType org=new ShedType();
			org.setName(shedTypeInfo.getName());
			org.setDes(shedTypeInfo.getDes());
			this.shedTypeManager.save(org,shedTypeInfo.getParentId());
			this.jsonString="{success:true,msg:'添加大棚类型完成!'}";
		}
		System.out.println(shedTypeInfo.getName());
		System.out.println(shedTypeInfo.getParentId());
		
		return "save";
	}
	public String update()
	{
		if(this.shedTypeManager.checkExistByName(this.shedTypeInfo.getName())){
			this.jsonString = "{success:false,msg:'修改大棚分类信息失败,该类型名已存在!'}";
		}
		else{
			ShedType org=this.shedTypeManager.get(shedTypeInfo.getId());
			org.setName(shedTypeInfo.getName());
			this.shedTypeManager.update(org);
			this.jsonString="{success:true,msg:'修改大棚分类信息完成！'}";
		}
		
		return "update";
	}
	public String delete()
	{
		boolean temp = this.shedTypeManager.delete(shedTypeInfo.getId());
			if(temp){
				this.jsonString="{success:true,msg:'删除大棚分类信息完成!'}";
			}
			else{
				this.jsonString="{success:false,msg:'存在子分类，不允许删除!'}";
			}
		
		return "delete";
	}
}