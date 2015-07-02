package pams.vo;

import java.util.Date;

public class Return_GoodsInfo extends BaseInfo{
	private Integer id;
	//供应商全称
	private String fullName;
	//联系人
	private String linkmanName;
	//经手人
	private String handlerName;
	//退幼苗时间
	private Date date;
	//幼苗名称
	private String seedingName;
	//幼苗产地
	private String field;
	//单价
	private double price;
	//数量
	private double amount;
	//总价
	private double total;
	//原因描述
	private String des;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSeedingName() {
		return seedingName;
	}
	public void setSeedingName(String seedingName) {
		this.seedingName = seedingName;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	

}
