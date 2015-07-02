package pams.events;

import java.util.Date;

import pams.model.DaqData;
import pams.util.CalcCRC;
/**
 * 数据采集仪事件
 * @author 恶灵骑士
 *
 */
public class DaqEvent extends AbstractEvent {
	
	public static byte[] crcs = {49, 83, 96, -109, -112, -109, -63, 83, 112, -110, 33, 82, -47, 82, -128, -110, -80, -111, -31, 81, 17, 81, 64, -111, -15, 80, -96, -112, -79, 84, 0x7c, (byte)0x9b};
	
	private boolean isSingle;
	//设备地址
	private static final byte address = 0x08;
	//操作码
	private byte opCode = 0x04;
	//首个寄存器高8位地址
	private byte hRgstAddress;
	//首个寄存器低8位地址
	private byte lRgstAddress;
	//寄存器数量高8位地址
	private byte hRgstCount;
	//寄存器数量低8位地址
	private byte lRgstCount;
	//CRC校验码高位地址
	private byte hCRC16;
	//CRC校验码低位地址
	private byte lCRC16;
	
	public DaqEvent(int router) {
		if(router == 12)
		{
			/*设置读取历史数据命令*/
			this.setOpCode((byte)0x41);
			this.setHRgstAddress(0x00);
			this.setLRgstAddress(0x00);
			this.setHRgstCount(0x00);
			this.setLRgstCount(0x0B);
			this.setHCRC16(DaqEvent.crcs[30]);
			this.setLCRC16(DaqEvent.crcs[31]);
			this.setRLen(AbstractEvent.hisLen);
		}else if(router == 11)
		{
			/*设置数据采集卡多字节读取命令*/
			this.setOpCode((byte)0x04);
			this.setHRgstAddress(0x00);
			this.setLRgstAddress(0x00);
			this.setHRgstCount(0x00);
			this.setLRgstCount(0x0B);
			this.setHCRC16(DaqEvent.crcs[28]);
			this.setLCRC16(DaqEvent.crcs[29]);
			this.setRLen(AbstractEvent.allLen);
		}else {
			/*设置数据采集卡单字节读取命令*/
			this.setOpCode((byte)0x04);
			this.setHRgstAddress(0x00);
			this.setLRgstAddress(router);
			this.setHRgstCount(0x00);
			this.setLRgstCount(0x01);
			this.setHCRC16(DaqEvent.crcs[2*router]);
			this.setLCRC16(DaqEvent.crcs[2*router+1]);
			this.setRLen(AbstractEvent.singleLen);
		}
	}
	
	/**
	 * 获取发送命令,get
	 */
	public byte[] getCommd()
	{
		//操作命令
		byte[] bytes = new byte[8];
		//设备地址
		bytes[0] = this.getAddress();
		//操作码
		bytes[1] = this.getOpCode();
		//首寄存器高位地址
		bytes[2] = this.getHRgstAddress();
		//首寄存器地位地址
		bytes[3] = this.getLRgstAddress();
		//Register数量寄存器高位地址
		bytes[4] = this.getHRgstCount();
		//Register数量寄存器低位地址
		bytes[5] = this.getLRgstCount();
		//CRC校验高位地址
		bytes[6] = this.getHCRC16();
		//CRC校验低位地址
		bytes[7] = this.getLCRC16();
		
		return bytes;	
	}
	
