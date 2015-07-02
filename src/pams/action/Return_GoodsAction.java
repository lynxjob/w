package pams.action;

import java.util.List;

import javax.annotation.Resource;

import pams.model.Return_Goods;
import pams.model.Stock;
import pams.service.Impl.Return_GoodsManager;
import pams.vo.Return_GoodsInfo;

import com.opensymphony.xwork2.ModelDriven;

public class Return_GoodsAction extends BaseAction implements ModelDriven<Return_GoodsInfo>{
	private static final long serialVersionUID = 1L;
	
	private Return_GoodsInfo return_GoodsInfo = new Return_GoodsInfo();
	private Return_GoodsManager return_GoodsManager;
	public Return_GoodsInfo getReturn_GoodsInfo() {
		return return_GoodsInfo;
	}

	public void setReturn_GoodsInfo(Return_GoodsInfo return_GoodsInfo) {
		this.return_GoodsInfo = return_GoodsInfo;
	}
	
	public Return_GoodsManager getReturn_GoodsManager() {
		return return_GoodsManager;
	}
	@Resource(name="return_GoodsManager")
	public void setReturn_GoodsManager(Return_GoodsManager return_GoodsManager) {
		this.return_GoodsManager = return_GoodsManager;
	}
	@Override
	public Return_GoodsInfo getModel() {
		// TODO Auto-generated method stub
		return this.return_GoodsInfo;
	}
	/**
	 * 加载退幼苗账单信息表格列表
	 * @return
	 */
	public String list(){
		List<Return_Goods> return_Goods = this.return_GoodsManager.list(this.return_GoodsInfo.getStart(), this.return_GoodsInfo.getLimit());
		long rowCount = this.return_GoodsManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.return_GoodsManager.getJsonStr(return_Goods)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加退幼苗账单信息
	 * @return
	 */
	public String save(){
		
		Return_Goods return_Good = new Return_Goods();
		return_Good.setFullName(this.return_GoodsInfo.getFullName());
		return_Good.setSeedingName(this.return_GoodsInfo.getSeedingName());
		return_Good.setField(this.return_GoodsInfo.getField());
		return_Good.setPrice(this.return_GoodsInfo.getPrice());
		return_Good.setAmount(this.return_GoodsInfo.getAmount());
		return_Good.setHandlerName(this.return_GoodsInfo.getHandlerName());
		return_Good.setTotal(this.return_GoodsInfo.getPrice()*this.return_GoodsInfo.getAmount());
		return_Good.setLinkmanName(this.return_GoodsInfo.getLinkmanName());
		return_Good.setDate(this.return_GoodsInfo.getDate());
		return_Good.setDes(this.return_GoodsInfo.getDes());
		return_GoodsManager.save(return_Good);
		this.jsonString = "{success:true,msg:'退幼苗账单信息添加成功!'}";
		
		return "save";
	}
	/**
	 * 更新退幼苗账单信息
	 * @return
	 */
	public String update(){
		
		Return_Goods return_Good = this.return_GoodsManager.get(this.return_GoodsInfo.getId());
		return_Good.setFullName(this.return_GoodsInfo.getFullName());
		return_Good.setSeedingName(this.return_GoodsInfo.getSeedingName());
		return_Good.setField(this.return_GoodsInfo.getField());
		return_Good.setPrice(this.return_GoodsInfo.getPrice());
		return_Good.setAmount(this.return_GoodsInfo.getAmount());
		return_Good.setHandlerName(this.return_GoodsInfo.getHandlerName());
		return_Good.setTotal(this.return_GoodsInfo.getPrice()*this.return_GoodsInfo.getAmount());
		return_Good.setLinkmanName(this.return_GoodsInfo.getLinkmanName());
		return_Good.setDate(this.return_GoodsInfo.getDate());
		return_Good.setDes(this.return_GoodsInfo.getDes());
		return_GoodsManager.update(return_Good);
		this.jsonString = "{success:true,msg:'退幼苗账单信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除退幼苗账单信息
	 * @return
	 */
	public String delete(){
		this.return_GoodsManager.delete(this.return_GoodsInfo.getIds());
		this.jsonString = "{success:true,msg:'退幼苗账单信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载退幼苗账单信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.return_GoodsManager.load();
		
		return "load";
	}
}
