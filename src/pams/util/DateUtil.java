package pams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author cjl
 * 处理日期
 */
public class DateUtil 
{
	private static DateUtil dateUtil=new DateUtil();
	
	//日期+时间
	private SimpleDateFormat format1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//日期
	private SimpleDateFormat format2= new SimpleDateFormat("yyyy-MM-dd");
	//时分秒
	private SimpleDateFormat format3=new SimpleDateFormat("HH:mm:ss");
	//备份数据的命名需要
	private SimpleDateFormat format4=new SimpleDateFormat("yyyyMMddHHmmss");
	
	private DateUtil()
	{	
	}
	
	public static DateUtil getInstance()
	{
		return dateUtil;
	}
	
	/**
	 * 获取日期和时间
	 */
	public String getDateString1(Date date)
	{
		return format1.format(date);
	}
	
	/**
	 * 只获取日期
	 */
	public String getDateString2(Date date)
	{
		return format2.format(date);
	}
	/**
	 * 只获取时分秒
	 * @param date
	 * @return
	 */
	public String getDateString3(Date date){
		return format3.format(date);
	}
	/**
	 * 日期+时间 字符串转date
	 * @throws ParseException 
	 */
	public Date parseDate1(String dateString)
	{
		Date date=null;
		try
		{
			date=format1.parse(dateString);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 日期 字符串转date
	 * @throws ParseException 
	 */
	public Date parseDate2(String dateString)
	{
		Date date=null;
		try
		{
			date=format2.parse(dateString);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 根据当前时间获得备份文件名,20120330142104
	 */
	public String getFormateName()
	{
		return format4.format(new Date());
	}
	
	/**
	 * 根据文件名获取日期格式 20110120112233转为 2011年01月20日 11时22分33秒
	 */
	public String getFormatDate(String name) 
	{
		if (name == null || "".equals(name) || name.length() < 13) 
		{
			return "";
		}
		else 
		{
			return name.substring(0, 4) + "年" + name.substring(4, 6) + "月"
					+ name.substring(6, 8) + "日 " + name.substring(8, 10) + "时"
					+ name.substring(10, 12) + "分" + name.substring(12, 14)+ "秒";
		}
	}
}