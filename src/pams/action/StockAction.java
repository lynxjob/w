package pams.action;

import java.util.List;

import javax.annotation.Resource;
import pams.model.Stock;
import pams.service.Impl.StockManager;
import pams.vo.StockInfo;

import com.opensymphony.xwork2.ModelDriven;

public class StockAction extends BaseAction implements ModelDriven<StockInfo>{

	private static final long serialVersionUID = 1L;
	
	private StockInfo stockInfo = new StockInfo();
	private StockManager stockManager;
	public StockInfo getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(StockInfo StockInfo) {
		this.stockInfo = StockInfo;
	}
	
	public StockManager getStockManager() {
		return stockManager;
	}
	@Resource(name="stockManager")
	public void setStockManager(StockManager stockManager) {
		this.stockManager = stockManager;
	}
	@Override
	public StockInfo getModel() {
		// TODO Auto-generated method stub
		return this.stockInfo;
	}
	/**
	 * 加载进幼苗账单信息表格列表
	 * @return
	 */
	public String list(){
		List<Stock> stocks = this.stockManager.list(this.stockInfo.getStart(), this.stockInfo.getLimit());
		long rowCount = this.stockManager.getTotal();
		this.jsonString = "{rowCount:"+rowCount+",result:"+this.stockManager.getJsonStr(stocks)+"}";
		System.out.println(jsonString);
		return "list";
	}
	/**
	 * 添加进幼苗账单信息
	 * @return
	 */
	public String save(){
		
		Stock stock = new Stock();
		stock.setFullName(this.stockInfo.getFullName());
		stock.setSeedingName(this.stockInfo.getSeedingName());
		stock.setField(this.stockInfo.getField());
		stock.setPrice(this.stockInfo.getPrice());
		stock.setAmount(this.stockInfo.getAmount());
		stock.setHandlerName(this.stockInfo.getHandlerName());
		stock.setTotal(this.stockInfo.getPrice()*this.stockInfo.getAmount());
		stock.setLinkmanName(this.stockInfo.getLinkmanName());
		stock.setDate(this.stockInfo.getDate());
		stockManager.save(stock);
		this.jsonString = "{success:true,msg:'进幼苗账单信息添加成功!'}";
		
		return "save";
		
	}
	/**
	 * 更新进幼苗账单信息
	 * @return
	 */
	public String update(){
		
		Stock stock = this.stockManager.get(this.stockInfo.getId());
		stock.setFullName(this.stockInfo.getFullName());
		stock.setSeedingName(this.stockInfo.getSeedingName());
		stock.setField(this.stockInfo.getField());
		stock.setPrice(this.stockInfo.getPrice());
		stock.setAmount(this.stockInfo.getAmount());
		stock.setHandlerName(this.stockInfo.getHandlerName());
		stock.setTotal(this.stockInfo.getPrice()*this.stockInfo.getAmount());
		stock.setLinkmanName(this.stockInfo.getLinkmanName());
		stock.setDate(this.stockInfo.getDate());
		stockManager.update(stock);
		this.jsonString = "{success:true,msg:'进幼苗账单信息更新成功!'}";
		
		return "update";
	}
	/**
	 * 删除进幼苗账单信息
	 * @return
	 */
	public String delete(){
		this.stockManager.delete(this.stockInfo.getIds());
		this.jsonString = "{success:true,msg:'进幼苗账单信息删除成功!'}";
		
		return "delete";
	}
	/**
	 * 加载进幼苗账单信息
	 * @return
	 */
	public String load(){
		this.jsonString = this.stockManager.load();
		
		return "load";
	}
}
