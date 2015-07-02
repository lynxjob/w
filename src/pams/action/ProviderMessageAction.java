package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.ProviderMessage;
import pams.service.Impl.ProviderMessageManager;
import pams.vo.ProviderInfo;

import com.opensymphony.xwork2.ModelDriven;

public class ProviderMessageAction extends BaseAction implements ModelDriven<ProviderInfo>{

	private static final long serialVersionUID = 1L;
	
	private ProviderInfo providerInfo = new ProviderInfo();
	private ProviderMessageManager providerManager;
	public ProviderInfo getproviderInfo() {
		return providerInfo;
	}

	public void setproviderInfo(ProviderInfo providerInfo) {
		this.providerInfo = providerInfo;
	}
	
	public ProviderMessageManager getproviderManager() {
		return providerManager;
	}
	@Resource(name="providerMessageManager")
	public void setproviderManager(ProviderMessageManager providerManager) {
		this.providerManager = providerManager;
	}
	@Override
	public ProviderInfo getModel() {
		// TODO Auto-generated method stub
		return this.providerInfo;
	}
	/**
	 * 加载供应商信息表格列表
	 * @return
	 */
	public String list(){
		List<ProviderMessage> providers = this.providerManager.list(this.providerInfo.getStart(), this.providerInfo.getLimit());
		long rowCount = this.providerManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.providerManager.getJsonStr(providers)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加供应商信息
	 * @return
	 */
	public String save(){
		
		ProviderMessage provider = new ProviderMessage();
		provider.setFullName(this.providerInfo.getFullName());
		provider.setShortName(this.providerInfo.getShortName());
		provider.setAddress(this.providerInfo.getAddress());
		provider.setPostcode(this.providerInfo.getPostcode());
		provider.setE_mail(this.providerInfo.getE_mail());
		provider.setLinkmanName(this.providerInfo.getLinkmanName());
		provider.setLinkmanPhone(this.providerInfo.getLinkmanPhone());
		provider.setTelephone(this.providerInfo.getTelephone());
		providerManager.save(provider);
		this.jsonString = "{success:true,msg:'供应商信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新供应商信息
	 * @return
	 */
	public String update(){
		
		ProviderMessage provider = this.providerManager.get(this.providerInfo.getId());
		provider.setFullName(this.providerInfo.getFullName());
		provider.setShortName(this.providerInfo.getShortName());
		provider.setAddress(this.providerInfo.getAddress());
		provider.setPostcode(this.providerInfo.getPostcode());
		provider.setE_mail(this.providerInfo.getE_mail());
		provider.setLinkmanName(this.providerInfo.getLinkmanName());
		provider.setLinkmanPhone(this.providerInfo.getLinkmanPhone());
		provider.setTelephone(this.providerInfo.getTelephone());
		providerManager.update(provider);
		this.jsonString = "{success:true,msg:'供应商信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除供应商信息
	 * @return
	 */
	public String delete(){
		this.providerManager.delete(this.providerInfo.getIds());
		this.jsonString = "{success:true,msg:'供应商信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载供应商信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.providerManager.load();
		
		return "load";
	}
	
}