	/**
	 * 解析接收数据 (注:CRC校验是通信双方的责任.一方想让另一方知道传送的数据是否正确,这方就要
	 * 把数据的CRC计算出来并放于数据后面,以待对方检查,同样另一方也要这样,计算出自己要传送数据
	 * 的CRC附于数据后以待对方检查)
	 */
	public  DaqData resolveData (byte[] bytes)
	{
		
		if(null == bytes)
			return null;
		//对接收数据进行校验
		int blen = bytes.length;
		//操作命令数据部分,不包括CRC校验
		byte[] preBytes = new byte[blen-2];
		for(int i=0; i<preBytes.length; i++)
		{ preBytes[i] = bytes[i];
		}
		//CRC校验结果
		//byte[] crc16 = CalcCRC.caluCRC(preBytes);
		
		//数据采集卡所有数据项
		DaqData daq = new DaqData();
		//设置演示日期
		daq.setSampleDate(new Date());
		System.out.println("长度："+blen);
		//CRC校验不符合,丢弃(daq为NULL)
		//if(crc16[0] != bytes[blen-2] || crc16[1] != bytes[blen-1])
			//return null;
		
		//记录采集卡数据项数据(除去从机地址,功能代码,字节数,CRC校验位高低位5字节,一个int为2个字节)
		int[] rsdData = new int[(blen-5)/2];
		int j;//j标识非传感器所占位数,读非历史数据时占用3为(前3位),读历史数据时占用9位(包含日期6位)
		//读取实时数据
		if(this.getOpCode() == 4)
		{
			j = 3;
		} else {
			j = 9;
		}
		for(int k = blen-j-2, m = 0; j<=k; j+=2, m++)
		{
			int hByte = (bytes[j]&0x00FF) << 8;
			int lByte = bytes[j+1]&0x00FF;
			if(hByte >= 0)
			{
				hByte += lByte;
			}else{
				hByte -= lByte;
			}
			rsdData[m] = hByte;
		
		}
	
		switch(this.getLRgstAddress()) {
			case DaqData.ZHENGFA:
				daq.setZhengfa(rsdData[0]*DaqData.UVALUE[DaqData.ZHENGFA]);
				//查询所有数据
				if(this.getLRgstCount() != 1)
				{
					daq.setDaqishidu(rsdData[1]*DaqData.UVALUE[DaqData.DAQISHIDU]);
					daq.setDaqiyali(rsdData[2]*DaqData.UVALUE[DaqData.DAQIYALI]);
					daq.setGuanghe(rsdData[3]*DaqData.UVALUE[DaqData.GUANGHE]);
					daq.setTurangshuifen(rsdData[4]*DaqData.UVALUE[DaqData.TURANGSHUIFEN]);
					daq.setCoc(rsdData[5]*DaqData.UVALUE[DaqData.COC]);
					daq.setFengsu(rsdData[6]*DaqData.UVALUE[DaqData.FENGSU]);
					daq.setFengli(getWindLevel(daq.getFengsu()));
					daq.setFengxiang(rsdData[7]*DaqData.UVALUE[DaqData.FENGXIANG]);
					daq.setSfengxiang(getWindDirect(daq.getFengxiang()));
					daq.setDaqiwendu(rsdData[8]*DaqData.UVALUE[DaqData.DAQIWENDU]);
					daq.setDadiwendu(rsdData[9]*DaqData.UVALUE[DaqData.DADIWENDU]);
					daq.setJiangyu(rsdData[10]*DaqData.UVALUE[DaqData.JIANGYU]);
				} else if(this.getOpCode() == 65){
					//带时间的查询 处理时间
					
				}
				break;
			case DaqData.DAQISHIDU:
				daq.setDaqishidu(rsdData[1]*DaqData.UVALUE[DaqData.DAQISHIDU]);
				break;
			case DaqData.DAQIYALI:
				daq.setDaqiyali(rsdData[2]*DaqData.UVALUE[DaqData.DAQIYALI]);
				break;
			case DaqData.GUANGHE:
				daq.setGuanghe(rsdData[3]*DaqData.UVALUE[DaqData.GUANGHE]);
				break;
			case DaqData.TURANGSHUIFEN:
				daq.setTurangshuifen(rsdData[4]*DaqData.UVALUE[DaqData.TURANGSHUIFEN]);
				break;
			case DaqData.COC:
				daq.setCoc(rsdData[5]*DaqData.UVALUE[DaqData.COC]);
				break;
			case DaqData.FENGSU:
				daq.setFengsu(rsdData[6]*DaqData.UVALUE[DaqData.FENGSU]);
				daq.setFengli(getWindLevel(daq.getFengsu()));
				break;
			case DaqData.FENGXIANG:
				daq.setFengxiang(rsdData[7]*DaqData.UVALUE[DaqData.FENGXIANG]);
				break;
			case DaqData.DAQIWENDU:
				daq.setDaqiwendu(rsdData[8]*DaqData.UVALUE[DaqData.DAQIWENDU]);
				break;
			case DaqData.DADIWENDU:
				daq.setDadiwendu(rsdData[9]*DaqData.UVALUE[DaqData.DADIWENDU]);
				break;
			case DaqData.JIANGYU:
				daq.setJiangyu(rsdData[10]*DaqData.UVALUE[DaqData.JIANGYU]);
				break;
		}	
		return daq;
	}
	
	
	public static String getWindDirect(float fengxiang)
	{	//判断风向
		if(fengxiang>=0 && fengxiang < 360f)
		{
			if(fengxiang < 11.25f || fengxiang > 348.75f)
			{
				return "北风";
			} else if(fengxiang < 33.75f)
			{
				return "北风偏东";
			} else if(fengxiang < 56.25f)
			{
				return "东北风";
			} else if(fengxiang < 78.75f)
			{
				return "东风偏北";
			} else if(fengxiang < 101.25f)
			{
				return "东风";
			} else if(fengxiang < 123.75f)
			{
				return "东风偏南";
			} else if(fengxiang < 146.25f)
			{
				return "东南风";
			} else if(fengxiang < 168.75f)
			{
				return "南风偏东";
			} else if(fengxiang < 191.25f)
			{
				return "南风";
			} else if(fengxiang < 213.75f)
			{
				return "南风偏西";
			} else if(fengxiang <= 236.25f)
			{
				return "西南风";
			} else if(fengxiang > 258.75f)
			{
				return "西风偏南";
			} else if(fengxiang < 281.25f)
			{
				return "西风";
			} else if(fengxiang < 303.75f)
			{
				return "西风偏北";
			} else if(fengxiang <= 326.25f)
			{
				return "西北风";
			} else if(fengxiang > 348.75f)
			{
				return "北风偏西";
			}else
			{
				return "风向未知...";
			}
		}else 
		{
			return "param error";
			}
	}
	
