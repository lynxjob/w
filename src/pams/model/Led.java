package pams.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Led implements Serializable{
	
	private int id;
	private int pinghao;//屏号
	private String IP;//IP地址
	private int pWidth;//屏宽
	private int pHeight;//屏高
	private int color;   //单双色(单色为1 ，双色为2)
	private int start;//起点x
	private int end;//起点y
	private int width;//宽度
	private int height;//高度
	private String sendText;//发送数据
	private Shed shed;
	private int sataus;
	
	
	@OneToOne
	@JoinColumn(name="sheId")
	public Shed getShed() {
		return shed;
	}
	public void setShed(Shed shed) {
		this.shed = shed;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPinghao() {
		return pinghao;
	}
	public void setPinghao(int pinghao) {
		this.pinghao = pinghao;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getpWidth() {
		return pWidth;
	}
	public void setpWidth(int pWidth) {
		this.pWidth = pWidth;
	}
	public int getpHeight() {
		return pHeight;
	}
	public void setpHeight(int pHeight) {
		this.pHeight = pHeight;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getSendText() {
		return sendText;
	}
	public void setSendText(String sendText) {
		this.sendText = sendText;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	

}
