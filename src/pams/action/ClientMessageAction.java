package pams.action;


import java.util.List;

import javax.annotation.Resource;

import pams.model.ClientMessage;
import pams.service.Impl.ClientMessageManager;
import pams.vo.ClientInfo;

import com.opensymphony.xwork2.ModelDriven;

public class ClientMessageAction extends BaseAction implements ModelDriven<ClientInfo>{

	private static final long serialVersionUID = 1L;
	
	private ClientInfo clientInfo = new ClientInfo();
	private ClientMessageManager clientManager;
	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	public ClientMessageManager getClientManager() {
		return clientManager;
	}
	@Resource(name="clientMessageManager")
	public void setClientManager(ClientMessageManager clientManager) {
		this.clientManager = clientManager;
	}
	@Override
	public ClientInfo getModel() {
		// TODO Auto-generated method stub
		return this.clientInfo;
	}
	/**
	 * 加载客户信息表格列表
	 * @return
	 */
	public String list(){
		List<ClientMessage> clients = this.clientManager.list(this.clientInfo.getStart(), this.clientInfo.getLimit());
		long rowCount = this.clientManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.clientManager.getJsonStr(clients)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加客户信息
	 * @return
	 */
	public String save(){
		
		ClientMessage client = new ClientMessage();
		client.setFullName(this.clientInfo.getFullName());
		client.setShortName(this.clientInfo.getShortName());
		client.setAddress(this.clientInfo.getAddress());
		client.setPostcode(this.clientInfo.getPostcode());
		client.setTelephone(this.clientInfo.getTelephone());
		client.setE_mail(this.clientInfo.getE_mail());
		client.setLinkmanName(this.clientInfo.getLinkmanName());
		client.setLinkmanPhone(this.clientInfo.getLinkmanPhone());
		clientManager.save(client);
		this.jsonString = "{success:true,msg:'客户信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新客户信息
	 * @return
	 */
	public String update(){
		/*StringBuffer jsonStr = new StringBuffer(this.clientInfo.getJsonStr());
		if(jsonStr!=null){
			jsonStr = jsonStr.deleteCharAt(jsonStr.length()-1);
		}*/
		ClientMessage client = this.clientManager.get(this.clientInfo.getId());
		client.setFullName(this.clientInfo.getFullName());
		client.setShortName(this.clientInfo.getShortName());
		client.setAddress(this.clientInfo.getAddress());
		client.setPostcode(this.clientInfo.getPostcode());
		client.setE_mail(this.clientInfo.getE_mail());
		client.setLinkmanName(this.clientInfo.getLinkmanName());
		client.setLinkmanPhone(this.clientInfo.getLinkmanPhone());
		client.setTelephone(this.clientInfo.getTelephone());
		clientManager.update(client);
		this.jsonString = "{success:true,msg:'客户信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除客户信息
	 * @return
	 */
	public String delete(){
		this.clientManager.delete(this.clientInfo.getIds());
		this.jsonString = "{success:true,msg:'客户信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载客户信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.clientManager.load();
		
		return "load";
	}
	

}
