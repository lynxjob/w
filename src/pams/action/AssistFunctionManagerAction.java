package pams.action;


import javax.annotation.Resource;

import pams.model.AssistFunction;
import pams.service.Impl.AssistFunctionManager;
import pams.vo.AssistFunctionInfo;

import com.opensymphony.xwork2.ModelDriven;

public class AssistFunctionManagerAction extends BaseAction implements ModelDriven<AssistFunctionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AssistFunctionManager assistFunctionManager;
	private AssistFunctionInfo assistFunctionInfo=new AssistFunctionInfo();

	public AssistFunctionManager getAssistFunctionManager() {
		return assistFunctionManager;
	}
	@Resource(name="assistFunctionManager")
	public void setAssistFunctionManager(AssistFunctionManager assistFunctionManager) {
		this.assistFunctionManager = assistFunctionManager;
	}

	@Override
	public AssistFunctionInfo getModel() {
		return assistFunctionInfo;
	}
	public String list()
	{
		this.jsonString = this.assistFunctionManager.list();
		System.out.println("assistmenu---"+jsonString);
		return "list";
	}
	public String save()
	{
		if(this.assistFunctionManager.checkExistByName(this.assistFunctionInfo.getName())){
			this.jsonString = "{success:false,msg:'添加大棚类型失败,该类型名已存在!'}";
		}
		else{
			AssistFunction org=new AssistFunction();
			org.setName(assistFunctionInfo.getName());
			org.setDes(assistFunctionInfo.getDes());
			this.assistFunctionManager.save(org,assistFunctionInfo.getParentId());
			this.jsonString="{success:true,msg:'添加大棚类型完成!'}";
		}
		System.out.println(assistFunctionInfo.getName());
		System.out.println(assistFunctionInfo.getParentId());
		
		return "save";
	}
	/*
	public String listGrid()
	{
		Long rowCount = this.assistFunctionManager.getCount(this.assistFunctionInfo.getId());
		List<Shed> sheds = this.assistFunctionManager.list(this.assistFunctionInfo.getId());
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
	}*/
}
