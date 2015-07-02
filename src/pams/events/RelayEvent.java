package pams.events;

import java.util.Hashtable;

/**
 * 继电器事件(修改过json数据)->resolveData()
 * @author 恶灵骑士
 * @see lynx 
 */
public class RelayEvent extends AbstractEvent {
	
	private String type;		//命令类型
	
	private byte lRouter = 0;	//低8路
	
	private byte hRouter = 0;	//高8路
	
	public String num;			//继电器路数
	
	public String status;		//状态标志
	
	public static Hashtable<String, ParamPair> pairs = new Hashtable<String, ParamPair>();
	
	public RelayEvent(String type, String num, String status)
	{
		this.type = type;
		this.num = num;
		this.status = status;
		//realControl实时控制
		if("realControl".equals(type))
		{
			this.setRLen((byte)4);
		} else if("readIO".equals(type)) //readIO读取IO输入
		{
			this.setRLen((byte)5);  //读取温湿度
		}
		
		ParamPair pp;
		pp = new ParamPair();
		//实时控制下发命令起始码和结束码
		pp.setName("realControl");
		pp.setStart(0x10);
		pp.setEnd(0x50);
		//实时控制回送命令起始码和结束码
		pp.setRtStart(17);
		pp.setRtEnd(81);
		pairs.put("realControl", pp);
		pp = new ParamPair();
		//读取IO输入下发命令起始码和结束码
		pp.setName("readIO");
		pp.setStart(35);
		pp.setEnd(99);
		//读取IO输入回送命令起始码和结束码
		pp.setRtStart(36);
		pp.setRtEnd(100);
		pairs.put("readIO", pp);
		pp = new ParamPair();
		//读取温湿度下发命令起始码和结束码
		pp.setName("readT");
		pp.setStart(37);
		pp.setEnd(101);
		//读取温度回送命令起始码和结束码
		pp.setRtStart(38);
		pp.setRtEnd(102);
		pairs.put("readT", pp);
		//开启标志0(开启)
		pp = new ParamPair();
		pp.setName("open");
		pp.setStart(00);
		pairs.put("open", pp);
		//关闭标志(1关闭)
		pp = new ParamPair();
		pp.setName("close");
		pp.setStart(255);
		pairs.put("close", pp);
	}
	
	/**
	 * 获取发送命令
	 * */
	public byte[] getCommd()
	{
		byte[] bytes = new byte[5];
		//16路继电器,每路控制一种设备
		int op = 1;
		byte lRouter = 0;	//低8路
		byte hRouter = 0;	//高8路
		if("realControl".equals(type))
		{
			int bits = 0;
			int id = Integer.valueOf(num);
			//低8位
			if(id<9 && id>=0)
			{
				bits = id-1;
				op = (op << bits);
				if("0".equals(status))
				{	//开启串口
					op = ~ op;
					hRouter = -1;
				} else{
					hRouter = 0;
				}
				lRouter = (byte)op;
			} else if(9<=id && id <17)//高8位
			{
				bits = id-9;
				op = (op << bits);
				if("0".equals(status))
				{	//开启串口
					op = ~ op;
					lRouter = -1;
				} else{
					lRouter = 0;
				}
				hRouter = (byte)op;
			}
			this.lRouter = lRouter;
			this.hRouter = hRouter;
			
			ParamPair pp = pairs.get("realControl");
			bytes[0] = (byte)pp.getStart();
			//D16~D9
			bytes[1] = hRouter;
			//D8~D0
			bytes[2] = lRouter;
			bytes[4] = (byte)pp.getEnd();
			if("0".equals(status))
			{
				pp = pairs.get("open");//00
			} else {
				pp = pairs.get("close");//255
			}
			//status:open?close?
			bytes[3] = (byte)pp.getStart();//status
		}
		else if("readIO".equals(type))
		{
			ParamPair pp = pairs.get("readIO");
			bytes[0] = (byte)pp.getStart();
			bytes[1] = (byte)pp.getEnd();
		}
		
		return bytes;
	}
	
	
	/**
	 * 解析返回的串口数据
	 * @param rResult 	返回的串口数据
	 * */
	public String resolveData(byte[] rResult) {
		if(rResult == null) return null;
		String rs = null;
		StringBuffer sb = new StringBuffer();
		if("realControl".equals(this.type))
		{
			//验证数据接收是否成功
			if(this.hRouter == (byte)rResult[1] && this.lRouter == (byte)rResult[2])
			{
				rs = sb.append("{rs:'success'").toString();
			}else{
				rs = sb.append("{rs:'fail'}").toString();
			}
			//产生数据为:{"rs":"success"}或{"rs":"fail"}
		}else if("readIO".equals(this.type))
		{
			rs = sb.append("{lRouter:").append(rResult[2])
			       .append(",hRouter:").append(rResult[1])
			       .toString();					      
		}
		
		return rs;
	}

}
