package pams.vo;

public class LedInfo extends BaseInfo{
	private int shed_id;
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
	public int getShed_id() {
		return shed_id;
	}
	public void setShed_id(int shedId) {
		shed_id = shedId;
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
	public Integer getStart() {
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
