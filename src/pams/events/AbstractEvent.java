package pams.events;
/**
 * 
 * @author lynx	
 * @msg.通信发送信息和接受信息的相关。
 *
 */
public abstract class AbstractEvent {
	
	//单字节读取时,数据采集卡返回数据所需字节数
	public static byte singleLen = 7;
	//多字节读取时,数据采集卡返回数据所需字节数(11个寄存器)
	public static byte allLen = 27;
	//读取历史数据时,数据采集卡返回数据所需字节数
	public static byte hisLen = 37;
	//数据采集卡返回数据实际字节数
	private byte rLen;	//获取数据的长度
	
	//获取发送命令
	public abstract byte[] getCommd();
	
	/*
	 * 解析返回的串口数据
	 * @param rResult 返回的串口数据
	 * */
	public abstract Object resolveData(byte[] rResult);

	public byte getRLen() {
		return rLen;
	}

	public void setRLen(byte len) {
		rLen = len;
	}
	
}
