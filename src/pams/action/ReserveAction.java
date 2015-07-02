package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.ClientMessage;
import pams.model.Reserve;
import pams.service.Impl.ReserveManager;
import pams.vo.ReserveInfo;

import com.opensymphony.xwork2.ModelDriven;

public class ReserveAction extends BaseAction implements ModelDriven<ReserveInfo>{

	private static final long serialVersionUID = 1L;
	
	private ReserveInfo reserveInfo = new ReserveInfo();
	private ReserveManager reserveManager;
	public ReserveInfo getReserveInfo() {
		return reserveInfo;
	}

	public void setReserveInfo(ReserveInfo reserveInfo) {
		this.reserveInfo = reserveInfo;
	}
	
	public ReserveManager getReserveManager() {
		return reserveManager;
	}
	@Resource(name="reserveManager")
	public void setReserveManager(ReserveManager reserveManager) {
		this.reserveManager = reserveManager;
	}
	public ReserveInfo getModel() {
		// TODO Auto-generated method stub
		return this.reserveInfo;
	}
	/**
	 * 加载库存信息表格列表
	 * @return
	 */
	public String list(){
		List<Reserve> reserves = this.reserveManager.list(this.reserveInfo.getStart(), this.reserveInfo.getLimit());
		long rowCount = this.reserveManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.reserveManager.getJsonStr(reserves)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加库存信息
	 * @return
	 */
	public String save(){
		
		Reserve reserve = new Reserve();
		reserve.setGoodsName(this.reserveInfo.getGoodsName());
		reserve.setPrice(this.reserveInfo.getPrice());
		reserve.setAmount(this.reserveInfo.getAmount());
		reserve.setRemainder(this.reserveInfo.getAmount());
		reserve.setTotal(this.reserveInfo.getTotal());
		reserveManager.save(reserve);
		this.jsonString = "{success:true,msg:'库存信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新库存信息
	 * @return
	 */
	public String update(){
		
		Reserve reserve = this.reserveManager.get(this.reserveInfo.getId());
		reserve.setGoodsName(this.reserveInfo.getGoodsName());
		reserve.setPrice(this.reserveInfo.getPrice());
		reserve.setAmount(this.reserveInfo.getAmount());
		//reserve.setRemainder(this.reserveInfo.getRemainder());
		//reserve.setTotal(this.reserveInfo.getTotal());
		reserveManager.update(reserve);
		this.jsonString = "{success:true,msg:'库存信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除库存信息
	 * @return
	 */
	public String delete(){
		this.reserveManager.delete(this.reserveInfo.getIds());
		this.jsonString = "{success:true,msg:'库存信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载库存信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.reserveManager.load();
		
		return "load";
	}
}
