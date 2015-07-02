package pams.vo;

public class ReserveInfo extends BaseInfo{

	private Integer id;
	//作物名称
	private String goodsName;
	//产量
	private double amount;
	//单价
	private double price;
	//剩余数量=总数量-销售数量
	private double remainder;
	//总收入=总销售数量*单价
	private double total;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRemainder() {
		return remainder;
	}
	public void setRemainder(double remainder) {
		this.remainder = remainder;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