	public static String getWindLevel(float fengsu)
	{	//判断风级
		if(fengsu>=0)
		{
			if(fengsu < 0.3f)
			{
				return "无风(0级)";
			} else if(fengsu < 1.6f)
			{
				return "软风(1级)";
			} else if(fengsu < 3.4f)
			{
				return "软风(2级)";
			} else if(fengsu < 5.5f)
			{
				return "微风(3级)";
			} else if(fengsu < 8.0f)
			{
				return "和风(4级)";
			} else if(fengsu < 10.8f)
			{
				return "清劲(5级)";
			} else if(fengsu < 13.9f)
			{
				return "强风(6级)";
			} else if(fengsu < 17.2f)
			{
				return "疾风(7级)";
			} else if(fengsu < 20.8f)
			{
				return "大风(8级)";
			} else if(fengsu < 24.5f)
			{
				return "烈风(9级)";
			} else if(fengsu <= 28.4f)
			{
				return "狂风(10级)";
			} else if(fengsu > 28.4)
			{
				return "暴风飓风(11-12级)";
			} else
			{
				return "风级未知...";
			}
		}else 
		{
			return "param error";
		}
		
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public byte getAddress() {
		return address;
	}

	public byte getOpCode() {
		return opCode;
	}

	public byte getHRgstAddress() {
		return hRgstAddress;
	}

	public void setHRgstAddress(int rgstAddress) {
		hRgstAddress = (byte)rgstAddress;
	}

	public byte getLRgstAddress() {
		return lRgstAddress;
	}

	public void setLRgstAddress(int rgstAddress) {
		lRgstAddress = (byte)rgstAddress;
	}

	public byte getHRgstCount() {
		return hRgstCount;
	}

	public void setHRgstCount(int rgstCount) {
		hRgstCount = (byte)rgstCount;
	}

	public byte getLRgstCount() {
		return lRgstCount;
	}

	public void setLRgstCount(int rgstCount) {
		lRgstCount = (byte)rgstCount;
	}

	public byte getHCRC16() {
		return hCRC16;
	}

	public void setHCRC16(byte hcrc16) {
		hCRC16 = hcrc16;
	}

	public byte getLCRC16() {
		return lCRC16;
	}

	public void setLCRC16(byte lcrc16) {
		lCRC16 = lcrc16;
	}

	public void setOpCode(byte opCode) {
		this.opCode = opCode;
	}
	
	
}
