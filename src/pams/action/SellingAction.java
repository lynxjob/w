package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.Reserve;
import pams.model.Selling;
import pams.service.Impl.ReserveManager;
import pams.service.Impl.SellingManager;
import pams.vo.ReserveInfo;
import pams.vo.SellingInfo;

import com.opensymphony.xwork2.ModelDriven;

public class SellingAction extends BaseAction implements ModelDriven<SellingInfo>{

	private static final long serialVersionUID = 1L;
	
	private SellingInfo sellingInfo = new SellingInfo();
	private ReserveInfo reserveInfo = new ReserveInfo();
	private ReserveManager reserveManager;
	private SellingManager sellingManager;
	public ReserveInfo getReserveInfo() {
		return reserveInfo;
	}
	public ReserveManager getReserveManager() {
		return reserveManager;
	}

	public void setReserveManager(ReserveManager reserveManager) {
		this.reserveManager = reserveManager;
	}
	
	public SellingInfo getSellingInfo() {
		return sellingInfo;
	}

	public void setSellingInfo(SellingInfo sellingInfo) {
		this.sellingInfo = sellingInfo;
	}
	
	public SellingManager getSellingManager() {
		return sellingManager;
	}
	@Resource(name="sellingManager")
	public void setSellingManager(SellingManager sellingManager) {
		this.sellingManager = sellingManager;
	}
	@Override
	public SellingInfo getModel() {
		// TODO Auto-generated method stub
		return this.sellingInfo;
	}
	/**
	 * 加载销售信息表格列表
	 * @return
	 */
	public String list(){
		List<Selling> sellings = this.sellingManager.list(this.sellingInfo.getStart(), this.sellingInfo.getLimit());
		long rowCount = this.sellingManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.sellingManager.getJsonStr(sellings)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加销售信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String save(){
		
		Selling selling = new Selling();
		selling.setFullName(this.sellingInfo.getFullName());
		selling.setGoodsName(this.sellingInfo.getGoodsName());
		selling.setPrice(this.sellingInfo.getPrice());
		selling.setAmount(this.sellingInfo.getAmount());
		selling.setHandlerName(this.sellingInfo.getHandlerName());
		selling.setTotal(this.sellingInfo.getPrice()*this.sellingInfo.getAmount());
		selling.setLinkmanName(this.sellingInfo.getLinkmanName());
		selling.setDate(this.sellingInfo.getDate());
		List<String> names = this.reserveManager.getName();
		System.out.println("库存："+names);
		String name = selling.getGoodsName();
		System.out.println("商品名称1："+name);
		Reserve reserve = this.reserveManager.get(this.reserveManager.getId(name));
			if(names.contains(name))
			{
				if(this.reserveManager.getRemainder(name)>=this.sellingInfo.getAmount())
				{
				reserve.setRemainder(this.reserveManager.getRemainder(name)-this.sellingInfo.getAmount());
				reserve.setTotal(this.reserveManager.getTotal(name)+this.sellingInfo.getAmount()*this.sellingInfo.getPrice());
				this.reserveManager.update(reserve);
				}
				else{
					this.jsonString = "{false:true,msg:'库存不足!'}";
					System.exit(0);
				}
			}else{
				this.jsonString = "{false:true,msg:'库存中不存在此商品!'}";
				System.exit(0);
			}
		sellingManager.save(selling);
		this.jsonString = "{success:true,msg:'销售信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新销售信息
	 * @return
	 */
	public String update(){
		
		Selling selling = this.sellingManager.get(this.sellingInfo.getId());
		selling.setFullName(this.sellingInfo.getFullName());
		selling.setGoodsName(this.sellingInfo.getGoodsName());
		selling.setPrice(this.sellingInfo.getPrice());
		selling.setAmount(this.sellingInfo.getAmount());
		selling.setHandlerName(this.sellingInfo.getHandlerName());
		selling.setTotal(this.sellingInfo.getPrice()*this.sellingInfo.getAmount());
		selling.setLinkmanName(this.sellingInfo.getLinkmanName());
		selling.setDate(this.sellingInfo.getDate());
		sellingManager.update(selling);
		this.jsonString = "{success:true,msg:'销售信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除销售信息
	 * @return
	 */
	public String delete(){
		this.sellingManager.delete(this.sellingInfo.getIds());
		this.jsonString = "{success:true,msg:'销售信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载销售信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.sellingManager.load();
		
		return "load";
	}
}
