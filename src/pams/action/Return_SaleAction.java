package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.Reserve;
import pams.model.Return_Sale;
import pams.service.Impl.ReserveManager;
import pams.service.Impl.Return_SaleManager;
import pams.vo.ReserveInfo;
import pams.vo.Return_SaleInfo;

import com.opensymphony.xwork2.ModelDriven;

public class Return_SaleAction extends BaseAction implements ModelDriven<Return_SaleInfo>{

	private static final long serialVersionUID = 1L;
	
	private Return_SaleInfo return_SaleInfo = new Return_SaleInfo();
	private Return_SaleManager return_SaleManager;
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

	public void setReserveManager(ReserveManager reserveManager) {
		this.reserveManager = reserveManager;
	}

	public Return_SaleInfo getReturn_SaleInfo() {
		return return_SaleInfo;
	}

	public void setReturn_SaleInfo(Return_SaleInfo return_SaleInfo) {
		this.return_SaleInfo = return_SaleInfo;
	}
	
	public Return_SaleManager getReturn_SaleManager() {
		return return_SaleManager;
	}
	@Resource(name="return_SaleManager")
	public void setReturn_SaleManager(Return_SaleManager return_SaleManager) {
		this.return_SaleManager = return_SaleManager;
	}
	public Return_SaleInfo getModel() {
		// TODO Auto-generated method stub
		return this.return_SaleInfo;
	}
	/**
	 * 加载退销售账单信息表格列表
	 * @return
	 */
	public String list(){
		List<Return_Sale> return_Sales = this.return_SaleManager.list(this.return_SaleInfo.getStart(), this.return_SaleInfo.getLimit());
		long rowCount = this.return_SaleManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.return_SaleManager.getJsonStr(return_Sales)+"}";
		System.out.println(jsonString);
		return "list";
	}
	
	/**
	 * 添加退销售账单信息
	 * @return
	 */
	public String save(){
		
		Return_Sale return_Sale = new Return_Sale();
		return_Sale.setFullName(this.return_SaleInfo.getFullName());
		return_Sale.setGoodsName(this.return_SaleInfo.getGoodsName());
		return_Sale.setPrice(this.return_SaleInfo.getPrice());
		return_Sale.setAmount(this.return_SaleInfo.getAmount());
		return_Sale.setHandlerName(this.return_SaleInfo.getHandlerName());
		return_Sale.setTotal(this.return_SaleInfo.getPrice()*this.return_SaleInfo.getAmount());
		return_Sale.setLinkmanName(this.return_SaleInfo.getLinkmanName());
		return_Sale.setDate(this.return_SaleInfo.getDate());
		return_Sale.setDes(this.return_SaleInfo.getDes());
		List<String> names = this.reserveManager.getName();
		System.out.println("库存："+names);
		String name = return_Sale.getGoodsName();
		System.out.println("商品名称1："+name);
		Reserve reserve = this.reserveManager.get(this.reserveManager.getId(name));
			if(names.contains(name))
			{
				reserve.setRemainder(this.reserveManager.getRemainder(name)+this.return_SaleInfo.getAmount());
				reserve.setTotal(this.reserveManager.getTotal(name)-this.return_SaleInfo.getAmount()*this.return_SaleInfo.getPrice());
				this.reserveManager.update(reserve);
			}
			return_SaleManager.save(return_Sale);
			this.jsonString = "{success:true,msg:'退销售账单信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新退销售账单信息
	 * @return
	 */
	public String update(){
		
		Return_Sale return_Sale = this.return_SaleManager.get(this.return_SaleInfo.getId());
		return_Sale.setFullName(this.return_SaleInfo.getFullName());
		return_Sale.setGoodsName(this.return_SaleInfo.getGoodsName());
		return_Sale.setPrice(this.return_SaleInfo.getPrice());
		return_Sale.setAmount(this.return_SaleInfo.getAmount());
		return_Sale.setHandlerName(this.return_SaleInfo.getHandlerName());
		return_Sale.setTotal(this.return_SaleInfo.getPrice()*this.return_SaleInfo.getAmount());
		return_Sale.setLinkmanName(this.return_SaleInfo.getLinkmanName());
		return_Sale.setDate(this.return_SaleInfo.getDate());
		return_Sale.setDes(this.return_SaleInfo.getDes());
		return_SaleManager.update(return_Sale);
		this.jsonString = "{success:true,msg:'退销售账单信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除退销售账单信息
	 * @return
	 */
	public String delete(){
		this.return_SaleManager.delete(this.return_SaleInfo.getIds());
		this.jsonString = "{success:true,msg:'退销售账单信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载退销售账单信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.return_SaleManager.load();
		
		return "load";
	}
}
